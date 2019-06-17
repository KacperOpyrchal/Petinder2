package com.example.kacperopyrchal.petinder.details

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import android.support.v7.app.AppCompatActivity
import android.view.Window
import com.example.kacperopyrchal.petinder.R
import com.example.kacperopyrchal.petinder.contacts.ContactsListFragment
import com.example.kacperopyrchal.petinder.details.PagerFragment.ContactList
import com.example.kacperopyrchal.petinder.details.PagerFragment.Profile
import com.example.kacperopyrchal.petinder.login.USERNAME
import com.example.kacperopyrchal.petinder.profile.ProfileFragment
import kotlinx.android.synthetic.main.activity_main.*

class DetailsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        this.requestWindowFeature(Window.FEATURE_NO_TITLE)

        setContentView(R.layout.activity_main)

        val prefs = getSharedPreferences("pref", Context.MODE_PRIVATE)

        supportActionBar?.hide()
        sliding_tabs.setupWithViewPager(viewPager)
        viewPager.adapter = MainPagerAdapter(supportFragmentManager, prefs.getString(USERNAME, ""))
        viewPager.offscreenPageLimit = 5
    }
}

class MainPagerAdapter(fragmentManager: FragmentManager, private val name: String) : FragmentPagerAdapter(fragmentManager) {

    private val fragments = arrayOf(Profile, PagerFragment.Details, ContactList)

    override fun getItem(position: Int): Fragment = fragments[position].fragment(name)

    override fun getCount(): Int = fragments.size

    override fun getPageTitle(position: Int): CharSequence? {
        return fragments[position].title
    }

}

sealed class PagerFragment(val fragment: (String) -> Fragment, val title: String) {

    object Details : PagerFragment({ DetailsFragment.newInstance(it) }, "Explore")

    object ContactList : PagerFragment({ ContactsListFragment.newInstance(1) }, "Contacts")

    object Profile : PagerFragment({ ProfileFragment.newInstance(it) }, "Profile")

}