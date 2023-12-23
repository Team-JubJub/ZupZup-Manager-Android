package zupzup.manager.ui.setting

import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import zupzup.manager.databinding.FragmentLeaveBinding
import zupzup.manager.ui.login.LoginActivity


@AndroidEntryPoint
class LeaveFragment : Fragment() {

    private lateinit var binding: FragmentLeaveBinding
    private val settingViewModel: SettingViewModel by viewModels()

    private val leaveClickListener = object : LeaveClickListener {
        override fun navigateToBackStack() {
            findNavController().popBackStack()
        }

        override fun leaveZupZup() {
            AlertDialog.Builder(requireContext())
                .setTitle("회원 탈퇴하기")
                .setMessage("정말 회원 탈퇴하시나요?")
                .setPositiveButton("확인", object : DialogInterface.OnClickListener {
                    override fun onClick(dialog: DialogInterface, which: Int) {
                        Log.d("회원 탈퇴", "확인")
                        lifecycleScope.launch {
                            settingViewModel.leaveZupZup()
                            Log.d("TAG", "회원탈퇴 완료 -> 액티비티 이동")
                            val loginIntent = Intent(context, LoginActivity::class.java)
                            loginIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK + Intent.FLAG_ACTIVITY_NEW_TASK)
                            startActivity(loginIntent)
                            Log.d("TAG", "액티비티 이동 완료")
                        }
                    }
                })
                .setNegativeButton("취소", object : DialogInterface.OnClickListener {
                    override fun onClick(dialog: DialogInterface, which: Int) {
                        Log.d("회원 탈퇴", "취소")
                        findNavController().popBackStack()
                    }
                })
                .create()
                .show()
        }
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentLeaveBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initBinding()
    }

    private fun initBinding() {
        with(binding) {
            lifecycleOwner = viewLifecycleOwner
            clickListener = leaveClickListener
            sellerName = LeaveFragmentArgs.fromBundle(requireArguments()).sellerName
        }
    }

    interface LeaveClickListener {
        fun navigateToBackStack()
        fun leaveZupZup()
    }
}