package com.example.nurservice.model

data class Schedule(
    val morning: ArrayList<HourInterval>,
    val afternoon: ArrayList<HourInterval>
)
