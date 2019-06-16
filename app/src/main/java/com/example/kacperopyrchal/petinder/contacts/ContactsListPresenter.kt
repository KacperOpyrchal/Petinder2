package com.example.kacperopyrchal.petinder.contacts

import javax.inject.Inject

class ContactsListPresenter @Inject constructor() {

    private val items = listOf<Contact>(
            Contact(id = "1"),
            Contact(id = "2"),
            Contact(id = "3"),
            Contact(id = "4"),
            Contact(id = "5"),
            Contact(id = "6"),
            Contact(id = "7"),
            Contact(id = "8"),
            Contact(id = "9")
    )

    fun onViewCreated(view: ContactsListView) {
        view.setItems(items)
    }

}

interface ContactsListView {
    fun setItems(items: List<Contact>)
}