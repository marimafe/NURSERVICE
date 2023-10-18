package com.example.nurservice.ui

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.nurservice.R
import com.example.nurservice.io.ApiService
import com.example.nurservice.io.response.loginResponse
import com.example.nurservice.util.PreferenceHelper
import com.example.nurservice.util.PreferenceHelper.get
import com.example.nurservice.util.PreferenceHelper.set
import retrofit2.Response

class MainActivity : AppCompatActivity() {

    private val apiService: ApiService by lazy {
        ApiService.create()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val preferences=PreferenceHelper.defaultPrefs(this)
        if (preferences["jwt", " "].contains( "."))
            iniciar()

        val registro=findViewById<TextView>(R.id.idCrearCuenta)
        registro.setOnClickListener {
            registrar()

        }
        val inicio=findViewById<Button>(R.id.btninicio)
        inicio.setOnClickListener {
            performLogin()

        }
    }
   private fun registrar(){
       val i=Intent (this, RegistroActivity::class.java)
       startActivities(arrayOf(i))

   }
   private fun iniciar(){
       val i=Intent(this, MenuActivity::class.java)
       startActivities(arrayOf(i))
       finish()
   }
   private fun creaSesionPreference(jwt: String){
    val preferences=PreferenceHelper.defaultPrefs(this)
       preferences["jwt"]=jwt
    }
    @SuppressLint("SuspiciousIndentation")
    private fun performLogin(){
      val email=findViewById<EditText>(R.id.email).text.toString()
      val password=findViewById<EditText>(R.id.password).text.toString()


        if (email.trim().isEmpty() || password.trim().isEmpty()){
            Toast.makeText(applicationContext, "Debe ingresar su correo y contraseña", Toast.LENGTH_SHORT).show()
            return
        }

        val call = apiService.postLogin(email, password)
            call.enqueue(object: retrofit2.Callback<loginResponse>
            {

                override fun onResponse(call: retrofit2.Call<loginResponse>,response: Response<loginResponse>) {
                    if (response.isSuccessful) {
                        val loginResponse = response.body()
                        if (loginResponse == null) {
                            Toast.makeText(
                                applicationContext,"Se produjo un error en el servidor",Toast.LENGTH_SHORT).show()
                            return
                        }
                        if (loginResponse.success) {
                            creaSesionPreference(loginResponse.jwt)
                            iniciar()
                        } else {
                            Toast.makeText(applicationContext,"Las credenciales son incorrectas",Toast.LENGTH_SHORT).show()
                        }
                    } else {
                        Toast.makeText(applicationContext,"Se produjo un error en el servidor",Toast.LENGTH_SHORT).show()
                    }

                    }

                override fun onFailure(call: retrofit2.Call<loginResponse>, t: Throwable) {
                    Toast.makeText(applicationContext,"Se produjo un error en el servidor",Toast.LENGTH_SHORT).show()
                }
                })
        }
}






