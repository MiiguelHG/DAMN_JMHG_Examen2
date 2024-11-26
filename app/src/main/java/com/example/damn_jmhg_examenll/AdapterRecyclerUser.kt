package com.example.damn_jmhg_examenll

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.damn_jmhg_examenll.api.entities.UserEntity

class AdapterRecyclerUser(var users: List<UserEntity>): RecyclerView.Adapter<AdapterRecyclerUser.ViewHolder>() {
    private lateinit var miListener: OnItemClickListener

    interface OnItemClickListener {
        fun onItemClick(position: Int)
        fun onLongItemClick(position: Int)
    }

    fun setOnItemClickListener(listener: OnItemClickListener) {
        miListener = listener
    }

    inner class ViewHolder(itemView: View, listener: OnItemClickListener): RecyclerView.ViewHolder(itemView) {
        val tvName: TextView = itemView.findViewById(R.id.tvName)
        val tvUsername: TextView = itemView.findViewById(R.id.tvUsername)
        val tvEmail: TextView = itemView.findViewById(R.id.tvEmail)
        val tvPhone: TextView = itemView.findViewById(R.id.tvPhone)
        val tvWebsite: TextView = itemView.findViewById(R.id.tvWebsite)
        val tvAdrres: TextView = itemView.findViewById(R.id.tvAddress)
        val tvCompany: TextView = itemView.findViewById(R.id.tvCompany)

        init {
            itemView.setOnClickListener {
                listener.onItemClick(adapterPosition)
            }

            itemView.setOnLongClickListener {
                listener.onLongItemClick(adapterPosition)
                true
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.usuario_item, parent, false)
        return ViewHolder(view, miListener)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.tvName.text = users[position].name
        holder.tvUsername.text = users[position].username
        holder.tvEmail.text = users[position].email
        holder.tvPhone.text = users[position].phone
        holder.tvWebsite.text = users[position].website
        holder.tvAdrres.text = "${users[position].address.street}, ${users[position].address.suite}, ${users[position].address.city}, ${users[position].address.zipcode}, ${users[position].address.geo.lat}, ${users[position].address.geo.lng}"
        holder.tvCompany.text = "${users[position].company.name}, ${users[position].company.catchPhrase}, ${users[position].company.bs}"
    }

    override fun getItemCount(): Int {
        return users.size
    }
}