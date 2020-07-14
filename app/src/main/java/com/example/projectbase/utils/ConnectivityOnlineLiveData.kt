package com.example.projectbase.utils

import android.Manifest
import android.app.Application
import android.content.Context
import android.net.*
import android.os.Build
import androidx.annotation.RequiresPermission
import androidx.lifecycle.LiveData
import com.example.projectbase.models.NetworkConnection
import com.example.projectbase.models.TypeNetwork
import kotlinx.coroutines.*

@Suppress("DEPRECATION")
class ConnectivityOnlineLiveData internal constructor(private val connectivityManager: ConnectivityManager) : LiveData<NetworkConnection>() {


    @RequiresPermission(allOf = [Manifest.permission.ACCESS_NETWORK_STATE])
    constructor(application: Application) : this(
        application.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    )

    private val networkCallback = object : ConnectivityManager.NetworkCallback() {
        override fun onAvailable(network: Network?) {

            GlobalScope.launch{
                var state = withContext(Dispatchers.IO){isInternetOn()}
                if(state){
                    val isWifi = connectivityManager.getNetworkCapabilities(network).hasTransport(
                        NetworkCapabilities.TRANSPORT_WIFI)
                    val isData = connectivityManager.getNetworkCapabilities(network).hasTransport(
                        NetworkCapabilities.TRANSPORT_CELLULAR)
                    if(isWifi){
                        postValue(NetworkConnection(true,TypeNetwork.WIFI))
                    } else if (isData){
                        postValue(NetworkConnection(true,TypeNetwork.DATA))
                    }
                }else{
                    postValue(NetworkConnection(false,TypeNetwork.WIFI))

                }
            }




        }

        override fun onLost(network: Network?) {
            postValue(NetworkConnection(false,TypeNetwork.NON))
        }

    }

    override fun onActive() {
        super.onActive()


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            connectivityManager.registerDefaultNetworkCallback(networkCallback)

        } else {
            val builder = NetworkRequest.Builder()
                .addTransportType(NetworkCapabilities.TRANSPORT_CELLULAR)
                .addTransportType(NetworkCapabilities.TRANSPORT_WIFI)
                .addTransportType(NetworkCapabilities.TRANSPORT_BLUETOOTH)
                .addTransportType(NetworkCapabilities.TRANSPORT_ETHERNET)
            connectivityManager.registerNetworkCallback(builder.build(), networkCallback)

        }
    }

    override fun onInactive() {
        super.onInactive()
        connectivityManager.unregisterNetworkCallback(networkCallback)
    }

    private fun isNetworkIsAvailable(): Boolean {
        val activeNetwork: NetworkInfo? = connectivityManager.activeNetworkInfo
        return activeNetwork?.isConnected == true
    }
    suspend fun isInternetOn(): Boolean {

        if (isNetworkIsAvailable()) {
            try {
                val p = Runtime.getRuntime().exec("ping -c 1 www.google.com")
                val value = p.waitFor()
                return value == 0

            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
        return false
    }



}