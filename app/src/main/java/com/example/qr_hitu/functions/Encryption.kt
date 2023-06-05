package com.example.qr_hitu.functions

import android.util.Base64
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import java.nio.charset.StandardCharsets
import javax.crypto.Cipher
import javax.crypto.spec.IvParameterSpec
import javax.crypto.spec.SecretKeySpec

const val encryptionKey = "0123456789abcdef"

fun encryptAES(data: String, key: String): String = runBlocking {
    withContext(Dispatchers.Default) {
        val cipher = Cipher.getInstance("AES/CBC/PKCS7Padding")
        val secretKey = SecretKeySpec(key.toByteArray(StandardCharsets.UTF_8), "AES")
        val iv = ByteArray(cipher.blockSize)
        val ivParams = IvParameterSpec(iv)
        cipher.init(Cipher.ENCRYPT_MODE, secretKey, ivParams)
        val encryptedBytes = cipher.doFinal(data.toByteArray(StandardCharsets.UTF_8))
        return@withContext android.util.Base64.encodeToString(encryptedBytes, android.util.Base64.DEFAULT)
    }
}

fun decryptAES(encryptedData: String, key: String): String = runBlocking {
    withContext(Dispatchers.Default) {
        val cipher = Cipher.getInstance("AES/CBC/PKCS7Padding")
        val secretKey = SecretKeySpec(key.toByteArray(StandardCharsets.UTF_8), "AES")
        val iv = ByteArray(cipher.blockSize)
        val ivParams = IvParameterSpec(iv)
        cipher.init(Cipher.DECRYPT_MODE, secretKey, ivParams)
        val decodedBytes = android.util.Base64.decode(encryptedData, android.util.Base64.DEFAULT)
        val decryptedBytes = cipher.doFinal(decodedBytes)
        return@withContext String(decryptedBytes, StandardCharsets.UTF_8)
    }
}


fun isEncryptedString(input: String): Boolean {
    return try {
        val decodedBytes = Base64.decode(input, Base64.DEFAULT)
        val secretKey = SecretKeySpec(encryptionKey.toByteArray(), "AES")
        val cipher = Cipher.getInstance("AES")

        cipher.init(Cipher.DECRYPT_MODE, secretKey)
        cipher.doFinal(decodedBytes)

        true
    } catch (e: Exception) {
        false
    }
}