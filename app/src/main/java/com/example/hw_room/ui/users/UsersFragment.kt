package com.example.hw_room.ui.users

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.hw_room.R
import com.example.hw_room.adapter.UserAdapter
import com.example.hw_room.databinding.FragmentUsersBinding
import com.example.hw_room.listener.ItemClickListener
import com.example.hw_room.model.User
import com.example.hw_room.repository
import com.example.hw_room.utils.RESULT_KEY
import com.example.hw_room.utils.USER_RESULT_KEY
import com.example.hw_room.utils.addBottomSpaceDecorationRes

class UsersFragment : Fragment(), ItemClickListener {

    private var _binding: FragmentUsersBinding? = null
    private val binding get() = requireNotNull(_binding) { "_binding is $_binding" }
    private val viewModel by viewModels<UsersViewModel> {
        UsersViewModelFactory(requireContext().repository)
    }
    private val userAdapter by lazy {
        UserAdapter(requireContext(), this)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return FragmentUsersBinding.inflate(layoutInflater, container, false)
            .also { _binding = it }
            .root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRecycler()
        observeUsers()

        childFragmentManager.setFragmentResultListener(RESULT_KEY, this) { _, bundle ->
            val newUser =
                bundle.getSerializable(USER_RESULT_KEY) as? User ?: return@setFragmentResultListener

            viewModel.updateUser(newUser)
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun observeUsers() {
        viewModel.getUsers().observe(viewLifecycleOwner) {
            userAdapter.submitList(it.toList())
        }
    }

    private fun initRecycler() = with(binding) {
        recycler.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = userAdapter
            addBottomSpaceDecorationRes(resources.getDimensionPixelSize(R.dimen.recycler_bottom_padding))
        }
    }

    private fun showAlertDialog(user: User) {
        AlertDialog.Builder(requireContext())
            .setMessage("Do you really want to delete this record?")
            .setPositiveButton(R.string.yes) { _, _ ->
                viewModel.deleteUser(user)
            }
            .setNegativeButton(R.string.no, null)
            .show()
    }

    private fun showUpdateDialog(user: User) {
        UpdateDialogFragment.getInstance(
            user
        )
            .show(childFragmentManager, null)
    }

    override fun onDeleteButtonItemClickListener(user: User) {
        showAlertDialog(user)
    }

    override fun onUpdateItemClickListener(user: User) {
        showUpdateDialog(user)
    }
}
