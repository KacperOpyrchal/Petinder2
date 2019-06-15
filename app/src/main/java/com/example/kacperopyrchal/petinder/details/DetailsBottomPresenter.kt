package com.example.kacperopyrchal.petinder.details

import javax.inject.Inject

class DetailsBottomPresenter @Inject constructor() {

    fun onPhoneCallClick(view: DetailsBottomFragment) {
        view.openPhoneCall("123 456 789")
    }

    fun onSmsClick(view: DetailsBottomFragment) {
        view.openSms("123 456 789")
    }

    fun onLocalizationClick(view: DetailsBottomFragment) {
        view.openGps()
    }

}

interface DetailsBottomView {
    fun openSms(phoneNumber: String)
    fun openPhoneCall(phoneNumber: String)
    fun openGps()
}