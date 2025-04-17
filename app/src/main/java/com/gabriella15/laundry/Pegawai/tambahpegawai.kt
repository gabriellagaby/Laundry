package com.gabriella15.laundry.Pegawai

import android.os.Build
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.gabriella15.laundry.R
import com.gabriella15.laundry.modeldata.ModelPegawai
import com.google.firebase.database.FirebaseDatabase



class tambahpegawai : AppCompatActivity() {
    val database = FirebaseDatabase.getInstance()
    val myRef = database.getReference("pegawai")
    lateinit var etNamapgw: EditText
    lateinit var etalamatpgw: EditText
    lateinit var etnohppgw: EditText
    lateinit var etcabangpgw: EditText
    lateinit var btsimpan: Button

    var idPegawai:String=""


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_tambahpegawai)

        // Pastikan inisialisasi dilakukan setelah setContentView
        init()
        getData()
        btsimpan.setOnClickListener{
            cekValidasi()
        }

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
        etNamapgw = findViewById(R.id.etNamapgw)
        etalamatpgw = findViewById(R.id.etalamatpgw)
        etnohppgw = findViewById(R.id.etnohppgw)
        etcabangpgw = findViewById(R.id.etcabangpgw)
        btsimpan = findViewById(R.id.btsimpanpgw)
    }
    fun getData(){
        idPegawai = intent.getStringExtra("idPegawai").toString()
        val judul = intent.getStringExtra("judul")
        val nama = intent.getStringExtra("namaPegawai")
        val alamat = intent.getStringExtra("alamatPegawai")
        val hp = intent.getStringExtra("noHPPegawai")
        val cabang = intent.getStringExtra("idcabang")
        judul.text=judul
        etNamapgw.setText(nama)
        etalamatpgw.setText(alamat)
        etnohppgw.setText(hp)
        etcabangpgw.setText(cabang)
        if(!judul.text.equals("tambahpegawai")){
            if (judul.equals("EditPegawai")){
                mati()
                btsimpan.text="Sunting"
            }
        }else{
            hidup()
            etNamapgw.requestFocus()
            btsimpan.text="Simpan"
        }
    }
    fun mati(){
        etNamapgw.isEnabled=false
        etalamatpgw.isEnabled=false
        etnohppgw.isEnabled=false
        etcabangpgw.isEnabled=false
    }
    fun hidup(){
        etNamapgw.isEnabled=true
        etalamatpgw.isEnabled=true
        etnohppgw.isEnabled=true
        etcabangpgw.isEnabled=true
    }
    @RequiresApi(Build.VERSION_CODES.O)
    fun update(){
        val pegawaiRef = database.getReference("pegawai").child(idPegawai)
        val data = ModelPegawai(
            idPegawai,
            etNamapgw.text.toString(),
            etalamatpgw.text.toString(),
            etnohppgw.text.toString(),
            etcabangpgw.text.toString()
        // Buat  Map untuk update data
        val updateData = mutableMapof <String, Any>()
        updateData("NamaPegawai") = data.NamaPegawai.toString()
        updateData("AlamatPegawai") = data.AlamatPegawai.toString()
        updateData("noHPPegawai") = data.noHPPegawai.toString()
        updateData("idCabang") = data.idCabang.toString()


        val updateData
        pegawaiRef.updateChildren(updateData).addOnSuccessListener {
            Toast.makeText(this@tambahpegawai,"Data Pegawai Berhasil Diperbarui", Toast.LENGTH_SHORT).show()
            finish()
        }.addOnFailureListener{
            Toast.makeText(this@tambahpegawai, "Data Pegawai Gagal Diperbarui", Toast.LENGTH_SHORT).show()
        }

    }
    fun simpan() {
        val pegawaiBaru = myRef.push() // Membuat node baru di Firebase
        val idpegawai = pegawaiBaru.key // Ambil ID dari Firebase, hentikan jika null

        val data = ModelPegawai(
            idpegawai = idpegawai, // ✅ Simpan ID yang benar
            Namapgw = etNamapgw.text.toString(),
            alamatpgw = etalamatpgw.text.toString(),
            nohppgw = etnohppgw.text.toString(),
            cabangpgw = etcabangpgw.text.toString()
        )

        pegawaiBaru.setValue(data)
            .addOnSuccessListener {
                Toast.makeText(this, getString(R.string.Simpan), Toast.LENGTH_SHORT).show()
                finish()
            }
            .addOnFailureListener {
                Toast.makeText(this, getString(R.string.errorsimpan), Toast.LENGTH_SHORT).show()
            }
    }


    fun cekValidasi() {
        val nama = etNamapgw.text.toString()
        val alamat = etalamatpgw.text.toString()
        val nohp = etnohppgw.text.toString()
        val cabang = etcabangpgw.text.toString()

        // Validasi data input
        if (nama.isEmpty()) {
            etNamapgw.error = this.getString(R.string.validasi_etNama)
            etNamapgw.requestFocus()
            return
        }
        if (alamat.isEmpty()) {
            etalamatpgw.error = this.getString(R.string.validasi_etalamat)
            etalamatpgw.requestFocus()
            return
        }
        if (nohp.isEmpty()) {
            etnohppgw.error = this.getString(R.string.validasi_etnohp)
            etnohppgw.requestFocus()
            return
        }
        if (cabang.isEmpty()) {
            etcabangpgw.error = this.getString(R.string.validasi_etcabang)
            etcabangpgw.requestFocus()
            return
        }
        if (btsimpan.text.equals("Simpan")){
            simpan()
        }else if(btsimpan.text.equals("Sunting")){
            hidup()
            etNamapgw.requestFocus()
            btsimpan.text="Perbarui"
        }else if (btsimpan.text.equals("Perbarui")){
        }
    }
}
