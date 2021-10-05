package com.utkuglsvn.roombasic.ui.listUi

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.utkuglsvn.roombasic.R
import com.utkuglsvn.roombasic.core.local.room.entity.MyListItem
import com.utkuglsvn.roombasic.databinding.DialogOpenItemBinding
import com.utkuglsvn.roombasic.utils.inputCheck
import com.utkuglsvn.roombasic.utils.textToInt
import com.utkuglsvn.roombasic.utils.toEditable
import com.utkuglsvn.roombasic.utils.toast
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class OpenItemDialog : DialogFragment() {

    private var _binding: DialogOpenItemBinding? = null
    private val binding get() = _binding!!
    private val viewModel by viewModels<ListViewModel>()
    private val args: OpenItemDialogArgs by navArgs()
    private var listId: Int = -1

    override
    fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = DialogOpenItemBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        dialog?.window?.setBackgroundDrawableResource(android.R.color.transparent)
        initView()
    }

    private fun initView() {
        binding.apply {
            if (args.myListArgs != null) {
                val item = args.myListArgs
                eTextItemName.text = item?.itemName?.toEditable() ?: "".toEditable()
                eTextItemQuantity.text =
                    item?.itemQuantity.toString().toEditable()
                listId = item?.id ?: -1
            }
        }
        closeDialog()
        saveUpdate()
    }

    private fun updateItem(item: MyListItem) {
        item.id = listId
        viewModel.updateItem(item)
        context.toast(getString(R.string.update_item))
    }

    private fun saveItem(item: MyListItem) {
        viewModel.insertItem(item)
        context.toast(getString(R.string.save_item))
    }

    private fun saveUpdate() {
        binding.apply {
            imgViewCheck.setOnClickListener {
                if (inputCheck(binding.eTextItemName.text, binding.eTextItemQuantity.text)) {
                    val item =
                        MyListItem(
                            itemName = eTextItemName.text.toString(),
                            itemQuantity = eTextItemQuantity.textToInt()
                        )
                    if (listId != -1)
                        updateItem(item)
                    else
                        saveItem(item)
                    dialog?.dismiss()
                } else {
                    context.toast(getString(R.string.input_check))
                }
            }
        }
    }

    private fun closeDialog() {
        binding.imgViewClose.setOnClickListener {
            dialog?.dismiss()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}