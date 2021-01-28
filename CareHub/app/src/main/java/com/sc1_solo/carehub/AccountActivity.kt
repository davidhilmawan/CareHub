package com.sc1_solo.carehub

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.view.isVisible
import com.bumptech.glide.Glide
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.sc1_solo.carehub.data.SettingModel
import com.sc1_solo.carehub.databinding.ActivityAccountBinding
import kotlinx.android.synthetic.main.activity_account.*

class AccountActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var auth: FirebaseAuth
    private lateinit var binding: ActivityAccountBinding
    private lateinit var googleSignInClient: GoogleSignInClient
    private lateinit var settingModel: SettingModel
    private lateinit var mSettingPreference: SettingPreference
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar!!.title = "Account"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        binding = ActivityAccountBinding.inflate(layoutInflater)
        binding.act = this
        setContentView(binding.root)
        auth = Firebase.auth
        mSettingPreference = SettingPreference(this)
        showExistingPreference()
        val currentUser = auth.currentUser
        if (currentUser == null) {
            val intent = Intent(this@AccountActivity, SignInActivity::class.java)
            startActivity(intent)
            finish()
        }
        binding.btnSignOut.setOnClickListener(this)
        binding.btnEmailVerify.setOnClickListener(this)
        binding.btnAddAddress.setOnClickListener(this)
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()
        googleSignInClient = GoogleSignIn.getClient(this, gso)
    }
    private fun updateUI(currentUser: FirebaseUser) {
        currentUser.let {
            val name = currentUser.displayName
            val phoneNumber = currentUser.phoneNumber
            val email = currentUser.email
            val photoUrl = currentUser.photoUrl
            val emailVerified = currentUser.isEmailVerified
            if(photoUrl != null) {
                Glide.with(this)
                    .load(photoUrl)
                    .circleCrop()
                    .override(160, 160)
                    .into(binding.ivImage)
            }
            binding.tvName.text = name
            if(TextUtils.isEmpty(name)){
                binding.tvName.text = "No Name"
            }
            binding.tvUserId.text = email
            for (profile in it.providerData) {
                val providerId = profile.providerId
                if(providerId=="password" && emailVerified){
                    binding.btnEmailVerify.isVisible = false
                }
                if(providerId=="phone"){
                    binding.tvName.text = phoneNumber
                    binding.tvUserId.text = providerId
                }
            }
        }
    }
    public override fun onStart() {
        super.onStart()
        val currentUser = auth.currentUser
        if (currentUser == null) {
            val intent = Intent(this@AccountActivity, SignInActivity::class.java)
            startActivity(intent)
            finish()
        }
        else{
            updateUI(currentUser)
        }
    }
    override fun onClick(v: View) {
        when (v.id) {
            R.id.btnSignOut -> {
                signOut()
            }
            R.id.btnEmailVerify -> {
                sendEmailVerification()
            }
            R.id.btn_save -> {
                openSetting()
            }
            R.id.btnAddAddress -> {
                openAddress()
            }
        }
    }
    fun openAddress(){
        val intent = Intent(this@AccountActivity, DashboardAddressActivity::class.java)
        startActivityForResult(intent, REQUEST_CODE)
    }
    private fun sendEmailVerification() {
        binding.btnEmailVerify.isEnabled = false
        val user = auth.currentUser!!
        user.sendEmailVerification()
            .addOnCompleteListener(this) { task ->
                binding.btnEmailVerify.isEnabled = true
                if (task.isSuccessful) {
                    Toast.makeText(baseContext,
                            "Verification email sent to ${user.email} ",
                            Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(baseContext,
                            "Failed to send verification email.",
                            Toast.LENGTH_SHORT).show()
                }
            }
    }

    private fun signOut() {
        auth.signOut()
        val currentUser = auth.currentUser
        if (currentUser == null) {
            googleSignInClient.signOut().addOnCompleteListener(this) {
                val intent = Intent(this@AccountActivity, SignInActivity::class.java)
                startActivity(intent)
                finish()
            }
        }
    }
    fun openSetting(){
        val intent = Intent(this@AccountActivity, SettingPreferenceActivity::class.java)
        startActivityForResult(intent, REQUEST_CODE)
    }
    companion object {
        private const val REQUEST_CODE = 100
    }
    private fun showExistingPreference() {
        settingModel = mSettingPreference.getSetting()
        populateView(settingModel)
    }
    private fun populateView(settingModel: SettingModel) {
        if (settingModel.isDarkTheme) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            delegate.applyDayNight()
        }
        else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            delegate.applyDayNight()
        }
        binding.settingModel = settingModel
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_CODE) {
            if (resultCode == SettingPreferenceActivity.RESULT_CODE) {
                showExistingPreference()
            }
        }
    }
    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}
