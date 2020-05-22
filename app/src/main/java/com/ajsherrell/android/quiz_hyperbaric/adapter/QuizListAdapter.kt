package com.ajsherrell.android.quiz_hyperbaric.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.ajsherrell.android.quiz_hyperbaric.R
import com.ajsherrell.android.quiz_hyperbaric.databinding.ButtonListBinding
import com.ajsherrell.android.quiz_hyperbaric.databinding.DetailItemBinding
import com.ajsherrell.android.quiz_hyperbaric.model.Category
import com.ajsherrell.android.quiz_hyperbaric.model.Questions
import java.lang.IllegalArgumentException

// used help from: https://medium.com/@ivancse.58/android-and-kotlin-recyclerview-with-multiple-view-types-65285a254393
class QuizListAdapter (private val clickListener: QuizListClickListener)
    : RecyclerView.Adapter<BaseViewHolder<*>>() {

    private var dataList: List<Any> = listOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<*> {
        val layoutInflater = LayoutInflater.from(parent.context)
        return when (viewType) {
            TYPE_LIST_HOLDER -> {
                val binding = DataBindingUtil.inflate<ButtonListBinding>(
                    layoutInflater,
                    R.layout.button_list,
                    parent,
                    false
                )
                ListHolder(binding, clickListener)
            }
            TYPE_DETAIL_HOLDER -> {
                val binding = DataBindingUtil.inflate<DetailItemBinding>(
                    layoutInflater,
                    R.layout.detail_item,
                    parent,
                    false
                )
                DetailHolder(binding, clickListener)
            }
            else -> throw IllegalArgumentException("Invalid binding type!!!")
        }
    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    override fun getItemViewType(position: Int): Int {
        return when (dataList[position]) {
            is Category -> TYPE_LIST_HOLDER
            is Questions -> TYPE_DETAIL_HOLDER
            else -> throw IllegalArgumentException("Invalid type!!!")
        }
    }

    override fun onBindViewHolder(holder: BaseViewHolder<*>, position: Int) {
        val element = dataList[position]
        when (holder) {
            is ListHolder -> holder.bind(element as Category, position)
            is DetailHolder -> holder.bind(element as Questions, position)
            else -> throw IllegalArgumentException()
        }
    }

    fun updateListItems(any: List<Any>) {
        this.dataList = any
    }

    inner class ListHolder(
        private val binding: ButtonListBinding,
        private val clickListener: QuizListClickListener) :
            BaseViewHolder<Category>(binding.root) {

        override fun bind(item: Category, pos: Int) {
            binding.apply {
                cat = item
                position = pos
                onRecyclerViewItemClick = clickListener
                executePendingBindings()
            }
        }
    }

    inner class DetailHolder(
        private val binding: DetailItemBinding,
        private val clickListener: QuizListClickListener
    ) : BaseViewHolder<Questions>(binding.root) {

        override fun bind(item: Questions, pos: Int) {
            binding.apply {
                q = item
                index = pos
                radio = clickListener
                executePendingBindings()
            }
        }
    }

    companion object {
        private const val TYPE_LIST_HOLDER = 0
        private const val TYPE_DETAIL_HOLDER = 1
    }

}

interface QuizListClickListener {
    fun onItemClicked(position: Int)
}

abstract class BaseViewHolder<T>(itemView: View) :
        RecyclerView.ViewHolder(itemView) {
    abstract fun bind(item: T, pos: Int)
}