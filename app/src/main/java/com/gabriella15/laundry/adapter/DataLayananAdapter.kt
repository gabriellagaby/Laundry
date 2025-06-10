package com.gabriella15.laundry.adapter

import android.app.AlertDialog
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.gabriella15.laundry.R
import com.gabriella15.laundry.modeldata.ModelLayanan

class DataLayananAdapter(
    private val listLayanan: ArrayList<ModelLayanan>,
    private val onEditClick: ((ModelLayanan, Int) -> Unit)? = null,
    private val onDeleteClick: ((ModelLayanan, Int) -> Unit)? = null,
    private val onViewClick: ((ModelLayanan, Int) -> Unit)? = null
) : RecyclerView.Adapter<DataLayananAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.cardlayanan, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = listLayanan[position]
        holder.tvDataIDLayanan.text = item.Idlyn ?: ""
        holder.tvNama.text = item.namalyn ?: ""
        holder.tvHarga.text = item.hargalyn ?: ""
        holder.tvCabang.text = "Cabang ${item.cabanglyn ?: "Tidak Ada Cabang"}"

        // Click listener untuk card view (edit/sunting)
        holder.itemView.setOnClickListener {
            onEditClick?.invoke(item, position)
        }

        // Click listener untuk tombol hapus
        holder.btnHapus?.setOnClickListener {
            showDeleteConfirmation(holder.itemView, item, position)
        }

        // Click listener untuk tombol EDT (dialog_mod_layanan)
        holder.btnSunting?.setOnClickListener {
            onViewClick?.invoke(item, position)
        }
    }

    private fun showDeleteConfirmation(view: View, item: ModelLayanan, position: Int) {
        AlertDialog.Builder(view.context)
            .setTitle("Konfirmasi Hapus")
            .setMessage("Apakah Anda yakin ingin menghapus layanan \"${item.namalyn}\"?")
            .setPositiveButton("Hapus") { _, _ ->
                onDeleteClick?.invoke(item, position)
            }
            .setNegativeButton("Batal", null)
            .show()
    }

    // Fungsi untuk menghapus item dari list
    fun removeItem(position: Int) {
        if (position >= 0 && position < listLayanan.size) {
            listLayanan.removeAt(position)
            notifyItemRemoved(position)
            notifyItemRangeChanged(position, listLayanan.size)
        }
    }

    // Fungsi untuk update item setelah edit
    fun updateItem(position: Int, updatedItem: ModelLayanan) {
        if (position >= 0 && position < listLayanan.size) {
            listLayanan[position] = updatedItem
            notifyItemChanged(position)
        }
    }

    override fun getItemCount(): Int {
        return listLayanan.size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvDataIDLayanan: TextView = itemView.findViewById(R.id.tvDataIDLayanan)
        val tvNama: TextView = itemView.findViewById(R.id.tvDataNamaLayanan)
        val tvHarga: TextView = itemView.findViewById(R.id.tvDataHargaLayanan)
        val tvCabang: TextView = itemView.findViewById(R.id.tv_Cabang)
        val tvTerdaftar: TextView = itemView.findViewById(R.id.tv_Terdaftar)

        // Tombol hapus dan lihat
        val btnHapus: Button? = itemView.findViewById(R.id.btn_delete_layanan)
        val btnSunting: Button? = itemView.findViewById(R.id.btn_edit_layanan)
    }
}