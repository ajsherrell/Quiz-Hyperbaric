/*
 * Copyright 2020 AJ Sherrell
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
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