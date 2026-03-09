package com.example.proyectorobotica

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import android.widget.Button
import androidx.compose.ui.res.colorResource

import java.net.HttpURLConnection
import java.net.URL
import kotlin.concurrent.thread
import androidx.core.content.ContextCompat


class MainActivity : AppCompatActivity() {

    var isOn: Boolean = false;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        val botonLlamada = findViewById<Button>(R.id.botonEncender)

        botonLlamada.setOnClickListener {
            if(isOn==true) {
                botonLlamada.setTextColor(ContextCompat.getColorStateList(this, R.color.red))
                isOn = false;
                //enviarComando("https://pruebaaaaa.free.beeceptor.com/off")
                enviarComando("http://192.168.1.4/off")

            }

            else{
                botonLlamada.setTextColor(ContextCompat.getColorStateList(this, R.color.green))
                isOn=true;
                //enviarComando("https://pruebaaaaa.free.beeceptor.com/on")
                enviarComando("http://192.168.1.4/on")


            }


        }
    }

}
fun enviarComando(urlString: String) {
    thread {
        try {
            val url = URL(urlString)
            val connection = url.openConnection() as HttpURLConnection
            connection.requestMethod = "GET"
            connection.connectTimeout = 5000
            connection.readTimeout = 5000

            val responseCode = connection.responseCode
            println("Respuesta: $responseCode")

        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}