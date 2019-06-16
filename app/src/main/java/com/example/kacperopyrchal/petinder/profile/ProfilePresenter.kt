package com.example.kacperopyrchal.petinder.profile

import com.example.kacperopyrchal.petinder.contacts.ContactInteractor
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposables
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class ProfilePresenter @Inject constructor(
        private val contactInteractor: ContactInteractor
) {

    private var disposable = Disposables.disposed()

    fun onCreate(view: ProfileFragment, name: String) {
        disposable = contactInteractor.contactSubject
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {
                    view.populateFields(it)
                    disposable.dispose()
                }

        contactInteractor.contact(name)
    }

}