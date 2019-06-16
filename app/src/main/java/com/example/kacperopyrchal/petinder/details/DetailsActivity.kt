package com.example.kacperopyrchal.petinder.details

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
import com.example.kacperopyrchal.petinder.profile.ProfileFragment
import kotlinx.android.synthetic.main.activity_main.*

class DetailsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        this.requestWindowFeature(Window.FEATURE_NO_TITLE)

        setContentView(R.layout.activity_main)

        supportActionBar?.hide()
        sliding_tabs.setupWithViewPager(viewPager)
        viewPager.adapter = MainPagerAdapter(supportFragmentManager)
    }
}

class MainPagerAdapter(fragmentManager: FragmentManager) : FragmentPagerAdapter(fragmentManager) {

    private val fragments = arrayOf(Profile, PagerFragment.Details, ContactList)

    override fun getItem(position: Int): Fragment = fragments[position].fragment()

    override fun getCount(): Int = fragments.size

    override fun getPageTitle(position: Int): CharSequence? {
        return fragments[position].title
    }

}

sealed class PagerFragment(val fragment: () -> Fragment, val title: String) {

    object Details : PagerFragment({ DetailsFragment.newInstance() }, "Explore")

    object ContactList : PagerFragment({ ContactsListFragment.newInstance(1) }, "Contacts")

    object Profile : PagerFragment({ ProfileFragment.newInstance() }, "Profile")

}