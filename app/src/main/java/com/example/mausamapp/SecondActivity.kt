package com.example.mausamapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView

class SecondActivity : AppCompatActivity() {

    lateinit var msg:TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_second)

        //msg=findViewById<TextView>(R.id.recived_message)
        var intent:Intent=intent
        //var output: String? =intent.getStringExtra("message_key")
        //msg.setText(output)
        //msg.setTextColor(android.graphics.Color.rgb(205, 0, 0))
        //msg.setTextSize(35F)
        val city_country:String?=intent.getStringExtra("city_country")
        findViewById<TextView>(R.id.textView).setText(city_country)

        val tempIntent:String?=intent.getStringExtra("tempIntent")
        findViewById<TextView>(R.id.temprature).setText(tempIntent)

        val descIntent:String?=intent.getStringExtra("descIntent")
        findViewById<TextView>(R.id.description).setText(descIntent)

        val minIntent:String?=intent.getStringExtra("minIntent")
        findViewById<TextView>(R.id.textView4).setText(minIntent)
        val maxIntent:String?=intent.getStringExtra("maxIntent")
        findViewById<TextView>(R.id.textView5).setText(maxIntent)

        val humIntent:String?=intent.getStringExtra("humIntent")
        findViewById<TextView>(R.id.textView6).setText(humIntent)

        val cloudIntent:String?=intent.getStringExtra("cloudIntent")
        findViewById<TextView>(R.id.textView2).setText(cloudIntent)

        val pressureIntent:String?=intent.getStringExtra("pressureIntent")
        findViewById<TextView>(R.id.textView3).setText(pressureIntent)




    }
}