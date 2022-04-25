package com.example.hw_room.ui.users

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.hw_room.adapter.UserAdapter
import com.example.hw_room.databinding.FragmentUsersBinding
import com.example.hw_room.repository

class UsersFragment : Fragment() {

    private var _binding: FragmentUsersBinding? = null
    private val binding get() = requireNotNull(_binding) { "_binding is $_binding" }
    private val viewModel by viewModels<UsersViewModel> {
        UsersViewModelFactory(requireContext().repository)
    }
    private val userAdapter by lazy {
        UserAdapter(requireContext())
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
        }
    }
}