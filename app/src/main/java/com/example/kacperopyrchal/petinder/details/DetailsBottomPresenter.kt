package com.example.kacperopyrchal.petinder.details

import com.example.kacperopyrchal.petinder.CurrentUser
import javax.inject.Inject

class DetailsBottomPresenter @Inject constructor() {

    fun onPhoneCallClick(view: DetailsBottomView) {
        view.openPhoneCall(CurrentUser.currentPartner?.phone ?: "123 456 789")
    }

    fun onSmsClick(view: DetailsBottomView) {
        view.openSms(CurrentUser.currentPartner?.phone ?: "123 456 789")
    }

    fun onLocalizationClick(view: DetailsBottomView) {
        CurrentUser.currentPartner?.apply {
            view.openGps(x, y, city)
        }
    }

    fun onVideoClicked(view: DetailsBottomView) {
        view.openVideo(CurrentUser.currentPartner?.video
                ?: "http://commondatastorage.googleapis.com/gtv-videos-bucket/sample/BigBuckBunny.mp4")
    }

    fun onMusicClicked(view: DetailsBottomView) {
        view.openMusic(CurrentUser.currentPartner?.music
                ?: "http://www.hochmuth.com/mp3/Haydn_Cello_Concerto_D-1.mp3")
    }

    fun onEmailClicked(view: DetailsBottomView) {
        view.openEmail(CurrentUser.currentPartner?.email ?: "dupa@gmail.com")
    }
}

interface DetailsBottomView {
    fun openSms(phoneNumber: String)
    fun openPhoneCall(phoneNumber: String)
    fun openEmail(email: String)
    fun openGps(x: Double, y: Double, city: String)
    fun openVideo(url: String)
    fun openMusic(url: String)
}