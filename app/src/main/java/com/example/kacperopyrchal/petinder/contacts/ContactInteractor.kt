package com.example.kacperopyrchal.petinder.contacts

import Pet
import com.apollographql.apollo.ApolloCall
import com.apollographql.apollo.ApolloClient
import com.apollographql.apollo.api.Response
import com.apollographql.apollo.exception.ApolloException
import io.reactivex.subjects.PublishSubject
import javax.inject.Inject

class ContactInteractor @Inject constructor(
        private val apolloClient: ApolloClient
) {

    val contactSubject = PublishSubject.create<Contact>()

    val inContactsSubject = PublishSubject.create<List<SubContact>>()
    val outContactsSubject = PublishSubject.create<List<SubContact>>()

    fun contact(username: String) {
        apolloClient.query(
                Pet.builder()
                        .name(username)
                        .build()
        ).enqueue(object : ApolloCall.Callback<Pet.Data>() {
            override fun onFailure(e: ApolloException) {
//                contactSubject.onNext()
            }

            override fun onResponse(response: Response<Pet.Data>) {
                contactSubject.onNext(response.data()!!.contact())
                inContactsSubject.onNext(response.data()!!.Pet()!!.inRelations()!!.map {
                    with(it.from()!!) {
                        SubContact(
                                name = name(),
                                surname = surname(),
                                image = image() ?: "",
                                phone = phone() ?: "",
                                email = email() ?: ""
                        )
                    }
                })
                outContactsSubject.onNext(response.data()!!.Pet()!!.outRelations()!!.map {
                    with(it.to()!!) {
                        SubContact(
                                name = name(),
                                surname = surname(),
                                image = image() ?: "",
                                phone = phone() ?: "",
                                email = email() ?: ""
                        )
                    }
                })
            }
        })
    }

}

private fun Pet.Data.contact(): Contact {
    with(Pet()!!) {
        return Contact(
                id = "123",
                name = name(),
                surname = surname(),
                phone = phone() ?: "",
                email = email() ?: "",
                video = video() ?: "",
                music = music() ?: "",
                gender = gender(),
                species = species(),
                image = image() ?: "",
                description = description() ?: ""
        )
    }
}