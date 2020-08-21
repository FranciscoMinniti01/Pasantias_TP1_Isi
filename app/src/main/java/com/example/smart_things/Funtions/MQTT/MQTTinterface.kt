package com.example.smart_things.Funtions.MQTT

import android.content.Context
import org.eclipse.paho.client.mqttv3.MqttCallback

interface MQTTinterface {

    interface MQTTcallback{
        fun AutheonSuccessful()
        fun AutheonFailure(message: String?)
    }

    fun connection(context: Context, ser:String, use:String, pass:String ,listener:MQTTinterface.MQTTcallback)
    fun StateConnection():Boolean
    fun Disconnec(listener: MQTTcallback)
    fun publish(topic:String,Type:String?,mensaje:String?,OnOff:Boolean?,listener:MQTTinterface.MQTTcallback)
    fun subscribe(topic: String,listener: MQTTcallback)
    fun callback(listener: MqttCallback)
}