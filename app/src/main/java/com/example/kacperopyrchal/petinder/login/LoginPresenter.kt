package com.example.kacperopyrchal.petinder.login

import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class LoginPresenter @Inject constructor() {

    fun onLoginClick(view: LoginView) {
        view.setProgress(true)
        Observable.timer(1, TimeUnit.SECONDS)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {
                    view.setProgress(false)
                    view.openHomeScreen()
                }
    }

    fun onRegisterClick(view: LoginView) {
        view.openRegistrationScreen()
    }

}

interface LoginView {
    fun openHomeScreen()
    fun openRegistrationScreen()
    fun setProgress(visible: Boolean)
}