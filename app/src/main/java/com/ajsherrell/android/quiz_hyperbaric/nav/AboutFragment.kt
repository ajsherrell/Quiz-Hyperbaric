package com.ajsherrell.android.quiz_hyperbaric.nav

import android.os.Bundle
import android.text.method.LinkMovementMethod
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.ajsherrell.android.quiz_hyperbaric.databinding.FragmentAboutBinding

class AboutFragment : Fragment() {

    private lateinit var binding: FragmentAboutBinding
    private lateinit var rootView: View

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAboutBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = this

        binding.devText.movementMethod = LinkMovementMethod.getInstance()

        rootView = binding.root
        return rootView
    }

}
