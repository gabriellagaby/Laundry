
package com.gabriella15.laundry.Tambahan

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
import com.gabriella15.laundry.adapter.DataTambahanAdapter
import com.gabriella15.laundry.modeldata.ModelTransaksiTambahan
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.database.*

class DataTambahan : AppCompatActivity() {

    private val database = FirebaseDatabase.getInstance()
    private val myRef = database.getReference("tambahan")

    private lateinit var rvDataTambahan: RecyclerView
    private lateinit var fabTambahTambahan: FloatingActionButton
    private lateinit var tambahanList: ArrayList<ModelTransaksiTambahan>

    private fun init() {
        rvDataTambahan = findViewById(R.id.rvDATA_TAMBAHAN)
        fabTambahTambahan = findViewById(R.id.fab_DATA_TAMBAH_TAMBAH)
        rvDataTambahan.layoutManager = LinearLayoutManager(this)
    }

    private fun getDATA() {
        val query = myRef.orderByChild("idLayanan").limitToLast(100)
        query.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    tambahanList.clear()
                    for (dataSnapshot in snapshot.children) {
                        val tambahan = dataSnapshot.getValue(ModelTransaksiTambahan::class.java)
                        tambahan?.let { tambahanList.add(it) }
                    }
                    val adapter = DataTambahanAdapter(tambahanList)
                    rvDataTambahan.adapter = adapter
                    adapter.notifyDataSetChanged()
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(this@DataTambahan, "Gagal load data: ${error.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_datatambahan)

        tambahanList = ArrayList()
        init()
        getDATA()

        fabTambahTambahan.setOnClickListener {
            val intent = Intent(this, TambahTambahan::class.java)
            startActivity(intent)
        }

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
}
