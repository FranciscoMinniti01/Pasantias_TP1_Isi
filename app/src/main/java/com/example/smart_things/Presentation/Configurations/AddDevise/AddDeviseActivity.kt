package com.example.smart_things.Presentation.Configurations.AddDevise

import android.app.AlertDialog
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.Observer
import com.example.smart_things.Class.ModelDevices
import com.example.smart_things.Class.ModelUser
import com.example.smart_things.Funtions.CloudFirebase.CloudFirebaseImpl
import com.example.smart_things.Funtions.CloudFirebase.CloudFirebaseInterface
import com.example.smart_things.R
import com.example.smart_things.RecyclerView.MainViewModel
import kotlinx.android.synthetic.main.activity_add_devise.*


class AddDeviseActivity : AppCompatActivity(){

    var cloudFiresImpl: CloudFirebaseImpl = CloudFirebaseImpl()

    private var viewModel: MainViewModel = MainViewModel()
    var user: ModelUser = ModelUser(null,null,null, false,null)

    var deviceList = mutableListOf<ModelDevices>()
    var roomList = mutableMapOf<String,Boolean>()
    var floorList = mutableMapOf<String,Boolean>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_devise)
        getUser()
        getList()
        Button()
    }

    fun getUser(){
        val prefs = getSharedPreferences(getString(R.string.prefsFile), Context.MODE_PRIVATE)
        user.Email = prefs.getString("email",null)
    }

    fun getList(){
        viewModel.fetchDeviseData(user.Email!!).observe(this, Observer{DeviseList->
            viewModel.fetchRoomsData(user.Email!!).observe(this, Observer {RoomLiat->
                viewModel.fetchFloorsData(user.Email!!).observe(this, Observer {FloorList->
                    if (DeviseList != null){
                        deviceList = DeviseList
                        roomList = RoomLiat
                        floorList = FloorList
                    }
                })
            })
        })
    }

    fun Button(){
        add_device_buttom.setOnClickListener{
            if (checkCampEmpty())
                ShowProgress()
                addDevice(edit_add_flow.text.toString().trim(),
                          edit_add_room.text.toString().trim(),
                          edit_add_type.text.toString().trim(),
                          edit_add_name.text.toString().trim())
        }
        add_back_buttom.setOnClickListener{
            finish()
        }

    }

    fun checkCampEmpty():Boolean{
        if (!edit_add_room.text.isNullOrEmpty()){
            if (!edit_add_flow.text.isNullOrEmpty()){
                if (!edit_add_type.text.isNullOrEmpty()){
                    if (!edit_add_name.text.isNullOrEmpty()){
                        return true
                    }else edit_add_name.error = getString(R.string.it_is_empty)
                }else edit_add_type.error = getString(R.string.it_is_empty)
            }else edit_add_flow.error = getString(R.string.it_is_empty)
        }else edit_add_room.error = getString(R.string.it_is_empty)
        return false
    }

    fun addDevice(floor:String,room:String,type:String,name: String){
        var floorA:Boolean = false
        var roomA:Boolean = false
        var nameA:Boolean = false
        for(device in deviceList){
            for(roomx in roomList){
                for (florx in floorList){
                    if(florx.key.compareTo(floor) == 0 ){
                        floorA = true
                    }
                    if(roomx.key.compareTo(room) == 0 ){
                        roomA = true
                    }
                    if (device.Name!!.compareTo(name) == 0){
                        nameA = true
                        roomA = true
                        floorA = true
                        Toast(getString(R.string.name_devise_exits))
                        edit_add_name.error = getString(R.string.name_devise_exits)
                        break
                    }
                }
            }
        }
        if (!nameA){
            if(!floorA){
                floorList[floor] = true
            }
            if(!roomA){
                roomList[room] = true
            }
            val devise = ModelDevices(room,floor,name,type)
            UpAddDevice(devise,roomList,floorList)
        }
    }

    fun UpAddDevice(device:ModelDevices,
                    roomList:MutableMap<String,Boolean>,
                    floorList:MutableMap<String,Boolean>){
        cloudFiresImpl.UpAddDevise(user.Email!!,floorList,roomList,device,object:CloudFirebaseInterface.CloudCallback{
            override fun AutheonSuccessful() {
                HideProgress()
                edit_add_name.text = null
                Toast(getString(R.string.succesful_add_device))
            }
            override fun AutheonFailure(message: String?) {
                HideProgress()
                Toast(getString(R.string.failure_add_device))
            }
        })
    }

    fun ShowProgress() {
        addProgres.visibility = View.VISIBLE
        progreSpace.visibility = View.GONE
    }

    fun HideProgress() {
        addProgres.visibility = View.GONE
        progreSpace.visibility = View.VISIBLE
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
        Toast.makeText(this,message, android.widget.Toast.LENGTH_LONG).show()
    }
}
