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
        "Rumah Sakit Umum Wishnu Husada",
        "Klinik Brayan Sehat (KBS)",
            "Rumah Sakit Islam Purwokerto",
            "RS Ananda Purwokerto"
    )
    private val avgOpenhour = arrayOf(
            "Buka 24 Jam",
            "Buka 24 Jam",
            "8AM–8:45PM",
            "Buka 24 Jam",
            "Buka 24 Jam"
    )
    private val openHour = arrayOf(
        "Senin - Minggu  : Buka 24 Jam\n",
        "Senin - Minggu  : Buka 24 Jam\n",
        "Senin - Jumat : 8AM–8:45PM\n"+
                 "Sabtu  : 8AM–1PM\n" +
                 "Minggu : Tutup",
        "Senin - Minggu  : Buka 24 Jam\n",
        "Senin - Minggu  : Buka 24 Jam\n",
    )
    private val address = arrayOf(
        "Jl. Dr. Gumbreg No.1, Kebontebu, Berkoh, Kec. Purwokerto Sel., Kabupaten Banyumas, Jawa Tengah 53146",
        "JL. Raya Notog, No. 200 RT. 03 RW. 01, Salamudin, Notog, Kec. Patikraja, Kabupaten Banyumas, Jawa Tengah 53176",
            "Karangbawang, Purwokerto Kulon, Kec. Purwokerto Sel., Kabupaten Banyumas, Jawa Tengah 53141",
            "Jl. Mashuri No.39, Kalibogor, Rejasari, Kec. Purwokerto Bar., Kabupaten Banyumas, Jawa Tengah 53134",
            "Jl. Pemuda No.30, Kober, Kec. Purwokerto Bar., Kabupaten Banyumas, Jawa Tengah 53132"
    )
    private val phone = arrayOf(
        "+62281632708",
        "+622816844850",
            "+622817772941",
            "+62281630019",
            "+62281636417"
    )
    private val email = arrayOf(
        "email@admin.com",
        "email@admin.com",
            "email@admin.com",
            "email@admin.com",
            "email@admin.com"

    )
    private val website = arrayOf(
        "http://rsmargono.jatengprov.go.id/",
        "-",
            "https://klinik-brayan-sehat-kbs.business.site/",
            "http://rsipurwokerto.co.id/",
            "https://rsananda.co.id/"

    )
    private val photo = arrayOf(
        "https://www.rsmargono.go.id/assets/public/images/building/building_1.jpg",
        "https://static.guesehat.com/static/directories_thumb/21170_Rumah_Sakit_Wisnu_Husada.jpg",
            "https://i.ibb.co/hyZJPYv/klinik-brayan-sehat.jpg",
            "https://www.garnesia.com/images/vendor/v4870.jpg",
            "https://3.bp.blogspot.com/-5pQoDyu1L54/WODy61EhBvI/AAAAAAAAFTk/mp0n0g-89LMN7DZrMW7p40gebjf2DkgPACLcB/s1600/rumahsakitanandapurwokerto.jpg"
    )
    private val lat = arrayOf(
        "-7.436388552290917",
        "-7.482656144713493",
            "-7.431008860733857",
            "-7.418509565324121",
            "-7.413848268070681"
    )
    private val lang = arrayOf(
        "109.26749203989951",
        "109.21422276627231",
            "109.24412046930946",
            "109.21265512596834",
            "109.22414496305355"
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