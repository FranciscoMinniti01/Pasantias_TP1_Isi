package com.example.smart_things

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
import android.widget.ListView
import com.example.smart_things.Configuration.ConfigActivity
import com.example.smart_things.Funtions.BaseValided
import com.example.smart_things.Funtions.CloudFirestoreFuntions
import com.example.smart_things.Funtions.Constantes.*
import com.example.smart_things.Funtions.MqttFuntions
import com.example.smart_things.Funtions.MqttValided
import com.example.smart_things.MainListView.*
import com.google.firebase.analytics.FirebaseAnalytics
import kotlinx.android.synthetic.main.activity_config_conec.*
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), MqttValided, BaseValided, ListValided {

    /*------------IMPORTACIONES------------*/
    val listDevicesfuntions = ListDevicesFuntions()
    val mqttfuntions = MqttFuntions()
    val cloudfirestorefuntions = CloudFirestoreFuntions()
    /*-------------------------------------*/

    /*----DATOS DE LA CUENTA NECESARIOS----*/
    public var email: String = ""
    var password: String = ""
    var seve_user: Boolean = false
    var GetData: Boolean = false

    var come_from: Int = 0

    var ip_server: String = ""
    var pass_server: String = ""
    var use_server: String = ""
    /*-------------------------------------*/

    var ListMainDevice: MutableList<ModelDevices> = mutableListOf()

    var list_main: ListView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        /*------------IMPORTACIONES------------*/
        mqttfuntions.MQsetInterfaz(this)
        cloudfirestorefuntions.BaseSetInterfaz(this)
        listDevicesfuntions.ListSetInterfaz(this)
        /*-------------------------------------*/

        /*-----------ANALYTIC EVENT------------*/
        val analytics = FirebaseAnalytics.getInstance(this)
        val analyticsbundle = Bundle()
        analyticsbundle.putString("message", "full firebase integration")
        analytics.logEvent("initScreen", analyticsbundle)
        /*-------------------------------------*/

        list_main = findViewById<ListView>(R.id.List_Main)

        if (!GetData) {
            getIntentData()
        }

        if (come_from == CONECCONFIG){
            mqttfuntions.connection(this,ip_server,use_server,pass_server,CONECMQTT)
            come_from = 0
        }

        if (seve_user) {
            SaveAcount()
        }

        Buttoms()

        listDevicesfuntions.CreateList(email,GETLISTHOUSE)

    }

    fun is_conected (){
        val prefs = getSharedPreferences(getString(R.string.prefsFile), Context.MODE_PRIVATE)
        ip_server = prefs.getString("IP_server",null).toString()
        pass_server = prefs.getString("pass_server",null).toString()
        use_server = prefs.getString("use_server",null).toString()
        if(ip_server.isNotEmpty() && pass_server.isNotEmpty() && use_server.isNotEmpty()){
            //Lo_layout.visibility = View.INVISIBLE
            mqttfuntions.connection(this,ip_server,use_server,pass_server,CONECMQTT)
        }
    }

    /*---------------------------------OPTIRNE LOS DATOS RECIVIDOS DE ACTIVITY LOG IN Y REGISTER---------------------------------------------*/
    fun getIntentData(){
        val bundle = intent.extras
        come_from = bundle?.getInt("come_from")!!
        if (come_from == LOG_IN || come_from == REGISTER){
            seve_user = bundle?.getBoolean("seve_user")!!
            email = bundle?.getString("email").toString()
            password = bundle?.getString("password").toString()
        }else if (come_from == CONECCONFIG ){
            ip_server = bundle?.getString("IP_server").toString()
            pass_server = bundle?.getString("pass_server").toString()
            use_server = bundle?.getString("use_server").toString()
        }
        GetData = true
    }
    /*---------------------------------------------------------------------------------------------------------------------------------------*/

    /*---------------------------------GUARDA LOS DATOS DE LA CUENTA EN UN FICHERO DEL CELULAR-----------------------------------------------*/
    fun SaveAcount(){
        val prefs = getSharedPreferences(getString(R.string.prefsFile),Context.MODE_PRIVATE).edit()
        if (come_from == LOG_IN){
            prefs.putString("email",email)
            prefs.putString("password",password)
        }else if (come_from == CONECCONFIG){
            prefs.putString("IP_server",ip_server)
            prefs.putString("pass_server",pass_server)
            prefs.putString("use_server",use_server)
        }
        prefs.apply()
        seve_user = false
    }
    /*---------------------------------------------------------------------------------------------------------------------------------------*/

    /*-----------------------------------------------EVENTO DE LOS BOTONES-------------------------------------------------------------------*/
    fun Buttoms(){
        config_buttom.setOnClickListener{
            val configActiviti = Intent(this, ConfigActivity::class.java)
            startActivity(configActiviti)
        }
        button_serch.setOnClickListener{

        }
        list_main?.setOnItemClickListener{parent:AdapterView<*>, view:View,position:Int,id:Long ->
            if (ListMainDevice[position].Nombre != null){
                mqttfuntions.publish(ListMainDevice[position].Nombre,"ON",MAINPUBLISH)
            }else if (ListMainDevice[position].Habitacion != null){
                mqttfuntions.publish(ListMainDevice[position].Habitacion,"ON",MAINPUBLISH)
            }else if (ListMainDevice[position].Piso != null){
                mqttfuntions.publish(ListMainDevice[position].Piso,"ON",MAINPUBLISH)
            }
        }
    }
    /*---------------------------------------------------------------------------------------------------------------------------------------*/



    /*---------------------------------CALBAKS DE LAS FUNCIONES DE MQTT Y DE LA BASE DE DATOS -----------------------------------------------*/
    override fun StatusMqttFuntions(valided: Boolean, Action: Int) {
        if (Action == CONECMQTT){
            if (valided){
                Toast.makeText(this,"conectado",Toast.LENGTH_LONG).show()
            }else{
                Toast.makeText(this,"Fallo la coneccion",Toast.LENGTH_LONG).show()
            }
        }
        if (Action == ISNOTCONECT){
            mqttfuntions.connection(this,ip_server,use_server,pass_server,CONECMQTT)
        }
    }

    override fun StatusBaseAction(valided: Boolean, Action: Int, listFloors: ModelListFloors?, listRooms: ModelListRooms?, listDevices: MutableList<ModelDevices>?) {

    }

    override fun StatusListDeviseFuntions(valided: Boolean, Action: Int, listdevices: MutableList<ModelDevices>){
        if (Action == GETLISTHOUSE){
            if (valided){
                ListMainDevice = listdevices
                list_main?.adapter = MyAdapterDevise(this,R.layout.list_view, ListMainDevice)
            }
        }
    }

    /*---------------------------------------------------------------------------------------------------------------------------------------*/
}