package com.example.smart_things.Autentication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View.X
import android.widget.Toast
import com.example.smart_things.Funtions.BaseValided
import com.example.smart_things.Funtions.CloudFirestoreFuntions
import com.example.smart_things.Funtions.Constantes
import com.example.smart_things.Funtions.Constantes.CREAT_FAMILI_BASE
import com.example.smart_things.Funtions.ShowAlert
import com.example.smart_things.MainListView.ModelDevices
import com.example.smart_things.MainListView.ModelListFloors
import com.example.smart_things.MainListView.ModelListRooms
import com.example.smart_things.R
import kotlinx.android.synthetic.main.activity_config_register.*
import kotlinx.android.synthetic.main.activity_register.*

class ConfigRegisterActivity : AppCompatActivity(), BaseValided {

    val showalert = ShowAlert()
    val cloudfirestorefuntions = CloudFirestoreFuntions()

    /*--------------VARIABLES--------------*/
    var come_from: Int = 0
    var seve_user: Boolean = false
    var RE_email: String = ""
    var RE_password: String = ""
    var GetData: Boolean = false

    var listRooms: ModelListRooms? = null
    var listFloors: ModelListFloors? = null
    /*-------------------------------------*/

    override fun onCreate(savedInstanceState: Bundle?) {

        cloudfirestorefuntions.BaseSetInterfaz(this)

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_config_register)

        if (GetData == false){
            getIntentData()
        }

        buttom()

    }

    /*---------------------------------OPTIRNE LOS DATOS RECIVIDOS DE ACTIVITY LOG IN Y REGISTER---------------------------------------------*/
    fun getIntentData(){
        val bundle = intent.extras
        come_from = bundle?.getInt("come_from")!!
        seve_user = bundle?.getBoolean("seve_user")!!
        RE_email = bundle?.getString("email").toString()
        RE_password = bundle?.getString("password").toString()
        GetData = true
    }
    /*---------------------------------------------------------------------------------------------------------------------------------------*/

    fun buttom(){
        add_flors_buttom.setOnClickListener{
            var valided: Boolean = false
            if (Re_edit_Floors.text.isNotEmpty() && Re_edit_Rooms.text.isNotEmpty()){
                var x:Int = 0
                for (x in 0..4){
                    valided = true
                    if(listFloors?.getFloors(x) == null){
                        break
                    }else if (listFloors?.getFloors(x) == Re_edit_Floors.text.toString()){
                        valided = false;
                        break
                    }
                }
                if (valided == true) listFloors?.setFloors(x,Re_edit_Floors.text.toString())
                x = 0
                for (x in 0..4){
                    valided = true
                    if(listRooms?.getRooms(x) == null){
                        break
                    }else if (listRooms?.getRooms(x) == Re_edit_Rooms.text.toString()){
                        valided = false;
                        break
                    }
                }
                if (valided == true)listRooms?.setRooms(x,Re_edit_Rooms.text.toString())
            }
        }
        finish_config_buton.setOnClickListener{
            if (listRooms != null && listFloors != null){
                cloudfirestorefuntions.CreatFamiliBase(RE_email,CREAT_FAMILI_BASE, listRooms!!, listFloors!!
                )
            }
        }
    }

    /*---------------------------------------------LOG IN PARA IR A MAIN ACTIVITY------------------------------------------------------------*/
    fun LogIn(){
        val logInActivity = Intent(this, ConfigRegisterActivity::class.java).apply {
            putExtra("come_from", Constantes.LOG_IN)
            putExtra("email", RE_email)
            putExtra("password", RE_password)
            if (Re_seve_pass.isChecked) {
                putExtra("seve_user", true)
            } else {
                putExtra("seve_user", false)
            }
        }
        startActivity(logInActivity)
    }
    /*---------------------------------------------------------------------------------------------------------------------------------------*/

    override fun StatusBaseAction(valided: Boolean, Action: Int, listFloors: ModelListFloors?, listRooms: ModelListRooms?, listDevices: MutableList<ModelDevices>?){
        if (Action == CREAT_FAMILI_BASE){
            if (valided){
                LogIn()
            }else{
                showalert.Showalert(this,"Error","Error","Aceptar")
            }
        }
    }

}