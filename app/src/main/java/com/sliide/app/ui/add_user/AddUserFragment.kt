package com.sliide.app.ui.add_user

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.StringRes
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import com.google.android.material.snackbar.Snackbar
import com.sliide.app.R
import com.sliide.app.databinding.FragmentAddUserBinding
import com.sliide.app.util.extension.invisibleIf
import com.sliide.app.util.extension.setResultToParentFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AddUserFragment : DialogFragment() {

    private var optionalBinding: FragmentAddUserBinding? = null

    // can be used only between [onCreateView] and [onDestroyView]
    private val binding get() = optionalBinding!!

    private val viewModel: AddUserViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NO_TITLE, R.style.DialogStyle)
    }

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
        setupSubscribers()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        optionalBinding = null
    }

    private fun createBinding(inflater: LayoutInflater) {
        optionalBinding = FragmentAddUserBinding.inflate(inflater)
    }

    private fun setupSubscribers() {
        subscribeToViewModelEvents()
        subscribeToUiEvents()
    }

    private fun subscribeToViewModelEvents() {
        viewModel.successEvent.observe(viewLifecycleOwner) { onUserAdded() }
        viewModel.isLoading.observe(viewLifecycleOwner, ::setIsLoadingVisibility)
        viewModel.isRequestError.observe(viewLifecycleOwner) {
            showMessage(R.string.error_network_request)
        }
        viewModel.isInvalidInputError.observe(viewLifecycleOwner) {
            showMessage(R.string.add_user_error_invalid_input)
        }
    }

    private fun subscribeToUiEvents() {
        binding.btCancel.setOnClickListener { dismiss() }
        binding.btAdd.setOnClickListener { onAddUserClicked() }
    }

    private fun onAddUserClicked() {
        val name = binding.etName.text?.toString()
        val emailAddress = binding.etEmailAddress.text?.toString()
        viewModel.addUser(name, emailAddress)
    }

    private fun onUserAdded() {
        showMessage(R.string.add_user_successful)
        setResultToParentFragment(REFRESH_DATA_KEY, true)
        dismiss()
    }

    private fun setIsLoadingVisibility(isLoading: Boolean) {
        binding.loadingProgressBar.invisibleIf(!isLoading)
    }

    private fun showMessage(@StringRes resID: Int) {
        view?.let {
            Snackbar.make(it, resID, Snackbar.LENGTH_LONG)
                .show()
        }
    }

    companion object {
        const val REFRESH_DATA_KEY = "refreshData"
    }

}