package com.sc1_solo.carehub

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.sc1_solo.carehub.data.Address
import com.sc1_solo.carehub.databinding.ActivityAddressAddUpdateBinding
import com.sc1_solo.carehub.helper.ALERT_DIALOG_CLOSE
import com.sc1_solo.carehub.helper.ALERT_DIALOG_DELETE
import com.sc1_solo.carehub.helper.EXTRA_ADDRESS
import com.sc1_solo.carehub.helper.EXTRA_POSITION
import com.sc1_solo.carehub.helper.RESULT_ADD
import com.sc1_solo.carehub.helper.RESULT_DELETE
import com.sc1_solo.carehub.helper.RESULT_UPDATE
import kotlinx.android.synthetic.main.activity_dashboard_address.*


class AddressAddUpdateActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var auth: FirebaseAuth
    private lateinit var firestore: FirebaseFirestore
    private var isEdit = false
    private var addressd: Address? = null
    private var position: Int = 0
    private lateinit var binding: ActivityAddressAddUpdateBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddressAddUpdateBinding.inflate(layoutInflater)
        setContentView(binding.root)
        firestore = Firebase.firestore
        auth = Firebase.auth
        addressd = intent.getParcelableExtra(EXTRA_ADDRESS)
        if (addressd != null) {
            position = intent.getIntExtra(EXTRA_POSITION, 0)
            isEdit = true
        } else {
            addressd = Address()
        }
        val actionBarTitle: String
        val btnTitle: String

        if (isEdit) {
            actionBarTitle = "Ubah"
            btnTitle = "Update"
            addressd?.let {
                binding.edtTitle.setText(it.title)
                binding.edtAddress.setText(it.address)
            }!!
        } else {
            actionBarTitle = "Tambah"
            btnTitle = "Simpan"
        }

        supportActionBar?.title = actionBarTitle
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        binding.btnSubmit.text = btnTitle
        binding.btnSubmit.setOnClickListener(this)
    }

    override fun onClick(view: View) {
        if (view.id == R.id.btn_submit) {
            val title = binding.edtTitle.text.toString().trim()
            val address = binding.edtAddress.text.toString().trim()
            if (title.isEmpty()) {
                binding.edtTitle.error = "Field can not be blank"
                return
            }
            if (isEdit) {
                val currentUser = auth.currentUser
                val user = hashMapOf(
                    "uid" to currentUser?.uid,
                    "title" to title,
                    "address" to address,
                )
                firestore.collection("address").document(addressd?.id.toString())
                    .set(user)
                    .addOnSuccessListener {
                        setResult(RESULT_UPDATE, intent)
                        finish()
                    }
                    .addOnFailureListener { e ->
                        Toast.makeText(this@AddressAddUpdateActivity, "Gagal engupdate data", Toast.LENGTH_SHORT).show()
                    }
            } else {
                val currentUser = auth.currentUser
                val user = hashMapOf(
                    "uid" to currentUser?.uid,
                    "title" to title,
                    "address" to address,
                )
                firestore.collection("address")
                    .add(user)
                    .addOnSuccessListener { documentReference ->
                        Toast.makeText(this@AddressAddUpdateActivity,
                            "DocumentSnapshot added with ID: ${documentReference.id}", Toast.LENGTH_SHORT).show()
                        setResult(RESULT_ADD, intent)
                        finish()
                    }
                    .addOnFailureListener { e ->
                        Toast.makeText(this@AddressAddUpdateActivity,
                            "Error adding document", Toast.LENGTH_SHORT).show()
                    }

            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        if (isEdit) {
            menuInflater.inflate(R.menu.menu_form, menu)
        }
        return super.onCreateOptionsMenu(menu)
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_delete -> showAlertDialog(ALERT_DIALOG_DELETE)
            android.R.id.home -> showAlertDialog(ALERT_DIALOG_CLOSE)
        }
        return super.onOptionsItemSelected(item)
    }

    private fun showAlertDialog(type: Int) {
        val isDialogClose = type == ALERT_DIALOG_CLOSE
        val dialogTitle: String
        val dialogMessage: String
        if (isDialogClose) {
            dialogTitle = "Batal"
            dialogMessage = "Apakah anda ingin membatalkan perubahan pada form?"
        } else {
            dialogMessage = "Apakah anda yakin ingin menghapus item ini?"
            dialogTitle = "Hapus Address"
        }
        val alertDialogBuilder = AlertDialog.Builder(this)
        alertDialogBuilder.setTitle(dialogTitle)
        alertDialogBuilder
            .setMessage(dialogMessage)
            .setCancelable(false)
            .setPositiveButton("Ya") { _, _ ->
                if (isDialogClose) {
                    finish()
                } else {
                    firestore.collection("address").document(addressd?.id.toString())
                        .delete()
                        .addOnSuccessListener {
                            Log.d("delete", "DocumentSnapshot successfully deleted!"+addressd?.id.toString())
                                val intent = Intent()
                            intent.putExtra(EXTRA_POSITION, position)
                            setResult(RESULT_DELETE, intent)
                            finish()
                        }
                        .addOnFailureListener { e ->
                            Log.w("a", "Error deleting document", e)
                            Toast.makeText(this@AddressAddUpdateActivity, "Gagal menghapus data", Toast.LENGTH_SHORT).show()
                        }

                }
            }
            .setNegativeButton("Tidak") { dialog, _ -> dialog.cancel() }
        val alertDialog = alertDialogBuilder.create()
        alertDialog.show()
    }
}