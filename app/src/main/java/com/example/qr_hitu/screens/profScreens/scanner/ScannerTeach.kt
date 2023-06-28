package com.example.qr_hitu.screens.profScreens.scanner

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.ImageProxy
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.navigation.NavController
import com.example.qr_hitu.ViewModels.ScannerViewModel
import com.example.qr_hitu.components.ScanProf
import com.example.qr_hitu.functions.Malf_ErrorDialogs
import com.example.qr_hitu.functions.decryptAES
import com.example.qr_hitu.functions.encryptionKey
import com.example.qr_hitu.functions.isEncryptedString
import com.google.mlkit.vision.barcode.BarcodeScanner
import com.google.mlkit.vision.barcode.BarcodeScannerOptions
import com.google.mlkit.vision.barcode.BarcodeScanning
import com.google.mlkit.vision.barcode.common.Barcode
import com.google.mlkit.vision.common.InputImage
import java.util.concurrent.Executors
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine


//  Tela com a camara do utilizador para dar scan nos QR Codes
@androidx.annotation.OptIn(androidx.camera.core.ExperimentalGetImage::class)
@Composable
fun ScannerTeachScreen(navController: NavController, viewModel: ScannerViewModel){
    //  Pede permissão para usar a camara
    var permission = true
    val requestPermissionLauncher = rememberLauncherForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
        permission = isGranted
    }

    //  Mostrar Dialog
    val show = remember { mutableStateOf(0) }

    //  Definições para a camara
    val context = LocalContext.current as Activity
    val lensFacing = CameraSelector.LENS_FACING_BACK
    val lifecycleOwner = LocalLifecycleOwner.current

    val preview = Preview.Builder().build()
    val previewView = remember {
        PreviewView(context)
    }
    val analysisUseCase : ImageAnalysis = ImageAnalysis.Builder().build()
    val cameraSelector = CameraSelector.Builder()
        .requireLensFacing(lensFacing)
        .build()
    val cameraExecutor = Executors.newSingleThreadExecutor()
    val options = BarcodeScannerOptions.Builder()
        .setBarcodeFormats(
            Barcode.FORMAT_QR_CODE
        ).build()
    val scanner = BarcodeScanning.getClient(options)

    //  Procedimento para pedir permissão ao utilizador para poder usar a camara
    fun requestCameraPermission() {
        when {
            ContextCompat.checkSelfPermission(
                context,
                Manifest.permission.CAMERA
            ) == PackageManager.PERMISSION_GRANTED -> {
                permission = true
            }

            ActivityCompat.shouldShowRequestPermissionRationale(
                context,
                Manifest.permission.CAMERA
            ) -> permission = true

            else -> requestPermissionLauncher.launch(Manifest.permission.CAMERA)
        }
    }

    //  Procedimento para analisar imagem
    fun processImageProxy(
        barcodeScanner: BarcodeScanner,
        imageProxy: ImageProxy
    ) {
        imageProxy.image?.let { image ->
            val inputImage =
                InputImage.fromMediaImage(
                    image,
                    imageProxy.imageInfo.rotationDegrees
                )
            //  Processa a imagem
            barcodeScanner.process(inputImage)
                .addOnSuccessListener { barcodeList ->
                    val barcode = barcodeList.getOrNull(0)
                    //  Código do QR
                    barcode?.rawValue?.let { value ->
                        //  Condição que verifica se o valor no QR está encriptado
                        if (isEncryptedString(value)) {
                            //  Desencripta o valor no QR
                            val decodedValue = decryptAES(value, encryptionKey)
                            //  Verifica se o conteúdo não coincide
                            if(!Regex("""Bloco \w+,Sala \p{all}+,\w+\w+""").containsMatchIn(decodedValue)){
                                //  Mostra Dialog de erro
                                show.value = 2
                            }else{
                                //  Guarda os dados do QR e mostra Dialog
                                viewModel.setMyData(decodedValue)
                                show.value = 1
                            }
                        } else {
                            //  Mostra Dialog de erro
                            show.value = 2
                        }
                    }
                }
                .addOnCompleteListener {
                    //  Fecha o procedimento
                    imageProxy.image?.close()
                    imageProxy.close()
                }
        }
    }

    //  Coroutine para pedir permissão para usar a camara
    LaunchedEffect(key1 = true) {
        requestCameraPermission()
    }

    //  Função para conseguir o cameraProvider
    suspend fun Context.getCameraProvider(): ProcessCameraProvider =
        suspendCoroutine { continuation ->
            ProcessCameraProvider.getInstance(this).also { cameraProvider ->
                cameraProvider.addListener({
                    continuation.resume(cameraProvider.get())
                }, ContextCompat.getMainExecutor(this))
            }
        }

    //  Coroutine que inicia a camara
    LaunchedEffect(key1 = permission) {
        val cameraProvider = context.getCameraProvider()
        cameraProvider.unbindAll()
        cameraProvider.bindToLifecycle(
            lifecycleOwner,
            cameraSelector,
            preview,
            analysisUseCase
        )

        preview.setSurfaceProvider(previewView.surfaceProvider)

        //  Verifica se existe QR Code e envia para a função de tratamento dos respetivo
        analysisUseCase.setAnalyzer(
            Executors.newSingleThreadExecutor()
        ) { imageProxy ->
            processImageProxy(scanner, imageProxy)
        }
    }

    //  Coroutine que ativa sempre que esta tela saí da composição
    DisposableEffect(Unit){
        onDispose {
            //  Se trocarmos de tela ativa
            //  Esta condição foi necessária para evitar que sempre que um dialog abra a camara tenha de reiniciar
            if (navController.currentDestination!!.route != ScanProf.route) {
                cameraExecutor.shutdown()
            }
        }
    }

    //  Condição que verifica a permissão
    if (permission){
        AndroidView({ previewView }, modifier = Modifier.fillMaxSize())
        //  Condição para mostrar Dialog de erro ou perguntar se quer adicionar avaria
        when(show.value){
            1 -> {
                Malf_ErrorDialogs(onDialogDismissed = {
                    show.value = 0
                    navController.navigate(ScanProf.route)
                }, navController, Err = false)
            }
            2 -> {
                Malf_ErrorDialogs(onDialogDismissed = {
                    show.value = 0
                    navController.navigate(ScanProf.route)
                }, navController, Err = true)
            }
        }
    }
}

