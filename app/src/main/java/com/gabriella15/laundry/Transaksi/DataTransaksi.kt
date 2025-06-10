package com.gabriella15.laundry.Transaksi

import android.annotation.SuppressLint
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.gabriella15.laundry.R
import com.gabriella15.laundry.adapter.pilihlayananadapter
import com.gabriella15.laundry.modeldata.ModelTransaksiTambahan
import com.google.firebase.FirebaseApp

class
DataTransaksi : AppCompatActivity() {
    private lateinit var tvPelangganNama: TextView
    private lateinit var tvPelangganNoHP: TextView
    private lateinit var tvLayananNama: TextView
    private lateinit var tvLayananHarga: TextView
    private lateinit var rvLayananTambahan: RecyclerView
    private lateinit var btnPilihPelanggan: Button
    private lateinit var btnPilihLayanan: Button
    private lateinit var btnTambahan: Button
    private lateinit var btn_proses: Button
    private val dataList = mutableListOf<ModelTransaksiTambahan>()

    private val pilihPelanggan = 1
    private val pilihLayanan = 2
    private val pilihLayananTambahan = 3

    private var idPelanggan: String = ""
    private var idCabang: String = ""
    private var namaPelanggan: String = ""
    private var noHP: String = ""
    private var idLayanan: String = ""
    private var namaLayanan: String = ""
    private var hargaLayanan: String = ""
    private var idPegawai: String = ""

    private lateinit var sharedPref: SharedPreferences

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        // Set layout
        setContentView(R.layout.activity_data_transaksi)

        // Setup SharedPreferences
        sharedPref = getSharedPreferences("user_data", MODE_PRIVATE)
        idCabang = sharedPref.getString("idCabang", null).toString()
        idPegawai = sharedPref.getString("idPegawai", null).toString()

        // Initialize Firebase
        FirebaseApp.initializeApp(this)

        // Initialize views dengan error handling
        initWithErrorHandling()

        // Setup RecyclerView
        setupRecyclerView()

        // Setup click listeners
        setupClickListeners()

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.da)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    private fun initWithErrorHandling() {
        try {
            Log.d("DataTransaksi", "Mulai inisialisasi views...")

            tvPelangganNama = findViewById(R.id.tv_nama_pelanggan)
                ?: throw NullPointerException("tvNamaPelanggan not found")
            Log.d("DataTransaksi", "tvPelangganNama: OK")

            tvPelangganNoHP = findViewById(R.id.tv_no_hp)
                ?: throw NullPointerException("tvpilihpelangganNoHP not found")
            Log.d("DataTransaksi", "tvPelangganNoHP: OK")

            tvLayananNama = findViewById(R.id.tvNamaLayanan)
                ?: throw NullPointerException("tvNamaLayanan not found")
            Log.d("DataTransaksi", "tvLayananNama: OK")

            tvLayananHarga = findViewById(R.id.tv_harga_layanan)
                ?: throw NullPointerException("tvpilihlayananHarga not found")
            Log.d("DataTransaksi", "tvLayananHarga: OK")

            rvLayananTambahan = findViewById(R.id.rv_layanan_tambahan)
                ?: throw NullPointerException("rvLayananTambahan not found")
            Log.d("DataTransaksi", "rvLayananTambahan: OK")

            btnPilihPelanggan = findViewById(R.id.btn_pilih_pelanggan)
                ?: throw NullPointerException("btnPilihPelanggan not found")
            Log.d("DataTransaksi", "btnPilihPelanggan: OK")

            btnPilihLayanan = findViewById(R.id.btn_pilih_layanan)
                ?: throw NullPointerException("btnPilihLayanan not found")
            Log.d("DataTransaksi", "btnPilihLayanan: OK")

            btnTambahan = findViewById(R.id.btn_tambahan)
                ?: throw NullPointerException("btnTambahan not found")
            Log.d("DataTransaksi", "btnTambahan: OK")

            btn_proses = findViewById(R.id.btn_proses)
                ?: throw NullPointerException("btnProsesTransaksi not found")
            Log.d("DataTransaksi", "btn_proses: OK")

            Log.d("DataTransaksi", "Semua views berhasil diinisialisasi")

        } catch (e: Exception) {
            Log.e("DataTransaksi", "Error saat inisialisasi: ${e.message}")
            Log.e("DataTransaksi", "Stacktrace: ", e)

            Toast.makeText(this, "Error: View tidak ditemukan - ${e.message}", Toast.LENGTH_LONG).show()

            // Tutup activity jika error kritis
            finish()
        }
    }

    private fun setupRecyclerView() {
        try {
            val layoutManager = LinearLayoutManager(this)
            layoutManager.reverseLayout = true
            rvLayananTambahan.layoutManager = layoutManager
            rvLayananTambahan.setHasFixedSize(true)

            // Inisialisasi adapter dengan callback untuk menghapus item
            tambahanAdapter = com.gabriella15.laundry.adapter.pilihtambahanadapter(dataList) { selectedTambahan ->
                // Hapus item dari list
                dataList.remove(selectedTambahan)
                tambahanAdapter.notifyDataSetChanged()
                Toast.makeText(this, "Layanan tambahan dihapus", Toast.LENGTH_SHORT).show()
            }
            rvLayananTambahan.adapter = pilihlayananadapter

            Log.d("DataTransaksi", "RecyclerView berhasil di-setup")
        } catch (e: Exception) {
            Log.e("DataTransaksi", "Error setup RecyclerView: ${e.message}")
        }
    }

    private fun setupClickListeners() {
        try {
            btnPilihPelanggan.setOnClickListener {
                val intent = Intent(this, PilihPelanggan::class.java)
                startActivityForResult(intent, pilihPelanggan)
            }

            btnPilihLayanan.setOnClickListener {
                val intent = Intent(this, PilihLayanan::class.java)
                startActivityForResult(intent, pilihLayanan)
            }

            btnTambahan.setOnClickListener {
                val intent = Intent(this, PilihTambahan::class.java)
                startActivityForResult(intent, pilihLayananTambahan)
            }

            // Validasi hanya untuk tombol proses
            btn_proses.setOnClickListener {
                if (validateDataForProcess()) {
                    val intent = Intent(this, KonfirmasiTransaksi::class.java)
                    intent.putExtra("namaPelanggan", namaPelanggan)
                    intent.putExtra("noHP", noHP)
                    intent.putExtra("namaLayanan", namaLayanan)
                    intent.putExtra("hargaLayanan", hargaLayanan)
                    intent.putExtra("listTambahan", ArrayList(dataList))
                    startActivity(intent)
                } else {
                    showValidationMessage()
                }
            }

            Log.d("DataTransaksi", "Click listeners berhasil di-setup")
        } catch (e: Exception) {
            Log.e("DataTransaksi", "Error setup click listeners: ${e.message}")
        }
    }

    // Pisahkan validasi untuk tombol proses
    private fun validateDataForProcess(): Boolean {
        return namaPelanggan.isNotEmpty() &&
                noHP.isNotEmpty() &&
                namaLayanan.isNotEmpty() &&
                hargaLayanan.isNotEmpty()
    }

    // Method untuk menampilkan pesan validasi yang lebih spesifik
    private fun showValidationMessage() {
        val missingData = mutableListOf<String>()

        if (namaPelanggan.isEmpty() || noHP.isEmpty()) {
            missingData.add("Data Pelanggan")
        }

        if (namaLayanan.isEmpty() || hargaLayanan.isEmpty()) {
            missingData.add("Layanan Utama")
        }

        val message = if (missingData.isNotEmpty()) {
            "Mohon lengkapi: ${missingData.joinToString(", ")}"
        } else {
            "Mohon lengkapi data transaksi"
        }

        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    @Deprecated("This method has been deprecated in favor of using the Activity Result API")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        when (requestCode) {
            pilihPelanggan -> {
                if (resultCode == RESULT_OK && data != null) {
                    idPelanggan = data.getStringExtra("idPelanggan") ?: ""
                    val nama = data.getStringExtra("namaPelanggan") ?: ""
                    val nomorHP = data.getStringExtra("noHPPelanggan") ?: ""

                    tvPelangganNama.text = "Nama Pelanggan : $nama"
                    tvPelangganNoHP.text = "No HP : $nomorHP"

                    namaPelanggan = nama
                    noHP = nomorHP
                } else if (resultCode == RESULT_CANCELED) {
                    Toast.makeText(this, "Batal Memilih Pelanggan", Toast.LENGTH_SHORT).show()
                }
            }

            pilihLayanan -> {
                if (resultCode == RESULT_OK && data != null) {
                    idLayanan = data.getStringExtra("idLayanan") ?: ""
                    val nama = data.getStringExtra("namaLayanan") ?: ""
                    val harga = data.getStringExtra("hargaLayanan") ?: ""

                    tvLayananNama.text = "Layanan : $nama"
                    tvLayananHarga.text = "Harga : Rp. $harga"

                    namaLayanan = nama
                    hargaLayanan = harga
                } else if (resultCode == RESULT_CANCELED) {
                    Toast.makeText(this, "Batal Memilih Layanan", Toast.LENGTH_SHORT).show()
                }
            }

            pilihLayananTambahan -> {
                if (resultCode == RESULT_OK && data != null) {
                    // PERBAIKAN: Gunakan key yang benar dari PilihTambahanActivity
                    val id = data.getStringExtra("idTambahan") ?: ""
                    val nama = data.getStringExtra("namaTambahan") ?: ""
                    val harga = data.getStringExtra("hargaTambahan") ?: ""

                    Log.d("DataTransaksi", "Received tambahan: id=$id, nama=$nama, harga=$harga")

                    // Pastikan data tidak kosong
                    if (nama.isNotEmpty() && harga.isNotEmpty()) {
                        // PERBAIKAN: Sesuaikan dengan constructor model Anda
                        val tambahan = ModelTransaksiTambahan(
                            id = id,
                            idLayanan = id,
                            namaLayanan = nama,
                            hargaLayanan = harga,
                            tanggalTerdaftar = ""
                        )
                        dataList.add(tambahan)

                        // Update RecyclerView
                        PilihTambahan.notifyDataSetChanged()

                        Toast.makeText(this, "Layanan tambahan ditambahkan: $nama", Toast.LENGTH_SHORT).show()
                        Log.d("DataTransaksi", "Tambahan berhasil ditambahkan. Total: ${dataList.size}")
                    } else {
                        Toast.makeText(this, "Data tambahan tidak valid", Toast.LENGTH_SHORT).show()
                        Log.e("DataTransaksi", "Data tambahan kosong atau null")
                    }
                } else if (resultCode == RESULT_CANCELED) {
                    Toast.makeText(this, "Batal Memilih Tambahan", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}

private fun PilihTambahan.Companion.notifyDataSetChanged() {
    TODO("Not yet implemented")
}
