package com.example.gladkikhvlasovtinkoff.ui.ui.selectcategory

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.gladkikhvlasovtinkoff.MainActivity
import com.example.gladkikhvlasovtinkoff.R
import com.example.gladkikhvlasovtinkoff.databinding.FragmentSelectOperationCategoryBinding
import com.example.gladkikhvlasovtinkoff.extension.setDisabled
import com.example.gladkikhvlasovtinkoff.extension.setEnabled
import com.example.gladkikhvlasovtinkoff.model.OperationCategoryData
import com.example.gladkikhvlasovtinkoff.model.OperationCategoryDataFactory
import com.example.gladkikhvlasovtinkoff.model.OperationCategoryDataFactoryImpl

class FragmentSelectOperationCategory : Fragment() {

    private var _binding: FragmentSelectOperationCategoryBinding? = null
    private val binding get() = _binding!!
    private var categoriesAdapter: OperationCategoryAdapter? = null

    private var categoryId: Int = -1
    private var imageId: Int = -1


    val args: FragmentSelectOperationCategoryArgs by navArgs()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSelectOperationCategoryBinding.inflate(inflater)

        binding.buttonConfirmOperationCategory.setOnClickListener {
            val operationData = args.operationData
            operationData.imageId = imageId
            operationData.categoryTextId = categoryId
            val action =
                FragmentSelectOperationCategoryDirections.
                actionFragmentSelectOperationCategoryToFragmentConfirmOperationCreating(
                    operationData
                )
            findNavController().navigate(action)
        }

        return binding.root
    }

    override fun onResume() {
        super.onResume()

        (activity as MainActivity?)
            ?.setActionBarTitle(getString(R.string.choose_category))
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        setupOperationCategoryList()
        categoriesAdapter?.checkedItem?.observe(viewLifecycleOwner) { checkedData ->
            if (checkedData != null)
                onCategoryChecked(checkedData)
            else
                onCategoryUnchecked()
        }
    }

    private fun onCategoryUnchecked() {
        binding.buttonConfirmOperationCategory.setDisabled(context)

    }

    private fun onCategoryChecked(checkedData: OperationCategoryData) {
        binding.buttonConfirmOperationCategory.setEnabled(context)
        categoryId = checkedData.nameId
        imageId = checkedData.iconId
    }


    private fun setupOperationCategoryList() {
        val categoryDataFactory: OperationCategoryDataFactory =
            OperationCategoryDataFactoryImpl()
        categoriesAdapter = OperationCategoryAdapter()
        binding.operationCategoryList.apply {
            adapter = categoriesAdapter
            layoutManager = LinearLayoutManager(context)
        }
        categoriesAdapter?.addItems(categoryDataFactory.getCategories())
    }


    override fun onDestroy() {
        super.onDestroy()
        _binding = null
        categoriesAdapter = null
    }

}
