package com.itesm.ic2007b.proyecto

import android.graphics.Bitmap
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.webkit.WebChromeClient
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import kotlinx.android.synthetic.main.activity_agentes_inmobiliarios.*
import kotlinx.android.synthetic.main.activity_donativos.*

class Donativos : AppCompatActivity() {

    /**
     * Link de ejemplo para PayPal, se configura y obtiene el link del botón
     * directamente de la página oficial en la parte de Botones -> Donaciones
     *
     * LINK:
     * https://www.paypal.com/donate/buttons
     * */
    //private val BASE_URL = "https://www.paypal.com/donate?hosted_button_id=WZQMS5539B5LU"

    /**
     * Link de reemplazo: página de esencia patrimonio
     * */
    private val BASE_URL = "https://esenciapatrimonioa.wixsite.com/my-site-3"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_donativos)

        // Refresh
        swipeRefreshLayout.setOnRefreshListener {
            Paypal.reload()
            Paypal.loadUrl(BASE_URL)
        }

        // WebView
        Paypal.webChromeClient = object : WebChromeClient(){
        }

        Paypal.webViewClient = object  : WebViewClient(){

            override fun shouldOverrideUrlLoading(
                view: WebView?,
                request: WebResourceRequest?
            ): Boolean {
                return false
            }

            override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
                super.onPageStarted(view, url, favicon)

                swipeRefreshLayout.isRefreshing = true
            }

            override fun onPageFinished(view: WebView?, url: String?) {
                super.onPageFinished(view, url)

                swipeRefreshLayout.isRefreshing = false
            }

        }

        val settings = Paypal.settings
        settings.javaScriptEnabled = true

        Paypal.loadUrl(BASE_URL)

        initializeBackAgent()

    }

    override fun onBackPressed() {
        if(Paypal.canGoBack()) {
            Paypal.goBack()
        } else {
            super.onBackPressed()
        }
    }

    private fun initializeBackAgent() {
        backDonativos.setOnClickListener{
            finish()
        }
    }

}