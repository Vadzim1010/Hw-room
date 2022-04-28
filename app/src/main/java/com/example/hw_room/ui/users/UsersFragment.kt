package com.example.hw_room.ui.users

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.PopupMenu
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.hw_room.R
import com.example.hw_room.adapter.UserAdapter
import com.example.hw_room.databinding.FragmentUsersBinding
import com.example.hw_room.listener.ItemClickListener
import com.example.hw_room.model.User
import com.example.hw_room.repository
import com.example.hw_room.ui.dialog.EditNameDialogFragment
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
        initSearchMenu()
        updateUser()
        initSwipe()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun showPopUpMenu(user: User, view: View) {
        val popup = PopupMenu(requireContext(), view)
        popup.menuInflater.inflate(R.menu.menu_card_popup, popup.menu)
        popup.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.delete -> {
                    showDeleteConfirmationDialog(user)
                    true
                }
                R.id.edit -> {
                    showEditDialog(user)
                    true
                }
                else -> false
            }
        }
        popup.show()
    }

    private fun initSwipe() {
        val swipe =
            object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
                override fun onMove(
                    recyclerView: RecyclerView,
                    viewHolder: RecyclerView.ViewHolder,
                    target: RecyclerView.ViewHolder
                ): Boolean {
                    return false
                }

                override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                    val user = userAdapter.currentList[viewHolder.adapterPosition]
                    showDeleteConfirmationDialog(user)
                    userAdapter.notifyItemChanged(viewHolder.adapterPosition)
                }
            }
        ItemTouchHelper(swipe).attachToRecyclerView(binding.recycler)
    }

    private fun updateUser() {
        childFragmentManager.setFragmentResultListener(RESULT_KEY, this) { _, bundle ->
            val newUser =
                bundle.getSerializable(USER_RESULT_KEY) as? User ?: return@setFragmentResultListener

            viewModel.updateUser(newUser)
        }
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
            addBottomSpaceDecorationRes(resources.getDimensionPixelSize(R.dimen.margin_bottom))
        }
    }

    private fun initSearchMenu() {
        binding.toolbar.inflateMenu(R.menu.menu_users)
        binding.toolbar.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.search -> {
                    val search = it.actionView as? SearchView
                    search?.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                        override fun onQueryTextSubmit(query: String?): Boolean {
                            //do noting
                            return false
                        }

                        override fun onQueryTextChange(newText: String?): Boolean {
                            viewModel.getUsers().observe(viewLifecycleOwner) { list ->
                                userAdapter.submitList(list.filter { user ->
                                    user.firstName.contains(newText ?: "", true) ||
                                            user.lastName.contains(newText ?: "", true)
                                })
                            }
                            return true
                        }
                    })
                    true
                }
                else -> false
            }
        }
    }

    private fun showDeleteConfirmationDialog(user: User) {
        AlertDialog.Builder(requireContext())
            .setMessage("Do you really want to delete this record?")
            .setPositiveButton(R.string.yes) { _, _ ->
                viewModel.deleteUser(user)
            }
            .setNegativeButton(R.string.no, null)
            .show()
    }

    private fun showEditDialog(user: User) {
        EditNameDialogFragment.getInstance(user)
            .show(childFragmentManager, null)
    }

    override fun onPopupMenuItemClickListener(user: User, view: View) {
        showPopUpMenu(user, view)
    }
}
