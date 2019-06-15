package com.example.kacperopyrchal.petinder.details

import javax.inject.Inject

class DetailsPresenter @Inject constructor() {

    fun onOptionButtonClicked(view: DetailsFragment) {
        view.openBottomDialog()
    }

}