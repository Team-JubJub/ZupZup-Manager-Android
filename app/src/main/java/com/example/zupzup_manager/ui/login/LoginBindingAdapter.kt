package com.example.zupzup_manager.ui.login

import android.view.View
import android.widget.ProgressBar
import android.widget.Toast
import androidx.databinding.BindingAdapter
import com.example.zupzup_manager.ui.common.UiEventState

@BindingAdapter("loginEventState")
fun bindReservationListToRecyclerView(
    progress: ProgressBar,
    loginEventState: UiEventState
) {
    when (loginEventState) {
        is UiEventState.Processing -> {
            progress.visibility = View.VISIBLE
        }
        is UiEventState.Complete -> {
            progress.visibility = View.GONE
        }
        is UiEventState.Fail -> {
            progress.visibility = View.GONE
            Toast.makeText(progress.context, loginEventState.errorMessage, Toast.LENGTH_SHORT)
                .show()
        }
    }
}