package com.example.chatapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.chatapp.databinding.ActivityMainBinding
import com.example.chatapp.databinding.ActivitySignUpBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.btnLogin.setOnClickListener {
            val database = MyFireBase()
            val email = binding.etEmail.text.toString()
            val password = binding.etPassword.text.toString()

            if(email.isNotBlank() && password.isNotBlank()){
                database.signIn(email,password,this, ::gotoNextActivity)
            }else{
                Toast.makeText(this,"Fill all the Fields",Toast.LENGTH_SHORT).show()
            }
        }
        binding.tvDontHaveAccount.setOnClickListener{
            gotoSignUp()
        }
    }

    private fun gotoSignUp(){
        val intent = Intent(this,SignUp::class.java)
        startActivity(intent)
        finish()
    }

    private fun gotoNextActivity(){
        val intent = Intent(this,UsersActivity::class.java)
        startActivity(intent)
        finish()
    }



}