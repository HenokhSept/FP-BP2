package com.example.kampus

import android.content.Intent
import android.content.SharedPreferences
import android.database.sqlite.SQLiteDatabase
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast

class Login : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.login)

        val edt_email:EditText = findViewById(R.id.edt_email)
        val edt_password:EditText = findViewById(R.id.edt_password)
        val btn_login:Button = findViewById(R.id.btn_login)

       btn_login.setOnClickListener {
           //dapatkan isi email dan passwword
           val  isi_email:String = edt_email.text.toString()
           val  isi_password:String = edt_password.text.toString()

            //koneksi ke db
           val dbkampus: SQLiteDatabase = openOrCreateDatabase("kampus", MODE_PRIVATE, null)

           //cek ke tabel user
           val query = dbkampus.rawQuery("SELECT * FROM user WHERE email_user='$isi_email' AND password_user='$isi_password'",null)
           val cek = query.moveToNext()

           Log.d("apa", cek.toString())

           //jika ada user dengan email dan password itu, maka true
           if (cek){
               //simpan data si pelogin ke sharedpreference
               val id= query.getString(0)
               val email = query.getString(1)
               val password = query.getString(2)
               val nama = query.getString(3)

               val session:SharedPreferences = getSharedPreferences("user", MODE_PRIVATE)
               val buattiket = session.edit()
               buattiket.putString("id_user", id)
               buattiket.putString("email_user", email)
               buattiket.putString("password_user", password)
               buattiket.putString("nama_user", nama)
               buattiket.commit()

               //pindah ke halaman mahasiswa
               val pindah:Intent = Intent(this, Home::class.java)
               startActivity(pindah)
           }else{
               Toast.makeText(this,"Email atau password salah!", Toast.LENGTH_LONG).show()
           }
       }
    }
}