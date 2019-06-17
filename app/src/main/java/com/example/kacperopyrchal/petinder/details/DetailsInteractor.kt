package com.example.kacperopyrchal.petinder.details

import Amor
import Recommendation
import com.apollographql.apollo.ApolloCall
import com.apollographql.apollo.ApolloClient
import com.apollographql.apollo.api.Response
import com.apollographql.apollo.exception.ApolloException
import com.example.kacperopyrchal.petinder.contacts.Contact
import io.reactivex.subjects.PublishSubject
import type.Gender
import type.RelationStatus
import type.Species
import javax.inject.Inject

class DetailsInteractor @Inject constructor(
        private val apolloClient: ApolloClient
) {

    val reactionSubject = PublishSubject.create<RelationStatus>()
    val recomendationsSubject = PublishSubject.create<List<Contact>>()

    fun react(userId: String, partnerId: String, relation: RelationStatus) {
        apolloClient.mutate(
                Amor.builder()
                        .status(relation)
                        .fromId(userId)
                        .toId(partnerId)
                        .build()
        ).enqueue(object : ApolloCall.Callback<Amor.Data>() {
            override fun onFailure(e: ApolloException) {
                reactionSubject.onError(Throwable("Reaction error"))
            }

            override fun onResponse(response: Response<Amor.Data>) {
                reactionSubject.onNext(relation)
            }
        })
    }

    fun recomendations(gender: Gender, species: Species) {
        apolloClient.query(
                Recommendation.builder()
                        .gender(gender)
                        .species(species)
                        .build()
        ).enqueue(object : ApolloCall.Callback<Recommendation.Data>() {
            override fun onFailure(e: ApolloException) {
                recomendationsSubject.onError(Throwable("Recomendation Error"))
            }

            override fun onResponse(response: Response<Recommendation.Data>) {
                recomendationsSubject.onNext(response.data()!!.allPets().map { it.contact() })
            }
        })
    }
}

private fun Recommendation.AllPet.contact(): Contact {
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
            prefGender = preferredGender() ?: Gender.None,
            prefSpecies = preferredSpecies() ?: Species.Goose,
            image = image() ?: "",
            description = description() ?: "",
            x = location()?.x() ?: 0.0,
            y = location()?.y() ?: 0.0,
            city = location()?.city() ?: "Krakow"

    )
}