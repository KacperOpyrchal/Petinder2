package com.example.kacperopyrchal.petinder.login

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.text.SpannableStringBuilder
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.Toast
import com.example.kacperopyrchal.petinder.DependencyInjector
import com.example.kacperopyrchal.petinder.R
import com.example.kacperopyrchal.petinder.adjustColor
import com.example.kacperopyrchal.petinder.biometric.BiometricCallback
import com.example.kacperopyrchal.petinder.biometric.BiometricManager
import com.example.kacperopyrchal.petinder.details.DetailsActivity
import com.example.kacperopyrchal.petinder.registration.RegistrationActivity
import kotlinx.android.synthetic.main.fragment_login.*
import javax.inject.Inject

const val PASSWORD = "password"
const val USERNAME = "username"
const val ID = "userId"

class LoginFragment : Fragment(), LoginView, BiometricCallback {

    @Inject
    lateinit var presenter: LoginPresenter

    private var biometricManager: BiometricManager? = null

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
            activity?.window?.setSoftInputMode(
                    WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN)
            presenter.onLoginClick(this, usernameField.text.toString(), passwordField.text.toString())
        }

        registerButton.setOnClickListener {
            presenter.onRegisterClick(this)
        }

        fingerprintButton.setOnClickListener {

            biometricManager = BiometricManager.BiometricBuilder(context)
                    .setTitle(getString(R.string.biometric_title))
                    .setSubtitle(getString(R.string.biometric_subtitle))
                    .setDescription(getString(R.string.biometric_description))
                    .setNegativeButtonText(getString(R.string.biometric_negative_button_text))
                    .build()

            //start authentication
            biometricManager?.authenticate(this)
        }
    }


    override fun setProgress(visible: Boolean) {
        progressBar.visibility = if (visible) View.VISIBLE else View.GONE
    }

    override fun showLoginError() {
        Toast.makeText(context, "Login error!", Toast.LENGTH_LONG).show()
        passwordField.text = SpannableStringBuilder("")
    }

    override fun openHomeScreen(username: String, id: String, password: String) {
        val prefs = activity?.getSharedPreferences("pref", Context.MODE_PRIVATE)
        prefs?.edit()
                ?.putString(ID, id)
                ?.putString(USERNAME, username)
                ?.putString(PASSWORD, password)
                ?.apply()

        val intent = Intent(activity, DetailsActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
    }

    override fun openRegistrationScreen() {
        val intent = Intent(activity, RegistrationActivity::class.java)
        startActivity(intent)
    }


    override fun onSdkVersionNotSupported() {
        Toast.makeText(context, getString(R.string.biometric_error_sdk_not_supported), Toast.LENGTH_LONG).show()
    }

    override fun onBiometricAuthenticationNotSupported() {
        Toast.makeText(context, getString(R.string.biometric_error_hardware_not_supported), Toast.LENGTH_LONG).show()
    }

    override fun onBiometricAuthenticationNotAvailable() {
        Toast.makeText(context, getString(R.string.biometric_error_fingerprint_not_available), Toast.LENGTH_LONG).show()
    }

    override fun onBiometricAuthenticationPermissionNotGranted() {
        Toast.makeText(context, getString(R.string.biometric_error_permission_not_granted), Toast.LENGTH_LONG).show()
    }

    override fun onBiometricAuthenticationInternalError(error: String) {
        Toast.makeText(context, error, Toast.LENGTH_LONG).show()
    }

    override fun onAuthenticationFailed() {
        //        Toast.makeText(getApplicationContext(), getString(R.string.biometric_failure), Toast.LENGTH_LONG).show();
    }

    override fun onAuthenticationCancelled() {
        Toast.makeText(context, getString(R.string.biometric_cancelled), Toast.LENGTH_LONG).show()
        biometricManager?.cancelAuthentication()
    }

    override fun onAuthenticationSuccessful() {
        val prefs = activity?.getSharedPreferences("pref", Context.MODE_PRIVATE)
        val username = prefs?.getString(USERNAME, "")
        val password = prefs?.getString(PASSWORD, "")

        Toast.makeText(context, getString(R.string.biometric_success), Toast.LENGTH_LONG).show()
        presenter.onLoginClick(this, username!!, password!!)
    }

    override fun onAuthenticationHelp(helpCode: Int, helpString: CharSequence) {
        //        Toast.makeText(getApplicationContext(), helpString, Toast.LENGTH_LONG).show();
    }

    override fun onAuthenticationError(errorCode: Int, errString: CharSequence) {
        //        Toast.makeText(getApplicationContext(), errString, Toast.LENGTH_LONG).show();
    }


    companion object {

        @JvmStatic
        fun newInstance() = LoginFragment().apply {
            arguments = Bundle().apply {}
        }
    }
}
