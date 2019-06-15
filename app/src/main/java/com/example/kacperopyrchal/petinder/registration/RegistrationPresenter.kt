package com.example.kacperopyrchal.petinder.registration

import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class RegistrationPresenter @Inject constructor() {

    fun onRegisterClick(view: RegisterView) {
        view.setProgress(true)
        Observable.timer(1, TimeUnit.SECONDS)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {
                    view.setProgress(false)
                    view.openHomeScreen()
                }
    }
}

interface RegisterView {
    fun openHomeScreen()
    fun setProgress(visible: Boolean)
}
