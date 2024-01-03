package com.example.kampus

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class Barang_item (val ini:Context, val id:MutableList<String>, val nama:MutableList<String>, val nim:MutableList<String>, val foto:MutableList<Bitmap>) : RecyclerView.Adapter<Barang_item.ViewHolder>(){
    //
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int):ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.barang_item, parent, false)
        return ViewHolder(view)
    }
    class ViewHolder(ItemView: View) : RecyclerView.ViewHolder(ItemView) {
        val txt_nama:TextView= itemView.findViewById(R.id.txt_nama)
        val txt_nim:TextView= itemView.findViewById(R.id.txt_nim)
        val iv_foto:ImageView= itemView.findViewById(R.id.iv_foto)
        val btn_hapus:Button = itemView.findViewById(R.id.btn_hapus)
        val btn_ubah:Button = itemView.findViewById(R.id.btn_ubah)
    }

    override fun getItemCount(): Int {
        return nama.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.txt_nama.text= nama.get(position)
        holder.txt_nim.text= nim.get(position)
        holder.iv_foto.setImageBitmap(foto.get(position))

        //btn hapus ditekan
        holder.btn_hapus.setOnClickListener {
            //dapatkan id_mahasiswa sesuai urutan dari tombol yang ditekan
            val id_mahasiswa_terpilih:String = id.get(position)

            //larikan ke activity Mahasiswa_hapus
            val pindah:Intent = Intent(ini, Barang_hapus::class.java)
            pindah.putExtra("id_mahasiswa_terpilih", id_mahasiswa_terpilih)
            ini.startActivity(pindah)
        }
        //btn ubah ditekan
        holder.btn_ubah.setOnClickListener {
            //dapatkan id_mahasiswa sesuai urutan dai tombol yang ditekan
            val id_mahasiswa_terpilih:String = id.get(position)
            //larikan ke activity Mahasiswa_hapus
            val pindah:Intent = Intent(ini, Barang_ubah::class.java)
            pindah.putExtra("id_mahasiswa_terpilih", id_mahasiswa_terpilih)
            ini.startActivity(pindah)
        }
    }
}