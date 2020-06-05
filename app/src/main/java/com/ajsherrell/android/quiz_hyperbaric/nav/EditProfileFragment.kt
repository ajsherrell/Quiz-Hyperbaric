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
