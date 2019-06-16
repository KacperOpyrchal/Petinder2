package com.example.kacperopyrchal.petinder.contacts

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.kacperopyrchal.petinder.DependencyInjector
import com.example.kacperopyrchal.petinder.R
import com.example.kacperopyrchal.petinder.login.USERNAME
import com.example.kacperopyrchal.petinder.profile.LOCAL_NAME
import com.example.kacperopyrchal.petinder.profile.ProfileActivity
import kotlinx.android.synthetic.main.fragment_item_list.*
import javax.inject.Inject

class ContactsListFragment : Fragment(), ContactsListView {

    @Inject
    lateinit var presenter: ContactsListPresenter

    private var columnCount = 1

    private val listener = object : OnListFragmentInteractionListener {
        override fun onListFragmentInteraction(item: SubContact?) {
            val intent = Intent(activity, ProfileActivity::class.java)
            intent.putExtra(LOCAL_NAME, item?.name)
            startActivity(intent)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        arguments?.let {
            columnCount = it.getInt(ARG_COLUMN_COUNT)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_item_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        DependencyInjector.applicationComponent()!!.inject(this)

        val prefs = activity!!.getSharedPreferences("pref", Context.MODE_PRIVATE)

        list.layoutManager = when {
            columnCount <= 1 -> LinearLayoutManager(context)
            else -> GridLayoutManager(context, columnCount)
        }
        presenter.onViewCreated(this, prefs.getString(USERNAME, ""))
    }

    override fun setItems(items: List<SubContact>) {
        list.adapter = MyItemRecyclerViewAdapter(items, listener, context!!)
    }

    interface OnListFragmentInteractionListener {

        fun onListFragmentInteraction(item: SubContact?)
    }

    companion object {

        const val ARG_COLUMN_COUNT = "column-count"

        @JvmStatic
        fun newInstance(columnCount: Int) =
                ContactsListFragment().apply {
                    arguments = Bundle().apply {
                        putInt(ARG_COLUMN_COUNT, columnCount)
                    }
                }
    }
}