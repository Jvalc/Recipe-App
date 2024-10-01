package com.example.dishdelight

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions

class FolderAdapter (private val context: Context, private val folderList: List<Folder>) :
    RecyclerView.Adapter<FolderAdapter.FolderViewHolder>() {

    inner class FolderViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val folderName: TextView = view.findViewById(R.id.folderName)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FolderViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.recipe_item, parent, false)
        return FolderViewHolder(view)
    }

    override fun onBindViewHolder(holder: FolderViewHolder, position: Int) {
        val folder = folderList[position]
        holder.folderName.text = folder.title

        holder.itemView.setOnClickListener {
            // Here ensure you are starting the ChatDetailActivity
            val intent = Intent(it.context, RecipeFolderActivity::class.java)
            intent.putExtra("FOLDER_NAME", folder.title)
            it.context.startActivity(intent)
        }
        holder.itemView.setOnClickListener {
            val intent = Intent(it.context, RecipeFolderActivity::class.java)
            // Assuming you have a method to get recipes by folder
           // val recipes = getRecipesByFolder(folder.title) // Implement this method
            //intent.putExtra("RECIPE_LIST", ArrayList(recipes)) // Sending the list
            it.context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return folderList.size
    }
}
