package com.example.kacperopyrchal.petinder.details

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.support.design.widget.BottomSheetDialogFragment
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.kacperopyrchal.petinder.DependencyInjector
import com.example.kacperopyrchal.petinder.R
import kotlinx.android.synthetic.main.fragment_details_bottom.*
import javax.inject.Inject

class DetailsBottomFragment : BottomSheetDialogFragment(), DetailsBottomView {

    @Inject
    lateinit var presenter: DetailsBottomPresenter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_details_bottom, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        DependencyInjector.applicationComponent()!!.inject(this)

        if (ContextCompat.checkSelfPermission(context!!,
                        Manifest.permission.CALL_PHONE)
                != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(activity!!, arrayOf(Manifest.permission.CALL_PHONE), 1)
        }

        phoneOption.setOnClickListener { presenter.onPhoneCallClick(this) }
        smsOption.setOnClickListener { presenter.onSmsClick(this) }
        gpsOption.setOnClickListener { presenter.onLocalizationClick(this) }

    }

    override fun openSms(phoneNumber: String) {
        val uri = Uri.parse("smsto:$phoneNumber")
        val intent = Intent(Intent.ACTION_SENDTO, uri)
        intent.putExtra("Bonieczku", "Jesteś boski bonieczku :*")
        startActivity(intent, null)
    }

    override fun openPhoneCall(phoneNumber: String) {
        if (ContextCompat.checkSelfPermission(context!!,
                        Manifest.permission.CALL_PHONE)
                != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(activity!!, arrayOf(Manifest.permission.CALL_PHONE), 1)
        } else {
            val intent = Intent(Intent.ACTION_CALL, Uri.parse("tel:$phoneNumber"))
            startActivity(intent, null)

        }
    }

    override fun openGps() {
        // no-op
    }
}