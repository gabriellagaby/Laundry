package com.gabriella15.laundry.Transaksi

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
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

class PilihPelanggan : AppCompatActivity() {
    private lateinit var dbRef: DatabaseReference
    private lateinit var recyclerView: RecyclerView
    private lateinit var searchView: SearchView
    private lateinit var tvKosong: TextView
    private lateinit var adapter: Adapterpilihpelanggan

    private val listPelanggan = mutableListOf<ModelPelanggan>()
    private val filteredList = mutableListOf<ModelPelanggan>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pilihpelanggan)

        // Initialize views
        recyclerView = findViewById(R.id.rvdatapelanggan)
        searchView = findViewById(R.id.searchView)
        tvKosong = findViewById(R.id.tvKosong)

        recyclerView.layoutManager = LinearLayoutManager(this)

        // Initialize adapter
        adapter = Adapterpilihpelanggan(filteredList) { selectedPelanggan ->
            val intent = Intent()
            intent.putExtra("idPelanggan", selectedPelanggan.idpelanggan)
            intent.putExtra("namaPelanggan", selectedPelanggan.namapel)
            intent.putExtra("noHPPelanggan", selectedPelanggan.nohppel)
            setResult(RESULT_OK, intent)
            finish()
        }
        recyclerView.adapter = adapter

        // Setup search functionality
        setupSearchView()

        // Firebase reference
        dbRef = FirebaseDatabase.getInstance().getReference("pelanggan")

        // Load data from Firebase
        loadDataFromFirebase()
    }

    private fun setupSearchView() {
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                filterData(newText ?: "")
                return true
            }
        })
    }

    private fun filterData(query: String) {
        filteredList.clear()

        if (query.isEmpty()) {
            // If search query is empty, show all data
            filteredList.addAll(listPelanggan)
        } else {
            // Filter data based on search query
            val searchQuery = query.lowercase()
            for (pelanggan in listPelanggan) {
                if (pelanggan.namapel?.lowercase()?.contains(searchQuery) == true ||
                    pelanggan.idpelanggan?.lowercase()?.contains(searchQuery) == true ||
                    pelanggan.nohppel?.contains(searchQuery) == true ||
                    pelanggan.alamatpel?.lowercase()?.contains(searchQuery) == true) {
                    filteredList.add(pelanggan)
                }
            }
        }

        // Update UI based on filtered results
        updateUI()

        // Notify adapter about data changes
        adapter.notifyDataSetChanged()
    }

    private fun updateUI() {
        if (filteredList.isEmpty()) {
            recyclerView.visibility = View.GONE
            tvKosong.visibility = View.VISIBLE
        } else {
            recyclerView.visibility = View.VISIBLE
            tvKosong.visibility = View.GONE
        }
    }

    private fun loadDataFromFirebase() {
        dbRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                listPelanggan.clear()
                for (data in snapshot.children) {
                    val pelanggan = data.getValue(ModelPelanggan::class.java)
                    if (pelanggan != null) {
                        listPelanggan.add(pelanggan)
                    }
                }

                // Initialize filtered list with all data
                filteredList.clear()
                filteredList.addAll(listPelanggan)

                // Update UI
                updateUI()
                adapter.notifyDataSetChanged()
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(this@PilihPelanggan, "Gagal memuat data", Toast.LENGTH_SHORT).show()
            }
        })
    }
}