package com.example.kacperopyrchal.petinder.contacts

import type.Gender
import type.Species

data class Contact(
        val id: String,
        val name: String = "Puszok",
        val surname: String = "Nowak",
        val phone: String = "123 456 789",
        val email: String = "puszok@gmail.com",
        val video: String = "http://commondatastorage.googleapis.com/gtv-videos-bucket/sample/BigBuckBunny.mp4",
        val music: String = "http://www.hochmuth.com/mp3/Haydn_Cello_Concerto_D-1.mp3",
        val gender: Gender = Gender.Male,
        val species: Species = Species.Cat,
        val prefGender: Gender = Gender.Male,
        val prefSpecies: Species = Species.Cat,
        val image: String = "https://media.mnn.com/assets/images/2018/07/cat_eating_frozen_treat.jpg.653x0_q80_crop-smart.jpg",
        val description: String = "take me baybe",
        val city: String = "Krakow",
        val x: Double = 0.0,
        val y: Double = 0.0
)

data class SubContact(
        val id: String,
        val name: String = "Puszok",
        val surname: String = "Nowak",
        val phone: String = "123 456 789",
        val email: String = "puszok@gmail.com",
        val image: String = "https://media.mnn.com/assets/images/2018/07/cat_eating_frozen_treat.jpg.653x0_q80_crop-smart.jpg"
)