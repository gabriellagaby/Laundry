package com.example.laundry

import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.gabriella15.laundry.R

class MetodePembayaranActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.metodepembayaran)

        // Ambil referensi tombol dari layout
        val btnBayarNanti = findViewById<Button>(R.id.btnBayarNanti)
        val btnTunai = findViewById<Button>(R.id.btnTunai)
        val btnQRIS = findViewById<Button>(R.id.btnQRIS)
        val btnDANA = findViewById<Button>(R.id.btnDANA)
        val btnGoPay = findViewById<Button>(R.id.btnGoPay)
        val btnOVO = findViewById<Button>(R.id.btnOVO)

        // Pasang listener untuk masing-masing tombol
        btnBayarNanti.setOnClickListener {
            showToast("Bayar Nanti dipilih")
        }

        btnTunai.setOnClickListener {
            showToast("Tunai dipilih")
        }

        btnQRIS.setOnClickListener {
            showToast("QRIS dipilih")
        }

        btnDANA.setOnClickListener {
            showToast("DANA dipilih")
        }

        btnGoPay.setOnClickListener {
            showToast("GoPay dipilih")
        }

        btnOVO.setOnClickListener {
            showToast("OVO dipilih")
        }
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}
