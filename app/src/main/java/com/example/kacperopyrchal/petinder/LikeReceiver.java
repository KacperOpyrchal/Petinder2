package com.example.kacperopyrchal.petinder;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class LikeReceiver extends BroadcastReceiver {

    private static final String TAG = "LL24";
    static Context context;

    @Override
    public void onReceive(Context context, Intent intent) {

        Intent ll24Service = new Intent(context, NotificationsService.class);
        context.startService(ll24Service);
    }
}
