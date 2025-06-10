package com.gabriella15.laundry.adapter

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.gabriella15.laundry.R
import com.gabriella15.laundry.Transaksi.DataTransaksi
import com.gabriella15.laundry.modeldata.ModelPelanggan
import com.google.firebase.database.DatabaseReference


class Adapterpilihpelanggan(
    private val list: List<ModelPelanggan>,
    private val onItemClick: (ModelPelanggan) -> Unit
) : RecyclerView.Adapter<Adapterpilihpelanggan.ViewHolder>() {

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvNama: TextView = itemView.findViewById(R.id.namapel)
        val tvID: TextView = itemView.findViewById(R.id.tvidpelanggan)
        val tvNoHp: TextView = itemView.findViewById(R.id.nohppel)
        val tvAlamat: TextView = itemView.findViewById(R.id.alamatpel)

        fun bind(pelanggan: ModelPelanggan) {
            tvNama.text = pelanggan.namapel
            tvID.text = "ID: ${pelanggan.idpelanggan}"
            tvNoHp.text = "No HP: ${pelanggan.nohppel}"
            tvAlamat.text = "Alamat: ${pelanggan.alamatpel}"

            itemView.setOnClickListener {
                onItemClick(pelanggan)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.cardpilihpelanggan, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int = list.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(list[position])
    }
}
