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
        fun onItemLongClick(view: View, position: Int)
    }

    private val decimal = DecimalFormat("#,###")


    var itemClick: ItemClick? = null
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyAdapter.Holder {
        val binding =
            ItemRecyclerviewBinding.inflate(LayoutInflater.from(parent.context), parent, false)
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
                // onClick에서 position은 deprecate 됨.
                // item의 포지션이 변경되었을 때 그 포지션이 정확하지 않을 수 있기 때문에 예기지 않은 오류가 발생할 수 도 있다고 함.
                // 그래서 bindingAdapterPosition을 사용하기를 권장함.
                itemClick?.onClick(it, bindingAdapterPosition)
            }

            itemView.setOnLongClickListener {
                itemClick?.onItemLongClick(it, bindingAdapterPosition)
                return@setOnLongClickListener(false)
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