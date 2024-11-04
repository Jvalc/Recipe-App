package com.codecrafters.dishdelight

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class FolderAdapter (private val context: Context, private val folderList: List<String>) :
    RecyclerView.Adapter<FolderAdapter.FolderViewHolder>() {

    inner class FolderViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val folderName: TextView = view.findViewById(R.id.folderName)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FolderViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.folder_item, parent, false)
        return FolderViewHolder(view)
    }

    override fun onBindViewHolder(holder: FolderViewHolder, position: Int) {
        val folder = folderList[position]
        Log.d("FolderAdapter", "Binding folder: $folder at position $position")
        holder.folderName.text = folder

        holder.itemView.setOnClickListener {
            // Starting the RecipeFolderActivity
            val intent = Intent(context, RecipeFolderActivity::class.java)
            intent.putExtra("FOLDER_NAME", folder) // Use the folder name directly
            context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return folderList.size
    }
}
