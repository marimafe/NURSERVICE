package com.example.nurservice.io
import com.example.nurservice.io.response.SimpleResponse
import com.example.nurservice.io.response.loginResponse
import com.example.nurservice.model.Appointment
import com.example.nurservice.model.Schedule
import com.example.nurservice.model.Speciality
import com.example.nurservice.model.enfermera
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query


interface ApiService {
    @POST( value = "login")
    fun postLogin(@Query(value = "email") email: String, @Query(value = "password") password: String):
            retrofit2.Call<loginResponse>

    @POST( value = "logout" )
    fun postLogout(@Header(value = "Authorization") authHeader: String):
            retrofit2.Call<Void>
    @GET(value = "especialidades")
     fun getespecialties():Call<ArrayList<Speciality>>

    @GET(value = "especialidades/{specialty}/medicos")
    fun getDoctors(@Path(value = "specialty") specialtyId: Int): Call<ArrayList<enfermera>>

    @GET(value = "horario/horas")
    fun getHours(@Query(value = "doctor_id") doctorId: Int, @Query(value = "date") date: String):
            Call<Schedule>
    @GET(value = "appointments")
    fun getAppointments(@Header("Authorization") authHeader: String):
            Call<ArrayList<Appointment>>

    @POST(value = "appointments")
    @Header("Accept: application/json")
    fun storeAppointments(
        @Header("Authorization") authHeader: String,
        @Query("description") description: String,
        @Query("scheduled_date") scheduledDate: String,
        @Query("scheduled_time") scheduledTime: String,
        @Query("type") type: String,
        @Query("doctor_id") doctorId: Int,
        @Query("specialty_id") specialtyId: Int
    ): Call<SimpleResponse>

    @POST(value = "register")
    @Header("Accept:application/json")
    fun postRegister(
        @Query("name") name: String,
        @Query("email") email: String,
        @Query("password") password: String,
        @Query("password_confirmation") passwordConfirmation: String
    ): Call<loginResponse>

    @POST(value = "fcm/token")
    fun postToken(
        @Header("Authorization") authHeader: String,
        @Query("device_token") token: String
    ): Call<Void>
    companion object Factory{
        private const val BASE_URL = "http://192.168.100.23:8000/api/"
        fun create(): ApiService {
            val retrofit = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
            return retrofit.create(ApiService::class.java)
        }
    }
}
