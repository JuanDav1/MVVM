package com.example.projectbase.utils

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.net.NetworkRequest
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat.getSystemService
import com.example.projectbase.App



class ConnectivityOnline {
    private var connection = true



fun enable(): Boolean{
    var cm =
        App.context?.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    val builder: NetworkRequest.Builder = NetworkRequest.Builder().addTransportType(NetworkCapabilities.TRANSPORT_CELLULAR)
        .addTransportType(NetworkCapabilities.TRANSPORT_WIFI)
    cm.registerNetworkCallback(
    builder.build(),
    object : ConnectivityManager.NetworkCallback() {

        override fun onAvailable(network: Network) {
            Log.i("MainActivity", "onAvailable!")
            connection = true
        }

        override fun onLost(network: Network) {

            Log.i("MainActivity", "onLost!")

            // doSomething
            connection = false
        }


    }
    )
    return connection
}
}