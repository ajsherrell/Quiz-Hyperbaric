package com.ajsherrell.android.quiz_hyperbaric.nav

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.ViewModelProvider
import com.ajsherrell.android.quiz_hyperbaric.databinding.FragmentEditProfileBinding
import com.ajsherrell.android.quiz_hyperbaric.viewModel.QuizFactory
import com.ajsherrell.android.quiz_hyperbaric.viewModel.QuizListViewModel
import kotlinx.android.synthetic.main.fragment_edit_profile.*

class EditProfileFragment : DialogFragment() {

    private lateinit var binding: FragmentEditProfileBinding
    private lateinit var rootView: View
    private val viewModelFactory = QuizFactory()
    private lateinit var model: QuizListViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        model = ViewModelProvider(this@EditProfileFragment, viewModelFactory).get(QuizListViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentEditProfileBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = this

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
            //todo: save the profile through the viewModel to sharedprefs
        }
    }

}
