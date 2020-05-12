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
import com.ajsherrell.android.quiz_hyperbaric.adapter.QuizDetailAdapter
import com.ajsherrell.android.quiz_hyperbaric.adapter.QuizListAdapter
import com.ajsherrell.android.quiz_hyperbaric.adapter.QuizListClickListener
import com.ajsherrell.android.quiz_hyperbaric.databinding.DetailItemBinding
import com.ajsherrell.android.quiz_hyperbaric.databinding.FragmentDetailBinding
import com.ajsherrell.android.quiz_hyperbaric.model.Category
import com.ajsherrell.android.quiz_hyperbaric.model.Response
import com.ajsherrell.android.quiz_hyperbaric.viewModel.QuizFactory
import com.ajsherrell.android.quiz_hyperbaric.viewModel.QuizListViewModel
import kotlinx.android.synthetic.main.detail_item.*
import timber.log.Timber

class DetailFragment : Fragment() {

    private lateinit var binding: FragmentDetailBinding
    private lateinit var dBinding: DetailItemBinding
    private lateinit var rootView: View

    private lateinit var model: QuizListViewModel

    private val viewModelFactory = QuizFactory()

//    private val quizDetailAdapter = QuizDetailAdapter()
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

        model.getQuizData()

        model.quizLiveData.observe(viewLifecycleOwner, Observer {
            quizListAdapter.updateListItems(it)
            quizListAdapter.notifyDataSetChanged()
            dBinding.cat = it[index]
        })

//        model.catLiveData.observe(viewLifecycleOwner, Observer {
//            quizListAdapter.updateListItems(it)
//            quizListAdapter.notifyDataSetChanged()
//            Timber.d("catLiveData is!!! ${it[1].questionText}")
//            it?.let {
//                dBinding.q = it[index]
//                Timber.d("dBinding.q = !!! ${it[index]}")
//            }
//        })

//        model.quizLiveData.observe(viewLifecycleOwner,
//            Observer<Category> {
//                it?.let {
//                    binding.detailRecyclerView = it.questions[index]
//                }
//            })

        rootView = binding.root
        return rootView
    }

}


