package com.ajsherrell.android.quiz_hyperbaric

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.*
import android.view.ViewGroup
import android.widget.Toast
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
import kotlinx.android.synthetic.main.fragment_list.*
import timber.log.Timber

class ListFragment : Fragment() {

    private var errorSnackbar: Snackbar? = null

    private val model: QuizListViewModel by navGraphViewModels(R.id.nav_graph)

    private lateinit var binding: FragmentListBinding
    private lateinit var rootView: View

    private val quizListAdapter = QuizListAdapter(object : QuizListClickListener {
        override fun onItemClicked(position: Int) {
            Toast.makeText(context,"Position $position was clicked", Toast.LENGTH_SHORT).show()
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

        model.getQuizData()

        model.quizLiveData.observe(viewLifecycleOwner, Observer {
            quizListAdapter.updateListItems(it.category)
            quizListAdapter.notifyDataSetChanged()
            Timber.d("!!!it = ${it.category}")
        })

        model.loading.observe(viewLifecycleOwner, Observer {
            displayLoading(it == true)
        })

        binding.listRecyclerView.apply {
            layoutManager = GridLayoutManager(context, 3)
            adapter = quizListAdapter
        }

        rootView = binding.root
        return rootView
    }

    private fun launchDetailFragment(position: Int) {
        val action = ListFragmentDirections.actionListFragmentToDetailFragment(position)
        findNavController().navigate(action)
    }

    // used: https://github.com/elpassion/crweather/blob/master/app/src/main/java/com/elpassion/crweather/MainActivity.kt
    private fun displayLoading(loading: Boolean) {
        progress_bar_list.visibility = if (loading) VISIBLE else GONE
    }
//
//    private fun showError(@StringRes errorMessage: Int) {
//        errorSnackbar = Snackbar.make(binding.root, errorMessage, Snackbar.LENGTH_INDEFINITE)
//        errorSnackbar?.setAction(R.string.retry, model.errorClickListener)
//        errorSnackbar?.show()
//    }
//
//    private fun hideError() {
//        errorSnackbar?.dismiss()
//    }

}