package com.codecrafters.dishdelight

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

// Adapter class for displaying a list of folder names in a RecyclerView
class FolderAdapter (private val context: Context, private val folderList: List<String>) :
    RecyclerView.Adapter<FolderAdapter.FolderViewHolder>() {

    // ViewHolder class to hold references to the views in each RecyclerView item
    inner class FolderViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val folderName: TextView = view.findViewById(R.id.folderName)
    }

    // Inflates the layout for each item in the RecyclerView
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FolderViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.folder_item, parent, false)
        return FolderViewHolder(view)
    }

    // Binds the data (folder name) to each item in the RecyclerView
    override fun onBindViewHolder(holder: FolderViewHolder, position: Int) {
        val folder = folderList[position]
        Log.d("FolderAdapter", "Binding folder: $folder at position $position")
        // Setting the folder name text to the TextView in the ViewHolder
        holder.folderName.text = folder

        holder.itemView.setOnClickListener {
            // Create an Intent to start RecipeFolderActivity
            val intent = Intent(context, RecipeFolderActivity::class.java)
            intent.putExtra("FOLDER_NAME", folder) // Use the folder name directly
            context.startActivity(intent)
        }
    }

    // Returns the total number of items in the list
    override fun getItemCount(): Int {
        return folderList.size
    }
}
