package com.boltic28.kointest

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.provider.Settings

//private val receiver = object : BroadcastReceiver() {
//    override fun onReceive(context: Context?, intent: Intent?) {
//        intent?.getStringExtra(THEME_EXTRA)?.let { theme ->
//            themeRepository.setNewTheme(theme)
//        }
//        intent?.getIntExtra(BRIGHTNESS_EXTRA, uiConfig.defaultBrightness)?.let { brightness ->
//            setBrightness(brightness)
//        }
//        intent?.getIntExtra(REQUEST_CODE, DEFAULT_THEME_REQUEST_CODE)?.let { requestCode ->
//            scheduleThemeUpdate(requestCode)
//        }
//    }
//}
//
//
//private fun setBrightness(brightness: Int) {
//    Settings.System.putInt(
//        applicationContext.contentResolver,
//        Settings.System.SCREEN_BRIGHTNESS,
//        brightness
//    )
//}
//
//
//private fun setCurrentTheme() {
//    val themeUpdateTimeData = themeRepository.themeUpdateTimeData
//    with(themeUpdateTimeData) {
//        val nowMinutes = DateTime.now(DateTimeZone.UTC).minuteOfDay
//        val defaultThemeTimeInMinutes = DateTime(defaultThemeTime).minuteOfDay
//        val nightThemeTimeInMinutes = DateTime(nightThemeTime).minuteOfDay
//        if (nowMinutes in defaultThemeTimeInMinutes..nightThemeTimeInMinutes || nowMinutes < nightThemeTimeInMinutes) {
//            themeRepository.setNewTheme(themeRepository.getCurrentDefaultTheme())
//            setBrightness(uiConfig.defaultBrightness)
//        } else {
//            themeRepository.setNewTheme(themeRepository.getNightTheme())
//            setBrightness(uiConfig.minimalBrightness)
//        }
//    }
//}