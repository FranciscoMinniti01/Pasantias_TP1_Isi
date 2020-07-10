package com.example.smart_things

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_config.*

class ConfigConecActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_config_conec)

        Buttons()

    }

    override fun onPause() {
        super.onPause()
        Toast.makeText(this,"onPause",Toast.LENGTH_LONG).show()
    }

    fun Buttons() {
        back_buttom.setOnClickListener {
            val configActivity = Intent(this, ConfigActivity::class.java)
            startActivity(configActivity)
        }
    }
}