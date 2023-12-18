package zupzup.manager.ui.setting

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import zupzup.manager.databinding.FragmentLeaveBinding


@AndroidEntryPoint
class LeaveFragment : Fragment() {

    private lateinit var binding: FragmentLeaveBinding

    private val leaveClickListener = object : LeaveClickListener {
        override fun navigateToBackStack() {
            findNavController().popBackStack()
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
    }
}