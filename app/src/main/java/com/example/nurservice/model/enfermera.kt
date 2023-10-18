package com.example.nurservice.model

data class enfermera(
    val id: Int,
    val name: String
){
    override fun toString(): String {
        return name
    }
}
