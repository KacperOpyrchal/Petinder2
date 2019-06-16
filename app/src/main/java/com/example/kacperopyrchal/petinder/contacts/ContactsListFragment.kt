package com.example.kacperopyrchal.petinder.contacts

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.kacperopyrchal.petinder.DependencyInjector
import com.example.kacperopyrchal.petinder.R
import kotlinx.android.synthetic.main.fragment_item_list.*
import javax.inject.Inject

class ContactsListFragment : Fragment(), ContactsListView {

    @Inject
    lateinit var presenter: ContactsListPresenter

    private var columnCount = 1

    private val listener = object : OnListFragmentInteractionListener {
        override fun onListFragmentInteraction(item: Contact?) {
            //TODO on click
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

        list.layoutManager = when {
            columnCount <= 1 -> LinearLayoutManager(context)
            else -> GridLayoutManager(context, columnCount)
        }
        presenter.onViewCreated(this)
    }

    override fun setItems(items: List<Contact>) {
        list.adapter = MyItemRecyclerViewAdapter(items, listener, context!!)
    }

    interface OnListFragmentInteractionListener {

        fun onListFragmentInteraction(item: Contact?)
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