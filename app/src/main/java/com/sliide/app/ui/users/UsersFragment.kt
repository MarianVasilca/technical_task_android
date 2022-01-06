package com.sliide.app.ui.users

import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.navigation.fragment.findNavController
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.snackbar.Snackbar
import com.sliide.app.R
import com.sliide.app.data.model.User
import com.sliide.app.databinding.FragmentUsersBinding
import com.sliide.app.ui.add_user.AddUserFragment.Companion.REFRESH_DATA_KEY
import com.sliide.app.util.extension.visibleIf
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class UsersFragment : Fragment() {

    private var optionalBinding: FragmentUsersBinding? = null

    // can be used only between [onCreateView] and [onDestroyView]
    private val binding get() = optionalBinding!!

    private val viewModel: UsersViewModel by viewModels()

    private val adapter by lazy { UserAdapter(::onItemLongClick) }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        createBinding(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupUI()
        setupSubscribers()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        optionalBinding = null
    }

    private fun createBinding(inflater: LayoutInflater) {
        optionalBinding = FragmentUsersBinding.inflate(inflater)
    }

    private fun setupSubscribers() {
        subscribeToViewModelEvents()
        subscribeToUiEvents()
        subscribeToChildFragmentEvents()
    }

    private fun subscribeToViewModelEvents() {
        viewModel.isLoading.observe(viewLifecycleOwner, ::setIsLoadingVisibility)
        viewModel.isError.observe(viewLifecycleOwner) { onErrorResponse() }
        viewModel.users.observe(viewLifecycleOwner, ::onNewItems)
        viewModel.refreshDataEvent.observe(viewLifecycleOwner) { viewModel.refreshData() }
        viewModel.addUserClickEvent.observe(viewLifecycleOwner) { onAddUserClicked() }
    }

    private fun subscribeToUiEvents() {
        binding.fabAdd.setOnClickListener { viewModel.onAddUserClicked() }
    }

    private fun setIsLoadingVisibility(isLoading: Boolean) {
        binding.loadingProgressBar.visibleIf(isLoading)
    }

    private fun onErrorResponse() {
        Snackbar.make(requireView(), R.string.error_network_request, Snackbar.LENGTH_LONG)
            .show()
    }

    private fun setupUI() {
        binding.rvUsers.adapter = adapter
    }

    private fun onNewItems(items: List<User>) {
        adapter.submitList(items)
    }

    private fun onItemLongClick(item: User) {
        showUserDeleteConfirmationDialog(item)
    }

    private fun showUserDeleteConfirmationDialog(item: User) {
        MaterialAlertDialogBuilder(requireContext())
            .setMessage(R.string.dialog_delete_user_message)
            .setPositiveButton(R.string.dialog_delete_user_positive) { dialog, _ ->
                onConfirmDeleteUser(dialog, item)
            }
            .setNegativeButton(R.string.dialog_delete_user_negative, null)
            .show()
    }

    private fun onConfirmDeleteUser(dialog: DialogInterface, item: User) {
        viewModel.deleteUser(item)
        dialog.dismiss()
    }

    private fun onAddUserClicked() {
        findNavController().navigate(R.id.action_users_to_addUser)
    }

    private fun subscribeToChildFragmentEvents() {
        val navBackStackEntry = findNavController().getBackStackEntry(R.id.users)

        val observer = LifecycleEventObserver { _, event ->
            val key = REFRESH_DATA_KEY
            if (event == Lifecycle.Event.ON_RESUME
                && navBackStackEntry.savedStateHandle.contains(key)
            ) {
                val result = navBackStackEntry.savedStateHandle.get<Boolean>(key)
                if (result == true) {
                    viewModel.refreshData()
                    navBackStackEntry.savedStateHandle.set(key, null)
                }
            }
        }

        navBackStackEntry.lifecycle.addObserver(observer)

        viewLifecycleOwner.lifecycle.addObserver(LifecycleEventObserver { _, event ->
            if (event == Lifecycle.Event.ON_DESTROY) {
                navBackStackEntry.lifecycle.removeObserver(observer)
            }
        })
    }
}