package com.gabriella15.laundry.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.gabriella15.laundry.R
import com.gabriella15.laundry.modeldata.ModelTransaksiTambahan

class pilihtambahanadapter(
    private val list: List<ModelTransaksiTambahan>,
    private val onItemClick: (ModelTransaksiTambahan) -> Unit
) : RecyclerView.Adapter<pilihtambahanadapter.ViewHolder>() {

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvNama: TextView = itemView.findViewById(R.id.tvDataNamaLayanan)
        val tvHarga: TextView = itemView.findViewById(R.id.tvDataHargaLayanan)

        fun bind(tambahan: ModelTransaksiTambahan) {
            tvNama.text = tambahan.namaLayanan
            tvHarga.text = "Harga: ${tambahan.hargaLayanan}"

            // Regular click untuk memilih tambahan (bukan long press)
            itemView.setOnClickListener {
                onItemClick(tambahan)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.datatambahan, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int = list.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(list[position])
    }
}