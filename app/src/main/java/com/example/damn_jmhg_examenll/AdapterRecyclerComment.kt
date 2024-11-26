package com.example.damn_jmhg_examenll

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.damn_jmhg_examenll.api.entities.CommentEntity

class AdapterRecyclerComment(var comments : List<CommentEntity>): RecyclerView.Adapter<AdapterRecyclerComment.ViewHolder>() {

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvNombre: TextView = itemView.findViewById(R.id.tvNameComment)
        val tvEmail: TextView = itemView.findViewById(R.id.tvEmailComment)
        val tvBody: TextView = itemView.findViewById(R.id.tvBodyComment)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.comment_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.tvNombre.text = comments[position].name
        holder.tvEmail.text = comments[position].email
        holder.tvBody.text = comments[position].body
    }

    override fun getItemCount(): Int {
        return comments.size
    }
}