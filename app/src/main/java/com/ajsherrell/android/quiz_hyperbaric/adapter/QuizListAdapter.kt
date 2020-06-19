
/*
 * Copyright 2020 AJ Sherrell
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */ package com.ajsherrell.android.quiz_hyperbaric.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.ajsherrell.android.quiz_hyperbaric.R
import com.ajsherrell.android.quiz_hyperbaric.databinding.ButtonListBinding
import com.ajsherrell.android.quiz_hyperbaric.model.Category

// used help from: https://medium.com/@ivancse.58/android-and-kotlin-recyclerview-with-multiple-view-types-65285a254393
class QuizListAdapter (private val clickListener: QuizListClickListener)
    : RecyclerView.Adapter<QuizListAdapter.ListHolder>() {

    private var dataList: List<Any> = listOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = DataBindingUtil.inflate<ButtonListBinding>(
            layoutInflater,
            R.layout.button_list,
            parent,
            false
        )
        return ListHolder(binding, clickListener)
    }


    override fun getItemCount(): Int {
        return dataList.size
    }

    override fun onBindViewHolder(holder: ListHolder, position: Int) {
        val element = dataList[position]
            holder.bind(element as Category, position)

    }

    fun updateListItems(any: List<Any>) {
        this.dataList = any
    }

    inner class ListHolder(
        private val binding: ButtonListBinding,
        private val clickListener: QuizListClickListener) :
            RecyclerView.ViewHolder(binding.root) {

        fun bind(item: Category, pos: Int) {
            binding.apply {
                cat = item
                position = pos
                onRecyclerViewItemClick = clickListener
                executePendingBindings()
            }
        }
    }

}

interface QuizListClickListener {
    fun onItemClicked(position: Int)
}
