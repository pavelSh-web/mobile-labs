package com.example.myapplication

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.InputType
import android.util.Log
import android.widget.EditText
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private fun checkETEmpty(w: EditText) = w.text.toString().isEmpty()
    private fun checkFormatPhone(w: EditText) = w.text.toString().matches(Regex("""^((8|\+7)[\- ]?)?(\(?\d{3}\)?[\- ]?)?[\d\- ]{7,10}${'$'}"""))
    private fun checkFormatEmail(w: EditText) = w.text.toString().matches(Regex("""^[A-Za-z0-9+_.-]+@(.+)${'$'}"""))

    private fun changeActivity(activity: Class<*>) {
        val intent = Intent(this, activity)
        startActivity(intent)
    }

    private lateinit var shared: SharedPreferences

    private fun checkSharedEmpty() = shared.getString("login", "no-login") == "no-login"
    private fun checkAutomaticLogin() = shared.getBoolean("automaticLogin", false)

    @SuppressLint("CommitPrefEdits")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        shared = getSharedPreferences("data", Context.MODE_PRIVATE)

        if (checkSharedEmpty()) {
            //скрыть прелоудер
        } else if (checkAutomaticLogin()){
            changeActivity(DirectoryActivity::class.java)
        } else {
            changeActivity(LoginInActivity::class.java)
        }

        etEmail.inputType = InputType.TYPE_NULL

        tvTel.setOnClickListener {
            tvTel.setTextColor(resources.getColor(R.color.purple_200))
            tvEmail.setTextColor(resources.getColor(R.color.black))
            etEmail.hint = "Введите номер"
            etEmail.inputType = InputType.TYPE_CLASS_PHONE
        }

        tvEmail.setOnClickListener {
            tvEmail.setTextColor(resources.getColor(R.color.purple_200))
            tvTel.setTextColor(resources.getColor(R.color.black))
            etEmail.hint = "Введите email"
            etEmail.inputType = InputType.TYPE_NULL
        }

        btnSendForm.setOnClickListener {
            if (etEmail.inputType ==  InputType.TYPE_CLASS_PHONE) {
                if (checkETEmpty(etEmail)) {
                    Toast.makeText(this, "Поле с номером телефона не заполнено", Toast.LENGTH_SHORT).show()
                    return@setOnClickListener
                }
                if (!checkFormatPhone(etEmail)) {
                    Toast.makeText(this, "Неккоректный формат номера телефона", Toast.LENGTH_SHORT).show()
                    return@setOnClickListener
                }
            }
            if (etEmail.inputType ==  InputType.TYPE_NULL) {
                if (checkETEmpty(etEmail)) {
                    Toast.makeText(this, "Поле с электронной почтой не заполнено", Toast.LENGTH_SHORT).show()
                    return@setOnClickListener
                }
                if (!checkFormatEmail(etEmail)) {
                    Toast.makeText(this, "Неккоректный формат электронной почты", Toast.LENGTH_SHORT).show()
                    return@setOnClickListener
                }
            }
            if (checkETEmpty(etPassword1) || checkETEmpty(etPassword2)) {
                Toast.makeText(this, "Введите пароль", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            if (etPassword1.text.toString() != etPassword2.text.toString()) {
                Toast.makeText(this, "Пароли не совпадают", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            shared.edit().putString("login", etEmail.text.toString()).apply()
            shared.edit().putString("password", etPassword1.text.toString()).apply()

            changeActivity(DirectoryActivity::class.java)
        }
    }
}