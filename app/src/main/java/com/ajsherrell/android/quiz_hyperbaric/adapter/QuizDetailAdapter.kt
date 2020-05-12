package com.ajsherrell.android.quiz_hyperbaric.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.ajsherrell.android.quiz_hyperbaric.R
import com.ajsherrell.android.quiz_hyperbaric.databinding.DetailItemBinding
import com.ajsherrell.android.quiz_hyperbaric.model.Category
import com.ajsherrell.android.quiz_hyperbaric.model.Questions
import com.ajsherrell.android.quiz_hyperbaric.viewModel.QuizListViewModel
import timber.log.Timber

class QuizDetailAdapter : RecyclerView.Adapter<QuizDetailAdapter.DetailHolder>() {

    private var cat: List<Category> = listOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DetailHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = DataBindingUtil.inflate<DetailItemBinding>(
            layoutInflater,
            R.layout.detail_item,
            parent,
            false
        )
        return DetailHolder(binding)
    }

    override fun getItemCount(): Int {
        return cat.size
    }

    override fun onBindViewHolder(holder: DetailHolder, position: Int) {
        holder.bind(cat[position], position)
        Timber.d("this is the onBindview category!!! ${cat[position]}")
    }

    fun updateListItems(catList: List<Category>) {
        this.cat = catList
    }

    inner class DetailHolder(private val binding: DetailItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        init {
            binding.model = QuizListViewModel()
        }

        fun bind(catList: Category, position: Int) {
            binding.apply {
                cat = catList
                index = position
                executePendingBindings()
            }
        }
    }

}