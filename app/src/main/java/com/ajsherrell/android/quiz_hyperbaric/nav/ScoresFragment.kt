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
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.navigation.navGraphViewModels
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
        binding.lifecycleOwner = this

        val newScore = model.scorePercentage(score)
        model.score = resources.getString(R.string.percent, newScore)
        if (newScore >= 60) {
            binding.scoreText.text = resources.getString(R.string.win, newScore)
        } else {
            binding.scoreText.text = resources.getString(R.string.lose, newScore)
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
