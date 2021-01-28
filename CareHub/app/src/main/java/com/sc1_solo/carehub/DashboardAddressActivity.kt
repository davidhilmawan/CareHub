package com.sc1_solo.carehub

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.sc1_solo.carehub.adapter.AddressAdapter
import com.sc1_solo.carehub.data.Address
import com.sc1_solo.carehub.databinding.ActivityDashboardAddressBinding
import com.sc1_solo.carehub.helper.REQUEST_ADD
import com.sc1_solo.carehub.helper.REQUEST_UPDATE
import com.sc1_solo.carehub.helper.RESULT_ADD
import com.sc1_solo.carehub.helper.RESULT_DELETE
import com.sc1_solo.carehub.helper.RESULT_UPDATE
import kotlinx.android.synthetic.main.activity_dashboard_address.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class DashboardAddressActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    private lateinit var firestore: FirebaseFirestore
    private lateinit var adapter: AddressAdapter
    private lateinit var binding: ActivityDashboardAddressBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDashboardAddressBinding.inflate(layoutInflater)
        setContentView(binding.root)
        firestore = Firebase.firestore
        auth = Firebase.auth
        supportActionBar?.title = "Address"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        binding.rvAddress.layoutManager = LinearLayoutManager(this)
        binding.rvAddress.setHasFixedSize(true)
        adapter = AddressAdapter(this)
    binding.fabAdd.setOnClickListener {
            val intent = Intent(this@DashboardAddressActivity, AddressAddUpdateActivity::class.java)
            startActivityForResult(intent, REQUEST_ADD)
        }
        loadAddress()
    }
    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        loadAddress()
    }

    private fun loadAddress() {
        GlobalScope.launch(Dispatchers.Main) {
            progressbar.visibility = View.VISIBLE
            val addressList = ArrayList<Address>()
            val currentUser = auth.currentUser
            firestore.collection("address")
                .whereEqualTo("uid", currentUser?.uid)
                .get()
                .addOnSuccessListener { result ->
                    progressbar.visibility = View.INVISIBLE
                    for (document in result) {
                        val id = document.id
                        val title = document.get("title").toString()
                        val address = document.get("address").toString()
                        addressList.add(Address(id, title, address))
                    }
                    if (addressList.size > 0) {
                        binding.rvAddress.adapter = adapter
                        adapter.listAddress = addressList
                    } else {
                        adapter.listAddress = ArrayList()
                        showSnackbarMessage("Tidak ada data saat ini")
                    }
                }
                .addOnFailureListener { exception ->
                    progressbar.visibility = View.INVISIBLE
                    Toast.makeText(
                        this@DashboardAddressActivity, "Error adding document",Toast.LENGTH_SHORT
                    ).show()
                }
        }
    }

    private fun showSnackbarMessage(message: String) {
        Snackbar.make(binding.rvAddress, message, Snackbar.LENGTH_SHORT).show()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (data != null) {
            when (requestCode) {
                REQUEST_ADD -> if (resultCode == RESULT_ADD) {
                    loadAddress()
                    showSnackbarMessage("Satu item berhasil ditambahkan")
                }
                REQUEST_UPDATE ->
                    when (resultCode) {
                        RESULT_UPDATE -> {
                            loadAddress()
                            showSnackbarMessage("Satu item berhasil diubah")
                        }
                        RESULT_DELETE -> {
                            loadAddress()
                            showSnackbarMessage("Satu item berhasil dihapus")
                        }
                    }
            }
        }
    }
}