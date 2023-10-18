package com.example.nurservice.model

data class Speciality(
    val  id: Int,
    val name: String
){
    override fun toString(): String {
        return name
    }
}

