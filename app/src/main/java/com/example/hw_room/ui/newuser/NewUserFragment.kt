package com.example.hw_room.ui.newuser

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.hw_room.databinding.FragmentNewUserBinding
import com.example.hw_room.model.User
import com.example.hw_room.repository
import com.example.hw_room.utils.getTextAndValidate

class NewUserFragment : Fragment() {

    private var _binding: FragmentNewUserBinding? = null
    private val binding get() = requireNotNull(_binding) { "binding is null $_binding" }

    private val viewModel by viewModels<UserDescViewModel> {
        UserDescViewModelFactory(requireContext().repository)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return FragmentNewUserBinding.inflate(layoutInflater, container, false)
            .also { _binding = it }
            .root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initButtons()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun initButtons() = with(binding) {
        addButton.setOnClickListener {

            val firstName = firstNameContainer.getTextAndValidate()
            val lastName = lastNameContainer.getTextAndValidate()

            if (firstName.isNotBlank() && lastName.isNotBlank()) {
                viewModel.insert(
                    User(firstName = firstName.toString(), lastName = lastName.toString())
                )
            }
        }
    }
}