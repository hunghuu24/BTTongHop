package com.ute.baitap19_11.network

import com.ute.baitap19_11.model.Student

data class StudentDto(val id: Int, val name: String, val email: String)

fun StudentDto.toEntity() = Student(
    studentCode = "API${id.toString().padStart(6, '0')}",
    fullName = name,
    email = email,
    gpa = 0.0
)
