package com.example.kacperopyrchal.petinder.registration

import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import com.example.kacperopyrchal.petinder.DependencyInjector
import com.example.kacperopyrchal.petinder.R
import com.example.kacperopyrchal.petinder.adjustColor
import com.example.kacperopyrchal.petinder.details.DetailsActivity
import com.example.kacperopyrchal.petinder.enums.Gender
import com.example.kacperopyrchal.petinder.enums.Species
import kotlinx.android.synthetic.main.fragment_registration.*
import javax.inject.Inject

class RegistrationFragment : Fragment(), RegisterView {

    @Inject
    lateinit var presenter: RegistrationPresenter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_registration, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        DependencyInjector.applicationComponent()!!.inject(this)

        registerButton.adjustColor(R.color.blue)

        yourGenderField.adapter = ArrayAdapter(context, android.R.layout.simple_spinner_item, Gender.values())
        yourSpeciesField.adapter = ArrayAdapter(context, android.R.layout.simple_spinner_item, Species.values())
        preferredGenderField.adapter = ArrayAdapter(context, android.R.layout.simple_spinner_item, Gender.values())
        preferredSpeciesField.adapter = ArrayAdapter(context, android.R.layout.simple_spinner_item, Species.values())

        registerButton.setOnClickListener {
            presenter.onRegisterClick(this)
        }
    }

    override fun setProgress(visible: Boolean) {
        progressBar.visibility = if (visible) View.VISIBLE else View.GONE
    }

    override fun openHomeScreen() {
        val intent = Intent(activity, DetailsActivity::class.java)
        startActivity(intent)
    }

    companion object {

        @JvmStatic
        fun newInstance() = RegistrationFragment().apply {
            arguments = Bundle().apply {}
        }
    }
}
