package com.example.hw_room.ui.dialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.DialogFragment
import com.example.hw_room.databinding.FragmentDialogEditNameBinding
import com.example.hw_room.model.User
import com.example.hw_room.utils.RESULT_KEY
import com.example.hw_room.utils.USER_RESULT_KEY
import com.example.hw_room.utils.getTextOrNullAndValidate

class EditNameDialogFragment private constructor() : DialogFragment() {

    private var _binding: FragmentDialogEditNameBinding? = null
    private val binding get() = requireNotNull(_binding) { "biding is null $_binding" }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return FragmentDialogEditNameBinding.inflate(layoutInflater, container, false)
            .also { _binding = it }
            .root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val user = arguments?.getSerializable(USER_KEY) as? User ?: return

        with(binding) {
            firstNameEditText.setText(user.firstName)
            lastNameEditText.setText(user.lastName)
            initButtons(user)
        }
    }

    private fun initButtons(user: User) = with(binding) {
        submitButton.setOnClickListener {

            val firstNameResult = firstNameContainer.getTextOrNullAndValidate()
            val lastNameResult = lastNameContainer.getTextOrNullAndValidate()

            firstNameResult ?: return@setOnClickListener
            lastNameResult ?: return@setOnClickListener

            parentFragmentManager.setFragmentResult(
                RESULT_KEY,
                bundleOf(
                    USER_RESULT_KEY to user.copy(
                        firstName = firstNameResult.toString(),
                        lastName = lastNameResult.toString()
                    )
                )
            )
            dismiss()
        }

        cancelButton.setOnClickListener {
            dismiss()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {

        private const val USER_KEY = "user_key"

        fun getInstance(user: User): EditNameDialogFragment {
            return EditNameDialogFragment().apply {
                arguments = bundleOf(USER_KEY to user)
            }
        }
    }
}
