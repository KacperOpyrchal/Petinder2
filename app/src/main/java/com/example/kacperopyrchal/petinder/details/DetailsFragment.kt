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
import com.squareup.seismic.ShakeDetector
import kotlinx.android.synthetic.main.fragment_details.*
import javax.inject.Inject

class DetailsFragment : Fragment() {

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

        opcjeButton.setOnClickListener {
            presenter.onOptionButtonClicked(this)
        }

        val shakeDetector = ShakeDetector {
            Toast.makeText(context, "Shake :D", Toast.LENGTH_LONG).show()
        }

        shakeDetector.start(activity!!.getSystemService(SENSOR_SERVICE) as SensorManager)

    }

    fun openBottomDialog() {
        val bottomDialog = DetailsBottomFragment()
        bottomDialog.show(this@DetailsFragment.fragmentManager, "bottom dialog")
    }

    companion object {

        @JvmStatic
        fun newInstance() =
                DetailsFragment().apply {
                    arguments = Bundle()
                }
    }
}
