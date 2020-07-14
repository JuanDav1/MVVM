package com.example.projectbase.repositories

import com.example.projectbase.db.dao.DataBaseDao
import com.example.projectbase.models.Deals

class LocalReposiroty(private var dataBaseDao: DataBaseDao) {

    suspend fun addDeals(deals: List<Deals>) {
        dataBaseDao.insertDeals(deals)
    }

    suspend fun deleteDeals(){
        dataBaseDao.deleteDeals()
    }

    suspend fun getDeals(): List<Deals>{
        return dataBaseDao.getDeals()
    }

}