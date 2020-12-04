package com.sc1_solo.carehub

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import kotlinx.android.synthetic.main.activity_detail.*
import kotlinx.android.synthetic.main.content_scrolling.*


class DetailActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        supportActionBar?.setDisplayShowTitleEnabled(false)
        val intent: Intent = intent
        val urlphoto: String = intent.getStringExtra("PHOTO").toString()
        name_content.text = intent.getStringExtra("NAME")
        Glide.with(this)
            .load(urlphoto)
            .apply(RequestOptions().override(700, 700))
            .into(iv_detail_photo)
        address_content.text = intent.getStringExtra("ADDRESS")
        workhours_content.text = intent.getStringExtra("HOUR")
        contact_phone_content.text = intent.getStringExtra("PHONE")
        contact_web_content.text = intent.getStringExtra("WEBSITE")
        contact_email_content.text = intent.getStringExtra("EMAIL")
    }
    override fun onSupportNavigateUp(): Boolean {
        super.onBackPressed()
        return true
    }
}