package zupzup.manager.ui.setting

import android.app.AlertDialog
import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import zupzup.manager.R
import zupzup.manager.databinding.FragmentStoreMatterBottomsheetBinding
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class StoreMatterBottomSheetFragment(
    private val storeMatterValue: String
) : BottomSheetDialogFragment() {

    private val settingViewModel: SettingViewModel by viewModels()
    private lateinit var binding: FragmentStoreMatterBottomsheetBinding

    private val storeMatterClickListener = object : StoreMatterClickListener {
        override fun modifyStoreMatter(storeMatter: String) {
            AlertDialog.Builder(requireContext())
                .setTitle("가게 소개 수정하기")
                .setMessage("가게 소개글 수정을 완료합니다.")
                .setPositiveButton("확인", object : DialogInterface.OnClickListener {
                    override fun onClick(dialog: DialogInterface, which: Int) {
                        Log.d("가게 소개 수정", "확인")
                        settingViewModel.modifyStoreMatter(storeMatter)
                    }
                })
                .setNegativeButton("취소", object : DialogInterface.OnClickListener {
                    override fun onClick(dialog: DialogInterface, which: Int) {
                        Log.d("가게 소개 수정", "취소")
                        dismiss()
                    }
                })
                .create()
                .show()
        }
    }

    private var onDismissListener: DialogInterface.OnDismissListener? = null
    fun setOnDismissListener(onDismissListener: DialogInterface.OnDismissListener) {
        this.onDismissListener = onDismissListener
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return BottomSheetDialog(requireContext(), R.style.BottomSheetStyle).apply {
            behavior.state = BottomSheetBehavior.STATE_EXPANDED
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding =
            FragmentStoreMatterBottomsheetBinding.inflate(layoutInflater, container, false)
        dialog?.setCanceledOnTouchOutside(true)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initBinding()
        closeDialog()
    }

    private fun closeDialog() {
        lifecycleScope.launchWhenStarted {
            settingViewModel.closeDialogEvent.collect {
                dismiss()
            }
        }
    }

    override fun onDismiss(dialog: DialogInterface) {
        super.onDismiss(dialog)
        onDismissListener?.onDismiss(dialog)
    }

    private fun initBinding() {
        with(binding) {
            storeMatter = storeMatterValue
            clickListener = storeMatterClickListener
        }
    }

    interface StoreMatterClickListener {
        fun modifyStoreMatter(storeMatter: String)
    }
}