package com.utkuglsvn.roombasic.ui.adapter

import android.content.Context
import android.service.autofill.OnClickAction
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.utkuglsvn.roombasic.R
import com.utkuglsvn.roombasic.core.local.room.entity.MyListItem
import com.utkuglsvn.roombasic.databinding.ListItemBinding

class ListItemAdapter(
    private val list: List<MyListItem>,
    private val context: Context,
    private val listener: OnItemClickListener
) : RecyclerView.Adapter<ListItemAdapter.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemBinding =
            ListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(itemBinding, listener)
    }


    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindItems(list[position])
    }

    inner class ViewHolder(
        private val itemBinding: ListItemBinding,
        onClickAction: OnItemClickListener
    ) :
        RecyclerView.ViewHolder(itemBinding.root) {

        fun bindItems(data: MyListItem) {
            itemBinding.apply {
                itemName.text = data.itemName
                itemQuantity.text = context.getString(R.string.quantity, data.itemQuantity)
                cardView.setOnClickListener(clickListener)
                imgViewDelete.setOnClickListener(clickListener)
            }
        }

        private val clickListener = View.OnClickListener { view ->
            when (view) {
                itemBinding.cardView -> {
                    onClickAction.onItemClick(adapterPosition, ItemType.UPDATE)
                }
                itemBinding.imgViewDelete -> {
                    onClickAction.onItemClick(adapterPosition, ItemType.DELETE)
                }
            }
        }

    }

    interface OnItemClickListener {
        fun onItemClick(position: Int, itemType: ItemType)
    }
}

enum class ItemType {
    UPDATE,
    DELETE
}