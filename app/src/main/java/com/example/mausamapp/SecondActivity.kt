package com.example.mausamapp

import android.content.Intent
import android.os.Bundle
import android.text.method.ScrollingMovementMethod
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity


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


        val restIntent:String?=intent.getStringExtra("rest")
        findViewById<TextView>(R.id.rest).setText(restIntent)
        findViewById<TextView>(R.id.rest).setTextColor(android.graphics.Color.rgb(205, 0, 0))
        //findViewById<TextView>(R.id.rest).setTextSize(25F)

    }
}