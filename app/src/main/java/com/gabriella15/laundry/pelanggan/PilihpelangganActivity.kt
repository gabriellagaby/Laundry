package com.gabriella15.laundry.pelanggan

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.gabriella15.laundry.R

import com.gabriella15.laundry.adapter.Adapterpilihpelanggan
import com.gabriella15.laundry.modeldata.ModelPelanggan
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class PilihpelangganActivity : AppCompatActivity() {
    val database = FirebaseDatabase.getInstance()
    val myRef = database.getReference("Pelanggan")
    lateinit var rvdatapilpel: RecyclerView
    lateinit var pelangganList: ArrayList<ModelPelanggan>
    lateinit var tvKosong : TextView
    private lateinit var recyclerView: RecyclerView
    private lateinit var databaseReference: DatabaseReference
    private lateinit var adapter: Adapterpilihpelanggan

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pilihpelanggan)

        recyclerView = findViewById(R.id.rvdatapilpel) // Sesuaikan ID RecyclerView
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.setHasFixedSize(true)

        pelangganList = ArrayList()
        adapter = Adapterpilihpelanggan(pelangganList) { pelanggan ->
            val resultIntent = Intent()
            resultIntent.putExtra("idPelanggan", pelanggan.idpelanggan)
            resultIntent.putExtra("nama", pelanggan.namapel)
            resultIntent.putExtra("noHP", pelanggan.nohppel)
            setResult(RESULT_OK, resultIntent)
            finish()
        }

        recyclerView.adapter = adapter

        databaseReference = FirebaseDatabase.getInstance().getReference("Pelanggan")

        databaseReference.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                pelangganList.clear()
                for (data in snapshot.children) {
                    val pelanggan = data.getValue(ModelPelanggan::class.java)
                    if (pelanggan != null) {
                        pelangganList.add(pelanggan)
                    }
                }
                adapter.notifyDataSetChanged()
            }

            override fun onCancelled(error: DatabaseError) {
                // Handle error
            }

        })
    }
}