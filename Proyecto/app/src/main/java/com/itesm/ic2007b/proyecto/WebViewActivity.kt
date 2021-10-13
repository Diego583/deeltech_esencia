package com.itesm.ic2007b.proyecto

import android.os.Bundle
import android.util.Log
import android.webkit.WebViewClient
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_web_view.*

class WebViewActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_web_view)

        val extras = intent.extras
        if (extras != null) {
            val docUrl = extras.getString("key")
//            docUrl?.let { Log.i("url", it) }
            webView.webViewClient = WebViewClient()
            webView.settings.setSupportZoom(true)
            webView.settings.javaScriptEnabled = true
            webView.loadUrl("https://docs.google.com/gview?embedded=true&url=$docUrl")
        }
    }

}