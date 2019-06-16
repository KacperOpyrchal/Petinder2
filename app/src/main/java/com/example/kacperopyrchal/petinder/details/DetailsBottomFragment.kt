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
import com.example.kacperopyrchal.petinder.GpsActivity
import com.example.kacperopyrchal.petinder.R
import kotlinx.android.synthetic.main.fragment_details_bottom.*
import javax.inject.Inject

const val POINT_X = "POINT_X"
const val POINT_Y = "POINT_Y"

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
        videoOption.setOnClickListener { presenter.onVideoClicked(this) }
        musicOption.setOnClickListener { presenter.onMusicClicked(this) }
        emailOption.setOnClickListener { presenter.onEmailClicked(this) }

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
        val intent = Intent(activity, GpsActivity::class.java)
        intent.putExtra(POINT_X, -34.0)
        intent.putExtra(POINT_Y, 151.0)
        startActivity(intent)
    }

    override fun openEmail(email: String) {
        val emailIntent = Intent(Intent.ACTION_SENDTO, Uri.fromParts(
                "mailto", email, null))
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Tratata")
        emailIntent.putExtra(Intent.EXTRA_TEXT, "lala :O")
        startActivity(Intent.createChooser(emailIntent, "Send email..."))
    }

    override fun openVideo(url: String) {
        val intent = Intent(Intent.ACTION_VIEW)
        intent.setDataAndType(Uri.parse(url), "video/mp4")
        startActivity(intent)
    }

    override fun openMusic(url: String) {
        val intent = Intent(Intent.ACTION_VIEW)
        intent.setDataAndType(Uri.parse(url), "audio/mp3")
        startActivity(intent)
    }
}