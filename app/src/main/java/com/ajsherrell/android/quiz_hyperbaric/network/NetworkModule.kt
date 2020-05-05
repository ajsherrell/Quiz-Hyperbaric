package com.ajsherrell.android.quiz_hyperbaric.network

import com.ajsherrell.android.quiz_hyperbaric.utils.BASE_URL
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

//import com.ajsherrell.android.quiz_hyperbaric.utils.BASE_URL
//import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
//import dagger.Module
//import dagger.Provides
//import dagger.Reusable
//import retrofit2.Retrofit
//import retrofit2.converter.moshi.MoshiConverterFactory
//
//@Module
//@Suppress("unused")
//object NetworkModule {
//
//    @Provides
//    @Reusable
//    @JvmStatic
//    internal fun provideQuizApi(retrofit: Retrofit): QuizApi {
//        return retrofit.create(QuizApi::class.java)
//    }
//
//    @Provides
//    @Reusable
//    @JvmStatic
//    internal fun provideRetrofitInterface(): Retrofit {
//        return Retrofit.Builder()
//            .baseUrl(BASE_URL)
//            .addConverterFactory(MoshiConverterFactory.create())
//            .addCallAdapterFactory(CoroutineCallAdapterFactory())
//            .build()
//    }
//
//}

private val moshi = Moshi.Builder().add(KotlinJsonAdapterFactory()).build()

private val retrofit = Retrofit.Builder()
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .baseUrl(BASE_URL)
    .build()

object NetworkModule {
    val apiService: QuizApi by lazy {
        retrofit.create(QuizApi::class.java)
    }
}