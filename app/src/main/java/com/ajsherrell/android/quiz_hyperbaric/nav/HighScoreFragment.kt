package com.ajsherrell.android.quiz_hyperbaric.nav

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.navGraphViewModels

import com.ajsherrell.android.quiz_hyperbaric.R
import com.ajsherrell.android.quiz_hyperbaric.databinding.FragmentHighScoreBinding
import com.ajsherrell.android.quiz_hyperbaric.viewModel.QuizListViewModel

private const val SCORE = "score"

class HighScoreFragment : Fragment() {

    private lateinit var binding: FragmentHighScoreBinding
    private lateinit var rootView: View

    private val model: QuizListViewModel by navGraphViewModels(R.id.nav_graph)

    private var score: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            score = it.getInt(SCORE)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHighScoreBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = this
        rootView = binding.root
        binding.model = model

        return rootView
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            HighScoreFragment().apply {
                arguments = Bundle().apply {
                    putInt(SCORE, score)
                }
            }
    }
}
