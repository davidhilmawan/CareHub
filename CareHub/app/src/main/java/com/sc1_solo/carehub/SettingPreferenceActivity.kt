package com.sc1_solo.carehub

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.FontsContract.Columns.RESULT_CODE
import android.text.TextUtils
import android.view.View
import android.widget.Toast
import com.sc1_solo.carehub.databinding.ActivitySettingPreferenceBinding

class SettingPreferenceActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var mSettingPreference: SettingPreference
    private lateinit var settingModel: SettingModel
    private lateinit var binding: ActivitySettingPreferenceBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySettingPreferenceBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.btnSave.setOnClickListener(this)
        mSettingPreference = SettingPreference(this)
        settingModel = mSettingPreference.getSetting()
        showPreferenceInForm()
        supportActionBar?.title = getString(R.string.setting_page)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    override fun onClick(p0: View?) {
        if (p0?.id == R.id.btn_save) {
            val isLoveMU = binding.rgLoveMu.checkedRadioButtonId == R.id.rb_yes
            saveSetting(isLoveMU)
            val resultIntent = Intent()
            setResult(RESULT_CODE, resultIntent)
            finish()
        }
    }

    companion object {
        const val RESULT_CODE = 101
    }
    private fun showPreferenceInForm() {
        if (settingModel.isDarkTheme) {
            binding.rbYes.isChecked = true
        } else {
            binding.rbNo.isChecked = true
        }
    }
    private fun saveSetting(isLoveMU: Boolean) {
        val settingPreference = SettingPreference(this)
        settingModel.isDarkTheme = isLoveMU
        settingPreference.setSetting(settingModel)
        Toast.makeText(this, "Data tersimpan", Toast.LENGTH_SHORT).show()
    }
}
