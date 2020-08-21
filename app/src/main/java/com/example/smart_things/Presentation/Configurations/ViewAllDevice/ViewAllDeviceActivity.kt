package com.example.smart_things.Presentation.Configurations.ViewAllDevice

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.smart_things.Class.ModelDevices
import com.example.smart_things.Class.ModelUser
import com.example.smart_things.Presentation.Configurations.ConfigActivity
import com.example.smart_things.Presentation.Devise.ViewDeviseActivity
import com.example.smart_things.R
import com.example.smart_things.RecyclerView.MainViewModel
import com.example.smart_things.Presentation.Main.MainRecyclerViewAdapter
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_main.MainShimmerView
import kotlinx.android.synthetic.main.activity_view_all_device.*

class ViewAllDeviceActivity : AppCompatActivity(),AllDeviceInterface.ViewAllDevice,
    AddRecyclerViewAdapter.OnRecycleOnClick{

    var user: ModelUser = ModelUser(null,null,null, false,null)
    private lateinit var adapter: AddRecyclerViewAdapter
    private var viewModel: MainViewModel = MainViewModel()

    var DeviseListModel:MutableList<ModelDevices> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_all_device)
        setUpRecyclerView()
        Buttoms()
        getUser()
        getList()
    }

    override fun setUpRecyclerView() {
        adapter = AddRecyclerViewAdapter(this, this)
        All_RecyclerView.layoutManager = LinearLayoutManager(this)
        All_RecyclerView.adapter = adapter
    }

    override fun getUser(){
        val prefs = getSharedPreferences(getString(R.string.prefsFile), Context.MODE_PRIVATE)
        user.Email = prefs.getString("email","null")
        user.Password = prefs.getString("password",null)
        user.Room = prefs.getString("room",null)
        user.Name = prefs.getString("name",null)
    }

    override fun getList(){
        ShowProgress()
        viewModel.fetchDeviseData(user.Email!!).observe(this, Observer{DeviseList->
            if (DeviseList!= null){
                DeviseListModel = DeviseList
                AdapterviewList(DeviseListModel)
            }else{
                HideProgress()
            }
        })
    }

    override fun AdapterviewList(deviceList: MutableList<ModelDevices>) {
        HideProgress()
        ViewList(deviceList)
    }

    override fun ViewList(DeviseListModel:MutableList<ModelDevices>){
        All_RecyclerView.addItemDecoration(DividerItemDecoration(this, DividerItemDecoration.VERTICAL))
        adapter.setListData(DeviseListModel)
        adapter.notifyDataSetChanged()
    }

    override fun Buttoms() {
        All_back_buttom.setOnClickListener{
            finish()
        }
        All_button_serch.setOnClickListener{
            All_SerchMainDevise.visibility = View.VISIBLE
            All_button_serch1.visibility = View.VISIBLE
            All_button_serch.visibility = View.GONE
        }
        All_button_serch1.setOnClickListener{
            val position_devise = sherchDevise(All_SerchMainDevise.text.toString())
            if (position_devise != null){
                All_SerchMainDevise.visibility = View.INVISIBLE
                All_button_serch1.visibility = View.INVISIBLE
                All_button_serch.visibility = View.VISIBLE
                All_RecyclerView.scrollToPosition(position_devise)
            }else{
                All_SerchMainDevise.visibility = View.INVISIBLE
                All_button_serch1.visibility = View.INVISIBLE
                All_button_serch.visibility = View.VISIBLE
                Toast(getString(R.string.Not_exits_devise))
            }
        }
    }

    override fun sherchDevise(Name: String):Int? {
        var x:Int  = 0
        for (devise in DeviseListModel){
            when {
                devise.Name?.compareTo(Name) == 0 -> {
                    return x
                }
                devise.Room?.compareTo(Name) == 0 -> {
                    return x
                }
                devise.Floor?.compareTo(Name) == 0 -> {
                    return x
                }
                else -> {
                    x += 1
                }
            }
        }
        return null
    }

    override fun onItemClick(devices: ModelDevices) {
        val configActivity = Intent(this, ViewDeviseActivity::class.java).apply {
            val bundle: Bundle = Bundle()
            bundle.putSerializable("data", devices)
            putExtras(bundle)
        }
        intent.flags = Intent.FLAG_ACTIVITY_REORDER_TO_FRONT or Intent.FLAG_ACTIVITY_PREVIOUS_IS_TOP
        startActivity(configActivity)
    }

    override fun onSwitchClick(devices: ModelDevices, SwitchVal: Boolean) {

    }

    override fun ShowProgress() {
        All_MainShimmerView.visibility = View.VISIBLE
        All_MainShimmerView.startShimmer()
    }

    override fun HideProgress() {
        All_MainShimmerView.hideShimmer()
        All_MainShimmerView.stopShimmer()
        All_MainShimmerView.visibility = View.GONE
    }

    override fun Toast(message: String) {
        android.widget.Toast.makeText(this,message, android.widget.Toast.LENGTH_LONG).show()
    }

}