package com.example.android_expert_assignment

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.Adapter
import com.example.android_expert_assignment.data.ItemData
import com.example.android_expert_assignment.databinding.ItemRecyclerviewBinding

class MyAdapter(private val item: MutableList<ItemData>) : Adapter<MyAdapter.Holder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyAdapter.Holder {
        val binding =
            ItemRecyclerviewBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return Holder(binding)
    }

    override fun onBindViewHolder(holder: MyAdapter.Holder, position: Int) {
        holder.itemIV.setImageResource(item[position].image)
        // 저장된 정보가 리소스 타입이라 Int를 받아야해서 이게 안됨.
        // holder.itemName.text = item[position].itemName

        holder.itemName.setText(item[position].itemName)
        holder.address.setText(item[position].address)
        holder.itemPrice.setText(item[position].price)
        holder.itemComment.text = item[position].chatting.toString()
        holder.itemLike.text = item[position].like.toString()
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getItemCount(): Int {
        return item.size
    }

    inner class Holder(binding: ItemRecyclerviewBinding) :
        RecyclerView.ViewHolder(binding.root) {
        val itemIV = binding.itemIV
        val itemName = binding.itemName
        val address = binding.address
        val itemPrice = binding.itemPrice
        val itemComment = binding.itemComment
        val itemLike = binding.itemLike
    }
}