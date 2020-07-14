package com.example.projectbase.viewmodels

import androidx.annotation.NonNull
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.projectbase.repositories.LocalReposiroty
import com.example.projectbase.repositories.RemoteRepository
import javax.inject.Inject

class ViewModelFactory @Inject constructor(private val remoteRepository: RemoteRepository, private var localReposiroty: LocalReposiroty) : ViewModelProvider.Factory  {
    @NonNull
    override fun <T : ViewModel?> create(@NonNull modelClass: Class<T>): T {
        modelClass.isAssignableFrom(DealsViewModel::class.java)
            return DealsViewModel(remoteRepository, localReposiroty) as T

    }
}