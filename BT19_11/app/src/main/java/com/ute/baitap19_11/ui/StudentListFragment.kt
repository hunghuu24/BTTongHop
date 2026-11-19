package com.ute.baitap19_11.ui

import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.ute.baitap19_11.MainActivity
import com.ute.baitap19_11.R
import com.ute.baitap19_11.databinding.FragmentStudentListBinding
import com.ute.baitap19_11.model.Student
import com.ute.baitap19_11.ui.adapter.StudentAdapter
import com.ute.baitap19_11.viewmodel.StudentViewModel

class StudentListFragment : Fragment() {
    private var _binding: FragmentStudentListBinding? = null
    private val binding get() = checkNotNull(_binding)
    private val viewModel: StudentViewModel by activityViewModels { (requireActivity() as MainActivity).viewModelFactory }
    private lateinit var studentAdapter: StudentAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentStudentListBinding.inflate(inflater, container, false)
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        studentAdapter = StudentAdapter({ showDetails(it) }, { confirmDelete(it) })
        binding.rvStudents.layoutManager = LinearLayoutManager(requireContext())
        binding.rvStudents.adapter = studentAdapter
        binding.rvStudents.setHasFixedSize(true)
        viewModel.allStudents.observe(viewLifecycleOwner) { students ->
            studentAdapter.submitList(students)
            binding.layoutEmpty.visibility = if (students.isEmpty()) View.VISIBLE else View.GONE
            binding.tvStudentCountBadge.text = "${students.size} sinh viên"
        }
        viewModel.statusMessage.observe(viewLifecycleOwner) { if (!it.isNullOrBlank()) Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show() }
        binding.fabAdd.setOnClickListener { AddStudentDialogFragment().show(childFragmentManager, "add_student") }
        binding.toolbar.setOnMenuItemClickListener { item ->
            if (item.itemId == R.id.action_sync) { viewModel.syncFromCloud(); true } else false
        }
    }
    private fun confirmDelete(student: Student) {
        AlertDialog.Builder(requireContext()).setTitle("Xác nhận xóa")
            .setMessage("Bạn có chắc muốn xóa ${student.fullName} khỏi danh sách?")
            .setPositiveButton("Xóa") { _, _ -> viewModel.delete(student) }
            .setNegativeButton("Hủy", null).show()
    }
    private fun showDetails(student: Student) {
        AlertDialog.Builder(requireContext()).setTitle("Thông tin sinh viên")
            .setMessage("MSSV: ${student.studentCode}\nHọ tên: ${student.fullName}\nEmail: ${student.email}\nNgành: ${student.major}\nGPA: ${"%.2f".format(student.gpa)}")
            .setPositiveButton("Đóng", null).show()
    }
    override fun onDestroyView() { super.onDestroyView(); _binding = null }
}
