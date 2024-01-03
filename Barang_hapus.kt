package com.example.kampus

import android.content.Intent
import android.database.sqlite.SQLiteDatabase
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class Barang_hapus : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.barang_hapus)


        //tangkap dulu id mahasiswa terpilih karena dia yang mau kita hapus
        val id_mahasiswa_terpilih: String =
            intent.getStringExtra("id_mahasiswa_terpilih").toString()

        //konek ke db
        val dbkampus: SQLiteDatabase = openOrCreateDatabase("kampus", MODE_PRIVATE, null)

        //query hapus data
        val query = dbkampus.rawQuery(
            "DELETE FROM mahasiswa WHERE id_mahasiswa='$id_mahasiswa_terpilih",
            null
        )
        query.moveToNext()

        val pindah: Intent = Intent(this, Home::class.java)
        startActivity(pindah)
    }
}