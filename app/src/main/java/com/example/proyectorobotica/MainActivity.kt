package com.example.proyectorobotica

import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import java.net.HttpURLConnection
import java.net.URL
import kotlin.concurrent.thread
import android.os.Handler
import android.os.Looper
import android.media.MediaPlayer


class MainActivity : AppCompatActivity() {

    var isOn: Boolean = false;
    var isLightning: Boolean = false;
    lateinit var mp: MediaPlayer

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
            if(isOn==true && isLightning==false) {
                botonLlamada.setTextColor(ContextCompat.getColorStateList(this, R.color.red))
                isOn = false;
                //enviarComando("https://pruebaaaaa.free.beeceptor.com/off")
                enviarComando("http://192.168.1.4/off")

            }

            else if(isLightning==false){
                botonLlamada.setTextColor(ContextCompat.getColorStateList(this, R.color.green))
                //enviarComando("https://pruebaaaaa.free.beeceptor.com/on")
                enviarComando("http://192.168.1.4/on")
                isLightning==true;
                //Logica música
                mp = MediaPlayer.create(this, R.raw.cancion)
                mp.start()

                Handler(Looper.getMainLooper()).postDelayed({
                    isLightning==false;
                    isOn=true;
                    mp.stop()
                }, 4000)

            }


        }
    }
    override fun onDestroy() {
        mp.release()
        super.onDestroy()
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
