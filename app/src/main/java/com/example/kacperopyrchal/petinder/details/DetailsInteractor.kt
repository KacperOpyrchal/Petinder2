package com.example.kacperopyrchal.petinder.details

import com.apollographql.apollo.ApolloClient
import io.reactivex.subjects.PublishSubject
import javax.inject.Inject

class DetailsInteractor @Inject constructor(
        private val apolloClient: ApolloClient
) {

    val likeSubject = PublishSubject.create<Boolean>()
    val dislikeSubject = PublishSubject.create<Boolean>()

    fun like() {

    }

    fun dilike() {

    }

}