package com.example.android_expert_assignment

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.Adapter
import com.example.android_expert_assignment.data.ItemData
import com.example.android_expert_assignment.databinding.ItemRecyclerviewBinding
import java.text.DecimalFormat

class MyAdapter(private val item: MutableList<ItemData>) : Adapter<MyAdapter.Holder>() {

    interface ItemClick {
        fun onClick(view: View, position: Int)
    }

    private val decimal = DecimalFormat("#,###")


    var itemClick: ItemClick? = null
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyAdapter.Holder {
        val binding =
            ItemRecyclerviewBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        binding.itemName
        return Holder(binding)
    }

    override fun onBindViewHolder(holder: MyAdapter.Holder, position: Int) {

        val price = decimal.format(item[position].price) + "원"
        holder.itemIV.setImageResource(item[position].image)
        holder.itemName.setText(item[position].itemName)
        holder.address.setText(item[position].address)
        holder.itemPrice.text = price
        holder.itemComment.text = decimal.format(item[position].chatting)
        holder.itemLike.text = decimal.format(item[position].like)
    }

    override fun getItemCount(): Int {
        return item.size
    }

    inner class Holder(binding: ItemRecyclerviewBinding) :
        RecyclerView.ViewHolder(binding.root) {

        init {
            itemView.setOnClickListener {
                itemClick?.onClick(it, position)
            }
        }

        val itemIV = binding.itemIV
        val itemName = binding.itemName
        val address = binding.address
        val itemPrice = binding.itemPrice
        val itemComment = binding.itemComment
        val itemLike = binding.itemLike
    }
}