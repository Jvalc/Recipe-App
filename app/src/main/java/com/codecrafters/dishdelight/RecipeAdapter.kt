package com.codecrafters.dishdelight

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
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RecipeAdapter(
    private val userId: String,
    private val recipeList: List<Recipe>) :
    RecyclerView.Adapter<RecipeAdapter.RecipeViewHolder>() {
    private lateinit var recipeDao: RecipeDao

    class RecipeViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val recipeTitle : TextView = itemView.findViewById(R.id.recipeTitle)
        val cookingTimeValue: TextView = itemView.findViewById(R.id.cookingTimeValue)
        val servingsValue: TextView = itemView.findViewById(R.id.servingsValue)
        val cuisineValue: TextView = itemView.findViewById(R.id.cuisineValue)
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
        val editBtn : ImageView = itemView.findViewById(R.id.editBtn)

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
        holder.servingsValue.text = recipe.servingSize
        holder.cuisineValue.text = recipe.cuisineType
        holder.ingredientsValue.text = recipe.ingredients.joinToString("\n") { "• $it" }
        holder.recipeValue.text = recipe.steps
            .mapIndexed { index, step -> "${index + 1}. $step" }
            .joinToString("\n") { "step $it" }

        var recipeId = recipe.id

        getUserNotes(userId, recipeId, holder.context, holder)

        val db = DatabaseBuilder.buildDatabase(holder.context)
        recipeDao = db.recipeDao()

        // Load recipe image (use Glide or any image loading library)
        Glide.with(holder.itemView.context)
            .load(recipe.imageUrl)
            .placeholder(R.drawable.ic_placeholder_image)
            .into(holder.recipeImage)

        holder.moreBtn.setOnClickListener {
            if(holder.shareBtn.visibility == View.VISIBLE ){
                holder.shareBtn.visibility = View.GONE
                holder.downloadBtn.visibility = View.GONE
                holder.saveBtn.visibility = View.GONE

            } else{
                holder.shareBtn.visibility = View.VISIBLE
                holder.downloadBtn.visibility = View.VISIBLE
                holder.saveBtn.visibility = View.VISIBLE
            }
        }

        holder.shareBtn.setOnClickListener{
            val recipeId = recipe.id // Get the recipe ID
            shareRecipe(holder.context, recipeId)
        }
        holder.saveBtn.setOnClickListener{
            promptFileNameAndSaveRecipe(userId, recipeId, holder.context)

        }
        holder.downloadBtn.setOnClickListener{
            CoroutineScope(Dispatchers.IO).launch {
                recipeDao.insertRecipes(listOf(recipe)) // Use a list with one recipe
            }
            Toast.makeText(holder.context, "Recipe downloaded!", Toast.LENGTH_SHORT).show()
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
                holder.editBtn.visibility = View.GONE
            } else {
                holder.notesValue.visibility = View.VISIBLE
                holder.editBtn.visibility = View.VISIBLE
            }
        }

        holder.editBtn.setOnClickListener{
            promptNewNote(userId, recipeId, holder.context, holder)
        }
    }

    override fun getItemCount(): Int {
        return recipeList.size
    }
    private fun promptNewNote(userId: String, recipeId: String, context: Context, holder: RecipeViewHolder) {
        // Show an AlertDialog to prompt for file name
        val builder = AlertDialog.Builder(context)
        builder.setTitle("Enter the new note to recipe")

        // Set up the input field
        val input = android.widget.EditText(context)
        builder.setView(input)

        // Set up the dialog buttons
        builder.setPositiveButton("Save") { _, _ ->
            val note = input.text.toString()

            if (note.isNotEmpty()) {
                // Call the API and save the recipe
                saveNote(userId, note, recipeId, context, holder)
            }
        }
        builder.setNegativeButton("Cancel") { dialog, _ -> dialog.cancel() }
        builder.show()
    }
    private fun saveNote(userId: String, note: String, recipeId: String, context: Context, holder: RecipeViewHolder) {
        val request = NoteRequest(note, userId)

        RetrofitClient.getClient().create(RecipeApiService::class.java)
            .addNote(recipeId, request).enqueue(object : Callback<NotesResponse> {
                override fun onResponse(call: Call<NotesResponse>, response: Response<NotesResponse>) {
                    if (response.isSuccessful) {
                        Toast.makeText(context, "Noted saved!", Toast.LENGTH_SHORT).show()
                        getUserNotes(userId, recipeId, context, holder)
                    } else {
                        Toast.makeText(context, "Error: ${response.errorBody()?.string()}", Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onFailure(call: Call<NotesResponse>, t: Throwable) {
                    Toast.makeText(context, "Failed to add note: ${t.message}", Toast.LENGTH_SHORT).show()
                }
            })
    }
    private fun getUserNotes(userId: String, recipeId: String, context: Context, holder: RecipeViewHolder) {
        val call = RetrofitClient.getClient().create(RecipeApiService::class.java)
            .getNotes(userId, recipeId)

        call.enqueue(object : Callback<NotesResponse> {
            override fun onResponse(call: Call<NotesResponse>, response: Response<NotesResponse>) {
                if (response.isSuccessful) {
                    val notes = response.body()?.notes ?: emptyList()
                    // Process the list of notes here, e.g., display them in a RecyclerView
                    if (notes != null) {
                        holder.notesValue.text = notes.joinToString("\n") { "• ${it.note}" }
                    }
                } else {
                    holder.notesValue.text = "No notes found"
                }
            }

            override fun onFailure(call: Call<NotesResponse>, t: Throwable) {
                Toast.makeText(context, "Error: ${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
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
                saveRecipeToFile(userId, category, recipeId, context)
            }
        }
        builder.setNegativeButton("Cancel") { dialog, _ -> dialog.cancel() }
        builder.show()
    }
    private fun saveRecipeToFile(userId: String, category: String, recipeId: String, context: Context) {
        val request = RecipeSaveRequest(userId, category, recipeId)

        RetrofitClient.getClient().create(RecipeApiService::class.java)
            .saveRecipe(request).enqueue(object : Callback<SaveResponse> {
                override fun onResponse(call: Call<SaveResponse>, response: Response<SaveResponse>) {
                    if (response.isSuccessful) {
                        Toast.makeText(context, "Recipe saved to ${category}!", Toast.LENGTH_SHORT).show()
                    } else {
                        Toast.makeText(context, "Error: ${response.errorBody()?.string()}", Toast.LENGTH_SHORT).show()
                    }
                }
                override fun onFailure(call: Call<SaveResponse>, t: Throwable) {
                    Toast.makeText(context, "Failed to save recipe: ${t.message}", Toast.LENGTH_SHORT).show()
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

