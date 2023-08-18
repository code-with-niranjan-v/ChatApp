package com.example.chatapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.ktx.Firebase


class SendViewHolder(itemView: View):ViewHolder(itemView){
    private val tvSendMessage = itemView.findViewById<TextView>(R.id.tvSendMessage)

    fun bindData(message:String){
        tvSendMessage.text = message
    }

}

class ReceiveViewHolder(itemView: View):ViewHolder(itemView){
    private val tvReceiveMessage = itemView.findViewById<TextView>(R.id.tvReceiveMessage)

    fun bindData(message:String){
        tvReceiveMessage.text = message
    }
}

class MessageAdapter(var listOfMessage: MutableList<Message>):Adapter<ViewHolder>(){
    private val ITEM_SEND = 1
    private val ITEM_RECEIVE = 2
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return if (viewType == 1){
            val view = LayoutInflater
                .from(parent.context)
                .inflate(R.layout.send, parent, false)
            SendViewHolder(view)
        }else{
            val view = LayoutInflater
                .from(parent.context)
                .inflate(R.layout.receive, parent, false)
            ReceiveViewHolder(view)
        }
    }

    override fun getItemCount(): Int {
        return listOfMessage.size
    }

    override fun getItemViewType(position: Int): Int {
        val currMessage = listOfMessage[position]
        return if(FirebaseAuth.getInstance().currentUser?.uid == currMessage.senderUid){
            ITEM_SEND
        }else {
            ITEM_RECEIVE
        }
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currMessage = listOfMessage[position]
        if(holder.javaClass == SendViewHolder::class.java){
            val viewHolder = holder as SendViewHolder
            viewHolder.bindData(currMessage.message.toString())
        }else{
            val viewHolder = holder as ReceiveViewHolder
            viewHolder.bindData(currMessage.message.toString())
        }
    }

}