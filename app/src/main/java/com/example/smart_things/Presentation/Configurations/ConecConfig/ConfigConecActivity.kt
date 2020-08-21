package com.example.smart_things.Presentation.Configurations.ConecConfig

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.example.smart_things.Class.ConnecData
import com.example.smart_things.Funtions.MQTT.MQTTinterface
import com.example.smart_things.Funtions.MQTT.MqttFuntionsImpl
import com.example.smart_things.Presentation.Configurations.ConfigActivity
import com.example.smart_things.Presentation.Main.MainActivity
import com.example.smart_things.R
import kotlinx.android.synthetic.main.activity_config.back_buttom
import kotlinx.android.synthetic.main.activity_config_conec.*
import kotlinx.android.synthetic.main.activity_login.*

class ConfigConecActivity : AppCompatActivity(){

    var conecData: ConnecData = ConnecData(null,null,null)
    val mqttFuntions = MqttFuntionsImpl()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_config_conec)

        getConecConfi()

        Buttons()

    }

    fun getConecConfi(){
        val prefs = getSharedPreferences(getString(R.string.prefsFile), Context.MODE_PRIVATE)
            conecData.ip = prefs.getString("IP_server",null)
            conecData.password = prefs.getString("pass_server",null)
            conecData.user = prefs.getString("use_server",null)
        if( !conecData.ip.isNullOrEmpty() && !conecData.user.isNullOrEmpty() && !conecData.password.isNullOrEmpty()){
            edit_ip.setText(conecData.ip)
            edit_use_server.setText(conecData.user)
            edit_password_server.setText(conecData.password)
        }
    }

    fun Buttons() {
        back_buttom.setOnClickListener {
            finish()
        }
        buttom_conec.setOnClickListener{
            conecData.ip = edit_ip.text.toString().trim()
            conecData.password = edit_password_server.text.toString().trim()
            conecData.user = edit_use_server.text.toString().trim()
            if (!conecData.ip.isNullOrEmpty() && !conecData.user.isNullOrEmpty() && !conecData.password.isNullOrEmpty()){
                if (!mqttFuntions.StateConnection()){
                    conect()
                }
            }
        }
    }

    fun conect(){
        showProgress()
        mqttFuntions.connection(this,conecData.ip!!,conecData.user!!,conecData.password!!,object:MQTTinterface.MQTTcallback{
            override fun AutheonSuccessful() {
                showProgress()
                Toast(getString(R.string.succesful_conec))
                saveDataConec()
            }
            override fun AutheonFailure(message: String?) {
                hideProgress()
                showMessage(getString(R.string.Error),message!!,getString(R.string.accept))
            }
        })
    }

    fun showProgress(){
        spaceProgres.visibility = View.GONE
        conecprogressBar.visibility = View.VISIBLE
    }

    fun hideProgress() {
        spaceProgres.visibility = View.VISIBLE
        conecprogressBar.visibility = View.GONE
    }

    fun saveDataConec(){
        val prefs = getSharedPreferences(getString(R.string.prefsFile),Context.MODE_PRIVATE).edit()
            prefs.putString("IP_server",conecData.ip)
            prefs.putString("pass_server",conecData.password)
            prefs.putString("use_server",conecData.user)
        prefs.apply()
        hideProgress()
        Disconnec()
    }

    fun Disconnec(){
        mqttFuntions.Disconnec(object:MQTTinterface.MQTTcallback{
            override fun AutheonSuccessful() {
                navigatetomain()
            }
            override fun AutheonFailure(message: String?) {
                Toast(getString(R.string.wait_to_disconnec))
                Disconnec()
            }
        })
    }

    fun navigatetomain(){
        finish()
    }

    fun showMessage(title: String, message: String, buttom1: String) {
        val builder = AlertDialog.Builder(this)
        builder.setTitle(title)
        builder.setMessage(message)
        builder.setPositiveButton(buttom1,null)
        val dialog: AlertDialog = builder.create()
        dialog.show()
    }

    fun Toast(message: String) {
        android.widget.Toast.makeText(this,message, android.widget.Toast.LENGTH_LONG).show()
    }

}