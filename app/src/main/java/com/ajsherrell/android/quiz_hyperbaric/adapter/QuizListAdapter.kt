package com.ajsherrell.android.quiz_hyperbaric.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.ajsherrell.android.quiz_hyperbaric.R
import com.ajsherrell.android.quiz_hyperbaric.databinding.FragmentListBinding
import com.ajsherrell.android.quiz_hyperbaric.model.Response
import com.ajsherrell.android.quiz_hyperbaric.viewModel.QuizListViewModel

class QuizListAdapter (val clickListener: QuizListClickListener)
    : RecyclerView.Adapter<QuizListAdapter.QuizHolder>() {

    private var response: List<Response> = listOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): QuizHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = DataBindingUtil.inflate<FragmentListBinding>(
            layoutInflater,
            R.layout.fragment_list,
            parent,
            false
        )

        return QuizHolder(binding, clickListener)
    }

    override fun getItemCount(): Int {
        return response.size
    }

    override fun onBindViewHolder(holder: QuizHolder, position: Int) {
        val response = response[position]
        holder.bind(response, position)
    }

    fun updateListItems(responseList: List<Response>) {
        this.response = responseList
    }

    inner class QuizHolder(
        private val binding: FragmentListBinding,
        private val clickListener: QuizListClickListener) :
            RecyclerView.ViewHolder(binding.root) {

        init {
            binding.model = QuizListViewModel()
        }
        fun bind(response: Response, position: Int) {
            binding.apply {
                category = response.category[position]
                onRecyclerViewItemClick = clickListener
                executePendingBindings()
            }
        }
    }

    class QuizDiffCallback : DiffUtil.ItemCallback<Response>() { //todo: what to use for?
        override fun areItemsTheSame(oldItem: Response, newItem: Response): Boolean =
            oldItem.category == newItem.category


        override fun areContentsTheSame(oldItem: Response, newItem: Response): Boolean =
            oldItem == newItem


    }

}

interface QuizListClickListener {
    fun onItemClicked(position: Int)
}