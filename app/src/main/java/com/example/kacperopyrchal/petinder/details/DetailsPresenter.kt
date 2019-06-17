package com.example.kacperopyrchal.petinder.details

import com.example.kacperopyrchal.petinder.CurrentUser
import com.example.kacperopyrchal.petinder.contacts.Contact
import com.example.kacperopyrchal.petinder.contacts.ContactInteractor
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposables
import io.reactivex.schedulers.Schedulers
import type.RelationStatus
import javax.inject.Inject

class DetailsPresenter @Inject constructor(
        private val detailsInteractor: DetailsInteractor,
        private val contactInteractor: ContactInteractor
) {

    private var disposable = Disposables.disposed()
    private var contactDis = Disposables.disposed()
    private var recommendationDis = Disposables.disposed()

    private var user: Contact? = null

    private var currentIndex = 0

    private var recommendations: List<Contact> = emptyList()

    fun onCreate(name: String, view: DetailsView) {
        view.setProgress(true)
        contactDis = contactInteractor.contactSubject
                .sub()
                .subscribe {
                    user = it
                    CurrentUser.user = it
                    detailsInteractor.recomendations(it.prefGender, it.prefSpecies)
                    contactDis.dispose()
                }
        recommendationDis = detailsInteractor.recomendationsSubject.sub()
                .subscribe {
                    view.setProgress(false)
                    recommendations = it
                    CurrentUser.currentPartner = it[0]
                    view.showPartner(it[0])
                    recommendationDis.dispose()

                }
        disposable = detailsInteractor.reactionSubject
                .sub()
                .subscribe {
                    currentIndex++
                    view.setProgress(false)
                    if (recommendations.size > currentIndex) {
                        CurrentUser.currentPartner = recommendations[currentIndex]
                        view.showPartner(recommendations[currentIndex])
                    } else {
                        view.showEmptyView()
                    }
                }
        contactInteractor.contact(name)
    }

    fun onOptionButtonClicked(view: DetailsFragment) {
        view.openBottomDialog()
    }

}


interface DetailsView {
    fun openBottomDialog()
    fun showPartner(contact: Contact)
    fun showEmptyView()
    fun setProgress(progress: Boolean)
}

fun <T> Observable<T>.sub(): Observable<T> =
        subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())