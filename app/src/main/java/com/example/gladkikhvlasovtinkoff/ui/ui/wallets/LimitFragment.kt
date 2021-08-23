package com.example.gladkikhvlasovtinkoff.ui.ui.wallets

import android.os.Bundle
import android.text.InputType
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.gladkikhvlasovtinkoff.MainActivity
import com.example.gladkikhvlasovtinkoff.R
import com.example.gladkikhvlasovtinkoff.databinding.FragmentLimitBinding
import com.example.gladkikhvlasovtinkoff.extension.setupTextStyleAndObserve
import com.example.gladkikhvlasovtinkoff.ui.ui.toolbar.ToolbarFragment
import com.example.gladkikhvlasovtinkoff.ui.ui.toolbar.ToolbarHolder


class LimitFragment : ToolbarFragment() {
    private var _binding: FragmentLimitBinding? = null
    private val binding get() = _binding!!

    private val args : LimitFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLimitBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        configureToolbar()
        initLayout()

        binding.layoutEnter.buttonConfirmOperationValue.setOnClickListener {
            //TODO сохранение лимита и передача данных
            val action = LimitFragmentDirections.actionLimitFragmentToNewWalletFragment(args.walletDataSample)
            findNavController().navigate(action)
            (activity as ToolbarHolder).setToolbarNavigationButtonIcon(R.drawable.ic_back_arrow)
        }

    }

    override fun configureToolbar() {
        activity?.let { activity ->
            val holder  = activity as ToolbarHolder
            holder.setToolbarTitle("")
            holder.setToolbarNavigationButtonIcon(R.drawable.ic_close)
        }
    }

    private fun initLayout() {
        binding.layoutEnter.newOperationValueField.inputType =
            InputType.TYPE_CLASS_NUMBER or InputType.TYPE_NUMBER_FLAG_DECIMAL

        binding.layoutEnter.newOperationValueField.setupTextStyleAndObserve(
            binding.layoutEnter.buttonConfirmOperationValue,
        )

        binding.layoutEnter.buttonConfirmOperationValue.text = getString(R.string.save)
        binding.layoutEnter.newOperationValueBlock.hint = getString(R.string.limit_description)
    }

}