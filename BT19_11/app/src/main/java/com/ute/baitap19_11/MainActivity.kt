package com.ute.baitap19_11

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.ute.baitap19_11.data.AppDatabase
import com.ute.baitap19_11.repository.StudentRepository
import com.ute.baitap19_11.ui.StudentListFragment
import com.ute.baitap19_11.viewmodel.StudentViewModel
import com.ute.baitap19_11.viewmodel.StudentViewModelFactory

class MainActivity : AppCompatActivity() {
    private val repository by lazy { StudentRepository(AppDatabase.getDatabase(this).studentDao()) }
    val viewModelFactory by lazy { StudentViewModelFactory(repository) }
    private val viewModel: StudentViewModel by viewModels { viewModelFactory }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        if (savedInstanceState == null) supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container_view, StudentListFragment()).commit()
    }
}
