package com.methleshkoshle.karmathfinal.constant;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkCapabilities;
import android.net.NetworkInfo;
import android.os.Build;

import androidx.appcompat.app.AppCompatActivity;

public class Conditionals extends AppCompatActivity {
    public static boolean isInternetWorking(Context context){
        ConnectivityManager cm =
                (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        return activeNetwork != null &&
                activeNetwork.isConnectedOrConnecting();
    }
//    public static boolean isInternetWorking(Context context) {
//        final ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
//
//        if (cm != null) {
//            if (Build.VERSION.SDK_INT < 23) {
//                final NetworkInfo ni = cm.getActiveNetworkInfo();
//
//                if (ni != null) {
//                    return (ni.isConnected() && (ni.getType() == ConnectivityManager.TYPE_WIFI || ni.getType() == ConnectivityManager.TYPE_MOBILE));
//                }
//            } else {
//                final Network n = cm.getActiveNetwork();
//
//                if (n != null) {
//                    final NetworkCapabilities nc = cm.getNetworkCapabilities(n);
//
//                    assert nc != null;
//                    return (nc.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) || nc.hasTransport(NetworkCapabilities.TRANSPORT_WIFI));
//                }
//            }
//        }
//        return false;
//    }
}