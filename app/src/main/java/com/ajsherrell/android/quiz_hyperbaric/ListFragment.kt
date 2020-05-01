package com.ajsherrell.android.quiz_hyperbaric

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.ajsherrell.android.quiz_hyperbaric.databinding.FragmentListBinding
import com.google.android.material.snackbar.Snackbar

private const val TAG = "ListFragment"

class ListFragment : Fragment() {

    private var errorSnackbar: Snackbar? = null
    
    companion object {
        fun newInstance() = ListFragment()
    }

    private lateinit var binding: FragmentListBinding
    private lateinit var rootView: View

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentListBinding.inflate(inflater, container, false)
        
        rootView = binding.root
        return rootView
    }

}