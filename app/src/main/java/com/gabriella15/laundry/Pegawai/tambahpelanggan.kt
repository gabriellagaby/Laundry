package com.gabriella15.laundry.Pegawai

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.gabriella15.laundry.R
import com.gabriella15.laundry.modeldata.ModelCabang
import com.gabriella15.laundry.modeldata.ModelPelanggan
import com.google.firebase.Firebase
import com.google.firebase.database.FirebaseDatabase
import org.w3c.dom.Text

class tambahpelanggan : AppCompatActivity() {
    val database = FirebaseDatabase.getInstance()
    val myRef = database.getReference("pelanggan")
    lateinit var tvjudul: TextView
    lateinit var etNama: EditText
    lateinit var etAlamat: EditText
    lateinit var etNohp: EditText
    lateinit var etNamaCabang: EditText
    lateinit var btsimpan: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_tambahpelanggan)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            init()
            getData()
            btsimpan.setOnClickListener{
                cekValidasi()
            }
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    private fun getData() {
        TODO("Not yet implemented")
    }

    fun init() {
        tvjudul = findViewById(R.id.tvJudul)
        etNama = findViewById(R.id.etNama)
        etAlamat = findViewById(R.id.etalamat)
        etNohp = findViewById(R.id.etnohp)
        etNamaCabang = findViewById(R.id.etcabang)
    }

    fun cekValidasi() {
        val nama = etNama.text.toString()
        val alamat = etAlamat.text.toString()
        val nohp = etNohp.text.toString()
        val cabang = etNamaCabang.text.toString()
        // Validasi data
        if (nama.isEmpty()) {
            etNama.error = this.getString(R.string.validasi_etNama)
            Toast.makeText(this, "", Toast.LENGTH_SHORT).show()
            etNama.requestFocus()
            return
        }
        if (alamat.isEmpty()) {
            etAlamat.error = this.getString(R.string.validasi_etalamat)
            Toast.makeText(this, "", Toast.LENGTH_SHORT).show()
            etAlamat.requestFocus()
            return
        }
        if (nohp.isEmpty()) {
            etNohp.error = this.getString(R.string.validasi_etnohp)
            Toast.makeText(this, "", Toast.LENGTH_SHORT).show()
            etNohp.requestFocus()
            return
        }
        if (cabang.isEmpty()) {
            etNamaCabang.error = this.getString(R.string.validasi_etcabang)
            Toast.makeText(this, "", Toast.LENGTH_SHORT).show()
            etNamaCabang.requestFocus()
            return
        }

        fun simpan(){
            val pelangganBaru= myRef.push()
            val pelangganID= pelangganBaru.key
            val data = ModelPelanggan(
                pelangganID.toString(),
                etNama.text.toString(),
                etAlamat.text.toString(),
                etNohp.text.toString(),
                etNamaCabang.text.toString(),
            )

        }
    }


}