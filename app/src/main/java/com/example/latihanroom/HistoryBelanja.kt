package com.example.latihanroom

import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.latihanroom.database.daftarBelanjaDB
import com.example.latihanroom.database.historyBelanja
import com.example.latihanroom.database.historyBelanjaDB
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async

class HistoryBelanja : AppCompatActivity() {
    private lateinit var DB: historyBelanjaDB
    private lateinit var adapterHistory : adapterHistory
    private var arHistory : MutableList<historyBelanja> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_history_belanja)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        DB = historyBelanjaDB.getDatabase(this)
        adapterHistory = adapterHistory(arHistory)

        var _rvHistory = findViewById<RecyclerView>(R.id.rvNotes)
        _rvHistory.layoutManager = LinearLayoutManager(this)
        _rvHistory.adapter = adapterHistory
    }

    override fun onStart() {
        super.onStart()
        CoroutineScope(Dispatchers.Main).async {
            val historyBelanja = DB.funhistoryBelanjaDAO().selectAll()
            adapterHistory.isiData(historyBelanja)
            Log.d("history ROOM", historyBelanja.toString())
        }
    }
}