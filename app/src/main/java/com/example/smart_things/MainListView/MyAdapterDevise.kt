package com.example.smart_things.MainListView

import android.annotation.SuppressLint
import android.content.Context
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.Switch
import com.example.smart_things.R


class MyAdapterDevise(var mCtx: Context, var resources: Int, var items:List<ModelDevices>): ArrayAdapter<ModelDevices>(mCtx,resources,items){
    @SuppressLint("WrongConstant")
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val layoutInflater: LayoutInflater = LayoutInflater.from(mCtx)
        val view: View = layoutInflater.inflate(resources,null)

        val button: Button = view.findViewById(R.id.List_Button)
        val switch: Switch = view.findViewById(R.id.list_Switch)

        val mItemN: String? = items[position].Nombre
        //val mItemT: String? = items[position].Tipo
        val mItemR: String? = items[position].Habitacion
        val mItemF: String? = items[position].Piso

        when {
            mItemN != null -> {
                //switch.id = mItemN.toInt()
                button.text = mItemN
                button.textAlignment = Gravity.LEFT
                //button.setTextAlignment(View.FOCUS_RIGHT);
            }
            mItemF != null -> {
                //switch.id = mItemF.toInt()
                button.text = mItemF
                button.textAlignment = Gravity.RIGHT
                //button.setTextAlignment(View.FOCUS_LEFT);
            }
            mItemR != null -> {
                //switch.id = mItemR.toInt()
                button.text = mItemR
                button.textAlignment = Gravity.CENTER
                //button.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
            }
        }
        return view
    }
}