package com.example.classroombtu.models

data class Student(
    val name: String? = null,
    val surname: String? = null,
    val email: String? = null,
    val subjects: ArrayList<Subject>
)
