package com.example.kacperopyrchal.petinder.details

import android.content.Context.SENSOR_SERVICE
import android.hardware.SensorManager
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.kacperopyrchal.petinder.DependencyInjector
import com.example.kacperopyrchal.petinder.R
import com.example.kacperopyrchal.petinder.adjustColor
import com.example.kacperopyrchal.petinder.contacts.Contact
import com.example.kacperopyrchal.petinder.profile.LOCAL_NAME
import com.squareup.picasso.Picasso
import com.squareup.seismic.ShakeDetector
import kotlinx.android.synthetic.main.fragment_details.*
import type.RelationStatus
import javax.inject.Inject

class DetailsFragment : Fragment(), DetailsView {

    @Inject
    lateinit var presenter: DetailsPresenter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_details, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        DependencyInjector.applicationComponent()!!.inject(this)

        lubieButton.adjustColor(R.color.green)
        nielubieButton.adjustColor(R.color.red)

        presenter.onCreate(arguments?.getString(LOCAL_NAME) ?: "", this)

        opcjeButton.setOnClickListener {
            presenter.onOptionButtonClicked(this)
        }
        lubieButton.setOnClickListener {
            presenter.react(RelationStatus.Like, this)
        }
        nielubieButton.setOnClickListener {
            presenter.react(RelationStatus.Dislike, this)
        }

        val shakeDetector = ShakeDetector {
            Toast.makeText(context, "Shake :D", Toast.LENGTH_LONG).show()
            presenter.react(RelationStatus.Dislike, this)
        }

        shakeDetector.start(activity!!.getSystemService(SENSOR_SERVICE) as SensorManager)

    }

    override fun openBottomDialog() {
        val bottomDialog = DetailsBottomFragment()
        bottomDialog.show(this@DetailsFragment.fragmentManager, "bottom dialog")
    }

    override fun showPartner(contact: Contact) {
        bottomId.visibility = View.VISIBLE
        Picasso.with(context).load(contact.image).into(partnerImage)
    }

    override fun showEmptyView() {
        emptyView.visibility = View.VISIBLE
        mainView.visibility = View.GONE
    }

    override fun setProgress(progress: Boolean) {
        detailsProgress.visibility = if (progress) View.VISIBLE else View.GONE
    }

    companion object {

        @JvmStatic
        fun newInstance(username: String) =
                DetailsFragment().apply {
                    arguments = Bundle().apply {
                        putString(LOCAL_NAME, username)
                    }
                }
    }
}
