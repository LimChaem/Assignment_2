package com.example.android_expert_assignment

import android.util.Log
import android.view.View
import androidx.recyclerview.widget.RecyclerView

class RecyclerViewScrollListener(private val fab: View) : RecyclerView.OnScrollListener() {
    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
        super.onScrolled(recyclerView, dx, dy)
        if (!recyclerView.canScrollVertically(-1)) {
            if (fab.visibility == View.VISIBLE) fab.visibility = View.GONE
            Log.d("ScrollTest", "Hiding button")
        } else {
            if (fab.visibility != View.VISIBLE) fab.visibility = View.VISIBLE
                Log.d("ScrollTest", "Visible button")
        }

    }
}