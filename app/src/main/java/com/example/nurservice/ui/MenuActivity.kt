package com.example.nurservice.ui

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.nurservice.R
import com.example.nurservice.io.ApiService
import com.example.nurservice.util.PreferenceHelper
import com.example.nurservice.util.PreferenceHelper.get
import com.example.nurservice.util.PreferenceHelper.set
import retrofit2.Call
import retrofit2.Response

class MenuActivity : AppCompatActivity() {

    private val apiService by lazy {
        ApiService.create()
    }
    private val preferences by lazy {
      PreferenceHelper.defaultPrefs(this)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu)

        val agendar = findViewById<Button>(R.id.btnagendar)
        agendar.setOnClickListener {
            agendarcita()

            val cerrar = findViewById<Button>(R.id.btncerrarSesion)
            cerrar.setOnClickListener {
                performLogout()
            }
            val btnMisCitas=findViewById<Button>(R.id.btn_mis_citas)
            btnMisCitas.setOnClickListener{
                MisCitas()

            }
        }

    }
    private fun MisCitas(){
        val i=Intent(this,MisCitasActivity::class.java)
        startActivities(arrayOf(i))

    }

    private fun agendarcita() {
        val i = Intent(this, AgendarActivity::class.java)
        startActivities(arrayOf(i))

    }


    private fun cerrarSesion() {
        val i = Intent(this, MainActivity::class.java)
        startActivities(arrayOf(i))
        finish()

    }
    private fun performLogout(){
        val jwt=preferences["jwt"," "]
        val call=apiService.postLogout("Bearer $jwt")
        call.enqueue(object:retrofit2.Callback<Void>{
            override fun onResponse(call: Call<Void>, response: Response<Void>) {
                crearSesionPreference()
                cerrarSesion()
            }

            override fun onFailure(call: Call<Void>, t: Throwable) {
                Toast.makeText(applicationContext,"Se produjo un error en el servidor",Toast.LENGTH_SHORT).show()

            }

        })
    }
    private fun crearSesionPreference() {
        preferences["jwt"] = "  "
    }
}