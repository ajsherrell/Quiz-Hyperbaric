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
import com.ajsherrell.android.quiz_hyperbaric.viewModel.QuizListViewModel
import com.google.android.material.snackbar.Snackbar
import timber.log.Timber

class ListFragment : Fragment() {

    private var errorSnackbar: Snackbar? = null

    private val model: QuizListViewModel by navGraphViewModels(R.id.nav_graph)

    private lateinit var binding: FragmentListBinding
    private lateinit var rootView: View

    private val quizListAdapter = QuizListAdapter(object : QuizListClickListener {
        override fun onItemClicked(position: Int) {
            Timber.d("Position clicked: $position!!!")
            launchDetailFragment(position)
        }
    })

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentListBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = this

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