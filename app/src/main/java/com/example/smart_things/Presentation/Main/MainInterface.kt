package com.example.smart_things.Presentation.Main

import android.content.Context
import com.example.smart_things.Class.ModelDevices
import com.example.smart_things.Presentation.Auth.LogIn.LogInInterfaz

interface MainInterface {

    interface MainView{
        fun getDataConec ()
        fun getInstance()
        fun setUpRecyclerView()
        fun getList()
        fun AdapterviewList(deviceList: MutableList<ModelDevices>,RoomsList:MutableMap<String, Boolean>,FloorsList:MutableMap<String,Boolean>)
        fun ViewList(DeviseListModel:MutableList<ModelDevices>)
        fun SherchDevise(Name:String):Int?
        fun dataChange (Devise:ModelDevices,state:Boolean)

        fun Buttoms()
        fun ShowProgress()
        fun HideProgress()
        fun showMessage( title :String, message :String, buttom1 :String)
        fun MainToast(message: String)
    }

    interface MainPresenter{
        fun conection(context:Context,ip_server:String,use_server:String,pass_server:String)
        fun isConeccted():Boolean
        fun AdjustList(deviceList: MutableList<ModelDevices>,RoomsList:MutableMap<String, Boolean>,FloorsList:MutableMap<String,Boolean>):MutableList<ModelDevices>
        fun publis(Topic:String,typeDevise:String,Mensaje:String)
        fun onItemClick(devices: ModelDevices)
        fun onSwitchClick(devices: ModelDevices,SwitchVal:Boolean)
        fun eventMqtt()
        fun subscribe(devices: MutableList<ModelDevices>)

        fun AttachView(view: MainInterface.MainView)
        fun DettachView()
        fun dettachJob()
        fun isViewAttached():Boolean
    }

}