package com.example.smart_things.MainListView

import com.example.smart_things.Funtions.BaseValided
import com.example.smart_things.Funtions.CloudFirestoreFuntions
import com.example.smart_things.Funtions.Constantes.*
import androidx.appcompat.app.AppCompatActivity as AppCompatActivity1

class ListDevicesFuntions: AppCompatActivity1(), BaseValided {

    val cloudfirestorefuntions = CloudFirestoreFuntions()

    /*---------LISTA DE DISPOSITIVOS---------*/
    var ListDevicess: MutableList<ModelDevices>? = null
    var list = mutableListOf<ModelDevices>()
    /*-------------------------------------*/

    var ListFloors : ModelListFloors? = null
    var ListRoom : ModelListRooms? = null

    var action: Int = 0

    private var listvalided: ListValided? = null

    fun ListSetInterfaz(interfaz : ListValided){
        this. listvalided= interfaz
    }

    fun CreateList(email:String,Action: Int){
        cloudfirestorefuntions.BaseSetInterfaz(this)
        action = Action
        cloudfirestorefuntions.getListHouse(email,action)
    }

    fun CreatListDevise(){
        var Floor: String = ""
        var Room: String = ""
        var FloorA :Boolean = false
        var RoomA  :Boolean = false
        for (i:Int in 0..4) {
            FloorA = true
            Floor = ListFloors?.getFloors(i).toString()
            if (Floor != "null"){
                for (j:Int in 0..9) {
                    RoomA = true
                    Room = ListRoom?.getRooms(j).toString()
                    if (Room != "null"){
                        if (ListDevicess?.size != 0){
                            for (devices in this!!.ListDevicess!!) {
                                if (devices.Piso == Floor && devices.Habitacion == Room) {
                                    if (FloorA == true) {
                                        FloorA = false
                                        list.add(ModelDevices(null, Floor, null, null))
                                    }
                                    if (RoomA == true){
                                        RoomA = false
                                        list.add(ModelDevices(Room,null,null,null))
                                    }
                                    list.add(ModelDevices(devices.Habitacion,devices.Piso,devices.Nombre,devices.Tipo))
                                    ListDevicess?.remove(devices)
                                }
                            }
                        }
                    }
                }
            }
        }
        listvalided?.StatusListDeviseFuntions(true,action, list)
    }

    override fun StatusBaseAction(valided: Boolean, Action: Int, listFloors: ModelListFloors?, listRooms: ModelListRooms?, listDevices: MutableList<ModelDevices>?){
        if (Action == action){
            if (valided) {
                ListFloors = listFloors
                ListRoom = listRooms
                ListDevicess = listDevices
                CreatListDevise()
            } else{

            }
        }
    }
}