package com.example.qr_hitu

import android.content.Context
import android.graphics.Bitmap
import android.graphics.ImageFormat
import android.os.Environment
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.ImageProxy
import androidx.compose.foundation.Image
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.asImageBitmap
import com.google.zxing.*
import com.google.zxing.common.HybridBinarizer
import com.journeyapps.barcodescanner.BarcodeEncoder
import java.io.File
import java.io.FileOutputStream
import java.nio.ByteBuffer


@Composable
fun CreateQR(query: String){

    if (query.isNotEmpty()){
        val barcodeEncoder = BarcodeEncoder()
        val bitmap = barcodeEncoder.encodeBitmap(query, BarcodeFormat.QR_CODE, 512, 512)
        Image(
            bitmap = bitmap.asImageBitmap(),
            contentDescription = "QrCode"
        )
    }
}

fun downloadQR(query: String, qrName: String, context: Context){

    val barcodeEncoder = BarcodeEncoder()
    val bitmap = barcodeEncoder.encodeBitmap(query, BarcodeFormat.QR_CODE, 512, 512)
    val downloadsDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)
    val imageFile = File(downloadsDir, "$qrName.png")

    val outputStream = FileOutputStream(imageFile)
    bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream)
    outputStream.flush()
    outputStream.close()

}

// Class that analyzes QR codes camera images
// Classe qeu analisa imagens da camera de QR codes
class QrCodeAnalyzer(
    // Function to be called when a QR code is detected in the image
    // Função a chamar quando detetado uma imagem QR Code
    private val onQrCodeScanned: (String) -> Unit
): ImageAnalysis.Analyzer {

    // List of supported image formats
    // Lista de formatos de imagens suportados
    private val supportedImageFormats = listOf(
        ImageFormat.YUV_420_888,
        ImageFormat.YUV_422_888,
        ImageFormat.YUV_444_888,
    )

    // Function to analyze the incoming camera images
    // Função de analise de imagens recebidas pela camera
    override fun analyze(image: ImageProxy) {
        // Check if the image format is supported
        // Verificação de formato de imagem
        if(image.format in supportedImageFormats){
            // Get the byte array representation of the image
            // Receber representação da imagem em byte array
            val bytes = image.planes.first().buffer.toByteArray()

            // Create a PlanarYUVLuminanceSource object with the byte array
            // Criação de um objeto PlanarYUVLuminanceSource com byte array
            val source = PlanarYUVLuminanceSource(
                bytes,
                image.width,
                image.height,
                0,
                0,
                image.width,
                image.height,
                false
            )
            // Create a BinaryBitmap object from the PlanarYUVLuminanceSource
            // Criação do objeto BinaryBitmap do PlanarYUVLuminanceSource
            val binaryBitmap = BinaryBitmap(HybridBinarizer(source))
            try{
                // Decode the QR code in the image using MultiFormatReader
                // Descodificação da imagem do QR Code utilizando MultiFormatReader
                val result = MultiFormatReader().apply {
                    setHints(
                        mapOf(
                            DecodeHintType.POSSIBLE_FORMATS to arrayListOf(
                                BarcodeFormat.QR_CODE
                            )
                        )
                    )
                }.decode(binaryBitmap)
                // Call the onQrCodeScanned function with the decoded text
                // Chamar função onQrCodeScanned com texto descodificado
                onQrCodeScanned(result.text)
            }catch(e: Exception){
                // Print the exception stack trace
                // Escrever a exception stack trace
                e.printStackTrace()
            }finally{
                // Close the image
                // Fechar imagem
                image.close()
            }
        }
    }

    // Extension function to convert a ByteBuffer to a ByteArray
    // Função para converter um ByteBuffer a um ByteArray
    private fun ByteBuffer.toByteArray(): ByteArray{
        rewind()
        return ByteArray(remaining()).also {
            get(it)
        }
    }

}

