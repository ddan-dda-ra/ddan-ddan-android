package com.ddanddan.ddanddan.util

import android.util.Base64
import com.ddanddan.ddanddan.BuildConfig
import timber.log.Timber
import javax.crypto.Cipher
import javax.crypto.spec.SecretKeySpec

object SecurityUtils {

    /**
     * AES를 사용해 문자열을 암호화하는 함수
     */
    fun encrypt(data: String): String? {
        return try {
            val aesKey = BuildConfig.AES_KEY
            val secretKeySpec = SecretKeySpec(Base64.decode(aesKey, Base64.DEFAULT), "AES")
            val cipher = Cipher.getInstance("AES/GCM/NoPadding")
            cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec)
            val iv = cipher.iv
            val encryptedBytes = cipher.doFinal(data.toByteArray(Charsets.UTF_8))
            val combined = iv + encryptedBytes
            Base64.encodeToString(combined, Base64.DEFAULT)
        } catch (e: Exception) {
            Timber.e(e, "암호화에 실패했습니다.")
            null
        }
    }
}
