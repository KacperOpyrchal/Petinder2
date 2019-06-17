package com.example.kacperopyrchal.petinder.contacts


import android.annotation.SuppressLint
import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import com.example.kacperopyrchal.petinder.R
import com.example.kacperopyrchal.petinder.contacts.ContactsListFragment.OnListFragmentInteractionListener
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.fragment_item.view.*


class MyItemRecyclerViewAdapter(
        private val mValues: List<SubContact>,
        private val mListener: OnListFragmentInteractionListener?,
        private val context: Context)
    : RecyclerView.Adapter<MyItemRecyclerViewAdapter.ViewHolder>() {

    private val mOnClickListener: View.OnClickListener

    init {
        mOnClickListener = View.OnClickListener { v ->
            val item = v.tag as SubContact

            mListener?.onListFragmentInteraction(item)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.fragment_item, parent, false)
        return ViewHolder(view)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = mValues[position]
        holder.apply {
            nameView.text = "${item.name} ${item.surname}"
            emailView.text = item.email
            phoneView.text = item.phone
            Picasso.with(context).load(item.image).into(iconView)
        }

        with(holder.view) {
            tag = item
            setOnClickListener(mOnClickListener)
        }
    }


    override fun getItemCount(): Int = mValues.size

    inner class ViewHolder(val view: View) : RecyclerView.ViewHolder(view) {

        val nameView: TextView = view.item_name
        val iconView: ImageView = view.item_icon
        val emailView: TextView = view.item_email
        val phoneView: TextView = view.item_phone
    }
}
