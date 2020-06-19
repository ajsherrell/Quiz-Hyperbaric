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
 */
package com.ajsherrell.android.quiz_hyperbaric.nav

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.navigation.navGraphViewModels
import com.ajsherrell.android.quiz_hyperbaric.R
import com.ajsherrell.android.quiz_hyperbaric.databinding.FragmentEditProfileBinding
import com.ajsherrell.android.quiz_hyperbaric.viewModel.QuizListViewModel
import kotlinx.android.synthetic.main.fragment_edit_profile.*

class EditProfileFragment : DialogFragment() {

    private lateinit var binding: FragmentEditProfileBinding
    private lateinit var rootView: View
    private val model: QuizListViewModel by navGraphViewModels(R.id.nav_graph)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentEditProfileBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = this
        binding.model = model
        rootView = binding.root
        return rootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        dialog?.setOnShowListener {
            dialog?.setTitle("Edit Profile")
            dialog?.window?.setLayout(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            )
        }

        saveButton.setOnClickListener {
            model.saveProfile()
            dismiss()
        }
    }

}
