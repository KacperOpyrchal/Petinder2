package com.example.kacperopyrchal.petinder.details

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import android.support.v7.app.AppCompatActivity
import com.example.kacperopyrchal.petinder.R
import com.example.kacperopyrchal.petinder.contacts.ContactsListFragment
import com.example.kacperopyrchal.petinder.details.PagerFragment.*
import kotlinx.android.synthetic.main.activity_main.*

class DetailsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)

        viewPager.adapter = MainPagerAdapter(supportFragmentManager)

//        if (savedInstanceState == null) {
//            supportFragmentManager.beginTransaction()
//                    .replace(R.id.viewHolder, DetailsFragment.newInstance(), "details_fragment")
//                    .commitAllowingStateLoss()
//        }
    }
}

class MainPagerAdapter(fragmentManager: FragmentManager) : FragmentPagerAdapter(fragmentManager) {

    private val fragments = arrayOf(ContactList, Details, Config)

    override fun getItem(position: Int): Fragment = fragments[position].fragment()

    override fun getCount(): Int = fragments.size

}

sealed class PagerFragment(val fragment: () -> Fragment) {

    object Details : PagerFragment({ DetailsFragment.newInstance() })

    object ContactList : PagerFragment({ ContactsListFragment.newInstance(1) })

    object Config : PagerFragment({ DetailsFragment.newInstance() })

}