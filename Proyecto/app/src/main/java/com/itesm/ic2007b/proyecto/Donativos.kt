package com.itesm.ic2007b.proyecto

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.webkit.WebChromeClient
import android.webkit.WebView
import android.webkit.WebViewClient
import kotlinx.android.synthetic.main.activity_donativos.*

class Donativos : AppCompatActivity() {

    private val BASE_URL = "https://www.paypal.com/donate?hosted_button_id=WZQMS5539B5LU"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_donativos)

        // WebView
        Paypal.webChromeClient = object : WebChromeClient(){
        }

        Paypal.webViewClient = object  : WebViewClient(){
        }

        val settings = Paypal.settings
        settings.javaScriptEnabled = true

        Paypal.loadUrl(BASE_URL)

    }

    override fun onBackPressed() {
        if(Paypal.canGoBack()) {
            Paypal.goBack()
        } else {
            super.onBackPressed()
        }
    }

}