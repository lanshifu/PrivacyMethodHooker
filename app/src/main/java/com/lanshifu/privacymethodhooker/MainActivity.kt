package com.lanshifu.privacymethodhooker

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.lanshifu.privacymethodhooker.test.getCurrentProcessName

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)


        val currentProcessName = getCurrentProcessName()
        println("currentProcessName=$currentProcessName")

    }


}