package com.example.smart_things

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.firebase.analytics.FirebaseAnalytics
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //Analytic Event
        val analytics = FirebaseAnalytics.getInstance(this)
        val analyticsbundle = Bundle()
        analyticsbundle.putString("message","full firebase integration")
        analytics.logEvent("initScreen",analyticsbundle)

        Buttoms()

    }

    fun Buttoms(){
        config_buttom.setOnClickListener{
            val configActiviti = Intent(this,ConfigActivity::class.java)
            startActivity(configActiviti)
        }
    }

}