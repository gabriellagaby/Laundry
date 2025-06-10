package com.gabriella15.laundry

import android.os.Bundle
import android.content.Intent
import android.widget.TextView
import android.widget.ImageView
import android.widget.LinearLayout
import android.view.ViewGroup
import android.view.LayoutInflater
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.gabriella15.laundry.Layanan.Datalayanan
import com.gabriella15.laundry.Transaksi.DataTransaksi
import com.gabriella15.laundry.laporan.DataLaporan
import com.gabriella15.laundry.Tambahan.DataTambahan
import com.gabriella15.laundry.cabang.DataCabang
import com.gabriella15.laundry.Pegawai.Datapegawai
import com.gabriella15.laundry.pelanggan.Datapelanggan
import com.google.firebase.database.*
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AlertDialog
import android.content.Context
import android.provider.ContactsContract.CommonDataKinds.Im
import android.util.Log
import java.util.Calendar

class MainActivity : AppCompatActivity() {
    lateinit var pelanggan: ImageView
    lateinit var pegawai: ImageView
    lateinit var layanan: ImageView
    lateinit var transaksi: ImageView
    lateinit var cabang : ImageView
    lateinit var tambahan : ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        init()
        tekan()
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    fun init() {
        pelanggan = findViewById(R.id.Pelanggan)
        pegawai = findViewById(R.id.pegawai)
        layanan = findViewById(R.id.layanan)
        transaksi = findViewById(R.id.transaksi)
        cabang = findViewById(R.id.cabang)
        tambahan = findViewById(R.id.tambahan)

        // Debug: Pastikan semua view ditemukan
        Log.d("MainActivity", "Pelanggan: ${pelanggan != null}")
        Log.d("MainActivity", "Pegawai: ${pegawai != null}")
        Log.d("MainActivity", "Layanan: ${layanan != null}")
        Log.d("MainActivity", "Cabang: ${cabang != null}")
        Log.d("MainActivity", "Transaksi: ${transaksi != null}")
    }

    fun tekan() {
        pelanggan.setOnClickListener {
            Log.d("MainActivity", "Pelanggan clicked")
            val intent = Intent(this, Datapelanggan::class.java)
            startActivity(intent)
        }

        pegawai.setOnClickListener {
            Log.d("MainActivity", "Pegawai clicked")
            val intent = Intent(this, Datapegawai::class.java)
            startActivity(intent)
        }

        layanan.setOnClickListener {
            Log.d("MainActivity", "Layanan clicked")
            val intent = Intent(this, Datalayanan::class.java)
            startActivity(intent)
        }

        cabang.setOnClickListener {
            Log.d("MainActivity", "Cabang clicked")
            val intent = Intent(this, DataCabang::class.java)
            startActivity(intent)
        }

        tambahan.setOnClickListener {
            Log.d("MainActivity", "Tambahan clicked")
            val intent = Intent(this, DataTambahan::class.java)
            startActivity(intent)
        }

        transaksi.setOnClickListener {
            Log.d("MainActivity", "Transaksi clicked")
            val intent = Intent(this, DataTransaksi::class.java)
            startActivity(intent)
        }

        // Referensi TextView
        val helloTextView = findViewById(R.id.Welcome) as TextView
        val dateTextView = findViewById(R.id.Calendar) as TextView

        // Mengatur tanggal hari ini
        val calendar = Calendar.getInstance()
        val dateFormat = SimpleDateFormat("dd MMMM yyyy", Locale.getDefault())
        val currentDate = dateFormat.format(calendar.time)
        dateTextView.text = currentDate

        // Mengatur pesan berdasarkan waktu
        val hour = calendar.get(Calendar.HOUR_OF_DAY)
        val greeting = when {
            hour in 5..11 -> "Selamat Pagi,Gaby"
            hour in 12..14 -> "Selamat Siang,Gaby"
            hour in 15..17 -> "Selamat Sore, Gaby"
            else -> "Selamat Malam, Gaby"
        }
        helloTextView.text = greeting
    }
}