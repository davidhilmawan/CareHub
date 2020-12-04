package com.sc1_solo.carehub.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import androidx.recyclerview.widget.RecyclerView
import com.sc1_solo.carehub.DetailActivity
import com.sc1_solo.carehub.MapsActivity
import com.sc1_solo.carehub.R

class CardViewDataAdapter: RecyclerView.Adapter<CardViewDataAdapter.ViewHolder>() {
    private val hospitalName = arrayOf(
        "RSUD Prof. Dr. Margono Soekarjo",
        "Rumah Sakit Umum Wishnu Husada")
    private val avgOpenhour = arrayOf(
        "Buka 24 Jam",
        "Buka 24 Jam"
    )
    private val openHour = arrayOf(
        "Senin  : Buka 24 Jam\n" +
                "Selasa : Buka 24 Jam\n" +
                "Rabu   : Buka 24 Jam\n" +
                "Kamis  : Buka 24 Jam\n" +
                "Jumat  : Buka 24 Jam\n" +
                "Sabtu  : Buka 24 Jam\n" +
                "Minggu : Buka 24 Jam",
        "Senin  : Buka 24 Jam\n" +
                "Selasa : Buka 24 Jam\n" +
                "Rabu   : Buka 24 Jam\n" +
                "Kamis  : Buka 24 Jam\n" +
                "Jumat  : Buka 24 Jam\n" +
                "Sabtu  : Buka 24 Jam\n" +
                "Minggu : Buka 24 Jam",
    )
    private val address = arrayOf(
        "Jl. Dr. Gumbreg No.1, Kebontebu, Berkoh, Kec. Purwokerto Sel., Kabupaten Banyumas, Jawa Tengah 53146",
        "JL. Raya Notog, No. 200 RT. 03 RW. 01, Salamudin, Notog, Kec. Patikraja, Kabupaten Banyumas, Jawa Tengah 53176"
    )
    private val phone = arrayOf(
        "+62281632708",
        "+622816844850"
    )
    private val email = arrayOf(
        "email@admin.com",
        "email@admin.com"
    )
    private val website = arrayOf(
        "http://rsmargono.jatengprov.go.id/",
        "-"
    )
    private val photo = arrayOf(
        "https://www.rsmargono.go.id/assets/public/images/building/building_1.jpg",
        "https://static.guesehat.com/static/directories_thumb/21170_Rumah_Sakit_Wisnu_Husada.jpg"
    )
    private val lat = arrayOf(
        "-7.436388552290917",
        "-7.482656144713493"
    )
    private val lang = arrayOf(
        "109.26749203989951",
        "109.21422276627231"
    )

    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): ViewHolder {
        val v = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.item_cardview, viewGroup, false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, i: Int) {
        Glide.with(viewHolder.itemView)
            .load(photo[i])
            .apply(RequestOptions().override(100, 100))
            .into(viewHolder.itemPhoto)
        viewHolder.hospitalname.text = hospitalName[i]
        viewHolder.avgopenHour.text = avgOpenhour[i]
    }

    override fun getItemCount(): Int {
        return hospitalName.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var hospitalname: TextView = itemView.findViewById(R.id.name_place)
        var avgopenHour: TextView = itemView.findViewById(R.id.open_hour_place)
        var itemPhoto: ImageView = itemView.findViewById(R.id.place_photo)
        var btnLocate: Button = itemView.findViewById(R.id.locate_button)
        init {
            itemView.setOnClickListener {
                var position: Int = adapterPosition
                var urlphoto: String = photo[position]
                var openhour: String = openHour[position]
                var hAddress: String = address[position]
                var phoneNumber: String = phone[position]
                var email: String = email[position]
                var website: String = website[position]
                val context = itemView.context
                val intent = Intent(context, DetailActivity::class.java).apply {
                    putExtra("NAME", hospitalname.text)
                    putExtra("HOUR", openhour)
                    putExtra("ADDRESS", hAddress)
                    putExtra("PHOTO", urlphoto.toString())
                    putExtra("PHONE", phoneNumber)
                    putExtra("WEBSITE", website)
                    putExtra("EMAIL", email)
                }
                context.startActivity(intent)
            }
            btnLocate.setOnClickListener {
                var position: Int = adapterPosition
                var dataLat: Double = lat[position].toDouble()
                var dataLang: Double = lang[position].toDouble()
                val context = itemView.context
                val intentmap = Intent(context, MapsActivity::class.java).apply {
                    putExtra("NAME", hospitalname.text)
                    putExtra("LAT",dataLat)
                    putExtra("LANG", dataLang)
                }
                context.startActivity(intentmap)
            }
        }
    }
}