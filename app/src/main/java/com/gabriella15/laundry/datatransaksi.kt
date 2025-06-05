package com.gabriella15.laundry

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.gabriella15.laundry.modeldata.ModelTransaksiTambahan

class datatransaksi : AppCompatActivity() {

    private lateinit var tvpelnama: TextView
    private lateinit var tvpelnohp: TextView
    private lateinit var btpilihpelanggan: Button
    private lateinit var btpilihlayanan: Button
    private lateinit var btTambahan: Button
    private lateinit var btProses: Button

    private val dataList = mutableListOf<ModelTransaksiTambahan>()

    private val   pilihPelanggan = 1
    private val pilihlayanan = 2

    private var idPelanggan: String = ""
    private var namaPelanggan: String = ""
    private var noHP: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.transaksi)

        init()
        setupListeners()

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.cardtransaksi)) { view, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            view.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    private fun init() {
        tvpelnama = findViewById(R.id.tvpelnama)
        tvpelnohp = findViewById(R.id.nohppel)
        btpilihpelanggan = findViewById(R.id.PilihPelanggan)
        btpilihlayanan = findViewById(R.id.Pilihlayanan)
        btTambahan = findViewById(R.id.btTambahan)
        btProses = findViewById(R.id.btProses)
    }

    private fun setupListeners() {
        btpilihpelanggan.setOnClickListener {
            val intent = Intent(this, pilihPelanggan::class.java)
            startActivityForResult(intent, pilihPelanggan)
        }

        btpilihlayanan.setOnClickListener {
            val intent = Intent(this, pilihlayanan::class.java)
            startActivityForResult(intent, pilihlayanan)
        }

        btTambahan.setOnClickListener {
            // Implementasikan logika tambahan
        }

        btProses.setOnClickListener {
            // Implementasikan logika proses
        }
    }

    @Deprecated("Gunakan Activity Result API sebagai pengganti")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == pilihPelanggan && resultCode == RESULT_OK && data != null) {
            idPelanggan = data.getStringExtra("idPelanggan").orEmpty()
            namaPelanggan = data.getStringExtra("nama").orEmpty()
            noHP = data.getStringExtra("noHP").orEmpty()

            tvpelnama.text = "Nama Pelanggan: $namaPelanggan"
            tvpelnohp.text = "No HP: $noHP"
        } else if (requestCode == pilihPelanggan && resultCode == RESULT_CANCELED) {
            Toast.makeText(this, "Batal Memilih Pelanggan", Toast.LENGTH_SHORT).show()
        }

        // Tambahkan logika untuk pilihlayanan jika diperlukan
    }
}
