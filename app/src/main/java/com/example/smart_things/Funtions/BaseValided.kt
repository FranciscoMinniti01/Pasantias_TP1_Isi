package com.example.smart_things.Funtions

import com.example.smart_things.MainListView.ModelDevices
import com.example.smart_things.MainListView.ModelListFloors
import com.example.smart_things.MainListView.ModelListRooms

interface BaseValided {



    fun StatusBaseAction(
        valided: Boolean, Action: Int,
        listFloors: ModelListFloors? = null,
        listRooms: ModelListRooms? = null,
        listDevices: MutableList<ModelDevices>? = null)

}