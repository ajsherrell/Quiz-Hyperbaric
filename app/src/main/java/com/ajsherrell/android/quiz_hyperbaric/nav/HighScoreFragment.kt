package com.ajsherrell.android.quiz_hyperbaric.nav

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.ObservableField
import androidx.navigation.fragment.navArgs
import androidx.navigation.navGraphViewModels
import com.ajsherrell.android.quiz_hyperbaric.DetailFragmentArgs

import com.ajsherrell.android.quiz_hyperbaric.R
import com.ajsherrell.android.quiz_hyperbaric.databinding.FragmentHighScoreBinding
import com.ajsherrell.android.quiz_hyperbaric.model.Category
import com.ajsherrell.android.quiz_hyperbaric.model.Response
import com.ajsherrell.android.quiz_hyperbaric.viewModel.QuizListViewModel

private const val SCORE = "score"

class HighScoreFragment : Fragment() {

    private lateinit var binding: FragmentHighScoreBinding
    private lateinit var rootView: View

    private val model: QuizListViewModel by navGraphViewModels(R.id.nav_graph)
    private val args: HighScoreFragmentArgs by navArgs()

    private var score: Int = 0
    private var scoreString = ""
    private var category: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            score = it.getInt(SCORE)
        }
//        model.saveHighScore()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHighScoreBinding.inflate(inflater, container, false)
        score = args.score
        category = args.category
        binding.lifecycleOwner = this
        rootView = binding.root
        binding.model = model

        scoreString = score.toString()
        category = model.scoreCategory.toString()
        scoreString = model.scoreNumber

        binding.highScoreCategory.text = category
        binding.highScoreNumber.text = scoreString

        return rootView
    }

    companion object {
        @JvmStatic
        fun newInstance() =
            HighScoreFragment().apply {
                arguments = Bundle().apply {
                    putInt(SCORE, score)
                }
            }
    }
}
