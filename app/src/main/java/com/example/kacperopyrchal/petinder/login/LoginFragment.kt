package com.example.kacperopyrchal.petinder.login

import android.annotation.TargetApi
import android.content.DialogInterface
import android.content.Intent
import android.hardware.biometrics.BiometricPrompt
import android.os.Build
import android.os.Bundle
import android.security.keystore.KeyGenParameterSpec
import android.security.keystore.KeyProperties
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.kacperopyrchal.petinder.DependencyInjector
import com.example.kacperopyrchal.petinder.R
import com.example.kacperopyrchal.petinder.adjustColor
import com.example.kacperopyrchal.petinder.details.DetailsActivity
import com.example.kacperopyrchal.petinder.registration.RegistrationActivity
import kotlinx.android.synthetic.main.fragment_login.*
import java.io.IOException
import java.security.*
import java.security.cert.CertificateException
import javax.crypto.KeyGenerator
import javax.inject.Inject

const val KEY_NAME = "key_name"

class LoginFragment : Fragment(), LoginView {

    @Inject
    lateinit var presenter: LoginPresenter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_login, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        DependencyInjector.applicationComponent()!!.inject(this)

        loginButton.adjustColor(R.color.green)
        registerButton.adjustColor(R.color.blue)

        loginButton.setOnClickListener {
            presenter.onLoginClick(this)
        }

        registerButton.setOnClickListener {
            presenter.onRegisterClick(this)
        }

        fingerprintButton.setOnClickListener {
        }
    }

    override fun setProgress(visible: Boolean) {
        progressBar.visibility = if (visible) View.VISIBLE else View.GONE
    }

    override fun openHomeScreen() {
        val intent = Intent(activity, DetailsActivity::class.java)
        startActivity(intent)
    }

    override fun openRegistrationScreen() {
        val intent = Intent(activity, RegistrationActivity::class.java)
        startActivity(intent)
    }

    companion object {

        @JvmStatic
        fun newInstance() = LoginFragment().apply {
            arguments = Bundle().apply {}
        }
    }
}
