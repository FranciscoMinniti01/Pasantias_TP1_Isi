package com.example.smart_things.Presentation.Configurations.ViewAllDevice

import com.example.smart_things.Class.ModelDevices

interface AllDeviceInterface {

    interface ViewAllDevice{
        fun getList()
        fun getUser()
        fun setUpRecyclerView()
        fun AdapterviewList(deviceList: MutableList<ModelDevices>)
        fun ViewList(DeviseListModel:MutableList<ModelDevices>)
        fun Buttoms()
        fun sherchDevise(Name: String):Int?
        fun Toast(message: String)
        fun ShowProgress()
        fun HideProgress()
    }

}