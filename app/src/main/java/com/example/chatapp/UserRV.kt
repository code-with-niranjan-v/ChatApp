package com.example.chatapp

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder

class UserViewHolder(itemView:View):ViewHolder(itemView){

    val tvName = itemView.findViewById<TextView>(R.id.tvUserName)

    fun bindData(user: User){

        tvName.text = user.name

    }

}

class UserAdapter(private var listOfUsers : List<User>,private var context: Context):Adapter<UserViewHolder>(){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val view = LayoutInflater
            .from(parent.context)
            .inflate(R.layout.users_list, parent, false)
        return UserViewHolder(view)
    }

    override fun getItemCount(): Int {
        return listOfUsers.size
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        holder.bindData(listOfUsers[position])
        holder.itemView.setOnClickListener {
            val intent = Intent(context,ChatActivity::class.java)

            intent.putExtra("name",listOfUsers[position].name)
            intent.putExtra("uid",listOfUsers[position].uid)
            context.startActivity(intent)
        }
    }

}