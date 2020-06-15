package com.ajsherrell.android.quiz_hyperbaric.nav

import android.os.Bundle
import android.text.method.LinkMovementMethod
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.ajsherrell.android.quiz_hyperbaric.databinding.FragmentResourcesBinding

class ResourcesFragment : Fragment() {

    private lateinit var binding: FragmentResourcesBinding
    private lateinit var rootView: View

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentResourcesBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = this

        binding.resText.movementMethod = LinkMovementMethod.getInstance()

        rootView = binding.root
        return rootView
    }

}
