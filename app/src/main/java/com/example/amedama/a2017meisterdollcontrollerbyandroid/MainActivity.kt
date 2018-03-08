package com.example.amedama.a2017meisterdollcontrollerbyandroid

import android.net.wifi.WifiConfiguration
import android.net.wifi.WifiManager
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val goRbtn : Button = findViewById(R.id.goR) as Button
        val stopRbtn : Button = findViewById(R.id.stopR) as Button

        val goLbtn : Button = findViewById(R.id.goL) as Button
        val stopLbtn : Button = findViewById(R.id.stopL) as Button

        goRbtn.setOnClickListener({
            val cond : TextView = findViewById(R.id.nowConditionR)
            var v : String = ""
            val handler : Handler = Handler()
            Toast.makeText(this,"GO",Toast.LENGTH_SHORT).show()
            val th = Thread(){
                setR()
                v = getValR()
                handler.post(Runnable {
                    cond.text = v
                })
            }.start()
        })

        goLbtn.setOnClickListener({
            val cond : TextView = findViewById(R.id.nowConditionL)
            var v : String = ""
            val handler : Handler = Handler()
            Toast.makeText(this,"GO",Toast.LENGTH_SHORT).show()
            val th = Thread(){
                setL()
                v = getValL()
                handler.post(Runnable {
                    cond.text = v
                })
            }.start()
        })

        stopRbtn.setOnClickListener({
            val cond : TextView = findViewById(R.id.nowConditionR)
            var v : String = ""
            val handler : Handler = Handler()
            Toast.makeText(this,"STOP",Toast.LENGTH_SHORT).show()
            val th = Thread(){
                resetR()
                v = getValR()
                handler.post(Runnable {
                    cond.text = v
                })
            }.start()
        })

        stopLbtn.setOnClickListener({
            val cond : TextView = findViewById(R.id.nowConditionL)
            var v : String = ""
            val handler : Handler = Handler()
            Toast.makeText(this,"STOP",Toast.LENGTH_SHORT).show()
            val th = Thread(){
                resetL()
                v = getValL()
                handler.post(Runnable {
                    cond.text = v
                })
            }.start()
        })
    }


    // for R
    fun getValR() : String{
        try {
            val url : URL = URL("http://192.168.20.2/doll/R/val")
            val urlConnect : HttpURLConnection
            urlConnect = url.openConnection() as HttpURLConnection
            urlConnect.connect()
            val br = BufferedReader(InputStreamReader(urlConnect.inputStream))

            var line: String? = null
            val sb = StringBuilder()

            for (line in br.readLines()) {
                line?.let { sb.append(line) }
            }

            br.close()
            val no = Integer.parseInt(sb.toString())
            var command : String

            when(no) {
                0 -> {
                    command = "STOP"
                }
                1 -> {
                    command = "GO"
                }
                else -> {
                    command = "ERROR"
                }
            }

            return command
        }catch (e : Exception) {
            Log.d("http error",e.toString())
            return "error"
        }
    }

    fun setR(){
        try {
            val url : URL = URL("http://192.168.20.2/doll/R/H")
            val urlConnect : HttpURLConnection
            urlConnect = url.openConnection() as HttpURLConnection
            urlConnect.connect()
            val br = BufferedReader(InputStreamReader(urlConnect.inputStream))

            var line: String? = null
            val sb = StringBuilder()

            for (line in br.readLines()) {
                line?.let { sb.append(line) }
            }

            br.close()
        }catch (e : Exception) {
            Log.d("http error",e.toString())
        }
    }

    fun resetR(){
        try {
            val url : URL = URL("http://192.168.20.2/doll/R/L")
            val urlConnect : HttpURLConnection
            urlConnect = url.openConnection() as HttpURLConnection
            urlConnect.connect()
            val br = BufferedReader(InputStreamReader(urlConnect.inputStream))

            var line: String? = null
            val sb = StringBuilder()

            for (line in br.readLines()) {
                line?.let { sb.append(line) }
            }

            br.close()
        }catch (e : Exception) {
            Log.d("http error",e.toString())
        }
    }


    // for L
    fun getValL() : String{
        try {
            val url : URL = URL("http://192.168.20.2/doll/L/val")
            val urlConnect : HttpURLConnection
            urlConnect = url.openConnection() as HttpURLConnection
            urlConnect.connect()
            val br = BufferedReader(InputStreamReader(urlConnect.inputStream))

            var line: String? = null
            val sb = StringBuilder()

            for (line in br.readLines()) {
                line?.let { sb.append(line) }
            }

            br.close()
            val no = Integer.parseInt(sb.toString())
            var command : String

            when(no) {
                0 -> {
                    command = "STOP"
                }
                1 -> {
                    command = "GO"
                }
                else -> {
                    command = "ERROR"
                }
            }

            return command
        }catch (e : Exception) {
            Log.d("http error",e.toString())
            return "error"
        }
    }

    fun setL(){
        try {
            val url : URL = URL("http://192.168.20.2/doll/L/H")
            val urlConnect : HttpURLConnection
            urlConnect = url.openConnection() as HttpURLConnection
            urlConnect.connect()
            val br = BufferedReader(InputStreamReader(urlConnect.inputStream))

            var line: String? = null
            val sb = StringBuilder()

            for (line in br.readLines()) {
                line?.let { sb.append(line) }
            }

            br.close()
        }catch (e : Exception) {
            Log.d("http error",e.toString())
        }
    }

    fun resetL(){
        try {
            val url : URL = URL("http://192.168.20.2/doll/L/L")
            val urlConnect : HttpURLConnection
            urlConnect = url.openConnection() as HttpURLConnection
            urlConnect.connect()
            val br = BufferedReader(InputStreamReader(urlConnect.inputStream))

            var line: String? = null
            val sb = StringBuilder()

            for (line in br.readLines()) {
                line?.let { sb.append(line) }
            }

            br.close()
        }catch (e : Exception) {
            Log.d("http error",e.toString())
        }
    }


    override fun onStart() {
        super.onStart()

        val wifiManager : WifiManager = getApplicationContext().getSystemService(WIFI_SERVICE) as WifiManager
        if(wifiManager.isWifiEnabled() == false) wifiManager.setWifiEnabled(true)
        wifiManager.disconnect()
        setWifi()
        connectWifi()

    }

    fun connectWifi() {
        val wifiManager : WifiManager = getApplicationContext().getSystemService(WIFI_SERVICE) as WifiManager
        val targetConfig : WifiConfiguration = WifiConfiguration()
        var list : List<WifiConfiguration> = wifiManager.configuredNetworks

        targetConfig.allowedKeyManagement.set(WifiConfiguration.KeyMgmt.WPA_PSK)
        targetConfig.SSID = '"'+"POP'N STAR MASTER AP"+'"'
        targetConfig.preSharedKey = '"'+"1234567890"+'"'

        list.forEach{
            if(it.SSID != null && it.SSID.equals('"'+"POP'N STAR MASTER AP"+'"')){
                wifiManager.disconnect()
                wifiManager.enableNetwork(it.networkId, true)
            }
        }
    }

    fun setWifi() {
        val wifiManager : WifiManager = getApplicationContext().getSystemService(WIFI_SERVICE) as WifiManager
        val targetConfig : WifiConfiguration = WifiConfiguration()
        targetConfig.allowedKeyManagement.set(WifiConfiguration.KeyMgmt.WPA_PSK)
        targetConfig.SSID = '"'+"POP'N STAR MASTER AP"+'"'
        targetConfig.preSharedKey = '"'+"1234567890"+'"'
        val networkID = wifiManager.addNetwork(targetConfig)
        if(networkID == -1) Toast.makeText(this,"wifi set error",Toast.LENGTH_LONG).show()
    }
}
