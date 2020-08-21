package com.example.smart_things.Presentation.Main

import android.annotation.SuppressLint
import android.content.Context
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.smart_things.Class.ModelDevices
import com.example.smart_things.R
import com.example.smart_things.RecyclerView.BaceViewHolder
import kotlinx.android.synthetic.main.recycler_item_view.view.*
import java.lang.IllegalArgumentException

class MainRecyclerViewAdapter(private val context: Context, private val onRecycleClickListener: OnRecycleOnClick): RecyclerView.Adapter<BaceViewHolder<*>>() {

    interface OnRecycleOnClick{
        fun onItemClick(devices: ModelDevices)
        fun onSwitchClick(devices: ModelDevices,SwitchVal:Boolean)
    }

    private var deviceslist = mutableListOf<ModelDevices>()

    fun setListData(data:MutableList<ModelDevices>){
        deviceslist = data
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaceViewHolder<*> {
        val view = LayoutInflater.from(context).inflate(R.layout.recycler_item_view,parent,false)
        return RecyclerViewHolder(view)
    }

    override fun getItemCount(): Int = deviceslist.size

    override fun onBindViewHolder(holder: BaceViewHolder<*>, position: Int) {
        when(holder){
            is RecyclerViewHolder -> holder.bind(deviceslist[position],position)
            else -> throw IllegalArgumentException("Error send View Holder")
        }
    }

    inner class RecyclerViewHolder(itemView: View):
        BaceViewHolder<ModelDevices>(itemView){
        @SuppressLint("WrongConstant", "RtlHardcoded", "SetTextI18n")
        override fun bind(item: ModelDevices, position: Int) {
            if (!item.Name.isNullOrEmpty()){
                itemView.setOnClickListener{onRecycleClickListener.onItemClick(item)}
                val modelDevices = ModelDevices(item.Room,item.Floor,item.Name,item.Type,item.State)
                itemView.list_Switch.setOnClickListener{onRecycleClickListener.onSwitchClick(modelDevices/*item*/,itemView.list_Switch.isChecked)}
                itemView.list_Switch.isChecked = item.State
                itemView.List_Button.text = item.Name
                itemView.List_Button.textAlignment = Gravity.LEFT
            }else if (!item.Room.isNullOrEmpty()){
                itemView.list_Switch.setOnClickListener{onRecycleClickListener.onSwitchClick(item,itemView.list_Switch.isChecked)}
                itemView.list_Switch.isChecked = item.State
                itemView.List_Button.text = item.Room
                itemView.List_Button.textAlignment = Gravity.CENTER
            }else if (!item.Floor.isNullOrEmpty()){
                itemView.list_Switch.setOnClickListener{onRecycleClickListener.onSwitchClick(item,itemView.list_Switch.isChecked)}
                itemView.list_Switch.isChecked = item.State
                itemView.List_Button.text = item.Floor
                itemView.List_Button.textAlignment = Gravity.RIGHT
            }else{
                itemView.List_Button.text = "Error"
            }
        }

    }

}