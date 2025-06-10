package com.gabriella15.laundry.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.gabriella15.laundry.R
import com.gabriella15.laundry.modeldata.ModelTransaksiTambahan


class DataTambahanAdapter(
    private val list: List<ModelTransaksiTambahan>
) : RecyclerView.Adapter<DataTambahanAdapter.ViewHolder>() {

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val idLayanan: TextView = itemView.findViewById(R.id.tvDataIDLayanan)
        val nama: TextView = itemView.findViewById(R.id.tvDataNamaLayanan)
        val harga: TextView = itemView.findViewById(R.id.tvDataHargaLayanan)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.card_data_tambahan, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = list[position]
        holder.idLayanan.text = "ID: ${item.idLayanan}"
        holder.nama.text = item.namaLayanan
        holder.harga.text = "Rp. ${item.hargaLayanan}"
    }

    override fun getItemCount(): Int = list.size
}