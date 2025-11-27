package com.ddanddan.ddanddan.util

import android.content.Context
import android.util.Log
import com.google.firebase.database.FirebaseDatabase
import kotlinx.coroutines.delay

class VersionChecker(
    private val context: Context,
    ) {
    fun checkVersion(
        forceUpdate: (String) -> Unit
    ) {
        FirebaseDatabase.getInstance().reference
            .child("app_version")
            .child("android")
            .get()
            .addOnSuccessListener {
                Log.d("kangmi", it.toString())
                val minimumVersion = it.child("minimum_version").value.toString()
                val updateMessage = it.child("update_message").value.toString()

                val currentVersion = getCurrentAppVersion()

                Log.d("kangmi", updateMessage.toString())
                Log.d("kangmi", minimumVersion.toString())
                Log.d("kangmi", currentVersion.toString())
                if (compareVersions(currentVersion, minimumVersion) < 0) {
                    forceUpdate(updateMessage)
                }
            }.addOnFailureListener {
                Log.d("kangmi", it.toString())
            }
    }

    // 현재 앱 버전 가져오기
    private fun getCurrentAppVersion(): String {
        return try {
            val packageInfo = context.packageManager.getPackageInfo(context.packageName, 0)
            packageInfo.versionName ?: "0.0.0"
        } catch (e: Exception) {
            "0.0.0"
        }
    }

    // 버전 비교 함수
    // 반환값: version1 < version2 이면 -1, 같으면 0, version1 > version2 이면 1
    private fun compareVersions(version1: String, version2: String): Int {
        val v1Parts = version1.split(".").map { it.toIntOrNull() ?: 0 }
        val v2Parts = version2.split(".").map { it.toIntOrNull() ?: 0 }

        val maxLength = maxOf(v1Parts.size, v2Parts.size)

        for (i in 0 until maxLength) {
            val v1Part = v1Parts.getOrNull(i) ?: 0
            val v2Part = v2Parts.getOrNull(i) ?: 0

            when {
                v1Part < v2Part -> return -1
                v1Part > v2Part -> return 1
            }
        }
        return 0
    }
}