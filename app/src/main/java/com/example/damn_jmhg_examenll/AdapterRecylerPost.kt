package com.example.damn_jmhg_examenll

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.damn_jmhg_examenll.api.entities.PostEntity

class AdapterRecylerPost(var posts: List<PostEntity>): RecyclerView.Adapter<AdapterRecylerPost.ViewHolder>() {
    private lateinit var miListener: OnItemClickListener

    interface OnItemClickListener {
        fun onItemClick(position: Int)
    }

    fun setOnItemClickListener(listener: OnItemClickListener) {
        miListener = listener
    }

    inner class ViewHolder(itemView: View, listener: OnItemClickListener) : RecyclerView.ViewHolder(itemView) {
        val tvTitulo: TextView = itemView.findViewById(R.id.tvTitle)
        val tvCuerpo: TextView = itemView.findViewById(R.id.tvBody)

        init {
            itemView.setOnClickListener {
                listener.onItemClick(adapterPosition)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.post_item, parent, false)
        return ViewHolder(view, miListener)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.tvTitulo.text = posts[position].title
        holder.tvCuerpo.text = posts[position].body
    }

    override fun getItemCount(): Int {
        return posts.size
    }
}