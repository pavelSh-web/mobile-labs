package com.example.myapplication

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_loginin.*
import kotlinx.android.synthetic.main.activity_loginin.btnSendForm
import kotlinx.android.synthetic.main.activity_main.*

class LoginInActivity : AppCompatActivity() {

    private fun checkETEmpty(w: EditText) = w.text.toString().isEmpty()

    private fun changeActivity(activity: Class<*>) {
        val intent = Intent(this, activity)
        startActivity(intent)
    }

    private lateinit var shared: SharedPreferences

    @SuppressLint("CommitPrefEdits")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_loginin)

        shared = getSharedPreferences("data", Context.MODE_PRIVATE)
        val login = shared.getString("login", "no-login")
        val password =  shared.getString("password", "no-password")

        btnSendForm.setOnClickListener {
            if (checkETEmpty(etLogin)) {
                Toast.makeText(this, "Введите логин", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            if (etLogin.text.toString() != login) {
                Toast.makeText(this, "Неверный email/пароль", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            if (checkETEmpty(etPassword)) {
                Toast.makeText(this, "Введите пароль", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            if (etPassword.text.toString() != password) {
                Toast.makeText(this, "Неверный пароль", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            shared.edit().putBoolean("automaticLogin", cbAutomaticLogin.isChecked).apply()

            changeActivity(DirectoryActivity::class.java)
        }
    }
}