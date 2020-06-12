package com.ajsherrell.android.quiz_hyperbaric.nav

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.navArgs
import androidx.navigation.navGraphViewModels
import com.ajsherrell.android.quiz_hyperbaric.R
import com.ajsherrell.android.quiz_hyperbaric.databinding.FragmentHighScoreBinding
import com.ajsherrell.android.quiz_hyperbaric.viewModel.QuizListViewModel

private const val SCORE = "score"

class HighScoreFragment : Fragment() {

    private lateinit var binding: FragmentHighScoreBinding
    private lateinit var rootView: View

    private val model: QuizListViewModel by navGraphViewModels(R.id.nav_graph)
    private val args: HighScoreFragmentArgs by navArgs()

    private var score: Int = 0
    private lateinit var category: String

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
        score = args.score
        category = args.category
        binding.lifecycleOwner = this
        rootView = binding.root
        binding.model = model

        addScores()

        binding.clearAllText.setOnClickListener {
            clearHighScores()
        }

        return rootView
    }

    private fun clearHighScores() {
        model.categories.clear()
        model.scores.clear()
        binding.highScoreTopTitle.text = getString(R.string.no_scores)
        binding.highScoreCategory.visibility = View.GONE
        binding.highScoreTitle.visibility = View.GONE
        binding.highScoreName.visibility = View.GONE
        binding.highScoreNumber.visibility = View.GONE
        binding.number.visibility = View.GONE
        binding.category.visibility = View.GONE
    }

    private fun addScores() {
        for(i in 0 until model.scores.size) {
            val sb = StringBuilder()
            sb.append(model.scores[i])
            sb.append("\n")
            val s = sb.toString()
            binding.highScoreNumber.append(s)
        }
        for(i in 0 until model.categories.size) {
            val sb = StringBuilder()
            sb.append(model.categories[i])
            sb.append(" ")
            sb.append("->")
            sb.append("\n")
            val c = sb.toString()
            binding.highScoreCategory.append(c)
        }
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

