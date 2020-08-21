package com.example.smart_things.Presentation.Configurations

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.smart_things.Funtions.Autentication.AutheFuntionImpl
import com.example.smart_things.Presentation.Configurations.AddDevise.AddDeviseActivity
import com.example.smart_things.Presentation.Configurations.ConecConfig.ConfigConecActivity
import com.example.smart_things.Presentation.Auth.LogIn.LogInActivity
import com.example.smart_things.Presentation.Configurations.UserConfig.UserConfigActivity
import com.example.smart_things.Presentation.Configurations.ViewAllDevice.ViewAllDeviceActivity
import com.example.smart_things.Presentation.Main.MainActivity
import com.example.smart_things.R
import kotlinx.android.synthetic.main.activity_config.*

class ConfigActivity : AppCompatActivity() {

    private val authenticationfuntions = AutheFuntionImpl()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_config)

        Buttons()

    }

    fun Buttons(){
        back_buttom.setOnClickListener{
            /*val configActivity = Intent(this, MainActivity::class.java)
            startActivity(configActivity)*/
            finish()
        }
        buttom_config_conec.setOnClickListener{
            val configconecActivity = Intent(this, ConfigConecActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_REORDER_TO_FRONT or Intent.FLAG_ACTIVITY_PREVIOUS_IS_TOP
            startActivity(configconecActivity)
        }
        buttom_config_user.setOnClickListener{
            Toast.makeText(this, getString(R.string.not_use_action), Toast.LENGTH_SHORT).show()
        }
        buttom_add_devise.setOnClickListener{
            val add_device = Intent(this, AddDeviseActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_REORDER_TO_FRONT or Intent.FLAG_ACTIVITY_PREVIOUS_IS_TOP
            startActivity(add_device)
        }
        buttom_see_devise.setOnClickListener{
            val intent = Intent(this, ViewAllDeviceActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_REORDER_TO_FRONT or Intent.FLAG_ACTIVITY_PREVIOUS_IS_TOP
            startActivity(intent)
        }
        buttom_news.setOnClickListener{
            Toast.makeText(this, getString(R.string.not_use_action), Toast.LENGTH_SHORT).show()
        }
        buttom_help.setOnClickListener{
            Toast.makeText(this, getString(R.string.not_use_action), Toast.LENGTH_SHORT).show()
        }
        buttom_exit.setOnClickListener{
            val prefs = getSharedPreferences(getString(R.string.prefsFile),Context.MODE_PRIVATE).edit()
            prefs.clear()
            prefs.apply()
            authenticationfuntions.LogOut()
            val logoutactivity = Intent(this, LogInActivity::class.java)
            startActivity(logoutactivity)
        }
    }
}