package com.example.gladkikhvlasovtinkoff.ui.ui.walletoperation

import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.gladkikhvlasovtinkoff.R
import com.example.gladkikhvlasovtinkoff.ToolbarFragment
import com.example.gladkikhvlasovtinkoff.ToolbarHolder
import com.example.gladkikhvlasovtinkoff.databinding.FragmentWalletTransactionBinding
import com.example.gladkikhvlasovtinkoff.model.WalletOperationBuilder


class WalletTransactionFragment : ToolbarFragment() {

    private val viewModel: WalletTransactionViewModel by viewModels()
    private val args: WalletTransactionFragmentArgs by navArgs()

    private var _binding: FragmentWalletTransactionBinding? = null
    private val binding get() = _binding!!

    private lateinit var operationsAdapter: WalletOperationAdapter
    private var transaction: WalletOperationBuilder? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        args.newOperationData?.let { data ->
            transaction = data

            if (transaction!!.type == resources.getString(R.string.costs_text)) {
                transaction!!.value = "- ${transaction!!.value}"
            }
        }
        //Добавление данных в удаленную бд
        //Обновление списка в соотвествии с данными бд
        if (transaction != null)
            viewModel.transactionList.value?.add(transaction!!.createModel())
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentWalletTransactionBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        configureToolbar()
        initWalletRecycler()
        setupButtonListener()

    }
    override fun configureToolbar() {
        setHasOptionsMenu(true)
        activity?.let{activity ->
            (activity as ToolbarHolder).setToolbarTitle("")
        }
    }

    private fun initWalletRecycler() {
        operationsAdapter = WalletOperationAdapter { _, action ->
            when (action.actionId) {
                R.id.edit -> Toast.makeText(context, "Edit", Toast.LENGTH_SHORT).show()
                R.id.delete -> {
                    val deleteDialog = DeleteDialogFragment()
                    val manager = activity?.supportFragmentManager
                    manager?.let { deleteDialog.show(it, getString(R.string.delete_dialog_tag)) }
                }
            }
        }

        binding.walletRecycle.setHasFixedSize(true)
        binding.walletRecycle.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = operationsAdapter
        }
        operationsAdapter.submitList(viewModel.transactionList.value)
    }

    private fun setupButtonListener(){
        val navController = findNavController()

        binding.buttonAddOperation.setOnClickListener {
            val action =
                WalletTransactionFragmentDirections.actionOptionFragmentToFragmentSelectOperationValue(
                    WalletOperationBuilder()
                )
            navController.navigate(action)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_main, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.action_settings) {
            Toast.makeText(context, "Settings", Toast.LENGTH_SHORT).show()
        }
        return true
    }


    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}