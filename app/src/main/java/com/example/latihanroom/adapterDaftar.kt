package com.example.latihanroom

import android.content.Intent
import android.view.LayoutInflater;
import android.view.View
import android.view.ViewGroup;
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

import com.example.latihanroom.database.daftarBelanja;

class adapterDaftar (private val daftarBelanja : MutableList<daftarBelanja>):
    RecyclerView.Adapter<adapterDaftar.ListViewHolder>(){

        private lateinit var onItemClickCallback : OnItemClickCallback

        interface OnItemClickCallback {
            fun delData(dtBelanja: daftarBelanja)

            fun updateComplete(dtBelanja: daftarBelanja)
        }

        fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
            this.onItemClickCallback = onItemClickCallback
        }

    inner class ListViewHolder (itemView: View) : RecyclerView.ViewHolder(itemView) {
        var _tvItemBarang = itemView.findViewById<TextView>(R.id.tvItemBarang)
        var _tvjumlahBarang = itemView.findViewById<TextView>(R.id.tvjumlahBarang)
        var _tvTanggal = itemView.findViewById<TextView>(R.id.tvTanggal)

        var _btnEdit = itemView.findViewById<ImageButton>(R.id.btnEdit)
        var _btnDelete = itemView.findViewById<ImageView>(R.id.btnDelete)
        var _btnComplete = itemView.findViewById<ImageView>(R.id.btnComplete)
    }

    override fun onCreateViewHolder(
        parent:ViewGroup,
        viewType: Int
    ): adapterDaftar.ListViewHolder {
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.item_recycler, parent, false)
        return ListViewHolder(view)
    }

    override fun getItemCount(): Int {
        return daftarBelanja.size
    }

    fun isiData(daftar: List<daftarBelanja>) {
        daftarBelanja.clear()
        daftarBelanja.addAll(daftar)
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: adapterDaftar.ListViewHolder, position: Int) {
        var daftar = daftarBelanja[position]
        holder._tvItemBarang.text = daftar.item
        holder._tvjumlahBarang.text = daftar.jumlah
        holder._tvTanggal.text = daftar.tanggal

        holder._btnEdit.setOnClickListener {
            val intent = Intent(it.context, TambahDaftar::class.java)
            intent.putExtra("id", daftar.id)
            intent.putExtra("addEdit", 1)
            it.context.startActivity(intent)
        }

        holder._btnDelete.setOnClickListener {
            onItemClickCallback.delData(daftar)
        }

        holder._btnComplete.setOnClickListener {
            onItemClickCallback.updateComplete(daftar)
        }
    }
}
