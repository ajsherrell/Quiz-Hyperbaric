package com.ajsherrell.android.quiz_hyperbaric

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.ajsherrell.android.quiz_hyperbaric.adapter.QuizListAdapter
import com.ajsherrell.android.quiz_hyperbaric.adapter.QuizListClickListener
import com.ajsherrell.android.quiz_hyperbaric.databinding.DetailItemBinding
import com.ajsherrell.android.quiz_hyperbaric.databinding.FragmentDetailBinding
import com.ajsherrell.android.quiz_hyperbaric.viewModel.QuizFactory
import com.ajsherrell.android.quiz_hyperbaric.viewModel.QuizListViewModel
import timber.log.Timber
import java.util.*

class DetailFragment : Fragment() {

    private lateinit var binding: FragmentDetailBinding
    private lateinit var dBinding: DetailItemBinding
    private lateinit var rootView: View

    private lateinit var model: QuizListViewModel

    private val viewModelFactory = QuizFactory()

    private val quizListAdapter = QuizListAdapter(object : QuizListClickListener {
        override fun onItemClicked(position: Int) {
            Toast.makeText(context,"Position $position was clicked", Toast.LENGTH_SHORT).show()
            Timber.d("Position clicked: $position!!!")
        }
    })

    private val args: DetailFragmentArgs by navArgs()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        model = ViewModelProvider(
            this@DetailFragment,
            viewModelFactory
        ).get(QuizListViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDetailBinding.inflate(inflater, container, false)
        dBinding = DetailItemBinding.inflate(inflater, container, false)
        val index = args.position
        binding.lifecycleOwner = this

        binding.detailRecyclerView.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = quizListAdapter
        }

        model.getQData()

        model.catLiveData.observe(viewLifecycleOwner, Observer {
            quizListAdapter.updateListItems(it[index].questions)
            quizListAdapter.notifyDataSetChanged()
            dBinding.q = it[0].questions[0]
            Timber.d("!!! it = ${it[0].questions[0]}")
        })

        rootView = binding.root
        return rootView
    }

}


