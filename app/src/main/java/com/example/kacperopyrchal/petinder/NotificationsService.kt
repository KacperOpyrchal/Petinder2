package com.example.kacperopyrchal.petinder

import android.app.NotificationManager
import android.app.PendingIntent
import android.app.Service
import android.content.Context
import android.content.Intent
import android.os.IBinder
import android.support.v4.app.NotificationCompat
import com.example.kacperopyrchal.petinder.contacts.ContactInteractor
import com.example.kacperopyrchal.petinder.details.DetailsInteractor
import com.example.kacperopyrchal.petinder.details.sub
import com.example.kacperopyrchal.petinder.login.LoginActivity
import com.example.kacperopyrchal.petinder.login.USERNAME
import io.reactivex.disposables.Disposables
import javax.inject.Inject

class NotificationsService : Service() {

    @Inject
    lateinit var detailsInteractor: DetailsInteractor

    @Inject
    lateinit var contactInteractor: ContactInteractor

    private var disposableRecommended = Disposables.disposed()

    private var disposableContact = Disposables.disposed()

    override fun onStartCommand(intent: Intent, flags: Int, startId: Int): Int {
        return START_NOT_STICKY
    }

    override fun onCreate() {
        super.onCreate()

        DependencyInjector.applicationComponent()!!.inject(this)

        disposableContact = contactInteractor.contactSubject.sub()
                .subscribe {
                    detailsInteractor.recomendations(it.prefGender, it.prefSpecies)
                }
        disposableRecommended = detailsInteractor.recomendationsSubject.sub()
                .subscribe {
                    if (it.isNotEmpty()) {
                        triggerNotification()
                    }
                }
        val prefs = getSharedPreferences("pref", Context.MODE_PRIVATE)

        contactInteractor.contact(prefs.getString(USERNAME, "Sebastian"))
    }

    private fun triggerNotification() {
        val id = 1
        val builder = NotificationCompat.Builder(this)
        val loginIntent = Intent(applicationContext, LoginActivity::class.java)
        loginIntent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        builder.setContentText("New opportunities awaits!")
                .setContentTitle("Explore our platform!")
                .setContentIntent(
                        PendingIntent.getActivity(applicationContext,
                                id,
                                loginIntent,
                                PendingIntent.FLAG_UPDATE_CURRENT
                        )
                )
                .setSmallIcon(R.mipmap.ic_launcher)
                .setVisibility(NotificationCompat.VISIBILITY_PUBLIC)

        val mNotificationManager =
                getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        mNotificationManager.notify(id, builder.build())
    }

    override fun onBind(intent: Intent): IBinder? {
        return null
    }
}
