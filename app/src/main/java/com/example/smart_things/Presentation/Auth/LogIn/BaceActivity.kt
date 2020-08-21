package com.example.smart_things.Presentation.Auth.LogIn

import android.os.Bundle
import android.view.View
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_login.*

abstract class BaceActivity: AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //requestWindowFeature(Window.FEATURE_NO_TITLE)                                                             //para quitar la barra
        //window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN)    //de notificaciones
        setContentView(getLayout())
    }

    override fun onStart() {
        super.onStart()
        Lo_layout.visibility = View.VISIBLE
    }

    @LayoutRes
    abstract fun getLayout():Int

}