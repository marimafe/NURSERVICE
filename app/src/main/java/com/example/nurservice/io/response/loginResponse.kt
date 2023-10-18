package com.example.nurservice.io.response

import com.example.nurservice.model.User

data class loginResponse(
    val success: Boolean,
    val user: User,
    val jwt: String
)
