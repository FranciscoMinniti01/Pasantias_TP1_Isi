package com.example.smart_things.Presentation.Main

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.smart_things.Class.*
import com.example.smart_things.Presentation.Configurations.ConfigActivity
import com.example.smart_things.Presentation.Devise.ViewDeviseActivity
import com.example.smart_things.R
import com.example.smart_things.RecyclerView.MainViewModel
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), MainInterface.MainView,
    MainRecyclerViewAdapter.OnRecycleOnClick{

    lateinit var presenter: MainPresenter
    private lateinit var adapter: MainRecyclerViewAdapter
    private var viewModel: MainViewModel = MainViewModel()

    var user:ModelUser = ModelUser(null,null,null, false,null)
    var conecData: ConnecData = ConnecData(null,null,null)
    var come_from:String = ""
    var DeviseListModel:MutableList<ModelDevices> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        getInstance()

        presenter = MainPresenter(this)
        presenter.AttachView(this)

        /*/*-----------ANALYTIC EVENT------------*/
        val analytics = FirebaseAnalytics.getInstance(this)
        val analyticsbundle = Bundle()
        analyticsbundle.putString("message", "full firebase integration")
        analytics.logEvent("initScreen", analyticsbundle)
        /*-------------------------------------*/*/

        setUpRecyclerView()

        Buttoms()

    }

    override fun onResume() {
        super.onResume()
        getDataConec()
        if(DeviseListModel.size == 0){
            getList()
        }else{
            ViewList(DeviseListModel)
        }
    }

    override fun getInstance(){
        val bundle = intent.extras
        if (bundle != null){
            come_from = bundle.getString("come_from").toString()
                if (come_from == "LogIn") user = bundle.getSerializable("data") as ModelUser
                else if (come_from == "ConfigConec") getDataConec()
                else if (come_from == "config"){}
        }
    }

    override fun setUpRecyclerView() {
        adapter = MainRecyclerViewAdapter(
            this,
            this
        )
        MainRecyclerView.layoutManager = LinearLayoutManager(this)
        MainRecyclerView.adapter = adapter
    }

    override fun getList(){
        ShowProgress()
        viewModel.fetchDeviseData(user.Email!!).observe(this, Observer{DeviseList->
            viewModel.fetchRoomsData(user.Email!!).observe(this, Observer {RoomLiat->
                viewModel.fetchFloorsData(user.Email!!).observe(this, Observer {FloorList->
                    if (DeviseList != null) {
                        AdapterviewList(DeviseList, RoomLiat, FloorList)
                    }else{
                        HideProgress()
                    }
                })
            })
        })
    }

    override fun AdapterviewList(deviceList: MutableList<ModelDevices>,RoomsList:MutableMap<String, Boolean>,FloorsList:MutableMap<String,Boolean>) {
        DeviseListModel = presenter.AdjustList(deviceList,RoomsList,FloorsList)
        if (presenter.isConeccted()) {
            presenter.subscribe(DeviseListModel)
        }
        ViewList(DeviseListModel)
    }

    override fun ViewList(DeviseListModel:MutableList<ModelDevices>){
        HideProgress()
        MainRecyclerView.addItemDecoration(DividerItemDecoration(this,DividerItemDecoration.VERTICAL))
        adapter.setListData(DeviseListModel)
        adapter.notifyDataSetChanged()
    }

    override fun getDataConec() {
        val prefs = getSharedPreferences(getString(R.string.prefsFile),Context.MODE_PRIVATE)
        conecData.ip = prefs.getString("IP_server",null)
        conecData.password = prefs.getString("pass_server",null)
        conecData.user = prefs.getString("use_server",null)
        if(!conecData.ip.isNullOrEmpty() && !conecData.user.isNullOrEmpty() && !conecData.password.isNullOrEmpty()){
            presenter.conection(this, conecData.ip!!,conecData.user!!,conecData.password!!)
        }
    }

    override fun Buttoms(){
        config_buttom.setOnClickListener{
            val configActivity = Intent(this, ConfigActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_REORDER_TO_FRONT or Intent.FLAG_ACTIVITY_PREVIOUS_IS_TOP
            startActivity(configActivity)
        }
        button_serch.setOnClickListener{
            SerchMainDevise.visibility = View.VISIBLE
            button_serch1.visibility = View.VISIBLE
            button_serch.visibility = View.GONE
        }
        button_serch1.setOnClickListener{
            val position_devise = SherchDevise(SerchMainDevise.text.toString())
            if (position_devise != null){
                SerchMainDevise.visibility = View.INVISIBLE
                button_serch1.visibility = View.INVISIBLE
                button_serch.visibility = View.VISIBLE
                MainRecyclerView.scrollToPosition(position_devise)
            }else{
                SerchMainDevise.visibility = View.INVISIBLE
                button_serch1.visibility = View.INVISIBLE
                button_serch.visibility = View.VISIBLE
                MainToast(getString(R.string.Not_exits_devise))
            }
        }
        joiner_buttom.setOnClickListener{
            if (presenter.isConeccted()){
                presenter.publis("joinDevices","join","all")
            }
        }
    }

    override fun SherchDevise(Name: String):Int? {
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

    override fun dataChange(Devise: ModelDevices, state: Boolean) {
        var position_devise = SherchDevise(Devise.Name!!)
        if(position_devise != null) {
            if (DeviseListModel[position_devise].State != state) {
                DeviseListModel.set(position_devise,ModelDevices(Devise.Room, Devise.Floor, Devise.Name, Devise.Type, state,Devise.RSSI))
                adapter.notifyItemChanged(position_devise)
            }
            if (!DeviseListModel[position_devise].RSSI.isNullOrEmpty()){
                DeviseListModel.set(position_devise,ModelDevices(Devise.Room, Devise.Floor, Devise.Name, Devise.Type, state,Devise.RSSI))
                adapter.notifyItemChanged(position_devise)
            }
        }else{
            MainToast(getString(R.string.unknown_message))
        }
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

    override fun onSwitchClick(devices: ModelDevices,SwitchVal:Boolean){
        if (presenter.isConeccted()){
            presenter.onSwitchClick(devices,SwitchVal)
        }else{
            MainToast(getString(R.string.it_not_conec))
        }
    }

    override fun ShowProgress() {
         MainShimmerView.visibility = View.VISIBLE
         MainShimmerView.startShimmer()
     }

     override fun HideProgress() {
         MainShimmerView.hideShimmer()
         MainShimmerView.stopShimmer()
         MainShimmerView.visibility = View.GONE
     }

     override fun showMessage(title: String, message: String, buttom1: String) {
         val builder = AlertDialog.Builder(this)
         builder.setTitle(title)
         builder.setMessage(message)
         builder.setPositiveButton(buttom1,null)
         val dialog: AlertDialog = builder.create()
         dialog.show()
     }

     override fun MainToast(message: String) {
         android.widget.Toast.makeText(this,message, android.widget.Toast.LENGTH_LONG).show()
     }

}