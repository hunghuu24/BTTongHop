package com.ute.baitap19_11.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.ute.baitap19_11.databinding.ItemStudentRoomBinding
import com.ute.baitap19_11.model.Student

class StudentAdapter(
    private val onClick: (Student) -> Unit,
    private val onDelete: (Student) -> Unit
) : ListAdapter<Student, StudentAdapter.StudentHolder>(Diff) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = StudentHolder(
        ItemStudentRoomBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    )
    override fun onBindViewHolder(holder: StudentHolder, position: Int) = holder.bind(getItem(position))

    inner class StudentHolder(private val binding: ItemStudentRoomBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(student: Student) {
            binding.tvFullName.text = student.fullName
            binding.tvStudentCode.text = "MSSV ${student.studentCode} · ${student.major}"
            binding.tvEmail.text = student.email
            binding.tvGpa.text = "GPA ${"%.2f".format(student.gpa)}"
            binding.tvAvatar.text = student.fullName.trim().take(1).uppercase()
            binding.root.setOnClickListener { onClick(student) }
            binding.btnDelete.setOnClickListener { onDelete(student) }
        }
    }
    private object Diff : DiffUtil.ItemCallback<Student>() {
        override fun areItemsTheSame(oldItem: Student, newItem: Student) = oldItem.id == newItem.id
        override fun areContentsTheSame(oldItem: Student, newItem: Student) = oldItem == newItem
    }
}
