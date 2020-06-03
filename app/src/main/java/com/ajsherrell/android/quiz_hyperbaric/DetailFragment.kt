package com.ajsherrell.android.quiz_hyperbaric

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.Toast
import androidx.core.view.get
import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.navigation.navGraphViewModels
import com.ajsherrell.android.quiz_hyperbaric.databinding.FragmentDetailBinding
import com.ajsherrell.android.quiz_hyperbaric.model.Questions
import com.ajsherrell.android.quiz_hyperbaric.viewModel.QuizListViewModel
import timber.log.Timber

private const val KEY_INDEX = "index"
private const val SCORE = "score"
private const val IS_ANSWERED = "isAnswered"

class DetailFragment : Fragment() {

    private lateinit var radioGroup: RadioGroup
    private lateinit var radioButton: RadioButton

    private var bank = listOf<Questions>()
    private var lastIndex = 0
    private var correctAnswer = ""
    private var totalScore = 0
    private var currentIndex = 0
    private lateinit var currentQuestion: Questions
    private var answers = listOf<String>()
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
            //initial question
            binding.q = it[index].questions[0]

            //answer
            correctAnswer = model.answer
            correctAnswer = it[index].questions[0].answer

            //has question been answered
            answered = it[index].answered

            //list of questions
            bank = model.questionBank
//            model.shuffleQuestions() //not working
            bank = it[index].questions
//            bank.shuffled() //not working
//            randomizeQuestions() //not working
        })

        //Current index of question bank.
        currentIndex = savedInstanceState?.getInt(KEY_INDEX, 0) ?: 0
        model.currentIndex = currentIndex

        //score
        totalScore = savedInstanceState?.getInt(SCORE, 0) ?: 0
        model.score = totalScore

        //has question been answered
        answered = savedInstanceState?.getBoolean(IS_ANSWERED, false) ?: false
        model.isAnswered = answered

//        randomizeQuestions()

        //radio button selection
        radioGroup = binding.radioGroup
        selectRadioButton()

//        if (!answered) { //answered is not captured
//            binding.next.visibility = View.GONE
//            binding.submit.visibility = View.GONE
//            Toast.makeText(context, "Choose option", Toast.LENGTH_SHORT).show()
//        } else {
//            binding.next.visibility = View.VISIBLE
//        }

        binding.next.setOnClickListener {
            nextButton()
        }
        binding.submit.setOnClickListener {
            launchScoresFragment(totalScore)
        }

        return rootView
    }

    //used: https://stackoverflow.com/questions/6780981/android-radiogroup-how-to-configure-the-event-listener
    private fun selectRadioButton() {
        var selectedText: String = ""
        radioGroup.setOnCheckedChangeListener(RadioGroup.OnCheckedChangeListener { group, checkedId ->
            radioButton = group.findViewById(checkedId)
            val isChecked: Boolean = radioButton.isChecked

            if (isChecked) {
                answered = true // not capturing
                selectedText = radioButton.text as String
                if (selectedText == correctAnswer) {
                    totalScore += 1
                } else {
                    binding.correctAnswerText.visibility = View.VISIBLE
                }
            } else {
                answered = false // not working correctly
                hasAnswered()
            }

            Timber.d("!!!totalScore is $totalScore. SelectedText is $selectedText. Correct Answer is $correctAnswer. SelectedId = $checkedId.")
            Toast.makeText(context, "Score is ${totalScore}.", Toast.LENGTH_SHORT).show()

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
        radioButton.isChecked = false
        answered = false
    }

    private fun hasAnswered() {
        if (!answered) {
            binding.next.isEnabled = false
            binding.submit.isEnabled = false
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
    }

    private fun changeOption(radioGroup: RadioGroup) {
        radioGroup.isEnabled = !radioGroup.isEnabled
    }

    private fun launchScoresFragment(score: Int) {
        val action = DetailFragmentDirections.actionDetailFragmentToScoresFragment(score)
        findNavController().navigate(action)
    }

    private fun randomizeQuestions() {
        bank.shuffled()
        currentIndex = 0
        setQuestion()
    }

    private fun setQuestion() {
        currentQuestion = bank[currentIndex]
        answers = currentQuestion.options.toMutableList()
        answers.shuffled()

    }

}

