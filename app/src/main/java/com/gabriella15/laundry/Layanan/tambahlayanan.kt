package com.gabriella15.laundry.Layanan

import android.os.Bundle
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.gabriella15.laundry.R
import com.gabriella15.laundry.modeldata.ModelLayanan
import com.google.firebase.database.FirebaseDatabase

class tambahlayanan : AppCompatActivity() {

    val database = FirebaseDatabase.getInstance()
    val myRef = database.getReference("layanan")

    lateinit var cbCuciKering: CheckBox
    lateinit var cbCuciSetrika: CheckBox
    lateinit var cbSetrikaSaja: CheckBox
    lateinit var cbCuciSprei: CheckBox
    lateinit var etBerat: EditText
    lateinit var btnHitung: Button
    lateinit var btnInput: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.layanan)

        init()

        btnHitung.setOnClickListener {
            hitungHarga()
        }

        btnInput.setOnClickListener {
            cekValidasi()
        }
    }

    fun init() {
        cbCuciKering = findViewById(R.id.card1)
        cbCuciSetrika = findViewById(R.id.card2)
        cbSetrikaSaja = findViewById(R.id.card3)
        cbCuciSprei = findViewById(R.id.card4)
        etBerat = findViewById(R.id.etberat)
        btnHitung = findViewById(R.id.hitung)
        btnInput = findViewById(R.id.input)
    }

    fun hitungHarga() {
        val berat = etBerat.text.toString().toDoubleOrNull()
        if (berat == null || berat <= 0) {
            etBerat.error = "Masukkan berat yang valid"
            return
        }

        var harga = 0.0
        if (cbCuciKering.isChecked) harga += 5000 * berat
        if (cbCuciSetrika.isChecked) harga += 7000 * berat
        if (cbSetrikaSaja.isChecked) harga += 4000 * berat
        if (cbCuciSprei.isChecked) harga += 10000 * berat

        Toast.makeText(this, "Total Harga: Rp$harga", Toast.LENGTH_LONG).show()
    }

    fun cekValidasi() {
        val berat = etBerat.text.toString()

        if (berat.isEmpty()) {
            etBerat.error = "Masukkan berat pakaian"
            etBerat.requestFocus()
            return
        }

        if (!cbCuciKering.isChecked && !cbCuciSetrika.isChecked && !cbSetrikaSaja.isChecked && !cbCuciSprei.isChecked) {
            Toast.makeText(this, "Pilih minimal satu layanan", Toast.LENGTH_SHORT).show()
            return
        }

        simpan()
    }

    fun simpan() {
        val layananBaru = myRef.push()
        val layanan = layananBaru.key
        val berat = etBerat.text.toString().toDoubleOrNull() ?: 0.0

        // Hitung harga total
        var totalHarga = 0.0
        val layananDipilih = mutableListOf<String>()

        if (cbCuciKering.isChecked) {
            totalHarga += 5000 * berat
            layananDipilih.add("Cuci Kering")
        }
        if (cbCuciSetrika.isChecked) {
            totalHarga += 7000 * berat
            layananDipilih.add("Cuci + Setrika")
        }
        if (cbSetrikaSaja.isChecked) {
            totalHarga += 4000 * berat
            layananDipilih.add("Setrika Saja")
        }
        if (cbCuciSprei.isChecked) {
            totalHarga += 10000 * berat
            layananDipilih.add("Cuci Sprei")
        }

        val data = ModelLayanan(
            layanan = layanan,
            namalyn = layananDipilih.joinToString(", "),
            hargalyn = totalHarga.toString(),
            cabanglyn = "Cabang A" // Ganti sesuai logika aplikasi Anda
        )

        layananBaru.setValue(data)
            .addOnSuccessListener {
                Toast.makeText(this, "Data berhasil disimpan", Toast.LENGTH_SHORT).show()
                finish()
            }
            .addOnFailureListener {
                Toast.makeText(this, "Gagal menyimpan data", Toast.LENGTH_SHORT).show()
            }
    }

}
