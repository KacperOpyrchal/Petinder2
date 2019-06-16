package com.example.kacperopyrchal.petinder.details

import javax.inject.Inject

class DetailsBottomPresenter @Inject constructor() {

    fun onPhoneCallClick(view: DetailsBottomView) {
        view.openPhoneCall("123 456 789")
    }

    fun onSmsClick(view: DetailsBottomView) {
        view.openSms("123 456 789")
    }

    fun onLocalizationClick(view: DetailsBottomView) {
        view.openGps()
    }

    fun onVideoClicked(view: DetailsBottomView) {
        view.openVideo("http://commondatastorage.googleapis.com/gtv-videos-bucket/sample/BigBuckBunny.mp4")
    }

    fun onMusicClicked(view: DetailsBottomView) {
        view.openMusic("http://www.hochmuth.com/mp3/Haydn_Cello_Concerto_D-1.mp3")
    }

    fun onEmailClicked(view: DetailsBottomView) {
        view.openEmail("dupa@gmail.com")
    }
}

interface DetailsBottomView {
    fun openSms(phoneNumber: String)
    fun openPhoneCall(phoneNumber: String)
    fun openEmail(email: String)
    fun openGps()
    fun openVideo(url: String)
    fun openMusic(url: String)
}