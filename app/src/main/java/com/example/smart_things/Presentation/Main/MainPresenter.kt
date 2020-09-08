package com.example.smart_things.Presentation.Main

import android.content.Context
import com.example.smart_things.Class.ModelDevices
import com.example.smart_things.Funtions.MQTT.MQTTinterface
import com.example.smart_things.Funtions.MQTT.MqttFuntionsImpl
import com.example.smart_things.R
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken
import org.eclipse.paho.client.mqttv3.MqttCallback
import org.eclipse.paho.client.mqttv3.MqttMessage

class MainPresenter(context: Context):MainInterface.MainPresenter {

    var view:MainInterface.MainView? = null
    val mqttFuntions = MqttFuntionsImpl()

    val context:Context

    init {
        this.context = context
    }

    override fun conection(context: Context, ip_server: String, use_server: String, pass_server: String) {
        mqttFuntions.connection(context,ip_server,use_server,pass_server,object:MQTTinterface.MQTTcallback{
            override fun AutheonSuccessful() {
                if (isViewAttached()){
                    view?.MainToast(context.getString(R.string.succesful_conec))
                    eventMqtt()
                }
            }
            override fun AutheonFailure(message: String?) {
                if (isViewAttached()){
                    view?.MainToast(context.getString(R.string.failure_conec))
                }
            }
        })
    }

    override fun isConeccted(): Boolean {
        return mqttFuntions.StateConnection()
    }

    override fun AdjustList(deviceList: MutableList<ModelDevices>,RoomsList:MutableMap<String, Boolean>,FloorsList:MutableMap<String,Boolean>): MutableList<ModelDevices> {
        val list = mutableListOf<ModelDevices>()
        for (floor in FloorsList) {
            var FloorA = true
            //if (!floor.isNullOrEmpty()){
                for (room in RoomsList) {
                    var RoomA = true
                    //if (!room.isNullOrEmpty()){
                        if (deviceList.size != 0){
                            for (devices in deviceList) {
                                if (devices.Floor == floor.key && devices.Room == room.key) {
                                    if (FloorA) {
                                        FloorA = false
                                        list.add(ModelDevices(null, floor.key,null,null))
                                    }
                                    if (RoomA){
                                        RoomA = false
                                        list.add(ModelDevices(room.key, null,null, null))
                                    }
                                    list.add(ModelDevices(devices.Room, devices.Floor, devices.Name, devices.Type))
                                    //deviceList.remove(devices)
                                }
                            }
                        }
                    //}
                }
            //}
        }
        return list
    }

    override fun publis(Topic:String,typeDevise:String,Mensaje:String) {
        mqttFuntions.publish(Topic,typeDevise,Mensaje,null,object:MQTTinterface.MQTTcallback{
            override fun AutheonSuccessful() {
                view?.MainToast("Init joiner")
            }
            override fun AutheonFailure(message: String?) {
                view?.MainToast(context.getString(R.string.failure_publish) )
            }
        })
    }

    override fun onItemClick(devices: ModelDevices) {

    }

    override fun onSwitchClick(devices: ModelDevices,SwitchVal:Boolean){
        if (!devices.Name.isNullOrEmpty()){
            mqttFuntions.publish(devices.Name!!,devices.Type, null,SwitchVal,object:MQTTinterface.MQTTcallback{
                override fun AutheonSuccessful() {
                    view?.MainToast(context.getString(R.string.succesful_publish) + "${devices.Name}")
                }
                override fun AutheonFailure(message: String?) {
                    view?.MainToast(context.getString(R.string.failure_publish) + "${devices.Name}")
                }
            })
        }else if (!devices.Floor.isNullOrEmpty()){
            mqttFuntions.publish(devices.Floor!!,devices.Type, null,SwitchVal,object:MQTTinterface.MQTTcallback{
                override fun AutheonSuccessful() {
                    view?.MainToast(context.getString(R.string.succesful_publish) + "${devices.Floor}")
                }
                override fun AutheonFailure(message: String?) {
                    view?.MainToast(context.getString(R.string.failure_publish) + "${devices.Floor}")
                }
            })
        }else if (!devices.Room.isNullOrEmpty()){
            mqttFuntions.publish(devices.Room!!,devices.Type, null,SwitchVal,object:MQTTinterface.MQTTcallback{
                override fun AutheonSuccessful() {
                    view?.MainToast(context.getString(R.string.succesful_publish) + " ${devices.Room}")
                }
                override fun AutheonFailure(message: String?) {
                    view?.MainToast(context.getString(R.string.failure_publish) + "${devices.Room}")
                }
            })
        }
    }

    fun getrssi(message: MqttMessage?):String?{
        val GETRSSI : String = "PlatRssi "
        val index = message.toString().indexOf(GETRSSI)
        val rssi: String? = if (index == -1) null else message.toString().substring(index+GETRSSI.length)
        return rssi
    }

    override fun eventMqtt() {
        mqttFuntions.callback(object :MqttCallback{
            override fun messageArrived(topic: String?, message: MqttMessage?) {
                if (message.toString().contains("OFF",false)){
                    val devices:ModelDevices = ModelDevices(null,null,topic?.replace("report","")?.trim(),null,false,getrssi(message))
                    view?.dataChange(devices,false)
                }else if (message.toString().contains("ON",false)){
                    val devices:ModelDevices = ModelDevices(null,null, topic?.replace("report","")?.trim(),null,true,getrssi(message))
                    view?.dataChange(devices,true)
                }else{
                    view?.MainToast(context.getString(R.string.unknown_message))
                }
            }

            override fun connectionLost(cause: Throwable?) {
                view?.showMessage(context.getString(R.string.conec_lost), cause.toString(),context.getString(R.string.accept))
                view?.getDataConec()
            }

            override fun deliveryComplete(token: IMqttDeliveryToken?) {
                if (token?.message.toString().compareTo("ON") == 1 || token?.message.toString().compareTo("OFF") == 1){
                    view?.MainToast(token?.message.toString())
                }
                if (token?.exception != null) {
                    if (!token.exception.toString().isEmpty()) {
                        view?.MainToast(token.exception.toString())
                    }
                }
            }

        })
    }

    override fun subscribe(devices: MutableList<ModelDevices>) {
        for (device in devices){
            if (device.Name != null){
                mqttFuntions.subscribe(device.Name+"report"!!,object:MQTTinterface.MQTTcallback{
                    override fun AutheonSuccessful() {
                    }
                    override fun AutheonFailure(message: String?) {
                        view?.showMessage(context.getString(R.string.Error),message!!,context.getString(R.string.accept))
                    }
                })
            }
        }
    }

    override fun AttachView(view: MainInterface.MainView) {
        this.view = view
    }

    override fun DettachView() {
        view = null
    }

    override fun dettachJob() {
        TODO("Not yet implemented")
    }

    override fun isViewAttached(): Boolean {
        return view != null
    }
}