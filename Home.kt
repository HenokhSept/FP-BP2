package com.example.kampus

import android.content.Intent
import android.content.SharedPreferences
import android.database.sqlite.SQLiteDatabase
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import java.io.ByteArrayInputStream
import java.lang.Exception

class Home : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.home)

        val rv_mahasiswa:RecyclerView = findViewById(R.id.rv_mahasiswa)

        val txt_nama_user:TextView = findViewById(R.id.txt_nama_user)

        //dapatkan nama_user yg login dari session
        val tiket:SharedPreferences = getSharedPreferences("user", MODE_PRIVATE)
        val nama_user:String = tiket.getString("nama_user", null).toString()
        txt_nama_user.text = nama_user

        //btn logout
        val btn_logout:Button = findViewById(R.id.btn_logout)
        btn_logout.setOnClickListener {
            ///menghapus session/tiket user
            val edittiket = tiket.edit()
            edittiket.clear()
            edittiket.commit()

            //pindah ke halaman login
            val keluar:Intent = Intent(this, Login::class.java)
            startActivity(keluar)
            finish()
        }


        val id:MutableList<String> = mutableListOf()
        val nama:MutableList<String> = mutableListOf()
        val nim:MutableList<String> = mutableListOf()
        val foto:MutableList<Bitmap> = mutableListOf()

        val dbkampus:SQLiteDatabase = openOrCreateDatabase("kampus", MODE_PRIVATE, null)

        val gali_mahasiswa = dbkampus.rawQuery("SELECT * FROM mahasiswa", null)
        while (gali_mahasiswa.moveToNext())
        {

            try {
                val bis = ByteArrayInputStream(gali_mahasiswa.getBlob(3))
                val gambarbitmap:Bitmap = BitmapFactory.decodeStream(bis)
                foto.add(gambarbitmap)
            } catch (e:Exception){
                val gambarbitmap:Bitmap = BitmapFactory.decodeResource(this.resources,R.drawable.noimages)
                foto.add(gambarbitmap)
            }


            id.add(gali_mahasiswa.getString(0))
            nim.add(gali_mahasiswa.getString(1))
            nama.add(gali_mahasiswa.getString(2))
        }

        val ni = Barang_item(this,id, nama, nim, foto)
        rv_mahasiswa.adapter = ni
        rv_mahasiswa.layoutManager = GridLayoutManager(this,2)

        val btn_tambah:Button = findViewById(R.id.btn_tambah)
        btn_tambah.setOnClickListener {
            val pindah:Intent= Intent(this,Barang_tambah::class.java)
            startActivity(pindah)
        }
    }
}