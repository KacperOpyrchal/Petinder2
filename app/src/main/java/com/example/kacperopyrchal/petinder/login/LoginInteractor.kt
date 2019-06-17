package com.example.kacperopyrchal.petinder.login

import Pet
import com.apollographql.apollo.ApolloCall
import com.apollographql.apollo.ApolloClient
import com.apollographql.apollo.api.Response
import com.apollographql.apollo.exception.ApolloException
import io.reactivex.subjects.PublishSubject
import javax.inject.Inject

class LoginInteractor @Inject constructor(
        private val apolloClient: ApolloClient
) {

    val loginSubject = PublishSubject.create<Pair<String, String>>()

    fun login(username: String) {
        apolloClient.query(
                Pet.builder()
                        .name(username)
                        .build()
        ).enqueue(object : ApolloCall.Callback<Pet.Data>() {
            override fun onFailure(e: ApolloException) {
                loginSubject.onNext("" to "")
            }

            override fun onResponse(response: Response<Pet.Data>) {
                loginSubject.onNext((response.data()?.Pet()?.password()
                        ?: "") to (response.data()?.Pet()?.id() ?: ""))
            }
        })
    }
}