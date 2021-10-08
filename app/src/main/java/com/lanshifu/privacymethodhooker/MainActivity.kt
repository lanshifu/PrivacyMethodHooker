package com.lanshifu.privacymethodhooker

import android.Manifest
import android.os.Build
import android.os.Bundle
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.lanshifu.privacymethodhooker.test.getCurrentProcessName

class MainActivity : AppCompatActivity() {

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)


//        requestPermissions(arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),0)
        val currentProcessName = getCurrentProcessName()
        println("currentProcessName=$currentProcessName")

    }


}