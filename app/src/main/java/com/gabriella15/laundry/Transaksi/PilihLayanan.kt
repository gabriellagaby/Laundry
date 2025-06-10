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
import com.gabriella15.laundry.adapter.pilihlayananadapter
import com.gabriella15.laundry.modeldata.ModelLayanan
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class PilihLayanan: AppCompatActivity() {
    private lateinit var dbRef: DatabaseReference
    private lateinit var recyclerView: RecyclerView
    private lateinit var searchView: SearchView
    private lateinit var tvKosong: TextView
    private lateinit var adapter: pilihlayananadapter

    private val listLayanan = mutableListOf<ModelLayanan>()
    private val filteredList = mutableListOf<ModelLayanan>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pilihlayanan)

        // Initialize views
        recyclerView = findViewById(R.id.rvdatalayanan)
        searchView = findViewById(R.id.searchView)
        tvKosong = findViewById(R.id.tvKosong)

        recyclerView.layoutManager = LinearLayoutManager(this)

        // Initialize adapter
        adapter = pilihlayananadapter(filteredList) { selectedLayanan ->
            val intent = Intent()
            intent.putExtra("idLayanan", selectedLayanan.Idlyn)
            intent.putExtra("namaLayanan", selectedLayanan.namalyn)
            intent.putExtra("hargaLayanan", selectedLayanan.hargalyn)
            setResult(RESULT_OK, intent)
            finish()
        }
        recyclerView.adapter = adapter

        // Setup search functionality
        setupSearchView()

        // Firebase reference
        dbRef = FirebaseDatabase.getInstance().getReference("layanan")

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
            filteredList.addAll(listLayanan)
        } else {
            // Filter data based on search query
            val searchQuery = query.lowercase()
            for (layanan in listLayanan) {
                if (layanan.namalyn?.lowercase()?.contains(searchQuery) == true ||
                    layanan.Idlyn?.lowercase()?.contains(searchQuery) == true ||
                    layanan.hargalyn?.toString()?.contains(searchQuery) == true) {
                    filteredList.add(layanan)
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
                listLayanan.clear()
                for (data in snapshot.children) {
                    val layanan = data.getValue(ModelLayanan::class.java)
                    if (layanan != null) {
                        listLayanan.add(layanan)
                    }
                }

                // Initialize filtered list with all data
                filteredList.clear()
                filteredList.addAll(listLayanan)

                // Update UI
                updateUI()
                adapter.notifyDataSetChanged()
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(this@PilihLayanan, "Gagal memuat data", Toast.LENGTH_SHORT).show()
            }
        })
    }
}