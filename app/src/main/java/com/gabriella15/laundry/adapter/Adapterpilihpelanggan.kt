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
import com.gabriella15.laundry.datatransaksi


class Adapterpilihpelanggan(
    private val context: Context,
    private val pelangganList: (Any) -> Unit
) :
    RecyclerView.Adapter<Adapterpilihpelanggan.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.cardpilihpelanggan, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val nomor = position + 1
        val item = pelangganList[position]

        holder.tvID.text = "[nomor]"
        holder.tvNama.text = item.namapel
        holder.tvAlamat.text = "Alamat: {item.alamatPelanggan}"
        holder.tvNoHP.text = "No HP: {item.noHPPelanggan}"

        holder.cvCARD.setOnClickListener {
            val intent = Intent(context, datatransaksi::class.java)
            intent.putExtra("idPelanggan", item.idpelanggan)
            intent.putExtra("nama", item.namapel)
            intent.putExtra("noHP", item.nohppel)

            (context as Activity).setResult(Activity.RESULT_OK, intent)
            context.finish()
        }
    }  

    override fun getItemCount(): Int {
        return pelangganList.size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val cvCARD: CardView = itemView.findViewById(R.id.cvpilpelanggan)
        val tvID: TextView = itemView.findViewById(R.id.tvidpilpel)
        val tvNama: TextView = itemView.findViewById(R.id.tvnamapilpel)
        val tvAlamat: TextView = itemView.findViewById(R.id.tvalamatpilpel)
        val tvNoHP: TextView = itemView.findViewById(R.id.tvnohppilpel)
    }
}