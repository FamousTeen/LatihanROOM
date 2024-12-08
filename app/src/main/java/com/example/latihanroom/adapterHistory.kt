package com.example.latihanroom

import android.content.Intent
import android.view.LayoutInflater;
import android.view.View
import android.view.ViewGroup;
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

import com.example.latihanroom.database.historyBelanja

class adapterHistory (private val historyBelanja : MutableList<historyBelanja>):
    RecyclerView.Adapter<adapterHistory.ListViewHolder>(){

    private lateinit var onItemClickCallback : OnItemClickCallback

    interface OnItemClickCallback {
//        fun delData(dtBelanja: daftarBelanja)
//
//        fun updateComplete(dtBelanja: daftarBelanja)
    }

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    inner class ListViewHolder (itemView: View) : RecyclerView.ViewHolder(itemView) {
        var _tvItemBarang = itemView.findViewById<TextView>(R.id.tvItemBarang)
        var _tvjumlahBarang = itemView.findViewById<TextView>(R.id.tvjumlahBarang)
        var _tvTanggal = itemView.findViewById<TextView>(R.id.tvTanggal)
        var _statusBelanja = itemView.findViewById<TextView>(R.id.statusBelanja)
    }

    override fun onCreateViewHolder(
        parent:ViewGroup,
        viewType: Int
    ): adapterHistory.ListViewHolder {
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.history_item, parent, false)
        return ListViewHolder(view)
    }

    override fun getItemCount(): Int {
        return historyBelanja.size
    }

    fun isiData(history: List<historyBelanja>) {
        historyBelanja.clear()
        historyBelanja.addAll(history)
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: adapterHistory.ListViewHolder, position: Int) {
        var history = historyBelanja[position]
        holder._tvItemBarang.text = history.item
        holder._tvjumlahBarang.text = history.jumlah
        holder._tvTanggal.text = history.tanggal
        holder._statusBelanja.text = history.status
    }
}
