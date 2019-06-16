package com.example.kacperopyrchal.petinder.contacts

import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposables
import io.reactivex.functions.BiFunction
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class ContactsListPresenter @Inject constructor(
        private val contactInteractor: ContactInteractor
) {

    private var disposables = Disposables.disposed()

    fun onViewCreated(view: ContactsListView, name: String) {
        disposables = Observable.combineLatest(
                contactInteractor.inContactsSubject as Observable<List<SubContact>>,
                contactInteractor.outContactsSubject as Observable<List<SubContact>>,
                BiFunction { inContacts: List<SubContact>, outContacts: List<SubContact> ->
                    val result = inContacts.toMutableList()
                    result.addAll(outContacts)
                    result
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())

                .subscribe {
                    view.setItems(it)
                }

        contactInteractor.contact(name)
    }

}

interface ContactsListView {
    fun setItems(items: List<SubContact>)
}