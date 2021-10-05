package com.utkuglsvn.roombasic.ui.listUi

import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.utkuglsvn.roombasic.R
import com.utkuglsvn.roombasic.core.local.room.entity.MyListItem
import com.utkuglsvn.roombasic.databinding.FragmentListBinding
import com.utkuglsvn.roombasic.ui.adapter.ItemType
import com.utkuglsvn.roombasic.ui.adapter.ListItemAdapter
import com.utkuglsvn.roombasic.utils.deleteUserDialog
import com.utkuglsvn.roombasic.utils.isShowActionBar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ListFragment : Fragment(), ListItemAdapter.OnItemClickListener {

    private var _binding: FragmentListBinding? = null
    private val binding get() = _binding!!
    private val viewModel by viewModels<ListViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        requireActivity().isShowActionBar(true)
        _binding = FragmentListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
    }

    private fun initView() {
        executeRecyclerView()
        binding.floatingActionButton.setOnClickListener {
            openDialog()
        }
    }

    private fun openDialog() {
        val action = ListFragmentDirections.actionListFragmentToOpenItemDialog(null)
        findNavController().navigate(action)
    }

    private fun executeRecyclerView() {
        viewModel.listItem()
        viewModel.list.observe(viewLifecycleOwner, {
            binding.recyclerView.apply {
                layoutManager = LinearLayoutManager(requireContext())
                adapter = ListItemAdapter(it, requireContext(), this@ListFragment)
            }
        })
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    override fun onItemClick(position: Int, itemType: ItemType) {
        if (itemType == ItemType.DELETE) {
            val positiveButtonClick = { dialog: DialogInterface, _: Int ->
                viewModel.list.value?.get(position)?.let { viewModel.deleteItem(it) }
                dialog.dismiss()
            }
            context.deleteUserDialog(positiveButtonClick)
        } else if (itemType == ItemType.UPDATE) {
            val item = viewModel.list.value?.get(position)?.let { it }
            val action = ListFragmentDirections.actionListFragmentToOpenItemDialog(item)
            findNavController().navigate(action)
        }
    }
}