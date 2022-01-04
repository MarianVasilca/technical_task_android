package com.sliide.app.ui.users

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.sliide.app.databinding.FragmentUsersBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class UsersFragment : Fragment() {

    private var optionalBinding: FragmentUsersBinding? = null

    // can be used only between [onCreateView] and [onDestroyView]
    private val binding get() = optionalBinding!!

    private val vm: UsersVM by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        createBinding(inflater)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        optionalBinding = null
    }

    private fun createBinding(inflater: LayoutInflater) {
        optionalBinding = FragmentUsersBinding.inflate(inflater)
    }
}