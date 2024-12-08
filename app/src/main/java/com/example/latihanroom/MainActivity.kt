package com.example.latihanroom

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.latihanroom.database.daftarBelanja
import com.example.latihanroom.database.daftarBelanjaDB
import com.example.latihanroom.database.historyBelanja
import com.example.latihanroom.database.historyBelanjaDB
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.withContext

class MainActivity : AppCompatActivity() {
    private lateinit var DB: daftarBelanjaDB
    private lateinit var DB2: historyBelanjaDB
    private lateinit var adapterDaftar : adapterDaftar
    private var arDaftar : MutableList<daftarBelanja> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        DB2 = historyBelanjaDB.getDatabase(this)
        DB = daftarBelanjaDB.getDatabase(this)
        val _fabAdd = findViewById<FloatingActionButton>(R.id.fabAdd)
        val _fabHistory = findViewById<FloatingActionButton>(R.id.fabHistory)
        _fabHistory.setOnClickListener {
            val intent = Intent(this, HistoryBelanja::class.java)
            startActivity(intent)
        }
        _fabAdd.setOnClickListener {
            val intent = Intent(this, TambahDaftar::class.java)
            startActivity(intent)
        }
        adapterDaftar = adapterDaftar(arDaftar)

        adapterDaftar.setOnItemClickCallback(object : adapterDaftar.OnItemClickCallback {
            override fun delData(dtBelanja: daftarBelanja) {
                CoroutineScope(Dispatchers.IO).async {
                    DB.fundaftarBelanjaDAO().delete(dtBelanja)
                    val daftar = DB.fundaftarBelanjaDAO().selectAll()
                    withContext(Dispatchers.Main) {
                        adapterDaftar.isiData(daftar)
                    }
                }
            }

            override fun updateComplete(dtBelanja: daftarBelanja) {
                CoroutineScope(Dispatchers.IO).async {
                    DB2.funhistoryBelanjaDAO().insert(
                        historyBelanja(
                            tanggal = dtBelanja.tanggal,
                            item = dtBelanja.item,
                            jumlah = dtBelanja.jumlah,
                            status = "Selesai"
                        )
                    )
                    DB.fundaftarBelanjaDAO().delete(dtBelanja)
                    val daftar = DB.fundaftarBelanjaDAO().selectAll()
                    withContext(Dispatchers.Main) {
                        adapterDaftar.isiData(daftar)
                    }
                }
            }
        })

        var _rvDaftar = findViewById<RecyclerView>(R.id.rvNotes)
        _rvDaftar.layoutManager = LinearLayoutManager(this)
        _rvDaftar.adapter = adapterDaftar
    }

    override fun onStart() {
        super.onStart()
        CoroutineScope(Dispatchers.Main).async {
            val daftarBelanja = DB.fundaftarBelanjaDAO().selectAll()
            adapterDaftar.isiData(daftarBelanja)
            Log.d("daftar ROOM", daftarBelanja.toString())
        }
    }
}