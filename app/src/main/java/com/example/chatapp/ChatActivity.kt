package com.example.chatapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.chatapp.databinding.ActivityChatBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.ktx.Firebase

class ChatActivity : AppCompatActivity() {
    private lateinit var binding: ActivityChatBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityChatBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val name = intent.getStringExtra("name")
        val receiverUid = intent.getStringExtra("uid")
        val senderUid = FirebaseAuth.getInstance().currentUser?.uid
        var senderRoom = senderUid+receiverUid
        var receiverRoom = receiverUid+senderUid
        val database = MyFireBase()
        binding.tvName.text = name
        val adapter = MessageAdapter(database.listOfMessages)
        binding.rvChat.layoutManager = LinearLayoutManager(this)
        binding.rvChat.adapter = adapter
        database.loadMessage(senderRoom,adapter)

        binding.btnSend.setOnClickListener {
            val message = Message(binding.etMessage.text.toString(),senderUid)
            database.storeMessage(message,senderRoom,receiverRoom)
            binding.etMessage.text.clear()
        }
    }
}