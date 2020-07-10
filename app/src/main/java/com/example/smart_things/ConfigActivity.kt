package com.example.smart_things

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_config.*

class ConfigActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_config)

        Buttons()

    }

    override fun onPause() {
        super.onPause()
        Toast.makeText(this,"onPause",Toast.LENGTH_LONG).show()
    }

    fun Buttons(){
        back_buttom.setOnClickListener{
            val mainActiviti = Intent(this,MainActivity::class.java)
            startActivity(mainActiviti)
        }
        buttom_config_conec.setOnClickListener{
            val configconecActivity = Intent(this,ConfigConecActivity::class.java)
            startActivity(configconecActivity)
        }
        buttom_config_user.setOnClickListener{

        }
        buttom_add_devise.setOnClickListener{

        }
        buttom_see_devise.setOnClickListener{

        }
        buttom_news.setOnClickListener{

        }
        buttom_help.setOnClickListener{

        }
        buttom_exit.setOnClickListener{

        }
    }
}