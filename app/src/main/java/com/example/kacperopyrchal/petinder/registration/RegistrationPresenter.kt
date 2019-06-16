package com.example.kacperopyrchal.petinder.registration

import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposables
import io.reactivex.schedulers.Schedulers
import type.Gender
import type.Species
import javax.inject.Inject

class RegistrationPresenter @Inject constructor(
        private val registrationInteractor: RegistrationInteractor
) {

    private var disposable = Disposables.disposed()

    fun onRegisterClick(view: RegisterView,
                        username: String,
                        surname: String,
                        password: String,
                        email: String,
                        yourSpecies: Species,
                        preferredSpecies: Species,
                        yourGender: Gender,
                        preferredGender: Gender
    ) {
        view.setProgress(true)
        disposable = registrationInteractor.registerSubject
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {
                    view.setProgress(false)
                    if (it) {
                        view.openHomeScreen()
                    }
                    disposable.dispose()
                }
        registrationInteractor.register(
                username = username,
                password = password,
                surname = surname,
                email = email,
                yourGender = yourGender,
                yourSpecies = yourSpecies,
                preferredGender = preferredGender,
                preferredSpecies = preferredSpecies
        )
    }
}

interface RegisterView {
    fun openHomeScreen()
    fun setProgress(visible: Boolean)
}
