package com.example.kampus

import android.content.Intent
import android.database.sqlite.SQLiteDatabase
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText

class Barang_ubah : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.barang_ubah)
        //dapatkan id_mahasiswa_terpilih yang dibawah saat Klik tombol ubah
        val id_mahasiswa_terpilih:String = intent.getStringExtra("id_mahasiswa_terpilih").toString()

        //koneksi ke db
        val dbkampus:SQLiteDatabase = openOrCreateDatabase("kampus", MODE_PRIVATE,null)
        val ambil=dbkampus.rawQuery("SELECT * FROM mahasiswa WHERE id_mahasiswa=$id_mahasiswa_terpilih' ",null)
        ambil.moveToNext()
            val isi_nim:String = ambil.getString(1)
            val isi_nama:String = ambil.getString(2)

            val edt_nim:EditText = findViewById(R.id.edt_nim)
            val edt_nama:EditText = findViewById(R.id.edt_nama)
            val btn_simpan:EditText = findViewById(R.id.edt_nama)

            edt_nim.setText(isi_nim)
            edt_nama.setText(isi_nama)

            //btn_simpan ditekan
        btn_simpan.setOnClickListener {
            //dapatkan inputan yang baru dari kotak edt_nim,edt_nama
            val nim_baru:String = edt_nim.text.toString()
            val  nama_baru:String = edt_nama.text.toString()

            val ubah=dbkampus.rawQuery("UPDATE mahasiswa SET nim_mahasiswa='$nim_baru',$ nama mahasiswa='$nama_baru'WHERE id_mahasiswa='$id_mahasiswa_terpilih'",null)
            ubah.moveToNext()

            val pindah:Intent = Intent(this,Home::class.java)
            startActivity(pindah)
        }
    }
}