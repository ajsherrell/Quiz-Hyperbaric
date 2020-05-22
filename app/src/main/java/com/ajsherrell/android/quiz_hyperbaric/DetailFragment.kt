package com.ajsherrell.android.quiz_hyperbaric

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import android.widget.Toast
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
import com.ajsherrell.android.quiz_hyperbaric.viewModel.QuizListViewModel
import timber.log.Timber

class DetailFragment : Fragment() {

    private var questionId = 0
    private var totalScore = 0
    private var bank = 0
    private var correctAnswer = ""
    private var selectedAnswer = ""

    private lateinit var binding: FragmentDetailBinding
    private lateinit var dBinding: DetailItemBinding
    private lateinit var rootView: View

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

        model.getQData()

        dBinding.radioGroup.setOnCheckedChangeListener { _, checkedId ->
            val rb: RadioButton? = view?.findViewById(checkedId)
            Toast.makeText(context, "On rb text = ${rb?.text}", Toast.LENGTH_SHORT).show()
            Timber.d("!!!On rb text = ${rb?.text}")
        }

        quizListAdapter = QuizListAdapter(object : QuizListClickListener {
            override fun onItemClicked(position: Int) {
                val radio: RadioButton? = view?.findViewById(dBinding.radioGroup.checkedRadioButtonId)
                Toast.makeText(context, "On radio clicked: ${radio?.text.toString()}", Toast.LENGTH_SHORT).show()
                Timber.d("!!!On radio clicked: ${radio?.text}")
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
            //todo: get user's score

            //answer
            correctAnswer = model.answer
            correctAnswer = it[index].questions[0].answer

            //user's selected answer
            selectedAnswer = model.selectedAnswerText
            //todo: get user's answer

            //list of questions
            bank = model.questionBank
            bank = it[index].questions.size

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

        rootView = binding.root
        return rootView
    }

    private fun launchScoresFragment() {
        val action = DetailFragmentDirections.actionDetailFragmentToScoresFragment()
        findNavController().navigate(action)
    }
}


