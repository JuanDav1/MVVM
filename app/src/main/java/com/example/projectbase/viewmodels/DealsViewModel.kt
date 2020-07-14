package com.example.projectbase.viewmodels

import android.app.Application
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.projectbase.App
import com.example.projectbase.models.Deals
import com.example.projectbase.models.NetworkConnection
import com.example.projectbase.models.OperationResult
import com.example.projectbase.repositories.LocalReposiroty
import com.example.projectbase.repositories.RemoteRepository
import com.example.projectbase.utils.ConnectivityOnline
import com.example.projectbase.utils.ConnectivityOnlineLiveData
import kotlinx.coroutines.*



class DealsViewModel(private val remoteRepository: RemoteRepository, private val localReposiroty: LocalReposiroty): ViewModel() {


        val connectivity: LiveData<NetworkConnection>

        init {
            connectivity = ConnectivityOnlineLiveData( App.context as Application)
        }

    private  var connectionInstance : ConnectivityOnline = ConnectivityOnline()

    private val _responseLiveDataDeals = MutableLiveData<List<Deals>>()
    val responseLiveDataDeals: LiveData<List<Deals>> = _responseLiveDataDeals
    private var _responseLiveDataGetDeals = MutableLiveData<List<Deals>>()
    val responseLiveDataGetDeals: LiveData<List<Deals>> = _responseLiveDataGetDeals

    private val _isViewLoading=MutableLiveData<Boolean>()
    val isViewLoading:LiveData<Boolean> = _isViewLoading

    private val _onMessageError=MutableLiveData<Any>()
    val onMessageError:LiveData<Any> = _onMessageError

    private val _isEmptyList=MutableLiveData<Boolean>()
    val isEmptyList:LiveData<Boolean> = _isEmptyList

    private val _connection = MutableLiveData<Boolean>()
    val connection : LiveData<Boolean> = _connection

    fun loadDeals() {
        _isViewLoading.postValue(true)
        viewModelScope.launch {
            var result: OperationResult<Deals> = withContext(Dispatchers.IO) {
                remoteRepository.getDeals()
            }
            _isViewLoading.postValue(false)
            when (result) {
                is OperationResult.Success ->{
                    if(result.data.isNullOrEmpty()){
                        _isEmptyList.postValue(true)
                    }else{
                        _responseLiveDataDeals.value = result.data
                    }
                }
                is OperationResult.Error ->{
                    _onMessageError.postValue(result.exception)
                    Log.w("api",result.exception)

                }

            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        viewModelScope.cancel()
        Log.i("viewModel","onCleared")
    }

    fun addDeals(deals: List<Deals>){
        try {
            viewModelScope.launch() {

                withContext(Dispatchers.IO) {localReposiroty.deleteDeals()}
                withContext(Dispatchers.IO) { localReposiroty.addDeals(deals)}
            }
        }catch (e:Exception){
            Log.d("db", "Error in the insertion")
        }

    }


    fun getDeals(){
        try {
            viewModelScope.launch { withContext(Dispatchers.IO) {
                  _responseLiveDataGetDeals.postValue(localReposiroty.getDeals())
            }
            }
        }catch (e:Exception){
            Log.i("db", "Error")
        }
    }

    fun connection(){
        connectionInstance.let {
            _connection.value  = it.enable()
        }
    }

    /*@Suppress("DEPRECATION")
    fun stateConexionInternet(){
        val cm =
            context!!.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetwork = cm.activeNetworkInfo
        _conexion.value = activeNetwork != null
    }*/

}