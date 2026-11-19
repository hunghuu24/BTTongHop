package com.ute.baitap19_11.viewmodel

import androidx.lifecycle.*
import com.ute.baitap19_11.model.Student
import com.ute.baitap19_11.repository.StudentRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class StudentViewModel(private val repository: StudentRepository) : ViewModel() {
    val allStudents: LiveData<List<Student>> = repository.allStudents.asLiveData(Dispatchers.IO)
    private val _statusMessage = MutableLiveData<String>()
    val statusMessage: LiveData<String> = _statusMessage

    fun insert(student: Student) = viewModelScope.launch(Dispatchers.IO) {
        runCatching { repository.insert(student) }
            .onSuccess { _statusMessage.postValue("Đã thêm sinh viên: ${student.fullName}") }
            .onFailure { _statusMessage.postValue("Không thể thêm sinh viên: ${it.localizedMessage}") }
    }
    fun delete(student: Student) = viewModelScope.launch(Dispatchers.IO) {
        runCatching { repository.delete(student) }
            .onSuccess { _statusMessage.postValue("Đã xóa sinh viên: ${student.fullName}") }
            .onFailure { _statusMessage.postValue("Không thể xóa sinh viên: ${it.localizedMessage}") }
    }
    fun syncFromCloud() = viewModelScope.launch(Dispatchers.IO) {
        val result = repository.syncFromCloud()
        _statusMessage.postValue(if (result.isSuccess) "Đã đồng bộ ${result.getOrNull()} sinh viên từ Cloud" else "Không thể kết nối máy chủ; dữ liệu Room vẫn được giữ nguyên")
    }
}

class StudentViewModelFactory(private val repository: StudentRepository) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(StudentViewModel::class.java)) return StudentViewModel(repository) as T
        throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
    }
}
