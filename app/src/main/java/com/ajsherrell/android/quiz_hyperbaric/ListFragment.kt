package com.ajsherrell.android.quiz_hyperbaric

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.navigation.navGraphViewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.ajsherrell.android.quiz_hyperbaric.adapter.QuizListAdapter
import com.ajsherrell.android.quiz_hyperbaric.adapter.QuizListClickListener
import com.ajsherrell.android.quiz_hyperbaric.databinding.FragmentListBinding
import com.ajsherrell.android.quiz_hyperbaric.model.Category
import com.ajsherrell.android.quiz_hyperbaric.viewModel.QuizListViewModel
import com.google.android.material.snackbar.Snackbar
import timber.log.Timber

class ListFragment : Fragment() {

    private lateinit var category: Category

    private var errorSnackbar: Snackbar? = null

    private val model: QuizListViewModel by navGraphViewModels(R.id.nav_graph)

    private lateinit var binding: FragmentListBinding
    private lateinit var rootView: View

    private var currentCategory = mutableListOf<Category>()

    private lateinit var quizListAdapter: QuizListAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentListBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = this
        binding.model = model

        quizListAdapter = QuizListAdapter(object : QuizListClickListener {
            override fun onItemClicked(position: Int) {
                Timber.d("Position clicked: $position!!!")
                launchDetailFragment(position)
                model.scoreCategory = currentCategory[position].title
                model.categories.add(model.scoreCategory)
            }
        })

        binding.listRecyclerView.apply {
            layoutManager = GridLayoutManager(context, 3)
            adapter = quizListAdapter
            hasFixedSize()
        }

        model.errorMessage.observe(viewLifecycleOwner, Observer { errorMessage ->
            if (errorMessage != null) showError(errorMessage) else hideError()
        })

        model.getQuizData()

        model.quizLiveData.observe(viewLifecycleOwner, Observer {
            quizListAdapter.updateListItems(it.category)
            quizListAdapter.notifyDataSetChanged()
            category = it.category[0]
            for (i in it.category) {
                currentCategory.add(i)
            }
        })

        rootView = binding.root
        return rootView
    }

    private fun launchDetailFragment(position: Int) {
        val action = ListFragmentDirections.actionListFragmentToDetailFragment(position)
        findNavController().navigate(action)
    }

    private fun showError(@StringRes errorMessage: Int) {
        errorSnackbar = Snackbar.make(binding.root, errorMessage, Snackbar.LENGTH_INDEFINITE)
        errorSnackbar?.setAction(R.string.retry, model.errorClickListener)
        errorSnackbar?.show()
    }

    private fun hideError() {
        errorSnackbar?.dismiss()
    }

}