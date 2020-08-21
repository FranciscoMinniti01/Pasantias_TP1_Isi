package com.example.smart_things.RecyclerView

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.smart_things.Class.ModelDevices
import com.example.smart_things.Funtions.CloudFirebase.CloudFirebaseImpl

class MainViewModel:ViewModel(){

    private val cloudFirebaseImpl: CloudFirebaseImpl = CloudFirebaseImpl()

    fun fetchDeviseData(email: String):LiveData<MutableList<ModelDevices>>{
        val mutableData = MutableLiveData<MutableList<ModelDevices>>()
        cloudFirebaseImpl.GetDeviseData(email).observeForever{
            mutableData.value = it
        }
        return mutableData
    }

    fun fetchFloorsData(email: String):LiveData<MutableMap<String,Boolean>>{
        val mutableData = MutableLiveData<MutableMap<String,Boolean>>()
        cloudFirebaseImpl.getListHouseFloors(email).observeForever{
            mutableData.value = it
        }
        return mutableData
    }

    fun fetchRoomsData(email: String):LiveData<MutableMap<String, Boolean>>{
        val mutableData = MutableLiveData<MutableMap<String, Boolean>>()
        cloudFirebaseImpl.getListHouseRooms(email).observeForever{
            mutableData.value = it
        }
        return mutableData
    }

}