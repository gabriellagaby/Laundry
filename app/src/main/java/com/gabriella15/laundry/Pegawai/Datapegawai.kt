package com.gabriella15.laundry.Pegawai

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.gabriella15.laundry.Pegawai.tambahpegawai
import com.gabriella15.laundry.R
import com.gabriella15.laundry.adapter.adapterdatapegawai
import com.gabriella15.laundry.modeldata.ModelPegawai
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class Datapegawai: AppCompatActivity() {
    val database = FirebaseDatabase.getInstance()
    val myRef = database.getReference("pegawai")
    lateinit var rvdatapegawai: RecyclerView
    lateinit var fabdata_pegawai_tambah: FloatingActionButton
    lateinit var pegawaiList: ArrayList<ModelPegawai>

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_datapegawai)

        init()

        val layoutManager = LinearLayoutManager(this)
        layoutManager.reverseLayout = true
        layoutManager.stackFromEnd = true
        rvdatapegawai.layoutManager = layoutManager
        rvdatapegawai.setHasFixedSize(true)

        pegawaiList = arrayListOf<ModelPegawai>()
        tekan()
        getData()
        fabdata_pegawai_tambah.setOnClickListener {
            val intent = Intent(this, tambahpegawai::class.java)
            intent.putExtra("judul", this.getString(R.string.Tambah_Pegawai))
            intent.putExtra("idPegawai","")
            intent.putExtra("namaPegawai","")
            intent.putExtra("noHPPegawai","")
            intent.putExtra("AlamatPegawai","")
            intent.putExtra("idcabang","")
            startActivity(intent)
        }

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    fun init() {
        rvdatapegawai = findViewById(R.id.rvdatapegawai)
        fabdata_pegawai_tambah = findViewById(R.id.fabdatapegawai_tambah)
    }

    fun getData() {
        val query = myRef.orderByChild("idpegawai").limitToLast(100)
        query.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    pegawaiList.clear()
                    for (dataSnapshot in snapshot.children) {
                        val pegawai = dataSnapshot.getValue(ModelPegawai::class.java)
                        if (pegawai != null) {
                            pegawaiList.add(pegawai!!)
                        }
                    }
                    val adapter = adapterdatapegawai(pegawaiList)
                    rvdatapegawai.adapter = adapter
                    adapter.notifyDataSetChanged()
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(this@Datapegawai, error.message, Toast.LENGTH_SHORT).show()
            }
        })
    }

    fun tekan() {
        fabdata_pegawai_tambah.setOnClickListener {
            val intent = Intent(this, tambahpegawai::class.java)
            startActivity(intent)
        }
    }
}
