package com.example.kampus

import android.app.Activity
import android.content.Intent
import android.database.sqlite.SQLiteDatabase
import android.graphics.Bitmap
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import androidx.activity.result.contract.ActivityResultContracts
import java.io.ByteArrayOutputStream

class Barang_tambah : AppCompatActivity() {

    var urlgambar:Uri? = null
    var bitmapgambar:Bitmap? = null
    var iv_upload:ImageView? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.barang_tambah)

        val edt_nim:EditText = findViewById(R.id.edt_nim)
        val edt_nama:EditText = findViewById(R.id.edt_nama)
        val btn_simpan:Button = findViewById(R.id.btn_simpan)

        iv_upload = findViewById(R.id.iv_Upload)
        //iv_upload di klik buka galeri
        iv_upload?.setOnClickListener {
            val bukagaleri:Intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI)
            pilih_gambar.launch(bukagaleri)
        }

        //btn simpan ditekan
        btn_simpan.setOnClickListener {
            //dapatkan isi dari edt_nim dan edt_nama
            val isi_nim:String = edt_nim.text.toString()
            val isi_nama:String = edt_nama.text.toString()

            //dapatkan gambar yg dipilih lalu dijadikan bytearray
            val bos = ByteArrayOutputStream()
            bitmapgambar?.compress(Bitmap.CompressFormat.JPEG, 100, bos)
            val bytearraygambar = bos.toByteArray()

            //query simpan ke database
            val dbkampus:SQLiteDatabase = openOrCreateDatabase("kampus", MODE_PRIVATE,null)
           val sql = "INSERT INTO mahasiswa (nim_mahasiswa,nama_mahasiswa,foto_mahasiswa) VALUES (?,?,?)"
            val statement = dbkampus.compileStatement(sql)
            statement.clearBindings()
            statement.bindString( 1, isi_nim)
            statement.bindString(2, isi_nama)
            statement.bindBlob(3, bytearraygambar)
            statement.executeInsert()

            
            //pindah lagi dari Mahasiswa_tambah ke Mahasiswa
            val pindah:Intent = Intent(this,Home::class.java)
            startActivity(pindah)
        }
    }
    val pilih_gambar = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
       if (it.resultCode==Activity.RESULT_OK) {
           val gambardiperoleh = it.data

           if(gambardiperoleh!=null) {
               //dapatkan urlnya gambar
               urlgambar =  gambardiperoleh.data

               //konversi ke bitmap
               bitmapgambar = MediaStore.Images.Media.getBitmap(contentResolver, urlgambar)
               iv_upload?.setImageBitmap(bitmapgambar)
           }
       }
    }
}