package com.example.gladkikhvlasovtinkoff

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.gladkikhvlasovtinkoff.databinding.FragmentConfirmOperationCreatedBinding
import com.example.gladkikhvlasovtinkoff.databinding.FragmentSelectOperationValueBinding

class FragmentSelectOperationValue : Fragment(){

    private var _binding: FragmentSelectOperationValueBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSelectOperationValueBinding.inflate(inflater)

        binding.layoutMyToolbar.text.text = getResources().getString(R.string.enter_sum)

        binding.buttonConfirmOperationValue.setOnClickListener {
            val action = FragmentSelectOperationValueDirections.actionFragmentSelectOperationValueToFragmentSelectOperationType()
            findNavController().navigate(action)
        }

        return binding.root
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}