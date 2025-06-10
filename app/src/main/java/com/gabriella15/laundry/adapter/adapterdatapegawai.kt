package com.gabriella15.laundry.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.gabriella15.laundry.Pegawai.tambahpegawai
import com.gabriella15.laundry.R
import com.gabriella15.laundry.modeldata.ModelPegawai
import com.google.firebase.database.DatabaseReference

class adapterdatapegawai(
    private val pegawaiList: ArrayList<ModelPegawai>
) : RecyclerView.Adapter<adapterdatapegawai.ViewHolder>() {

    private lateinit var appContext: Context
    private lateinit var databaseReference: DatabaseReference

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.carddatapegawai, parent, false)
        appContext = parent.context
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = pegawaiList[position]

        holder.tvIdPegawai.text = item.idpegawai
        holder.tvNamaPgw.text = item.Namapgw
        holder.tvAlamatPgw.text = item.alamatpgw
        holder.tvNoHpPgw.text = item.nohppgw
        holder.tvCabangPgw.text = item.cabangpgw

        holder.cardView.setOnClickListener {
            val intent = Intent(appContext, tambahpegawai::class.java)
            intent.putExtra("Judul", "Edit Pegawai")
            intent.putExtra("idPegawai", item.idpegawai)
            intent.putExtra("namaPegawai", item.Namapgw)
            intent.putExtra("noHPPegawai", item.nohppgw)
            intent.putExtra("alamatPegawai", item.alamatpgw)
            intent.putExtra("idcabang", item.cabangpgw)
            appContext.startActivity(intent)
        }

        // Implementasi klik tombol jika diperlukan
        holder.btHubungiPgw.setOnClickListener {
            // Misal: buka dialer
            val intent = Intent(Intent.ACTION_DIAL)
            intent.data = android.net.Uri.parse("tel:${item.nohppgw}")
            appContext.startActivity(intent)
        }

        holder.btLihatPgw.setOnClickListener {
            // Tambahkan aksi sesuai kebutuhan
        }
    }

    override fun getItemCount(): Int = pegawaiList.size

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val cardView: View = itemView.findViewById(R.id.cvpegawai)
        val tvIdPegawai: TextView = itemView.findViewById(R.id.idpegawai)
        val tvNamaPgw: TextView = itemView.findViewById(R.id.Namapgw)
        val tvAlamatPgw: TextView = itemView.findViewById(R.id.alamatpgw)
        val tvNoHpPgw: TextView = itemView.findViewById(R.id.nohppgw)
        val tvCabangPgw: TextView = itemView.findViewById(R.id.cabangpgw)
        val btHubungiPgw: Button = itemView.findViewById(R.id.hubungipgw)
        val btLihatPgw: Button = itemView.findViewById(R.id.lihatpgw)
    }
}
