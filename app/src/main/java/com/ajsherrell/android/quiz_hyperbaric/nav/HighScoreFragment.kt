/*
 * Copyright 2020 AJ Sherrell
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
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

        if (model.scores.size != 0) {
            addScores()
            model.loadHighScores()
        } else {
            gone()
        }

        binding.clearAllText.setOnClickListener {
            clearHighScores()
        }

        return rootView
    }

    private fun clearHighScores() {
        model.clearSharedPrefs()
        gone()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        model.categories.clear()
        model.scores.clear()
        //keeps category list from populating twice.
    }

    private fun gone() {
        binding.highScoreTopTitle.text = getString(R.string.no_scores)
        binding.highScoreTitle.visibility = View.GONE
        binding.highScoreName.visibility = View.GONE
        binding.highScoreCat.visibility = View.GONE
        binding.highScoreNum.visibility = View.GONE
        binding.number.visibility = View.GONE
        binding.category.visibility = View.GONE
        binding.clearAllText.visibility = View.GONE
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
        for (i in 0 until model.categories.size) {
            model.sbCat.append(model.categories[i])
            model.sbCat.append("\n")
        }
        for (i in 0 until model.scores.size) {
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

