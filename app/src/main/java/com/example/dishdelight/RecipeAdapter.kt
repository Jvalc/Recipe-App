package com.example.dishdelight

import android.app.AlertDialog
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat.getSystemService
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RecipeAdapter(
    private val userId: String,
    private val recipeList: List<Recipe>) :
    RecyclerView.Adapter<RecipeAdapter.RecipeViewHolder>() {
    class RecipeViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val recipeTitle : TextView = itemView.findViewById(R.id.recipeTitle)
        val cookingTimeValue: TextView = itemView.findViewById(R.id.cookingTimeValue)
        val servingsValue: TextView = itemView.findViewById(R.id.servingsValue)
        val recipeImage: ImageView = itemView.findViewById(R.id.recipeImage)
        val moreBtn : Button = itemView.findViewById(R.id.btnMore)
        val shareBtn : LinearLayout = itemView.findViewById(R.id.shareBtn)
        val saveBtn : LinearLayout = itemView.findViewById(R.id.saveBtn)
        val downloadBtn : LinearLayout = itemView.findViewById(R.id.downloadBtn)
        val ingredientsDropdown : ImageView = itemView.findViewById(R.id.ingredientsDropdown)
        val ingredientsValue : TextView = itemView.findViewById(R.id.ingredientsValue)
        val recipeDropdown : ImageView = itemView.findViewById(R.id.recipeDropdown)
        val recipeValue : TextView = itemView.findViewById(R.id.recipeValue)
        val notesDropdown : ImageView = itemView.findViewById(R.id.notesDropdown)
        val notesValue : TextView = itemView.findViewById(R.id.notesValue)

        val context: Context = itemView.context
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecipeViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.recipe_item, parent, false)
        return RecipeViewHolder(view)
    }

    override fun onBindViewHolder(holder: RecipeViewHolder, position: Int) {
        val recipe = recipeList[position]
        holder.recipeTitle.text = recipe.recipeName
        holder.cookingTimeValue.text = "${recipe.cookingTime}"
        holder.servingsValue.text = "${recipe.servingSize} people"
        holder.ingredientsValue.text = recipe.ingredients.joinToString("\n") { "• $it" }
        holder.recipeValue.text = recipe.steps
            .mapIndexed { index, step -> "${index + 1}. $step" }
            .joinToString("\n") { "• $it" }
        holder.notesValue.text = recipe.notes.joinToString("\n") { "• $it" }

        // Load recipe image (use Glide or any image loading library)
        Glide.with(holder.itemView.context)
            .load(recipe.imageUrl)
            .placeholder(R.drawable.ic_placeholder_image)
            .into(holder.recipeImage)

        holder.moreBtn.setOnClickListener {
            holder.shareBtn.visibility = View.VISIBLE
            holder.downloadBtn.visibility = View.VISIBLE
            holder.saveBtn.visibility = View.VISIBLE
        }

        holder.shareBtn.setOnClickListener{
            val recipeId = recipe.id // Get the recipe ID
            shareRecipe(holder.context, recipeId)
        }
        holder.saveBtn.setOnClickListener{
            val recipeId = recipe.id // Get the recipe ID
            promptFileNameAndSaveRecipe(userId, recipeId, holder.context)

        }

        holder.ingredientsDropdown.setOnClickListener {
            if (holder.ingredientsValue.visibility == View.VISIBLE) {
                holder.ingredientsValue.visibility = View.GONE
            } else {
                holder.ingredientsValue.visibility = View.VISIBLE
            }
        }
        holder.recipeDropdown.setOnClickListener {
            if (holder.recipeValue.visibility == View.VISIBLE) {
                holder.recipeValue.visibility = View.GONE
            } else {
                holder.recipeValue.visibility = View.VISIBLE
            }
        }
        holder.notesDropdown.setOnClickListener {
            if (holder.notesValue.visibility == View.VISIBLE) {
                holder.notesValue.visibility = View.GONE
            } else {
                holder.notesValue.visibility = View.VISIBLE
            }
        }
    }

    override fun getItemCount(): Int {
        return recipeList.size
    }
    private fun promptFileNameAndSaveRecipe(userId: String, recipeId: String, context: Context) {
        // Show an AlertDialog to prompt for file name
        val builder = AlertDialog.Builder(context)
        builder.setTitle("Enter the file name to save the recipe")

        // Set up the input field
        val input = android.widget.EditText(context)
        builder.setView(input)

        // Set up the dialog buttons
        builder.setPositiveButton("Save") { _, _ ->
            val category = input.text.toString()

            if (category.isNotEmpty()) {
                // Call the API and save the recipe
                saveRecipeToServer(userId, category, recipeId, context)
            }
        }
        builder.setNegativeButton("Cancel") { dialog, _ -> dialog.cancel() }
        builder.show()
    }
    private fun saveRecipeToServer(userId: String, category: String, recipeId: String, context: Context) {
        val request = RecipeSaveRequest(userId, category, recipeId)

        RetrofitClient.getClient().create(RecipeApiService::class.java)
            .saveRecipe(request).enqueue(object : Callback<SaveResponse> {
            override fun onResponse(call: Call<SaveResponse>, response: Response<SaveResponse>) {
                if (response.isSuccessful) {
                    Toast.makeText(context, "Recipe saved to clipboard!", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(context, "Error: ${response.errorBody()?.string()}", Toast.LENGTH_SHORT).show()
                }
            }
            override fun onFailure(call: Call<SaveResponse>, t: Throwable) {
                Toast.makeText(context, "Failed to share recipe: ${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }
    private fun shareRecipe(context: Context, recipeId: String) {
        val request = ShareRequest(recipeId)

        RetrofitClient.getClient().create(RecipeApiService::class.java)
            .shareRecipe(request).enqueue(object : Callback<ShareResponse> {
                override fun onResponse(call: Call<ShareResponse>, response: Response<ShareResponse>) {
                    if (response.isSuccessful && response.body() != null) {
                        val shareableText = response.body()!!.shareableText
                        saveToClipboard(context, shareableText) // Save to clipboard
                        Toast.makeText(context, "Recipe saved to clipboard!", Toast.LENGTH_SHORT).show()
                    } else {
                        Toast.makeText(context, "Error: ${response.errorBody()?.string()}", Toast.LENGTH_SHORT).show()
                    }
                }
                override fun onFailure(call: Call<ShareResponse>, t: Throwable) {
                    Toast.makeText(context, "Failed to share recipe: ${t.message}", Toast.LENGTH_SHORT).show()
                }
            })
    }
    private fun saveToClipboard(context: Context, text: String) {
        val clipboard = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
        val clip = ClipData.newPlainText("shared_recipe", text)
        clipboard.setPrimaryClip(clip)
    }
}

