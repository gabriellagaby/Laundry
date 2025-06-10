package com.gabriella15.laundry.pelanggan

import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.gabriella15.laundry.R
import com.gabriella15.laundry.modeldata.ModelPelanggan
import com.google.firebase.database.FirebaseDatabase

class Tambahpelanggan : AppCompatActivity() {
    val database = FirebaseDatabase.getInstance()
    val myRef = database.getReference("pelanggan")
    lateinit var etNamapel: EditText
    lateinit var etalamatpel: EditText
    lateinit var etnohppel: EditText
    lateinit var etcabangpel: EditText
    lateinit var btsimpan: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_tambahpelanggan)

        // Pastikan inisialisasi dilakukan setelah setContentView
        init()

        // Atur listener untuk tombol simpan
        btsimpan.setOnClickListener {
            cekValidasi()
        }

        // Pengaturan WindowInsets
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    fun init() {
        etNamapel = findViewById(R.id.etNamapel)
        etalamatpel = findViewById(R.id.etalamatpel)
        etnohppel = findViewById(R.id.etnohppel)
        etcabangpel = findViewById(R.id.etcabangpel)
        btsimpan = findViewById(R.id.btsimpan)
    }

    fun simpan() {
        val pelangganBaru = myRef.push() // Membuat node baru di Firebase
        val idpelanggan = pelangganBaru.key // Ambil ID dari Firebase, hentikan jika null

        val data = ModelPelanggan(
            idpelanggan = idpelanggan, // ✅ Simpan ID yang benar
            namapel = etNamapel.text.toString(),
            alamatpel = etalamatpel.text.toString(),
            nohppel = etnohppel.text.toString(),
            cabangpel = etcabangpel.text.toString()
        )

        pelangganBaru.setValue(data)
            .addOnSuccessListener {
                Toast.makeText(this, getString(R.string.Simpan), Toast.LENGTH_SHORT).show()
                finish()
            }
            .addOnFailureListener {
                Toast.makeText(this, getString(R.string.errorsimpan), Toast.LENGTH_SHORT).show()
            }
    }


    fun cekValidasi() {
        val nama = etNamapel.text.toString()
        val alamat = etalamatpel.text.toString()
        val nohp = etnohppel.text.toString()
        val cabang = etcabangpel.text.toString()

        // Validasi data input
        if (nama.isEmpty()) {
            etNamapel.error = this.getString(R.string.validasi_etNama)
            etNamapel.requestFocus()
            return
        }
        if (alamat.isEmpty()) {
            etalamatpel.error = this.getString(R.string.validasi_etalamat)
            etalamatpel.requestFocus()
            return
        }
        if (nohp.isEmpty()) {
            etnohppel.error = this.getString(R.string.validasi_etnohp)
            etnohppel.requestFocus()
            return
        }
        if (cabang.isEmpty()) {
            etcabangpel.error = this.getString(R.string.validasi_etcabang)
            etcabangpel.requestFocus()
            return
        }
        simpan()
    }
}
