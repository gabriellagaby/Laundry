package com.gabriella15.laundry.datalayanan

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.gabriella15.laundry.R
import com.gabriella15.laundry.adapter.adapterdatalayanan
import com.gabriella15.laundry.modeldata.ModelLayanan
import com.gabriella15.laundry.pelanggan.tambahpelanggan
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class Datalayanan: AppCompatActivity() {
    val database = FirebaseDatabase.getInstance()
    val myRef = database.getReference("layanan")
    lateinit var rvdatalayanan: RecyclerView
    lateinit var fabdata_layanan_tambah: FloatingActionButton
    lateinit var layananList: ArrayList<ModelLayanan>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_layanan)

        init()

        val layoutManager = LinearLayoutManager(this)
        layoutManager.reverseLayout = true
        layoutManager.stackFromEnd = true
        rvdatalayanan.layoutManager = layoutManager
        rvdatalayanan.setHasFixedSize(true)

        layananList = arrayListOf<ModelLayanan>()
        tekan()
        getData()

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    fun init() {
        rvdatalayanan = findViewById(R.id.rvdatalayanan)
        fabdata_layanan_tambah = findViewById(R.id.fabdatalayanan_tambah)
    }

    fun getData() {
        val query = myRef.orderByChild("idlayanan").limitToLast(100)
        query.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    layananList.clear()
                    for (dataSnapshot in snapshot.children) {
                        val layanan = dataSnapshot.getValue(ModelLayanan::class.java)
                        if (layanan != null) {
                            layananList.add(layanan!!)
                        }
                    }
                    val adapter = adapterdatalayanan(layananList)
                    rvdatalayanan.adapter = adapter
                    adapter.notifyDataSetChanged()
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(this@Datalayanan, error.message, Toast.LENGTH_SHORT).show()
            }
        })
    }

    fun tekan() {
        fabdata_layanan_tambah.setOnClickListener {
            val intent = Intent(this, tambahpelanggan::class.java)
            startActivity(intent)
        }
    }
}
