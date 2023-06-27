package com.example.qr_hitu.functions

import android.graphics.Bitmap
import android.graphics.ImageFormat
import android.graphics.drawable.Drawable
import android.os.Environment
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.ImageProxy
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asAndroidBitmap
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.toBitmap
import com.example.qr_hitu.R
import com.github.alexzhirkevich.customqrgenerator.QrData
import com.github.alexzhirkevich.customqrgenerator.vector.QrCodeDrawable
import com.github.alexzhirkevich.customqrgenerator.vector.QrVectorOptions
import com.github.alexzhirkevich.customqrgenerator.vector.style.QrVectorBackground
import com.github.alexzhirkevich.customqrgenerator.vector.style.QrVectorBallShape
import com.github.alexzhirkevich.customqrgenerator.vector.style.QrVectorColor
import com.github.alexzhirkevich.customqrgenerator.vector.style.QrVectorFrameShape
import com.github.alexzhirkevich.customqrgenerator.vector.style.QrVectorLogo
import com.github.alexzhirkevich.customqrgenerator.vector.style.QrVectorLogoPadding
import com.github.alexzhirkevich.customqrgenerator.vector.style.QrVectorLogoShape
import com.github.alexzhirkevich.customqrgenerator.vector.style.QrVectorPixelShape
import com.github.alexzhirkevich.customqrgenerator.vector.style.QrVectorShapes
import com.google.zxing.BarcodeFormat
import com.google.zxing.BinaryBitmap
import com.google.zxing.DecodeHintType
import com.google.zxing.MultiFormatReader
import com.google.zxing.PlanarYUVLuminanceSource
import com.google.zxing.common.HybridBinarizer
import java.io.File
import java.io.FileOutputStream
import java.nio.ByteBuffer

//  Neste ficheiro estão as funções relacionadas com QR codes


//  Cria um novo QR Code
@Composable
fun createQR(content: String): ImageBitmap {
    val context = LocalContext.current

    //  Define o conteúdo do QR Code
    val data = QrData.Text(content)
    //  Definições do aspeto
    val options = QrVectorOptions.Builder()
        .setPadding(.1f)
        .setBackground(QrVectorBackground(
            color =  QrVectorColor.Solid(
                ContextCompat.getColor(context, R.color.white)
            )
        ))
        .setLogo(
            QrVectorLogo(
                drawable = ContextCompat
                    .getDrawable(context, R.drawable.logo_qr),
                size = .33f,
                padding = QrVectorLogoPadding.Natural(.1f),
                shape = QrVectorLogoShape
                    .Default
            )
        )
        .setShapes(
            shapes = QrVectorShapes(
                ball = QrVectorBallShape.RoundCorners(.25f),
                darkPixel = QrVectorPixelShape.RoundCorners(.5f),
                frame = QrVectorFrameShape.RoundCorners(.25f),
            )
        )
        .build()

    //  Cria e retorna uma imagem do QR Code
    val drawable: Drawable = QrCodeDrawable(data, options)
    return drawable.toBitmap(height = 650, width = 650).asImageBitmap()
}


//  Faz download do QR Code
fun downloadQR(bitmap: ImageBitmap, qrName: String) {

    //  Acede ao diretório de downloads
    val downloadsDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)
    //  Cria um ficheiro .png
    val imageFile = File(downloadsDir, "$qrName.png")
    val outputStream = FileOutputStream(imageFile)
    bitmap.asAndroidBitmap().compress(Bitmap.CompressFormat.JPEG, 100, outputStream)
    outputStream.flush()
    outputStream.close()

}


//  Analisa imagens da camera de QR codes
class QrCodeAnalyzer(
    //  Função a chamar quando detetado uma imagem QR Code
    private val onQrCodeScanned: (String) -> Unit
) : ImageAnalysis.Analyzer {
    //  Lista de formatos de imagens suportados
    private val supportedImageFormats = listOf(
        ImageFormat.YUV_420_888,
        ImageFormat.YUV_422_888,
        ImageFormat.YUV_444_888,
    )

    //  Função de analise de imagens recebidas pela camera
    override fun analyze(image: ImageProxy) {
        //  Verificação de formato de imagem
        if (image.format in supportedImageFormats) {
            //  Receber representação da imagem em byte array
            val bytes = image.planes.first().buffer.toByteArray()
            //  Criação de um objeto PlanarYUVLuminanceSource com byte array
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
            //  Criação do objeto BinaryBitmap do PlanarYUVLuminanceSource
            val binaryBitmap = BinaryBitmap(HybridBinarizer(source))
            try {
                //  Descodificação da imagem do QR Code utilizando MultiFormatReader
                val result = MultiFormatReader().apply {
                    setHints(
                        mapOf(
                            DecodeHintType.POSSIBLE_FORMATS to arrayListOf(
                                BarcodeFormat.QR_CODE
                            )
                        )
                    )
                }.decode(binaryBitmap)
                //  Chamar função onQrCodeScanned com texto descodificado
                onQrCodeScanned(result.text)
            } catch (e: Exception) {
                //  Escrever a exception stack trace
                e.printStackTrace()
            } finally {
                //  Fechar imagem
                image.close()
            }
        }
    }

    //  Função para converter um ByteBuffer a um ByteArray
    private fun ByteBuffer.toByteArray(): ByteArray {
        rewind()
        return ByteArray(remaining()).also {
            get(it)
        }
    }

}