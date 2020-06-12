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

        if (model.scores.isNotEmpty() && model.categories.isNotEmpty()) {
            addScores()
        }

        binding.clearAllText.setOnClickListener {
            clearHighScores()
        }

        return rootView
    }

    private fun clearHighScores() {
        model.clearSharedPrefs()
        model.categories.clear()
        model.scores.clear()
        gone()
    }

    private fun gone() {
        binding.highScoreTopTitle.text = getString(R.string.no_scores)
        binding.highScoreTitle.visibility = View.GONE
        binding.highScoreName.visibility = View.GONE
        binding.highScoreCat.visibility = View.GONE
        binding.highScoreNum.visibility = View.GONE
        binding.number.visibility = View.GONE
        binding.category.visibility = View.GONE
    }

    private fun visible() {
        binding.highScoreTopTitle.text = getString(R.string.high_score_text)
        binding.highScoreTitle.visibility = View.VISIBLE
        binding.highScoreName.visibility = View.VISIBLE
        binding.highScoreCat.visibility = View.VISIBLE
        binding.highScoreNum.visibility = View.VISIBLE
        binding.number.visibility = View.VISIBLE
        binding.category.visibility = View.VISIBLE
        binding.clearAllText.visibility = View.VISIBLE
    }

    private fun addScores() {
        visible()
        for(i in 0 until model.categories.size) {
            model.sbCat.append(model.categories[i])
            model.sbCat.append("\n")
        }
        for(i in 0 until model.scores.size) {
            model.sbScore.append(resources.getString(R.string.separator))
            model.sbScore.append(" ")
            model.sbScore.append(model.scores[i])
            model.sbScore.append("\n")
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

