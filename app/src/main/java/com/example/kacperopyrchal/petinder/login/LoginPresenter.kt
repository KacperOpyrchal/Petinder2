package com.example.kacperopyrchal.petinder.login

import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposables
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class LoginPresenter @Inject constructor(
        private val loginInteractor: LoginInteractor
) {

    private var disposable = Disposables.disposed()

    fun onLoginClick(view: LoginView, username: String, password: String) {
        view.setProgress(true)

        disposable = loginInteractor.loginSubject
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {
                    view.setProgress(false)
                    if (it.first.isNotBlank() && password == it.first) {
                        view.openHomeScreen(username, it.second, password)
                    } else {
                        view.showLoginError()
                    }
                    disposable.dispose()
                }

        loginInteractor.login(username)
    }

    fun onRegisterClick(view: LoginView) {
        view.openRegistrationScreen()
    }

}

interface LoginView {
    fun openHomeScreen(username: String, id: String, password: String)
    fun openRegistrationScreen()
    fun setProgress(visible: Boolean)
    fun showLoginError()
}