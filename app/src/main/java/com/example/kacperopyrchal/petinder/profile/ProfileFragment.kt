package com.example.kacperopyrchal.petinder.profile

import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.kacperopyrchal.petinder.CurrentUser
import com.example.kacperopyrchal.petinder.DependencyInjector
import com.example.kacperopyrchal.petinder.R
import com.example.kacperopyrchal.petinder.contacts.Contact
import com.example.kacperopyrchal.petinder.details.DetailsBottomFragment
import com.example.kacperopyrchal.petinder.login.LoginActivity
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.fragment_profile.*
import javax.inject.Inject

const val LOCAL_NAME = "local_name"
const val SHOW_MENU = "show_menu"

class ProfileFragment : Fragment() {

    @Inject
    lateinit var presenter: ProfilePresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {}
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        return inflater.inflate(R.layout.fragment_profile, container, false)
    }

    fun populateFields(contact: Contact) {
        CurrentUser.currentPartner = contact
        with(contact) {
            profileName.text = "$name $surname"
            profileDescription.text = description
            profileNumber.text = phone
            profileEmail.text = email
            profileLocation.text = city
            Picasso.with(context).load(image).into(profileImage)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        DependencyInjector.applicationComponent()!!.inject(this)

        if (arguments?.getBoolean(SHOW_MENU) == false) {
            editButton.visibility = View.GONE
            logoutButton.visibility = View.GONE
            settingsButton.visibility = View.GONE
            detailsButton.visibility = View.VISIBLE
        }

        presenter.onCreate(this, arguments?.getString(LOCAL_NAME) ?: "")

        logoutButton.setOnClickListener {
            val intent = Intent(activity, LoginActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
        }

        detailsButton.setOnClickListener {
            val bottomDialog = DetailsBottomFragment()
            bottomDialog.show(this@ProfileFragment.fragmentManager, "bottom dialog")
        }
    }

    companion object {

        @JvmStatic
        fun newInstance(username: String, showMenu: Boolean = false) = ProfileFragment().apply {
            arguments = Bundle().apply {
                putString(LOCAL_NAME, username)
                putBoolean(SHOW_MENU, showMenu)
            }
        }
    }
}
