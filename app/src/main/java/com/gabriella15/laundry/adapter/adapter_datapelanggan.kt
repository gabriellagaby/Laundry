package com.gabriella15.laundry.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.gabriella15.laundry.R
import com.gabriella15.laundry.modeldata.ModelPelanggan


class adapter_datapelanggan(
    private val pelangganList: ArrayList<ModelPelanggan>
) : RecyclerView.Adapter<adapter_datapelanggan.ViewHolder>() {
    override  fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.carddatapelanggan, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

            val item =pelangganList [position];
            holder.tvidpelanggan.text = item.idpelanggan
            holder.namapel.text = item.namapel
            holder.alamatpel.text = item.alamatpel
            holder.nohppel.text =item.nohppel
            holder.cabangpel.text =item.cabangpel
            holder.cvpelanggan.setOnClickListener {
            }
            holder.bthubungi.setOnClickListener {
            }
            holder.btLihat.setOnClickListener {
            }
        }

    override fun getItemCount(): Int {
        return pelangganList.size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val cvpelanggan = itemView.findViewById<View>(R.id.cvpelanggan)
        val tvidpelanggan = itemView.findViewById<TextView>(R.id.tvidpelanggan)
        val namapel = itemView.findViewById<TextView>(R.id.namapel)
        val alamatpel = itemView.findViewById<TextView>(R.id.alamatpel)
        val nohppel = itemView.findViewById<TextView>(R.id.nohppel)
        val cabangpel = itemView.findViewById<TextView>(R.id.cabangpel)
        val bthubungi = itemView.findViewById<Button>(R.id.bthubungi)
        val btLihat = itemView.findViewById<Button>(R.id.btlihat)

    }
}

