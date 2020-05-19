package com.ajsherrell.android.quiz_hyperbaric.nav

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.ajsherrell.android.quiz_hyperbaric.R

class ScreenSlidePageFragment: Fragment() { //todo: switch to viewPager2

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_screen_slide_page, container, false)
    }
}

