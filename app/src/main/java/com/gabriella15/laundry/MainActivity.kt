package com.gabriella15.laundry

import android.annotation.SuppressLint
import android.os.Bundle
import android.content.Intent
import android.provider.ContactsContract.Profile
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.gabriella15.laundry.Cabang.DataCabangActivity
import com.gabriella15.laundry.Cabang.profil
import com.gabriella15.laundry.Pegawai.tambahpegawai
import com.gabriella15.laundry.pelanggan.DataPelanggan
import com.gabriella15.laundry.pelanggan.tambahpelanggan
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

class   MainActivity : AppCompatActivity() {
    lateinit var Pelanggan : ImageView
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        // Inisialisasi TextView
        val tvGreeting: TextView = findViewById(R.id.Welcome)
        val calendarTextView: TextView = findViewById(R.id.Calendar)

        // Menentukan bahasa perangkat
        val language = Locale.getDefault().language

        // Mendapatkan jam saat ini
        val calendar = Calendar.getInstance()
        val hour = calendar.get(Calendar.HOUR_OF_DAY)

        // Menampilkan pesan selamat berdasarkan waktu dan bahasa perangkat
        val greeting = when (hour) {
            in 5..10 -> if (language == "id") "Selamat Pagi, Gaby" else "Good Morning, Gaby"
            in 11..14 -> if (language == "id") "Selamat Siang, Gaby" else "Good Afternoon, Gaby"
            in 15..17 -> if (language == "id") "Selamat Sore, Gaby" else "Good Evening, Gaby"
            else -> if (language == "id") "Selamat Malam, Gaby" else "Good Night, Gaby"
        }
        tvGreeting.text = greeting

        // Menampilkan tanggal saat ini
        val dateFormat = SimpleDateFormat("dd MMMM yyyy", Locale.getDefault())
        calendarTextView.text = dateFormat.format(Date())
        enableEdgeToEdge()
        // Redirect ke halaman pelanggan
        val cardMasukPelanggan = findViewById<ImageView>(R.id.Pelanggan)
        cardMasukPelanggan.setOnClickListener {
            val intent = Intent(this, DataPelanggan::class.java)
            startActivity(intent)
        }
        val cardMasukPegawai = findViewById<CardView>(R.id.card5)
        cardMasukPegawai.setOnClickListener {
            val intent = Intent(this, tambahpegawai::class.java)
            startActivity(intent)
        }
        val cardMasuklayanan = findViewById<CardView>(R.id.card3)
        cardMasuklayanan.setOnClickListener {
            val intent = Intent(this, Layanan::class.java)
            startActivity(intent)
        }
        val cardMasukProfil = findViewById<CardView>(R.id.profil)
        cardMasukProfil.setOnClickListener {
            val intent = Intent(this, profil::class.java)
            startActivity(intent)
        }

        val cardMasuktransaksi= findViewById<ImageView>(R.id.transaksi)
        cardMasuktransaksi.setOnClickListener {
            val intent = Intent(this,datatransaksi::class.java)
            startActivity(intent)
        }
        val cardMasukcabang= findViewById<CardView>(R.id.card6)
        cardMasukcabang.setOnClickListener {
            val intent = Intent(this, DataCabangActivity::class.java)
            startActivity(intent)
        }






        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
}
