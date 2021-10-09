package com.lanshifu.privacymethodhooker

import android.Manifest
import android.os.Build
import android.os.Bundle
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.lanshifu.privacymethodhooker.test.getCurrentProcessName
import com.lanshifu.privacymethodhooker.test.getRecentTasks
import com.lanshifu.privacymethodhooker.test.getRunningAppProcesses
import com.lanshifu.privacymethodhooker.test.getRunningTasks
import com.lanshifu.privacymethodhooker.utils.PrivacyUtil
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)


//        requestPermissions(arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),0)
        val currentProcessName = getCurrentProcessName()
        println("currentProcessName=$currentProcessName")

        val getRunningAppProcesses = getRunningAppProcesses(this)
        println("getRunningAppProcesses,size=${getRunningAppProcesses?.size}")


        cbAgree.setOnCheckedChangeListener { compoundButton, b ->
            PrivacyUtil.isAgreePrivacy = b
        }


        btnGetRunningAppProcesses.setOnClickListener {
            val getRunningAppProcesses = getRunningAppProcesses(this)
            Toast.makeText(this, "size=${getRunningAppProcesses?.size}", Toast.LENGTH_SHORT).show()
        }

        btnGetRecentTasks.setOnClickListener {
            val getRunningAppProcesses = getRecentTasks(this)
            Toast.makeText(this, "size=${getRunningAppProcesses?.size}", Toast.LENGTH_SHORT).show()
        }

        btnGetRunningTasks.setOnClickListener {
            val getRunningTasks = getRunningTasks(this)
            Toast.makeText(this, "size=${getRunningTasks?.size}", Toast.LENGTH_SHORT).show()
        }

    }


}