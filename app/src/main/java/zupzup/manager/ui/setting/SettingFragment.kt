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
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.kakao.sdk.common.util.KakaoCustomTabsClient
import com.kakao.sdk.talk.TalkApiClient
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import zupzup.manager.R
import zupzup.manager.databinding.FragmentSettingBinding
import zupzup.manager.ui.common.User
import zupzup.manager.ui.login.LoginActivity


@AndroidEntryPoint
class SettingFragment : Fragment() {

    private lateinit var binding: FragmentSettingBinding
    private val settingViewModel: SettingViewModel by viewModels()
    private var storeMatterBottomSheet: StoreMatterBottomSheetFragment? = null

    private val settingClickListener = object : SettingClickListener {
        override fun navigateToStore() {
            val storeInfo = settingViewModel.storeInfo.value
            val action =
                SettingFragmentDirections.actionFragSettingToStoreFragment(storeInfo)
            findNavController().navigate(action)
        }

        override fun signout() {
            showSignOutAlertDialog()
        }

        override fun navigateToLeave() {
            val sellerName = settingViewModel.storeInfo.value.sellerName
            val action =
                SettingFragmentDirections.actionFragSettingToLeaveFragment(sellerName)
            findNavController().navigate(action)
        }

        override fun modifyStoreMatter() {
            storeMatterBottomSheet =
                StoreMatterBottomSheetFragment(settingViewModel.storeInfo.value.saleMatters)
            storeMatterBottomSheet!!.setOnDismissListener {
                settingViewModel.getStoreInfo(User.getStoreId())
            }
            storeMatterBottomSheet!!.show(parentFragmentManager, null)
        }

        override fun kakaoTalkChannelBtnClicked() {
            // 카카오톡 채널 채팅 URL
            val url = TalkApiClient.instance.chatChannelUrl("_qMxbxlG")

            // CustomTabs 로 열기
            KakaoCustomTabsClient.openWithDefault(requireContext(), url)

        }
    }

    private fun showSignOutAlertDialog() {
        AlertDialog.Builder(requireContext())
            .setTitle("로그아웃 하기")
            .setMessage("정말 로그아웃 하시겠습니까?")
            .setPositiveButton("로그아웃") { dialog, which ->
                lifecycleScope.launch {
                    settingViewModel.signOut()
                    val loginIntent = Intent(context, LoginActivity::class.java)
                    loginIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK + Intent.FLAG_ACTIVITY_NEW_TASK)
                    startActivity(loginIntent)
                }
            }
            .setNegativeButton(
                "취소"
            ) { dialog, which -> }
            .create()
            .show()
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSettingBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initBinding()
        initCollect()
    }

    private fun initCollect() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {
                    settingViewModel.openStatus.collect {
                        Log.d("TAG", "initCollect:$it ")
                        if (it) {
                            binding.toggleBtn.setImageResource(R.drawable.ic_switch_on)
                        } else {
                            binding.toggleBtn.setImageResource(R.drawable.ic_switch_off)
                        }
                    }
                }
            }
        }
    }

    override fun onResume() {
        super.onResume()
        settingViewModel.getStoreInfo(User.getStoreId())
    }

    private fun initBinding() {
        with(binding) {
            lifecycleOwner = viewLifecycleOwner
            viewModel = settingViewModel
            clickListener = settingClickListener
            toggleBtn.setOnClickListener {
                Log.d("TAG", "initBinding: ")
                val isOpen = settingViewModel.openStatus.value
                val titleText = if (!isOpen) "가게 영업하기" else "가게 문 닫기"
                val messageText = if (!isOpen) "가게를 영업합니다." else "가게 문을 닫습니다."

                AlertDialog.Builder(requireContext())
                    .setTitle(titleText)
                    .setMessage(messageText)
                    .setPositiveButton("확인", object : DialogInterface.OnClickListener {
                        override fun onClick(dialog: DialogInterface, which: Int) {
                            Log.d("가게 영업", "확인")
                            settingViewModel.changeStoreStatus(!isOpen)
                        }
                    })
                    .setNegativeButton("취소", object : DialogInterface.OnClickListener {
                        override fun onClick(dialog: DialogInterface, which: Int) {
                            Log.d("가게 영업", "취소")
                        }
                    })
                    .create()
                    .show()


            }
        }
    }

    interface SettingClickListener {
        fun navigateToStore()
        fun signout()
        fun navigateToLeave()
        fun modifyStoreMatter()

        fun kakaoTalkChannelBtnClicked()
    }
}