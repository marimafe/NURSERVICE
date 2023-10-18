package com.example.nurservice.model

data class User(
    val id:Int,
    val name:String,
    val email: String,
    val cedula: String,
    val address: String,
    val phone: String,
    val role:String
)
