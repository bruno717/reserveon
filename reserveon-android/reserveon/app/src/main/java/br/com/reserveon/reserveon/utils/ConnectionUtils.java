package br.com.reserveon.reserveon.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiManager;

/**
 * Created by Bruno on 09/04/2016.
 */
public class ConnectionUtils {

    /**
     * Verifica se o dispositivo esta
     * conectado a internet
     * @param context
     * @return boolean
     */
    public static boolean isConnected(Context context) {
        return isWifiConnected(context) && isConnectingToInternet(context);
    }

    public static boolean isWifiConnected(Context context) {
        ConnectivityManager conectivtyManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        return conectivtyManager.getActiveNetworkInfo() != null
                && conectivtyManager.getActiveNetworkInfo().isAvailable()
                && conectivtyManager.getActiveNetworkInfo().isConnected();
    }

    public static boolean isConnectingToInternet(Context context) {
        ConnectivityManager conectivtyManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (conectivtyManager != null) {
            NetworkInfo[] info = conectivtyManager.getAllNetworkInfo();
            if (info != null) {
                for (int i = 0; i < info.length; i++) {
                    if (info[i].getState() == NetworkInfo.State.CONNECTED) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public static void activateWifiConnection(Context context) {
        WifiManager WifiManager = (android.net.wifi.WifiManager) context.getSystemService(Context.WIFI_SERVICE);
        WifiManager.setWifiEnabled(true);
    }

}
