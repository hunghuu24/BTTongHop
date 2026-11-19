package com.ute.baitap19_11.repository

import com.ute.baitap19_11.data.StudentDao
import com.ute.baitap19_11.model.Student
import com.ute.baitap19_11.network.RetrofitClient
import com.ute.baitap19_11.network.StudentApiService
import com.ute.baitap19_11.network.toEntity
import kotlinx.coroutines.flow.Flow

class StudentRepository(private val dao: StudentDao, private val api: StudentApiService = RetrofitClient.apiService) {
    val allStudents: Flow<List<Student>> = dao.getAllStudents()
    suspend fun insert(student: Student) = dao.insertStudent(student)
    suspend fun delete(student: Student) = dao.deleteStudent(student)
    suspend fun syncFromCloud(): Result<Int> = runCatching {
        val students = api.getStudents().map { it.toEntity() }
        students.forEach { remote ->
            val existing = dao.getStudentByCode(remote.studentCode)
            dao.insertStudent(if (existing == null) remote else remote.copy(id = existing.id))
        }
        students.size
    }
}
