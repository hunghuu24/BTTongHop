package com.ute.baitap19_11.data

import androidx.room.*
import com.ute.baitap19_11.model.Student
import kotlinx.coroutines.flow.Flow

@Dao
interface StudentDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE) suspend fun insertStudent(student: Student): Long
    @Insert(onConflict = OnConflictStrategy.REPLACE) suspend fun insertAll(students: List<Student>)
    @Update suspend fun updateStudent(student: Student)
    @Delete suspend fun deleteStudent(student: Student)
    @Query("DELETE FROM students_table WHERE id = :id") suspend fun deleteStudentById(id: Int)
    @Query("SELECT * FROM students_table ORDER BY id DESC") fun getAllStudents(): Flow<List<Student>>
    @Query("SELECT * FROM students_table WHERE studentCode = :code LIMIT 1") suspend fun getStudentByCode(code: String): Student?
}
