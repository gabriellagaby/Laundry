package com.gabriella15.laundry.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.gabriella15.laundry.R
import com.gabriella15.laundry.modeldata.ModelLayanan

class pilihlayananadapter(
    private val list: List<ModelLayanan>,
    private val onItemClick: (ModelLayanan) -> Unit
) : RecyclerView.Adapter<pilihlayananadapter.ViewHolder>() {
    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvNama: TextView = itemView.findViewById(R.id.tvDataNamaLayanan)
        val tvID: TextView = itemView.findViewById(R.id.tvDataIDLayanan)
        val tvHarga: TextView = itemView.findViewById(R.id.tvDataHargaLayanan)

        fun bind(layanan: ModelLayanan) {
            tvNama.text = layanan.namalyn
            tvID.text = "ID: ${layanan.Idlyn}"
            tvHarga.text = "Harga: ${layanan.hargalyn}"

            itemView.setOnClickListener {
                onItemClick(layanan)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.card_data_transaksi_layanan, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int = list.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(list[position])
    }
}