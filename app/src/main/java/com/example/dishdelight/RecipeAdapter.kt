package com.example.dishdelight

import android.app.AlertDialog
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class RecipeAdapter(private val context: Context, private val recipes: List<Recipe>) :
    RecyclerView.Adapter<RecipeAdapter.RecipeViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecipeViewHolder {
        val view: View = LayoutInflater.from(context).inflate(R.layout.recipe_item, parent, false)
        return RecipeViewHolder(view)
    }

    override fun onBindViewHolder(holder: RecipeViewHolder, position: Int) {
        val recipe = recipes[position]
        holder.recipeTitle.text = recipe.name
        holder.cookingTimeValue.text = recipe.cookingTime
        holder.servingsValue.text = recipe.servings
        // Load image using a library like Glide or Picasso
        Glide.with(context).load(recipe.imageUrl).into(holder.recipeImage)

        holder.moreBtn.setOnClickListener {
            holder.shareBtn.visibility = View.VISIBLE
            holder.downloadBtn.visibility = View.VISIBLE
            holder.saveBtn.visibility = View.VISIBLE
        }

        holder.recipeBtn.setOnClickListener {
            holder.recipeValue.visibility = View.VISIBLE
            val formattedSteps = recipe.steps
                .mapIndexed { index, step -> "${index + 1}. $step" }
                .joinToString("\n")

            holder.recipeValue.text = formattedSteps
        }

        holder.ingredientsBtn.setOnClickListener {
            holder.ingredientsValue.visibility = View.VISIBLE

            val ingredients = recipe.ingredients.joinToString("\n") { "• $it" }
            holder.recipeValue.text = ingredients
        }
        holder.notesDropdown.setOnClickListener {
            holder.notesValue.visibility = View.VISIBLE

            val notes = recipe.notes.joinToString("\n") { "• $it" }
            holder.recipeValue.text = notes
        }
    }

    override fun getItemCount(): Int {
        return recipes.size
    }

    class RecipeViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var recipeTitle: TextView
        var cookingTimeValue: TextView
        var servingsValue: TextView
        var recipeImage: ImageView
        var moreBtn : Button
        var shareBtn : Button
        var saveBtn : Button
        var downloadBtn : Button
        var ingredientsBtn : Button
        var ingredientsValue : TextView
        var recipeBtn : Button
        var recipeValue : TextView
        var notesDropdown : Button
        var notesValue : TextView

        init {
            recipeTitle = itemView.findViewById<TextView>(R.id.recipeTitle)
            cookingTimeValue = itemView.findViewById<TextView>(R.id.cookingTimeValue)
            servingsValue = itemView.findViewById<TextView>(R.id.servingsValue)
            recipeImage = itemView.findViewById<ImageView>(R.id.recipeImage)
            moreBtn = itemView.findViewById<Button>(R.id.btnMore)
            shareBtn = itemView.findViewById<Button>(R.id.shareBtn)
            saveBtn = itemView.findViewById<Button>(R.id.saveBtn)
            downloadBtn = itemView.findViewById<Button>(R.id.downloadBtn)
            ingredientsBtn = itemView.findViewById<Button>(R.id.ingredientsDropdown)
            ingredientsValue = itemView.findViewById<TextView>(R.id.ingredientsValue)
            recipeBtn = itemView.findViewById<Button>(R.id.recipeDropdown)
            recipeValue = itemView.findViewById<TextView>(R.id.recipeValue)
            notesDropdown = itemView.findViewById<Button>(R.id.notesDropdown)
            notesValue = itemView.findViewById<TextView>(R.id.notesValue)
            moreBtn = itemView.findViewById<Button>(R.id.btnMore)
        }
    }
}


