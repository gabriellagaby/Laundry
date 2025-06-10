package com.gabriella15.laundry.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.gabriella15.laundry.R
import com.gabriella15.laundry.modeldata.ModelCabang

class adaptercabang(
    private val cabangList: ArrayList<ModelCabang>,
    private val onViewClick: (ModelCabang) -> Unit,
    private val onItemClick: (ModelCabang) -> Unit
) : RecyclerView.Adapter<adaptercabang.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvIdCabang: TextView = itemView.findViewById(R.id.idTambahCabang)
        val tvNamaCabang: TextView = itemView.findViewById(R.id.NamaCabang)
        val tvManagerCabang: TextView = itemView.findViewById(R.id.Managercabang)
        val tvAlamatCabang: TextView = itemView.findViewById(R.id.alamatcabang)
        val btnView: Button = itemView.findViewById(R.id.lihatcabang)
        val btnContact: Button = itemView.findViewById(R.id.hubungi)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.carddatacabang, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val cabang = cabangList[position]
        holder.tvIdCabang.text = cabang.idCabang ?: ""
        holder.tvNamaCabang.text = cabang.namaCabang ?: ""
        holder.tvManagerCabang.text = cabang.ManagerCabang ?: ""
        holder.tvAlamatCabang.text = cabang.alamatCabang ?: ""
        holder.btnView.setOnClickListener { onViewClick(cabang) }
        holder.btnContact.setOnClickListener { onItemClick(cabang) }
    }

    override fun getItemCount(): Int = cabangList.size
}