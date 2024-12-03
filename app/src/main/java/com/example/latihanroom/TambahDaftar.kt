package com.example.latihanroom

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.latihanroom.database.daftarBelanja
import com.example.latihanroom.database.daftarBelanjaDB
import com.example.latihanroom.helper.DateHelper.getCurrentDate
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async

class TambahDaftar : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_tambah_daftar)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        var iID : Int = 0
        var iAddEdit : Int = 0


        iID = intent.getIntExtra("id", 0)
        iAddEdit = intent.getIntExtra("addEdit", 0)

        val btnTambah = findViewById<Button>(R.id.btnTambah)
        val btnUpdate = findViewById<Button>(R.id.btnUpdate)
        val etItem = findViewById<EditText>(R.id.etItem)
        val etJumlah = findViewById<EditText>(R.id.etJumlah)

        var DB = daftarBelanjaDB.getDatabase(this)
        var tanggal = getCurrentDate()

        if (iAddEdit == 0) {
            btnTambah.visibility = View.VISIBLE
            btnUpdate.visibility = View.GONE
            etItem.isEnabled = true
        } else {
            btnTambah.visibility = View.GONE
            btnUpdate.visibility = View.VISIBLE
            etItem.isEnabled = false

            CoroutineScope(Dispatchers.IO).async {
                val item = DB.fundaftarBelanjaDAO().getItem(iID)
                etItem.setText(item.item)
                etJumlah.setText(item.jumlah)
            }
        }

        btnUpdate.setOnClickListener{
            CoroutineScope(Dispatchers.IO).async {
                DB.fundaftarBelanjaDAO().update(
                    isi_tanggal = tanggal,
                    isi_item = etItem.text.toString(),
                    isi_jumlah = etJumlah.text.toString(),
                    pilihid = iID
                )
            }
            finish()
        }

        btnTambah.setOnClickListener {
            CoroutineScope(Dispatchers.IO).async {
                DB.fundaftarBelanjaDAO().insert(
                    daftarBelanja(
                        tanggal = tanggal,
                        item = etItem.text.toString(),
                        jumlah = etJumlah.text.toString()
                    )
                )
            }
            finish()
        }
    }
}