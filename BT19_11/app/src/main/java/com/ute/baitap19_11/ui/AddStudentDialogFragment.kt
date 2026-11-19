package com.ute.baitap19_11.ui

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.activityViewModels
import com.ute.baitap19_11.MainActivity
import com.ute.baitap19_11.databinding.DialogAddStudentBinding
import com.ute.baitap19_11.model.Student
import com.ute.baitap19_11.viewmodel.StudentViewModel

class AddStudentDialogFragment : DialogFragment() {
    private val viewModel: StudentViewModel by activityViewModels { (requireActivity() as MainActivity).viewModelFactory }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val binding = DialogAddStudentBinding.inflate(LayoutInflater.from(requireContext()))
        return AlertDialog.Builder(requireContext())
            .setTitle("Thêm sinh viên")
            .setView(binding.root)
            .setPositiveButton("Lưu", null)
            .setNegativeButton("Hủy", null)
            .create().also { dialog ->
                dialog.setOnShowListener {
                    dialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener {
                        val name = binding.etFullName.text.toString().trim()
                        val code = binding.etStudentCode.text.toString().trim()
                        val email = binding.etEmail.text.toString().trim()
                        val major = binding.etMajor.text.toString().trim()
                        val gpa = binding.etGpa.text.toString().trim().toDoubleOrNull()
                        when {
                            name.isBlank() || code.isBlank() -> Toast.makeText(requireContext(), "Vui lòng nhập họ tên và MSSV", Toast.LENGTH_SHORT).show()
                            gpa == null || gpa !in 0.0..4.0 -> Toast.makeText(requireContext(), "GPA phải nằm trong khoảng 0 đến 4", Toast.LENGTH_SHORT).show()
                            else -> {
                                viewModel.insert(Student(fullName = name, studentCode = code, email = email.ifBlank { "$code@ute.udn.vn" }, gpa = gpa, major = major.ifBlank { "Công Nghệ Thông Tin" }))
                                dialog.dismiss()
                            }
                        }
                    }
                }
            }
    }
}
