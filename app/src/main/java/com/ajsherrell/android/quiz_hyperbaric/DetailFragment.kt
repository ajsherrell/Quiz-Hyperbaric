package com.ajsherrell.android.quiz_hyperbaric

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import android.widget.Toast
import androidx.core.view.children
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.navigation.navGraphViewModels
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.ajsherrell.android.quiz_hyperbaric.adapter.QuizListAdapter
import com.ajsherrell.android.quiz_hyperbaric.adapter.QuizListClickListener
import com.ajsherrell.android.quiz_hyperbaric.databinding.DetailItemBinding
import com.ajsherrell.android.quiz_hyperbaric.databinding.FragmentDetailBinding
import com.ajsherrell.android.quiz_hyperbaric.model.Questions
import com.ajsherrell.android.quiz_hyperbaric.viewModel.QuizListViewModel
import kotlinx.android.synthetic.main.detail_item.*
import kotlinx.android.synthetic.main.fragment_detail.*
import timber.log.Timber

class DetailFragment : Fragment() {

    private var questionId = 0
    private var totalScore = 0
    private var bank = listOf<Questions>()
    private var currentIndex = 0
    private var correctAnswer = ""
    private var selectedAnswer = ""

    private lateinit var binding: FragmentDetailBinding
    private lateinit var dBinding: DetailItemBinding
    private lateinit var rootView: View
    private lateinit var dRootView: View

    private val model: QuizListViewModel by navGraphViewModels(R.id.nav_graph)

    private lateinit var quizListAdapter: QuizListAdapter

    private val args: DetailFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDetailBinding.inflate(inflater, container, false)
        dBinding = DetailItemBinding.inflate(inflater, container, false)
        val index = args.position
        binding.lifecycleOwner = this
        dBinding.lifecycleOwner = this
        rootView = binding.root
        dRootView = dBinding.root
        binding.model = model
        dBinding.model = model

        model.getQData()

        quizListAdapter = QuizListAdapter(object : QuizListClickListener {
            override fun onItemClicked(position: Int) {
                onRadioButtonClicked()
            }
        })

        model.catLiveData.observe(viewLifecycleOwner, Observer {
            quizListAdapter.updateListItems(it[index].questions)
            quizListAdapter.notifyDataSetChanged()
            dBinding.q = it[index].questions[0]

            //ID
            questionId = model.currentQuestionId
            questionId = it[index].questions[0].id

            //score
            totalScore = model.score

            //answer
            correctAnswer = model.answer
            correctAnswer = it[index].questions[0].answer

            //user's selected answer
            selectedAnswer = model.selectedAnswerText

            //list of questions
            bank = model.questionBank
            bank = it[index].questions


        })

        binding.submit.setOnClickListener {
            launchScoresFragment()
            //todo: pass score to ScoresFragment
        }

        binding.detailRecyclerView.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = quizListAdapter
            addItemDecoration(DividerItemDecoration(context, LinearLayoutManager.VERTICAL))
            //used: https://stackoverflow.com/questions/57886100/how-to-add-item-divider-for-recyclerview-in-kotlin
        }

        return rootView
    }

    private fun moveToNext() {
        currentIndex = (currentIndex + 1) % bank.size
    }

    private fun launchScoresFragment() {
        val action = DetailFragmentDirections.actionDetailFragmentToScoresFragment()
        findNavController().navigate(action)
    }

    //used some of: https://android--code.blogspot.com/2018/02/android-kotlin-radiogroup-and.html
    private fun onRadioButtonClicked() {
        val selectedId = radioGroup.checkedRadioButtonId
        val radioButton: RadioButton = radioGroup.findViewById(selectedId)
        if (radioButton.isChecked) {
            for (i in radioGroup.children) {
                i.isEnabled = false
                moveToNext()//not working right
            }
            val selectedText: String = radioButton.text as String
            if (selectedText == correctAnswer) totalScore += 1

            Timber.d("!!!totalScore is $totalScore. SelectedText is $selectedText. Correct Answer is $correctAnswer.")
        }
        Toast.makeText(context, "Score is $totalScore.", Toast.LENGTH_SHORT).show()
    }

}


