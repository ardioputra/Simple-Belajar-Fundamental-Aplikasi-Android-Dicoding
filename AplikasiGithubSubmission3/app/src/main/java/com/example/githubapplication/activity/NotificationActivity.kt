package com.example.githubapplication.activity

import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.preference.PreferenceFragmentCompat
import com.example.githubapplication.R
import com.example.githubapplication.receiver.AlarmReceiver


class NotificationActivity : AppCompatActivity(){

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_notification)
        supportActionBar?.title = "Notification"
        supportFragmentManager.beginTransaction().add(R.id.setting_holder, NotificationFragment()).commit()
    }

    class NotificationFragment : PreferenceFragmentCompat(), SharedPreferences.OnSharedPreferenceChangeListener{
        override fun onSharedPreferenceChanged(preference: SharedPreferences, key: String) {
            val onAlarm = preference.getBoolean(key, true)
            val alarmManager = AlarmReceiver()
            if (onAlarm) {
                alarmManager.setRepeatingAlarm(requireActivity(), AlarmReceiver.TYPE_REPEATING, getString(R.string.des_reminder))
            } else {
                alarmManager.cancelAlarm(requireActivity(), AlarmReceiver.TYPE_REPEATING)
            }

        }

        override fun onResume() {
            super.onResume()
            preferenceScreen.sharedPreferences
                .registerOnSharedPreferenceChangeListener(this)
        }

        override fun onPause() {
            super.onPause()
            preferenceScreen.sharedPreferences
                .unregisterOnSharedPreferenceChangeListener(this)
        }

        override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
            setPreferencesFromResource(R.xml.preferences, rootKey)
        }
    }
}