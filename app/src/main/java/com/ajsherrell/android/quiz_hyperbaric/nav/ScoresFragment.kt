package com.ajsherrell.android.quiz_hyperbaric.nav

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.navGraphViewModels
import com.ajsherrell.android.quiz_hyperbaric.R
import com.ajsherrell.android.quiz_hyperbaric.databinding.FragmentScoresBinding
import com.ajsherrell.android.quiz_hyperbaric.viewModel.QuizListViewModel
import timber.log.Timber


class ScoresFragment : Fragment() {

    private lateinit var binding: FragmentScoresBinding
    private lateinit var rootView: View

    private val model: QuizListViewModel by navGraphViewModels(R.id.nav_graph)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentScoresBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = this

        binding.homeButton.setOnClickListener {
            Toast.makeText(context,"Home button was clicked", Toast.LENGTH_SHORT).show()
            Timber.d("Home button was clicked!!!")
        }

        rootView = binding.root
        return rootView
    }

    private fun launchListFragment() {
        val action = ScoresFragmentDirections.actionScoresFragmentToListFragment()
        findNavController().navigate(action)
    }

}
