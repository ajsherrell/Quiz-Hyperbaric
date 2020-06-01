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
    private var answered = false

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

            //list of questions
            bank = model.questionBank
            bank = it[index].questions

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

        //radio button selection
        radioGroup = binding.radioGroup
        selectRadioButton()

        binding.next.setOnClickListener {
            nextButton()
            if (currentIndex == lastIndex) {
                launchScoresFragment(totalScore)
            }
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
                selectedText = radioButton.text as String
            }
            if (selectedText == correctAnswer) {
                totalScore += 1
            } else {
                binding.correctAnswerText.visibility = View.VISIBLE
            }


            Timber.d("!!!totalScore is $totalScore. SelectedText is $selectedText. Correct Answer is $correctAnswer. SelectedId = $checkedId.")
            Toast.makeText(context, "Score is ${totalScore}.", Toast.LENGTH_SHORT).show()

        })
    }

    private fun nextButton() {
        radioButton.isChecked = false
        binding.correctAnswerText.visibility = View.GONE
        currentIndex = (currentIndex + 1) % bank.size
        lastIndex = bank.size-1
        if (currentIndex == lastIndex) {
            binding.next.text = resources.getString(R.string.submit)
        } else {
            updateQuestion()
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putInt(KEY_INDEX, model.currentIndex)
        outState.putInt(SCORE, totalScore)
        outState.putBoolean(IS_ANSWERED, model.isAnswered)
    }

    private fun updateQuestion() {
        binding.q = bank[currentIndex]
        correctAnswer = bank[currentIndex].answer
    }

    private fun launchScoresFragment(score: Int) {
        val action = DetailFragmentDirections.actionDetailFragmentToScoresFragment(score)
        findNavController().navigate(action)
    }

}

