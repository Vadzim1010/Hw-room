package com.example.hw_room.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.example.hw_room.R
import com.example.hw_room.databinding.FragmentBottomNavBinding

class BottomNavFragment : Fragment() {

    private var _binding: FragmentBottomNavBinding? = null
    private val binding get() = requireNotNull(_binding) { "binding is null $_binding" }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return FragmentBottomNavBinding.inflate(layoutInflater, container, false)
            .also { _binding = it }
            .root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(binding) {
            val nestedController =
                (childFragmentManager.findFragmentById(R.id.page_container) as NavHostFragment)
                    .navController
            bottomNavigation.setupWithNavController(nestedController)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}