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
package com.ajsherrell.android.quiz_hyperbaric

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioGroup
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.navigation.navGraphViewModels
import com.ajsherrell.android.quiz_hyperbaric.databinding.FragmentDetailBinding
import com.ajsherrell.android.quiz_hyperbaric.model.Questions
import com.ajsherrell.android.quiz_hyperbaric.viewModel.QuizListViewModel

private const val KEY_INDEX = "index"
private const val SCORE = "score"
private const val IS_ANSWERED = "isAnswered"

class DetailFragment : Fragment() {

    private var bank = listOf<Questions>()
    private var lastIndex = 0
    private var correctAnswer = ""
    private var totalScore = 0
    private var currentIndex = 0
    private lateinit var currentQuestion: Questions
    private var answered: Boolean = false

    private lateinit var binding: FragmentDetailBinding
    private lateinit var rootView: View

    private val model: QuizListViewModel by navGraphViewModels(R.id.nav_graph)

    private val args: DetailFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDetailBinding.inflate(inflater, container, false)
        val index = args.position
        binding.lifecycleOwner = this
        rootView = binding.root
        binding.model = model

        model.getQData()

        model.catLiveData.observe(viewLifecycleOwner, Observer {

            //list of questions
            bank = model.questionBank
            bank = it[index].questions
            bank.shuffled()
            for (i in bank) {
                currentQuestion = i
                correctAnswer = i.answer
                answered = i.answered
                binding.q = currentQuestion
            }
        })

        //Current index of question bank.
        currentIndex = savedInstanceState?.getInt(KEY_INDEX, 0) ?: 0
        model.currentIndex = currentIndex

        //score
        totalScore = savedInstanceState?.getInt(SCORE, 0) ?: 0

        //has question been answered
        answered = savedInstanceState?.getBoolean(IS_ANSWERED, false) ?: false
        model.isAnswered = answered

        //radio button selection
        model.rg = binding.radioGroup
        model.rb1 = binding.radioButton1
        model.rb2 = binding.radioButton2
        model.rb3 = binding.radioButton3
        model.rb4 = binding.radioButton4
        hasAnswered()
        selectRadioButton()

        binding.next.setOnClickListener {
            nextButton()
        }
        binding.submit.setOnClickListener {
            model.saveHighScore()
            val scoreString = resources.getString(R.string.percent, model.scorePercentage(totalScore))
            model.scores.add(scoreString)
            launchScoresFragment(totalScore)
        }

        return rootView
    }

    // used: https://stackoverflow.com/questions/15560904/setting-custom-actionbar-title-from-fragment
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        activity?.run {
            model.updateActionBarTitle(model.scoreCategory)
        }
    }

    //used: https://stackoverflow.com/questions/6780981/android-radiogroup-how-to-configure-the-event-listener
    private fun selectRadioButton() {
        model.rg.setOnCheckedChangeListener(RadioGroup.OnCheckedChangeListener { group, checkedId ->
            model.rb = view?.findViewById(checkedId)
            val isChecked: Boolean = model.rb?.isChecked ?: false

            if (isChecked) {
                answered = true
                hasAnswered()
                model.selectedText = model.rb?.text as String
                if (model.selectedText == correctAnswer) {
                    totalScore+=1
                } else {
                    binding.incorrectText.visibility = View.VISIBLE
                    binding.correctAnswerText.visibility = View.VISIBLE
                }
            } else {
                answered = false
                hasAnswered()
            }
        })
    }

    private fun nextButton() {
        resetButton()
        updateQuestion()
        lastIndex = bank.size - 1
        if (currentIndex == lastIndex) {
            binding.next.visibility = View.GONE
            binding.submit.visibility = View.VISIBLE
        }
    }

    private fun resetButton() {
        model.rg.clearCheck()
        model.rb1.isChecked = false
        model.rb2.isChecked = false
        model.rb3.isChecked = false
        model.rb4.isChecked = false
        answered = false
    }

    private fun hasAnswered() {
        if (!answered) {
            enableRadioButtons()
            binding.next.isEnabled = false
            binding.submit.isEnabled = false
        } else {
            disableRadioButtons()
            binding.next.isEnabled = true
            binding.submit.isEnabled = true
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putInt(KEY_INDEX, currentIndex)
        outState.putInt(SCORE, totalScore)
        outState.putBoolean(IS_ANSWERED, answered)
    }

    private fun updateQuestion() {
        currentIndex = (currentIndex + 1) % bank.size
        binding.q = bank[currentIndex]
        correctAnswer = bank[currentIndex].answer
        binding.correctAnswerText.visibility = View.GONE
        binding.incorrectText.visibility = View.GONE
    }

    private fun enableRadioButtons() {
        model.rb1.isEnabled = true
        model.rb2.isEnabled = true
        model.rb3.isEnabled = true
        model.rb4.isEnabled = true
    }

    private fun disableRadioButtons() {
        model.rb1.isEnabled = false
        model.rb2.isEnabled = false
        model.rb3.isEnabled = false
        model.rb4.isEnabled = false
    }

    private fun launchScoresFragment(score: Int) {
        val action = DetailFragmentDirections.actionDetailFragmentToScoresFragment(score)
        findNavController().navigate(action)
    }
}

