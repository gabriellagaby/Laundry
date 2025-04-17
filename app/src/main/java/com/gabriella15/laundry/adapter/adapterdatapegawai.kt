package com.gabriella15.laundry.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.gabriella15.laundry.R
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.gabriella15.laundry.Pegawai.tambahpegawai
import com.gabriella15.laundry.modeldata.ModelPegawai
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.core.Context


class adapterdatapegawai(
    private val pegawaiList: ArrayList<ModelPegawai>
) : RecyclerView.Adapter<adapterdatapegawai.ViewHolder>() {
    lateinit var appContext: Context
    lateinit var databaseReference: DatabaseReference
    override  fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.carddatapegawai, parent, false)
        appContext = parent.context
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val item =pegawaiList [position];
        holder.tvidpegawai.text = item.idpegawai
        holder.Namapgw.text = item.Namapgw
        holder.alamatpgw.text = item.alamatpgw
        holder.nohppgw.text =item.nohppgw
        holder.cabangpgw.text =item.cabangpgw
        holder.cvpegawai.setOnClickListener {
        }
        holder.bthubungipgw.setOnClickListener {
        }
        holder.btLihatpgw.setOnClickListener {
        }
    }
    holder.cvpegawai.setOnClickListener{
        val intent = Intent(appContext, tambahpegawai::class.java)
        intent.putExtra("Judul", "Edit Pegawai")
        intent.putExtra("idPegawai", item.idpegawai)
        intent.putExtra("namaPegawai", item.namapegawai)
        intent.putExtra("noHPPegawai", item.noHPegawai)
        intent.putExtra("alamatPegawai", item.alamatpegawai)
        intent.putExtra("idcabang", item.idcabang)
        appContext .startActivity(intent)
    }
    override fun getItemCount(): Int {
        return pegawaiList.size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val cvpegawai = itemView.findViewById<View>(R.id.cvpegawai)
        val tvidpegawai = itemView.findViewById<TextView>(R.id.idpegawai)
        val Namapgw = itemView.findViewById<TextView>(R.id.Namapgw)
        val alamatpgw = itemView.findViewById<TextView>(R.id.alamatpgw)
        val nohppgw = itemView.findViewById<TextView>(R.id.nohppgw)
        val cabangpgw = itemView.findViewById<TextView>(R.id.cabangpgw)
        val bthubungipgw = itemView.findViewById<Button>(R.id.hubungipgw)
        val btLihatpgw = itemView.findViewById<Button>(R.id.lihatpgw)

    }
}

