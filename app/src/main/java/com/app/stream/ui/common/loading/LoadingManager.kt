package com.app.stream.ui.common.loading

import androidx.fragment.app.FragmentManager

class LoadingManager(
    private val fragmentManager: FragmentManager
) : LoadingController {

    override fun show(message: String?) {
        if (fragmentManager.isStateSaved || isShowing()) return
        LoadingDialogFragment.newInstance(message).show(fragmentManager, LoadingDialogFragment.TAG)
    }

    override fun hide() {
        if (fragmentManager.isStateSaved) return
        val dialog = fragmentManager.findFragmentByTag(LoadingDialogFragment.TAG)
            as? LoadingDialogFragment
        dialog?.dismissAllowingStateLoss()
    }

    override fun isShowing(): Boolean {
        return fragmentManager.findFragmentByTag(LoadingDialogFragment.TAG) is LoadingDialogFragment
    }
}
