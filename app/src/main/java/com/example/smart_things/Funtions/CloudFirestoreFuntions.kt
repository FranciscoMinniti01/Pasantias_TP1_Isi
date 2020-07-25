package com.example.smart_things.Funtions

import android.nfc.Tag
import android.util.Log
import com.example.smart_things.Funtions.Constantes.FLOORS
import com.example.smart_things.Funtions.Constantes.ROOMS
import com.example.smart_things.MainListView.ModelDevices
import com.example.smart_things.MainListView.ModelListFloors
import com.example.smart_things.MainListView.ModelListRooms
import com.google.firebase.firestore.FirebaseFirestore

@Suppress("UNCHECKED_CAST")
class CloudFirestoreFuntions {

    val db = FirebaseFirestore.getInstance()

    var ListFloors : ModelListFloors? = null
    var ListRoom : ModelListRooms? = null

    private var basevalided: BaseValided? = null
    fun BaseSetInterfaz(interfaz : BaseValided){
        this.basevalided = interfaz
    }

    fun DeletDot(email: String):String{
        return email.replace(".","")
    }

    fun CreatFamiliBase(email: String,Action :Int,rooms:ModelListRooms,floors: ModelListFloors){
        //val userHashMap = HashMap<String, Any>()
        if (rooms != null) {
            db.collection("users")
            .document(DeletDot(email))
            .collection("House")
            .document("Rooms")
            .set(rooms!!).addOnCompleteListener {
                if (it.isSuccessful) {
                    CreatFamiliBase1(email, Action,floors)
                } else {
                    basevalided?.StatusBaseAction(false, Action)
                }
            }
        }
    }
    fun CreatFamiliBase1(email: String,Action :Int,floors: ModelListFloors){
        if (floors != null) {
            db.collection("users")
            .document(DeletDot(email))
            .collection("House")
            .document("Floors")
            .set(floors).addOnCompleteListener {
                if (it.isSuccessful) {
                    basevalided?.StatusBaseAction(true, Action)
                } else {
                    basevalided?.StatusBaseAction(false, Action)
                }
            }
        }
    }

    /*fun ValidedNameUser(user: String,email: String,Action :Int){
        db.collection("users")
            .document(DeletDot(email))
            .collection(user)
            .document(user)
            .get().addOnSuccessListener{colleccion->
            if (colleccion.exists()){
                basevalided?.StatusBaseAction(true, Action)
            }else{
                basevalided?.StatusBaseAction(false, Action)
            }
        }
    }*/

    fun getAllDevices(email: String,Action: Int){
        var ListDevices = mutableListOf<ModelDevices>()
        db.collection("users")
        .document(DeletDot(email))
        .collection("Devices")
        .get().addOnSuccessListener { resultado ->
            for (documento in resultado) {
                val Devices: ModelDevices = documento.toObject(ModelDevices::class.java)//documento.data?.toMutableMap() as MutableList<ModelDevices>
                ListDevices.add(Devices)
            }
            basevalided?.StatusBaseAction(true, Action, ListFloors, ListRoom, ListDevices)
        }
        .addOnFailureListener{
            basevalided?.StatusBaseAction(false, Action)
        }
    }

    fun getListHouse(email: String,Action: Int){
        db.collection("users")
        .document(DeletDot(email))
        .collection("House")
        .document("Rooms")
        .get().addOnSuccessListener { resultado ->
            if (resultado.exists()) {
                ListRoom = resultado.toObject(ModelListRooms::class.java)!!
                getListHouse1(email,Action)
            }
        }.addOnFailureListener{
            basevalided?.StatusBaseAction(true, Action, null, null, null)
        }
    }

   fun getListHouse1 (email: String,Action: Int) {
       db.collection("users")
       .document(DeletDot(email))
       .collection("House")
       .document("Floors")
       .get().addOnSuccessListener {resultado->
           if (resultado.exists()) {
               ListFloors = resultado.toObject(ModelListFloors::class.java)!!
               getAllDevices(email,Action)
           }
       }
       .addOnFailureListener{
           basevalided?.StatusBaseAction(true, Action, null, null, null)
       }
   }

}
