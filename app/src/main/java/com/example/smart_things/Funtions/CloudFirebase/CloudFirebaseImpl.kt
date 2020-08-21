package com.example.smart_things.Funtions.CloudFirebase

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.smart_things.Class.ModelDevices
import com.google.firebase.firestore.FirebaseFirestore

class CloudFirebaseImpl:CloudFirebaseInterface{

    fun DeletDot(email: String):String{
        return email.replace(".","")
    }

    fun GetDeviseData(email: String):LiveData<MutableList<ModelDevices>>{
        val mutableData = MutableLiveData<MutableList<ModelDevices>>()
        FirebaseFirestore
        .getInstance()
        .collection("users")
        .document(DeletDot(email))
        .collection("Devices")
        .get().addOnSuccessListener { resultado ->
            val ListDevices = mutableListOf<ModelDevices>()
            if (resultado.size() != 0){
                for (documento in resultado) {
                    val Devices: ModelDevices = documento.toObject(ModelDevices::class.java)//documento.data?.toMutableMap() as MutableList<ModelDevices>
                    ListDevices.add(Devices)
                }
                mutableData.value = ListDevices
            }else{
                mutableData.value = null
            }
        }
        .addOnFailureListener{

        }
        return mutableData
    }

    fun getListHouseRooms(email: String): MutableLiveData<MutableMap<String,Boolean>> {
        val mutableData = MutableLiveData<MutableMap<String,Boolean>>()
        FirebaseFirestore
        .getInstance()
        .collection("users")
        .document(DeletDot(email))
        .collection("House")
        .document("Rooms")
        .get().addOnSuccessListener {resultad->
            if (resultad.exists()) {
                //mutableData = it.data?.get("Rooms") as MutableLiveData<MutableList<String>>

                /*val map: Map<String, Any> = it.data as Map<String, Any>
                for ((key, value) in map) {
                    if (key == "Rooms") {
                        mutableData = value as MutableLiveData<MutableList<String>>
                    }
                }*/
                mutableData.value = resultad.data as MutableMap<String, Boolean>
            }else{
                mutableData.value = null
            }
        }.addOnFailureListener{
            print(it.message)
        }
        return mutableData
    }



    fun getListHouseFloors(email: String): MutableLiveData<MutableMap<String,Boolean>> {
        val mutableData = MutableLiveData<MutableMap<String,Boolean>>()
        FirebaseFirestore
            .getInstance()
            .collection("users")
            .document(DeletDot(email))
            .collection("House")
            .document("Floors")
            .get().addOnSuccessListener {resultad->
                if (resultad.exists()) {
                    //mutableData = it.data?.get("Rooms") as MutableLiveData<MutableList<String>>

                    /*val map: Map<String, Any> = it.data as Map<String, Any>
                    for ((key, value) in map) {
                        if (key == "Rooms") {
                            mutableData = value as MutableLiveData<MutableList<String>>
                        }
                    }*/
                    mutableData.value = resultad.data as MutableMap<String, Boolean>
                }else{
                    mutableData.value = null
                }
            }.addOnFailureListener{
                print(it.message)
            }
        return mutableData
    }

    override fun UpAddDevise(
        email: String,
        FloorList: MutableMap<String, Boolean>,
        RoomList: MutableMap<String, Boolean>,
        Devise: ModelDevices,
        listener:CloudFirebaseInterface.CloudCallback
    ) {
        FirebaseFirestore
        .getInstance()
        .collection("users")
        .document(DeletDot(email))
        .collection("House")
        .document("Floors")
        .set(FloorList).addOnCompleteListener {
            if (it.isSuccessful){
                UpAddDevise1(email,RoomList,Devise,listener)
            }else{
                listener.AutheonFailure(it.exception?.message)
            }
        }
    }

    override fun UpAddDevise1(
        email: String,
        RoomList: MutableMap<String, Boolean>,
        Devise: ModelDevices,
        listener: CloudFirebaseInterface.CloudCallback
    ) {
        FirebaseFirestore
        .getInstance()
        .collection("users")
        .document(DeletDot(email))
        .collection("House")
        .document("Rooms")
        .set(RoomList).addOnCompleteListener {
            if (it.isSuccessful){
                UpAddDevise2(email,Devise,listener)
            }else{
                listener.AutheonFailure(it.exception?.message)
            }
        }
    }

    override fun UpAddDevise2(
        email: String,
        Devise: ModelDevices,
        listener: CloudFirebaseInterface.CloudCallback
    ) {
        FirebaseFirestore
            .getInstance()
            .collection("users")
            .document(DeletDot(email))
            .collection("Devices")
            .document(Devise.Name.toString())
            .set(Devise).addOnCompleteListener {
                if (it.isSuccessful){
                    listener.AutheonSuccessful()
                }else{
                    listener.AutheonFailure(it.exception?.message)
                }
            }
    }

    override fun BorrarDevice(email: String, devicesName:String,listener:CloudFirebaseInterface.CloudCallback) {
        FirebaseFirestore
            .getInstance()
            .collection("users")
            .document(DeletDot(email))
            .collection("Devices")
            .document(devicesName)
            .delete()
            .addOnCompleteListener {
                if (it.isSuccessful){
                    listener.AutheonSuccessful()
                }else{
                    listener.AutheonFailure(it.exception?.message)
                }
            }
    }


}
