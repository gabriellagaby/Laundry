package com.gabriella15.laundry

import android.os.Bundle
import android.content.Intent
import android.widget.ImageView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.gabriella15.laundry.pelanggan.datapelanggan

class MainActivity : AppCompatActivity() {
    lateinit var Pelanggan : ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        enableEdgeToEdge()

        // Redirect ke halaman pelanggan
        val cardMasukPelanggan = findViewById<ImageView>(R.id.Pelanggan)
        cardMasukPelanggan.setOnClickListener {
            val intent = Intent(this, datapelanggan::class.java)
            startActivity(intent)
        }
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
    }
}
}
