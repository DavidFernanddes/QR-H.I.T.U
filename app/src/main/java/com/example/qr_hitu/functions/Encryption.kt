package com.example.qr_hitu.functions

import android.util.Base64
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import java.nio.charset.StandardCharsets
import javax.crypto.Cipher
import javax.crypto.spec.IvParameterSpec
import javax.crypto.spec.SecretKeySpec

//  Neste ficheiro temos as funções relacionadas com a encriptação do conteúdo dos QRs

//  Constante com a chave de encriptação
const val encryptionKey = "0123456789abcdef"

//  Encriptação do texto passado
fun encryptAES(data: String, key: String): String = runBlocking {
    //  Abre coroutine
    withContext(Dispatchers.Default) {
        //  Informações necessárias para a encriptação
        val cipher = Cipher.getInstance("AES/CBC/PKCS7Padding")
        val secretKey = SecretKeySpec(key.toByteArray(StandardCharsets.UTF_8), "AES")
        val iv = ByteArray(cipher.blockSize)
        val ivParams = IvParameterSpec(iv)
        cipher.init(Cipher.ENCRYPT_MODE, secretKey, ivParams)
        val encryptedBytes = cipher.doFinal(data.toByteArray(StandardCharsets.UTF_8))
        //  Retorna a string encriptada
        return@withContext Base64.encodeToString(encryptedBytes, Base64.DEFAULT)
    }
}

//  Desencriptação do texto passado
fun decryptAES(encryptedData: String, key: String): String = runBlocking {
    //  Abre coroutine
    withContext(Dispatchers.Default) {
        //  Informação necessária á desencriptação
        val cipher = Cipher.getInstance("AES/CBC/PKCS7Padding")
        val secretKey = SecretKeySpec(key.toByteArray(StandardCharsets.UTF_8), "AES")
        val iv = ByteArray(cipher.blockSize)
        val ivParams = IvParameterSpec(iv)
        cipher.init(Cipher.DECRYPT_MODE, secretKey, ivParams)
        val decodedBytes = Base64.decode(encryptedData, Base64.DEFAULT)
        val decryptedBytes = cipher.doFinal(decodedBytes)
        //  Retorna a string original
        return@withContext String(decryptedBytes, StandardCharsets.UTF_8)
    }
}


//  Verifica se a string colocada está encriptada e se usa o mesmo algoritmo que a app ou não
fun isEncryptedString(input: String): Boolean {
    //  Retorna true or false
    return try {
        //  Informações para verificar
        val decodedBytes = Base64.decode(input, Base64.DEFAULT)
        val secretKey = SecretKeySpec(encryptionKey.toByteArray(StandardCharsets.UTF_8), "AES")
        val iv = ByteArray(16)
        val ivParams = IvParameterSpec(iv)
        val cipher = Cipher.getInstance("AES/CBC/PKCS7Padding")

        cipher.init(Cipher.DECRYPT_MODE, secretKey, ivParams)
        cipher.doFinal(decodedBytes)

        true
    } catch (e: Exception) {
        false
    }
}