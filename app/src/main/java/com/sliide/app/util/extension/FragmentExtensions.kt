package com.sliide.app.util.extension

import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController

fun <T> Fragment.setResultToParentFragment(key: String, value: T?) {
    findNavController().previousBackStackEntry?.savedStateHandle?.set(key, value)
}