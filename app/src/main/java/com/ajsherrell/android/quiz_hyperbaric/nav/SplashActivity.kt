package com.ajsherrell.android.quiz_hyperbaric.nav

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.ajsherrell.android.quiz_hyperbaric.MainActivity
import com.ajsherrell.android.quiz_hyperbaric.R

class SplashActivity : AppCompatActivity() {

    //used: https://medium.com/@ssaurel/create-a-splash-screen-on-android-the-right-way-93d6fb444857
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val intent = Intent(this, MainActivity::class.java)
        val timer = object : Thread() {
            override fun run() {
                try {
                    sleep(3000)
                } catch (e: InterruptedException) {
                    e.printStackTrace()
                } finally {
                    startActivity(intent)
                    finish()
                }
            }
        }
        timer.start()
    }
}