package com.ajsherrell.android.quiz_hyperbaric.nav

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.navigation.navGraphViewModels
import com.ajsherrell.android.quiz_hyperbaric.DetailFragmentArgs
import com.ajsherrell.android.quiz_hyperbaric.R
import com.ajsherrell.android.quiz_hyperbaric.databinding.FragmentScoresBinding
import com.ajsherrell.android.quiz_hyperbaric.viewModel.QuizListViewModel
import timber.log.Timber


class ScoresFragment : Fragment() {

    private lateinit var binding: FragmentScoresBinding
    private lateinit var rootView: View

    private val model: QuizListViewModel by navGraphViewModels(R.id.nav_graph)

    private val args: ScoresFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentScoresBinding.inflate(inflater, container, false)
        val score = args.score
        model.score = score.toString()
        binding.lifecycleOwner = this

        if (score >= 3) {
            binding.scoreText.text = resources.getString(R.string.win, score)
        } else {
            binding.scoreText.text = resources.getString(R.string.lose, score)
        }

        binding.homeButton.setOnClickListener {
            Timber.d("Home button was clicked!!!")
            launchListFragment()
        }

        rootView = binding.root
        return rootView
    }

    private fun launchListFragment() {
        val action = ScoresFragmentDirections.actionScoresFragmentToListFragment()
        findNavController().navigate(action)
    }

}
