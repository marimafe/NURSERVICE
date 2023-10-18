package com.example.nurservice.ui

import android.os.Bundle
import android.telecom.Call
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.view.ActionMode.Callback
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.nurservice.R
import com.example.nurservice.io.ApiService
import com.example.nurservice.model.Appointment
import com.example.nurservice.util.PreferenceHelper
import com.example.nurservice.util.PreferenceHelper.get
import retrofit2.Response

class MisCitasActivity : AppCompatActivity() {
    private val apiService: ApiService by lazy {
        ApiService.create()
    }

    private val preferences by lazy {
        PreferenceHelper.defaultPrefs(this)
    }

    private val appointmentAdapter = AppCompatActivity()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mis_citas)

        val rvAppointments = findViewById<RecyclerView>(R.id.rv_appointments)

        rvAppointments.layoutManager = LinearLayoutManager(this)
        rvAppointments.adapter = AppointmentAdapter

        loadAppointments()
        refreshAppointments()
    }

    private fun loadAppointments() {
        val jwt = preferences["jwt", ""]
        val call = apiService.getAppointments("Bearer $jwt")
        call.enqueue(object: Callback<ArrayList<Appointment>> {


            override fun onResponse(
                call: Call<ArrayList<Appointment>>,
                response: Response<ArrayList<Appointment>>
            ) {
                if (response.isSuccessful){
                    response.body()?.let {
                        appointmentAdapter.appointmets = it
                        appointmentAdapter.notify()
                    }

                }
            }

            override fun onFailure(call: Call<ArrayList<Appointment>>, t: Throwable) {
                Toast.makeText(this@MisCitasActivity, "Error: no se pudo cargar las citas médicas.", Toast.LENGTH_SHORT).show()
            }

        })
    }

    private fun refreshAppointments() {
        val swipeRefresh = findViewById<SwipeRefreshLayout>(R.id.swipe_to_refresh)
        swipeRefresh.setOnRefreshListener {
            loadAppointments()
            swipeRefresh.isRefreshing = false
            Toast.makeText(this, "Citas actualizadas", Toast.LENGTH_SHORT).show()

        }
    }

}