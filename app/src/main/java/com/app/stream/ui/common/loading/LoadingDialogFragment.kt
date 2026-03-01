package com.app.stream.ui.common.loading

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import androidx.appcompat.app.AlertDialog
import androidx.core.os.bundleOf
import androidx.fragment.app.DialogFragment
import com.app.stream.databinding.DialogGeneralLoadingBinding

class LoadingDialogFragment : DialogFragment() {

    private var _binding: DialogGeneralLoadingBinding? = null

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        _binding = DialogGeneralLoadingBinding.inflate(LayoutInflater.from(requireContext()))
        _binding?.tvLoadingMessage?.text = getMessageArg()

        return AlertDialog.Builder(requireContext())
            .setView(_binding?.root)
            .create()
            .apply {
                setCancelable(false)
                setCanceledOnTouchOutside(false)
            }
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

    private fun getMessageArg(): String {
        return arguments?.getString(ARG_MESSAGE).orEmpty().ifBlank { DEFAULT_MESSAGE }
    }

    companion object {
        const val TAG = "general_loading_dialog"

        private const val ARG_MESSAGE = "arg_message"
        private const val DEFAULT_MESSAGE = "Loading..."

        fun newInstance(message: String?): LoadingDialogFragment {
            return LoadingDialogFragment().apply {
                arguments = bundleOf(ARG_MESSAGE to message)
            }
        }
    }
}
