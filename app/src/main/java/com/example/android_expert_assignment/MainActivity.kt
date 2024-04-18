package com.example.android_expert_assignment

import android.app.AlertDialog
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Intent
import android.media.AudioAttributes
import android.media.RingtoneManager
import android.net.Uri
import android.os.Build
import android.os.Build.VERSION_CODES
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.view.View
import android.widget.ArrayAdapter
import android.window.OnBackInvokedDispatcher
import androidx.activity.OnBackPressedCallback
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.android_expert_assignment.data.DataList
import com.example.android_expert_assignment.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val callback by lazy {
        object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                showDialog()
            }
        }
    }

    private val TAG = "backButton"



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        ViewCompat.setOnApplyWindowInsetsListener(view) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        initFun()

        val recyclerViewScrollListener = RecyclerViewScrollListener(binding.fab)
        binding.recyclerView.addOnScrollListener(recyclerViewScrollListener)

    }

    private fun initFun() {
        pressedBackButton()
        clickedNotification()
        initRecyclerView()
        setUpSpinner()
        pressedFloatingButton()
    }


    private fun clickedNotification() {
        binding.notificationIV.setOnClickListener {
            notification()
            checkPermissions()
        }
    }

    private fun notification() {

        val manager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
        val builder: NotificationCompat.Builder
        if (Build.VERSION.SDK_INT >= VERSION_CODES.O) {
            val channelId = "one-channel"
            val channelName = R.string.app_name.toString()
            val channel = NotificationChannel(
                channelId, channelName, NotificationManager.IMPORTANCE_DEFAULT
            ).apply {
                description = "사용자 선택 알람"
                setShowBadge(true)
                val uri: Uri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
                val audioAttributes = AudioAttributes.Builder()
                    .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                    .setUsage(AudioAttributes.USAGE_ALARM).build()
                setSound(uri, audioAttributes)
                enableVibration(true)
            }
            manager.createNotificationChannel(channel)
            builder = NotificationCompat.Builder(this, channelId)
        } else {
            builder = NotificationCompat.Builder(this)
        }

        builder.run {
            setSmallIcon(R.drawable.bell_2)
            setWhen(System.currentTimeMillis())
            setContentTitle("키워드 알림")
            setContentText("설정한 키워드에 대한 알림이 도착했습니다.")
        }
        manager.notify(11, builder.build())
    }

    private fun checkPermissions() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (!NotificationManagerCompat.from(this).areNotificationsEnabled()) {
                val intent = Intent(Settings.ACTION_APP_NOTIFICATION_SETTINGS).apply {
                    putExtra(Settings.EXTRA_APP_PACKAGE, packageName)
                }
                startActivity(intent)
            }
        }
    }

    private fun pressedBackButton() {
        // onBackPressed()가 deprecate 됨에 따라 Target API 33 이상 사용.
        // 다만 Target API가 33 이상 이라는 조건을 충족 하기 위해선 아직 시간이 많이 걸릴 것임.
        // 그래서 onBackPressedDispatcher를 통해 callback을 처리하여 사용함. (얘는 범용 적용)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            try {
                onBackInvokedDispatcher.registerOnBackInvokedCallback(
                    OnBackInvokedDispatcher.PRIORITY_OVERLAY
                ) {
                    Log.d(TAG, "onBackInvoked() 호출")
                    showDialog()
                    Log.d(TAG, "Back button invoked")
                }
                Log.d(TAG, "Back invoked callback registered")
            } catch (e: NoSuchMethodError) {
                Log.e(TAG, "Method getOnBackInvokedDispatcher not found", e)
            }
        } else {
            onBackPressedDispatcher.addCallback(this, callback)
        }
    }

    fun showDialog() {
        Log.d(TAG, "showDialog() called")
        AlertDialog.Builder(this)
            .setTitle("종료")
            .setMessage("정말로 종료하시겠습니까?")
            .setIcon(R.drawable.chat)
            .setPositiveButton("예") { _, _ ->
                Log.d(TAG, "Dialog: Yes clicked")
                finish()
            }
            .setNegativeButton("아니요") { dial, _ ->
                Log.d(TAG, "Dialog: No clicked")

            }.show()
        Log.d(TAG, "AlertDialog should be displayed")
    }

    private fun initRecyclerView() {
        val decoration = DividerItemDecoration(this, DividerItemDecoration.VERTICAL)
        val adapter = MyAdapter(DataList.dataList)
        binding.recyclerView.adapter = adapter
        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        binding.recyclerView.addItemDecoration(decoration)

        adapter.itemClick = object : MyAdapter.ItemClick {
            override fun onClick(view: View, position: Int) {
                val item = DataList.dataList[position]
                val intent = Intent(this@MainActivity, DetailPageActivity::class.java).apply {
                    putExtra("data", item)
                }
                startActivity(intent)
            }
        }
    }

    private fun pressedFloatingButton() {
        binding.fab.setOnClickListener {
            binding.recyclerView.smoothScrollToPosition(0)
        }
    }

    private fun setUpSpinner() {
        val category = resources.getStringArray(R.array.category)
        val adapter = ArrayAdapter(this, R.layout.item_spinner, category)
        binding.spinnerView.adapter = adapter
    }

}


