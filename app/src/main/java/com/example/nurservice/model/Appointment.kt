package com.example.nurservice.model

import com.google.gson.annotations.SerializedName

data class Appointment(
    val id: Int,
    val description: String,
    val type: String,
    val status: String,

    @SerializedName("scheduled_date") val scheduledDate: String,
    @SerializedName("scheduled_time_12") val scheduledTime: String,
    @SerializedName("created_at") val createdAt: String,

    val specialty: Speciality,
    val doctor: enfermera
)
