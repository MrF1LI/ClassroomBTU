package com.example.classroombtu.models

data class Subject(
    val id: Int = 0,
    val name: String? = null,
    val lecturer: String? = null,
    val code: String? = null,
    val point: Int = 0,
    val credit: Int = 0,
    val minCredit: Int = 0,
    val imageUrl: String? = null
)
