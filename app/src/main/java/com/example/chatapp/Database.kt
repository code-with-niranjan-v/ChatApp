package com.example.chatapp

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.FirebaseDatabaseKtxRegistrar

interface Database {
    fun signIn(email:String,password:String,context: Context,gotoNextActivity:() -> Unit)
    fun signUp(email:String,password:String,name: String,context: Context)
    fun storeUser(email:String,name: String)
    fun addUserToList(userAdapter: UserAdapter)
    fun storeMessage(message: Message,senderRoom:String,receiverRoom:String)
    fun loadMessage(senderRoom:String,adapter: MessageAdapter)
}

class MyFireBase():Database{
    private val mFirebaseAuth = FirebaseAuth.getInstance()
    private val mFireDatabase = FirebaseDatabase.getInstance()
    val listOfUsers = mutableListOf<User>()
    val listOfMessages = mutableListOf<Message>()


    override fun signIn(email: String, password: String,context:Context,gotoNextActivity:() -> Unit) {
        mFirebaseAuth.signInWithEmailAndPassword(email,password).addOnSuccessListener {
            Toast.makeText(context,"Login SuccessFull!",Toast.LENGTH_SHORT).show()
            gotoNextActivity()
        }.addOnFailureListener{
            Toast.makeText(context,it.toString(),Toast.LENGTH_SHORT).show()
        }
    }

    override fun signUp(email: String, password: String,name: String, context: Context,) {
        mFirebaseAuth.createUserWithEmailAndPassword(email, password).addOnSuccessListener {
            Toast.makeText(context,"Account Created",Toast.LENGTH_SHORT).show()
            storeUser(email,name)

        }.addOnFailureListener{
            Toast.makeText(context,it.toString(),Toast.LENGTH_SHORT).show()
        }
    }

    override fun storeUser(email: String,name:String) {
        val reference = mFireDatabase.reference
        val uid = mFirebaseAuth.currentUser?.uid.toString()
        val user = User(email,name,uid)
        val userReference = reference.child("Users").child(uid).setValue(user)

    }

    override fun addUserToList(adapter: UserAdapter) {
        val userReference = mFireDatabase.reference
        userReference.child("Users").addValueEventListener(object: ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                listOfUsers.clear()

                for(postSnapShot in snapshot.children){
                    val currentUser = postSnapShot.getValue(User::class.java)
                    if (currentUser != null) {
                        if(mFirebaseAuth.currentUser?.uid.toString() != currentUser.uid ){
                            listOfUsers.add(currentUser!!)
                        }
                    }


                }
                adapter.notifyDataSetChanged()
            }

            override fun onCancelled(error: DatabaseError) {

            }

        })
    }

    override fun storeMessage(message: Message,senderRoom:String,receiverRoom:String){
        mFireDatabase.reference.child("Messages").child(senderRoom).push().setValue(message).addOnSuccessListener {
            mFireDatabase.reference.child("Messages").child(receiverRoom).push().setValue(message)
        }
    }

    override fun loadMessage(senderRoom:String,adapter: MessageAdapter){

        mFireDatabase.reference.child("Messages").child(senderRoom).addValueEventListener(object: ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                listOfMessages.clear()
                for (postSnapShot in snapshot.children){
                    val message = postSnapShot.getValue(Message::class.java)
                    listOfMessages.add(message!!)
                }
                adapter.notifyDataSetChanged()
            }

            override fun onCancelled(error: DatabaseError) {

            }
        })

    }


}