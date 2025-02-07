package com.gabriella15.laundry.pelanggan

import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.gabriella15.laundry.R
import com.gabriella15.laundry.adapter.adapterdatapelanggan
import com.gabriella15.laundry.modeldata.ModelPelanggan
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class  datapelanggan : AppCompatActivity() {
    private lateinit var layoutManager: LinearLayoutManager
    val database =FirebaseDatabase.getInstance()
    val myRef = database.getReference("pelanggan")
    lateinit var rvdatapelanggan: RecyclerView
    lateinit var fabdata_pengguna_tambah: FloatingActionButton
    lateinit var pelangganList: ArrayList<ModelPelanggan>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_datapelanggan)
        init()
        val layoutManager = LinearLayoutManager(this)
        layoutManager.reverseLayout = true
        layoutManager.stackFromEnd = true
        rvdatapelanggan.layoutManager=layoutManager
        rvdatapelanggan.setHasFixedSize(true)
        pelangganList = arrayListOf(ModelPelanggan())
        getData()

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    fun init(){
        rvdatapelanggan = findViewById(R.id.rvdatapelanggan)
        fabdata_pengguna_tambah = findViewById(R.id.fabdatapengguna_tambah)
    }

    fun getData(){
        val query =myRef.orderByChild("idpelanggan").limitToLast(100)
        query.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()){
                    pelangganList.clear()
                    for (dataSnapshoot in snapshot.children){
                        val pegawai = dataSnapshoot.getValue(ModelPelanggan::class.java)
                        pelangganList.add(pegawai!!)
                    }
                    val adapter = adapterdatapelanggan(pelangganList)
                    rvdatapelanggan.adapter= adapter
                    adapter.notifyDataSetChanged()
                }

            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(this@datapelanggan, error.message, Toast.LENGTH_SHORT).show()
            }
        })
    }
}