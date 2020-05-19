package com.ajsherrell.android.quiz_hyperbaric

import android.os.Bundle
import android.os.PersistableBundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.ajsherrell.android.quiz_hyperbaric.nav.ScreenSlidePageFragment

private const val ITEM_COUNT = 5

class ScreenSlidePagerActivity : FragmentActivity() { //todo: switch to viewPager2

    private lateinit var viewPager: ViewPager2

    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onCreate(savedInstanceState, persistentState)
        setContentView(R.layout.activity_screen_slide)

        viewPager = findViewById(R.id.pager)

        val pagerAdapter = ScreenSlidePagerAdapter(this)
        viewPager.adapter = pagerAdapter
    }

    override fun onBackPressed() {
        if (viewPager.currentItem == 0) {
            super.onBackPressed()
        } else {
            viewPager.currentItem = viewPager.currentItem - 1
        }
    }

    private inner class ScreenSlidePagerAdapter(fa: FragmentActivity) : FragmentStateAdapter(fa) {
        override fun getItemCount(): Int = ITEM_COUNT

        override fun createFragment(position: Int): Fragment = ScreenSlidePageFragment()
    }
}