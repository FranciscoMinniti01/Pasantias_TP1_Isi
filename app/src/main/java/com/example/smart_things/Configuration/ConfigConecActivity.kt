package com.example.smart_things.Configuration

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.smart_things.Funtions.Constantes.CONECCONFIG
import com.example.smart_things.MainActivity
import com.example.smart_things.R
import kotlinx.android.synthetic.main.activity_config.*
import kotlinx.android.synthetic.main.activity_config.back_buttom
import kotlinx.android.synthetic.main.activity_config_conec.*

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
        buttom_conec.setOnClickListener{
            val mainActivity = Intent(this, MainActivity::class.java).apply {
                putExtra("come_from",CONECCONFIG)
                putExtra("IP_server",edit_ip.text.toString())
                putExtra("pass_server",edit_use_server.text.toString())
                putExtra("use_server",edit_use_server.text.toString())
            }
            startActivity(mainActivity)
        }
    }
}