package com.example.android_expert_assignment

import android.os.Build
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.android_expert_assignment.data.ItemData
import com.example.android_expert_assignment.databinding.ActivityDetailPageBinding
import java.text.DecimalFormat

class DetailPageActivity : AppCompatActivity() {
    private val decimal = DecimalFormat("#,##0")
    private val binding by lazy { ActivityDetailPageBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(binding.root) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        val item = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            intent.getParcelableExtra("data", ItemData::class.java)
        } else {
            intent.getParcelableExtra<ItemData>("data")
        }

        item?.let{
            bindingItem(it)
        }

        pressedReturnButton()

    }

    private fun bindingItem(item: ItemData) {
        val price = decimal.format(item.price) + "원"
        binding.ivItem.setImageResource(item.image)
        binding.tvSeller.setText(item.seller)
        binding.tvAddress.setText(item.address)
        binding.tvItemName.setText(item.itemName)
        binding.tvItemIntroduction.setText(item.itemIntroduction)
        binding.tvPrice.text = price
    }

    private fun pressedReturnButton() {
        binding.ivBackBtn.setOnClickListener {
            finish()
        }
    }
}