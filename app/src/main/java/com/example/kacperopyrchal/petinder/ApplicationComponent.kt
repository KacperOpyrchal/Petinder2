package com.example.kacperopyrchal.petinder

import com.example.kacperopyrchal.petinder.contacts.ContactsListFragment
import com.example.kacperopyrchal.petinder.details.DetailsBottomFragment
import com.example.kacperopyrchal.petinder.details.DetailsFragment
import com.example.kacperopyrchal.petinder.login.LoginFragment
import com.example.kacperopyrchal.petinder.profile.ProfileFragment
import com.example.kacperopyrchal.petinder.registration.RegistrationFragment
import dagger.Component
import javax.inject.Singleton


@Singleton
@Component(modules = [ApplicationModule::class])
interface ApplicationComponent {

    fun inject(fragment: DetailsFragment)
    fun inject(fragment: DetailsBottomFragment)
    fun inject(fragment: LoginFragment)
    fun inject(fragment: RegistrationFragment)
    fun inject(fragment: ContactsListFragment)
    fun inject(fragment: ProfileFragment)
    fun inject(service: NotificationsService)
}