package com.example.chatapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.chatapp.databinding.ActivitySignUpBinding

class SignUp : AppCompatActivity() {
    private lateinit var binding: ActivitySignUpBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.btnSignUp.setOnClickListener {
            val database = MyFireBase()
            val email = binding.etEmail.text.toString()
            val password = binding.etPassword.text.toString()
            val confirmPass = binding.etConPassword.text.toString()
            val name = binding.etName.text.toString()
            if(email.isNotBlank() && password.isNotBlank() && confirmPass.isNotBlank()&&name.isNotBlank()){
                if(password == confirmPass){
                    database.signUp(email,password,name,this)
                    gotoLogin()
                }else{
                    Toast.makeText(this,"Passwords are not Equal",Toast.LENGTH_SHORT).show()
                }
            }else{
                Toast.makeText(this,"Fill all the Fields",Toast.LENGTH_SHORT).show()
            }

        }


    }

    fun gotoLogin(){
        val intent = Intent(this,MainActivity::class.java)
        startActivity(intent)
        finish()
    }


}