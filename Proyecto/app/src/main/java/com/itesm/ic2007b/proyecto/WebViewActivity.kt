package com.itesm.ic2007b.proyecto

import android.os.Bundle
import android.util.Log
import android.webkit.WebViewClient
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_web_view.*

/**
 * Activity para abrir un link en una View en la Web
 * **/
class WebViewActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_web_view)

        // Obtener datos accediendo a los extras
        val extras = intent.extras
        if (extras != null) {
            val docUrl = extras.getString("key")
            // Se crea el cliente
            webView.webViewClient = WebViewClient()
            // Se configura el webview
            webView.settings.setSupportZoom(true)
            webView.settings.javaScriptEnabled = true
            // Se carga el url
            webView.loadUrl("https://docs.google.com/gview?embedded=true&url=$docUrl")
        }
    }

}