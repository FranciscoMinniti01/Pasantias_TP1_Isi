package com.example.smart_things.Funtions.CloudFirebase

import com.example.smart_things.Class.ModelDevices

interface CloudFirebaseInterface {

    interface CloudCallback{
        fun AutheonSuccessful()
        fun AutheonFailure(message: String?)
    }

    fun UpAddDevise(email:String,FloorList: MutableMap<String,Boolean>,RoomList: MutableMap<String,Boolean>,Devise: ModelDevices,listener:CloudFirebaseInterface.CloudCallback)
    fun UpAddDevise1(email:String,RoomList: MutableMap<String,Boolean>,Devise: ModelDevices,listener:CloudFirebaseInterface.CloudCallback)
    fun UpAddDevise2(email:String,Devise: ModelDevices,listener:CloudFirebaseInterface.CloudCallback)
    fun BorrarDevice(email: String,devicesName:String,listener:CloudFirebaseInterface.CloudCallback)

}