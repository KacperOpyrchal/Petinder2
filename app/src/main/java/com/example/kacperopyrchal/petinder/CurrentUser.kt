package com.example.kacperopyrchal.petinder

import com.example.kacperopyrchal.petinder.contacts.Contact
import javax.inject.Singleton

@Singleton
object CurrentUser {
    var user: Contact? = null
    var currentPartner: Contact? = null
}