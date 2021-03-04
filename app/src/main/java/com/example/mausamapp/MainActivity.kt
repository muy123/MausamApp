package com.example.mausamapp

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.google.android.material.bottomsheet.BottomSheetDialog
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject
import java.text.DecimalFormat


class MainActivity: AppCompatActivity() {

    var etCity:EditText?=null
    var etCountry:EditText?=null
    var tvResult:TextView?=null
    var progress:ProgressBar?=null

    var bar:ProgressBar?=null

    private val url:String="http://api.openweathermap.org/data/2.5/weather"
    private var apiKey:String="fb4260a4c046b571f665dc912214de2a"
    var df = DecimalFormat("#.##")




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        etCity=findViewById(R.id.etCity)
        etCountry=findViewById(R.id.etCountry)
        tvResult=findViewById(R.id.tvResult)
        progress=findViewById(R.id.progress)



        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel("MY_ID", "MY_ID", NotificationManager.IMPORTANCE_DEFAULT)
            val notificationManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }



        findViewById<Button>(R.id.btnGet).setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {


                var net: Boolean = isOnline(this@MainActivity)
                if (!net) {
                    tvResult?.setText("OOPS! Something went wrong")
                    return
                }
                var tempUrl: String

                var city: String = etCity?.text.toString().trim()
                var country: String = etCountry?.text.toString().trim()

                if (city.equals("")) {
                    tvResult?.setText("Oops! Please enter city name")

                    tvResult?.setTextColor(android.graphics.Color.rgb(204, 255, 255))
                    tvResult?.setTextSize(25F)

                } else {
                    if (!country.equals("")) {
                        tempUrl = url + "?q=" + city + "," + country + "&appid=" + apiKey
                    } else {
                        tempUrl = url + "?q=" + city + "&appid=" + apiKey
                    }


                    var requestQueue: RequestQueue? = Volley.newRequestQueue(this@MainActivity)

                    //dialog = ProgressDialog.show(this, "", "Please wait...", true);

                    val strReq: StringRequest = object : StringRequest(

                        //https://nabeelj.medium.com/making-a-simple-get-and-post-request-using-volley-beginners-guide-ee608f10c0a9#:~:text=While%20a%20GET%20request%20is,you're%20sending%20data%20to.
                        Method.POST, tempUrl,
                        Response.Listener { response ->
                            var output: String = ""
                            try {
                                var jsonResponse: JSONObject = JSONObject(response)
                                var jsonArray: JSONArray = jsonResponse.getJSONArray("weather")
                                var jsonObjectWeather: JSONObject = jsonArray.getJSONObject(0)
                                var description: String = jsonObjectWeather.getString("description")

                                var jsonObjectMain: JSONObject = jsonResponse.getJSONObject("main")
                                var temp: Double = jsonObjectMain.getDouble("temp") - 273.15
                                var feelsLike: Double =
                                    jsonObjectMain.getDouble("feels_like") - 273.15

                                var min_temp: Double = jsonObjectMain.getDouble("temp_min") - 273.15
                                var max_temp: Double = jsonObjectMain.getDouble("temp_max") - 273.15

                                var humidity: Int = jsonObjectMain.getInt("humidity")
                                var pressure: Float = jsonObjectMain.getInt("pressure").toFloat()

                                var jsonObjectWind: JSONObject = jsonResponse.getJSONObject("wind")
                                var speed: String = jsonObjectWind.getString("speed")

                                var jsonObjectCloud = jsonResponse.getJSONObject("clouds")
                                var clouds: String = jsonObjectCloud.getString("all")

                                var jsonObjectSys: JSONObject = jsonResponse.getJSONObject("sys")
                                var countryName: String = jsonObjectSys.getString("country")

                                var cityName: String = jsonResponse.getString("name")


                                output += "Feels like: " + df.format(feelsLike) + "°C" + "\n Min Temprature: " + df.format(
                                    min_temp
                                ) + "°C" + "\n Max Temprature: " + df.format(max_temp) + "°C" + "\n Humidity: " + humidity + "%" +
                                        "\n Wind speed: " + speed + "m/s" + "\n Cloudiness: " + clouds + "%" + "\n Pressure: " +
                                        pressure + " hPa"
                                //tvResult.setText(output)


                                //sending data to second activity
                                val intent = Intent(
                                    applicationContext,
                                    SecondActivity::class.java
                                )
                                var city_country: String = ""
                                city_country += cityName + "(" + countryName + ")"
                                intent.putExtra("city_country", city_country)

                                var tempIntent: String = ""
                                tempIntent += df.format(temp) + "°C"
                                intent.putExtra("tempIntent", tempIntent)

                                intent.putExtra("descIntent", description)


                                var restIntent: String = ""
                                restIntent += "Other Classifiers: " + "\n" + output
                                intent.putExtra("rest", restIntent)


                                startActivity(intent)


//                                Log.d("anshul", "response: " + response.toString())
//                                dialog?.dismiss()
                            } catch (e: JSONException) {
                                e.printStackTrace()
                            }

                        },
                        Response.ErrorListener { volleyError -> // error occurred
                            Log.d(
                                "anshul",
                                "problem occurred, volley error: " + volleyError.message
                            )
                            Toast.makeText(
                                this@MainActivity,
                                "Something went wrong!",
                                Toast.LENGTH_SHORT
                            ).show()
                        }) {
                    }
                    requestQueue?.add(strReq)


                }

            }
        })


        findViewById<Button>(R.id.button2).setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                val modelSheetView = layoutInflater.inflate(R.layout.bottom_sheet, null)
                val dialog = BottomSheetDialog(this@MainActivity)
                dialog.setContentView(modelSheetView)
                dialog.show()
            }
        })

        

    }




    override fun onResume() {
        super.onResume()
        val sh:SharedPreferences = getSharedPreferences("MySharedPref", MODE_PRIVATE)
        var s1: String? =sh.getString("numb1", "")
        var s2:String?=sh.getString("numb2", "")

        etCity?.setText(s1)
        etCountry?.setText(s2)

    }

    override fun onPause() {
        super.onPause()
        val sharedPreferences = getSharedPreferences("MySharedPref", MODE_PRIVATE)
        val myEdit = sharedPreferences.edit()
        myEdit.putString("numb1", etCity?.text.toString())
        myEdit.putString("numb2", etCountry?.text.toString())
        myEdit.apply()

    }






}
fun isOnline(context: Context?): Boolean {
    if (context == null) return false
    val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
        val capabilities = connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)
        if (capabilities != null) {
            when {
                capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> {
                    return true
                }
                capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> {
                    return true
                }
                capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> {
                    return true
                }
            }
        }
    } else {
        val activeNetworkInfo = connectivityManager.activeNetworkInfo
        if (activeNetworkInfo != null && activeNetworkInfo.isConnected) {
            return true
        }
    }
    return false
}