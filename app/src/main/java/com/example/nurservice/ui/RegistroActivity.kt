package com.example.nurservice.ui

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.nurservice.R
import com.example.nurservice.io.ApiService
import com.example.nurservice.io.response.loginResponse
import com.example.nurservice.util.PreferenceHelper
import com.example.nurservice.util.PreferenceHelper.set
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RegistroActivity : AppCompatActivity() {
        override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registro)
        private lateinit var binding: ActivityRegistroBinding
        val apiService by lazy {
            ApiService.create()
        }

        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)

            binding = ActivityRegistroBinding.inflate(layoutInflater)
            setContentView(binding.root)

            binding.idvolverLogin.setOnClickListener{
                gotologin()
            }

            binding.btnConfirmRegister.setOnClickListener {
                performRegister()
            }
        }
        private fun goToLogin(){
            val i = Intent(this, MainActivity::class.java)
            startActivity(i)
        }

        private fun performRegister() {
            val name = binding.etRegisterName.text.toString().trim()
            val email = binding.etRegisterEmail.text.toString().trim()
            val password = binding.etRegisterPassword.text.toString()
            val passwordConfirmation = binding.etRegisterPasswordConfirmation.text.toString()

            if (name.isEmpty() || email.isEmpty() || password.isEmpty() || passwordConfirmation.isEmpty()){
                Toast.makeText(this, "Debe de completar todos los campos.", Toast.LENGTH_SHORT).show()
                return
            }

            if (password != passwordConfirmation) {
                Toast.makeText(this, "Las contraseñas ingresadas no son iguales.", Toast.LENGTH_SHORT).show()
                return
            }

            val call = apiService.postRegister(name, email, password, passwordConfirmation)
            call.enqueue(object: Callback<loginResponse> {
                override fun onResponse(call: Call<loginResponse>, response: Response<loginResponse>) {
                    if(response.isSuccessful){
                        val loginResponse = response.body()
                        if (loginResponse == null){
                            Toast.makeText(applicationContext, "Se produjo un error en el servidor", Toast.LENGTH_SHORT).show()
                            return
                        }
                        if (loginResponse.success){
                            creaSesionPreference(loginResponse.jwt)
                            goToMenu()
                        }else {
                            Toast.makeText(applicationContext, "Se produjo un error en el servidor", Toast.LENGTH_SHORT).show()
                        }
                    }else {
                        Toast.makeText(applicationContext, "El correo ya existe en la plataforma, Prueba con otro.", Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onFailure(call: Call<loginResponse>, t: Throwable) {
                    Toast.makeText(this@RegistroActivity, "Error: No se pudo registrarse", Toast.LENGTH_SHORT).show()
                }

            })
        }

        private fun goToMenu() {
            val i = Intent(this, MenuActivity::class.java)
            startActivity(i)
            finish()
        }
        private fun creaSessionPreference( jwt: String ) {
            val preferences = PreferenceHelper.defaultPrefs(this)
            preferences["jwt"] = jwt
        }

    }
}








