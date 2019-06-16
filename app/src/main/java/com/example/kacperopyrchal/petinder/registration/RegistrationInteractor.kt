package com.example.kacperopyrchal.petinder.registration

import RegisterPet
import com.apollographql.apollo.ApolloCall
import com.apollographql.apollo.ApolloClient
import com.apollographql.apollo.api.Response
import com.apollographql.apollo.exception.ApolloException
import io.reactivex.subjects.PublishSubject
import type.Gender
import type.Species
import javax.inject.Inject

class RegistrationInteractor @Inject constructor(
        private val apolloClient: ApolloClient
) {

    val registerSubject = PublishSubject.create<Boolean>()

    fun register(username: String,
                 surname: String,
                 password: String,
                 email: String,
                 yourSpecies: Species,
                 preferredSpecies: Species,
                 yourGender: Gender,
                 preferredGender: Gender
    ) {
        apolloClient.mutate(
                RegisterPet.builder()
                        .name(username)
                        .password(password)
                        .surname(surname)
                        .email(email)
                        .species(yourSpecies)
                        .preferredSpecies(preferredSpecies)
                        .gender(yourGender)
                        .preferredGender(preferredGender)
                        .build()
        ).enqueue(object : ApolloCall.Callback<RegisterPet.Data>() {
            override fun onResponse(response: Response<RegisterPet.Data>) {
                registerSubject.onNext(true)
            }

            override fun onFailure(e: ApolloException) {
                registerSubject.onNext(false)
            }
        })
    }

}