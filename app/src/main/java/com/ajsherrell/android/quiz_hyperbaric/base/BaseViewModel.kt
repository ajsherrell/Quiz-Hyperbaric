package com.ajsherrell.android.quiz_hyperbaric.base

//import androidx.lifecycle.ViewModel
//import com.ajsherrell.android.quiz_hyperbaric.component.ViewModelInjector
//import com.ajsherrell.android.quiz_hyperbaric.network.NetworkModule
//import com.ajsherrell.android.quiz_hyperbaric.viewModel.ListViewModel
//
//abstract class BaseViewModel: ViewModel() {
//
//    private val injector: ViewModelInjector = DaggerViewModelInjector
//        .builder()
//        .networkModule(NetworkModule)
//        .build()
//
//    init {
//        inject()
//    }
//
//    private fun inject() {
//        when (this) {
//            is ListViewModel -> injector.inject(this)
//        }
//    }
//}