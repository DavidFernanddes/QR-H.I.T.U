package com.example.qr_hitu.screens.profScreens


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
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.navigation.NavController
import com.example.qr_hitu.ViewModels.ScannerViewModel
import com.example.qr_hitu.screens.components.ScanInput
import com.example.qr_hitu.screens.components.ScanProf
import com.example.qr_hitu.screens.theme.md_theme_light_primary
import com.example.qr_hitu.screens.theme.md_theme_light_primaryContainer
import com.google.mlkit.vision.barcode.BarcodeScanner
import com.google.mlkit.vision.barcode.BarcodeScannerOptions
import com.google.mlkit.vision.barcode.BarcodeScanning
import com.google.mlkit.vision.barcode.common.Barcode
import com.google.mlkit.vision.common.InputImage
import java.util.concurrent.Executors
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

@androidx.annotation.OptIn(androidx.camera.core.ExperimentalGetImage::class)
@Composable
fun ScannerTeachScreen(navController: NavController, viewModel: ScannerViewModel){
    var permission = true
    val requestPermissionLauncher = rememberLauncherForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
        permission = isGranted
    }
    val showState = remember { mutableStateOf(false) }
    val show by rememberUpdatedState(showState.value)

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

    fun processImageProxy(
        barcodeScanner: BarcodeScanner,
        imageProxy: ImageProxy,
        cameraProvider: ProcessCameraProvider
    ) {
        imageProxy.image?.let { image ->
            val inputImage =
                InputImage.fromMediaImage(
                    image,
                    imageProxy.imageInfo.rotationDegrees
                )

            barcodeScanner.process(inputImage)
                .addOnSuccessListener { barcodeList ->
                    val barcode = barcodeList.getOrNull(0)

                    barcode?.rawValue?.let { value ->
                        cameraProvider.unbindAll()
                        viewModel.setMyData(value)
                        showState.value = true
                    }
                }
                .addOnFailureListener {

                }
                .addOnCompleteListener {
                    imageProxy.image?.close()
                    imageProxy.close()
                }
        }
    }

    LaunchedEffect(key1 = true) {
        requestCameraPermission()
    }

    suspend fun Context.getCameraProvider(): ProcessCameraProvider =
        suspendCoroutine { continuation ->
            ProcessCameraProvider.getInstance(this).also { cameraProvider ->
                cameraProvider.addListener({
                    continuation.resume(cameraProvider.get())
                }, ContextCompat.getMainExecutor(this))
            }
        }

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

        analysisUseCase.setAnalyzer(
            Executors.newSingleThreadExecutor()
        ) { imageProxy ->
            processImageProxy(scanner, imageProxy, cameraProvider)
        }
    }

    DisposableEffect(Unit){
        onDispose {
            cameraExecutor.shutdown()
        }
    }

    if (permission){
        AndroidView({ previewView }, modifier = Modifier.fillMaxSize())
        if (show) {
            Dialog(onDialogDismissed = { viewModel.myData.value == ""
                showState.value = false
                navController.navigate(ScanProf.route)
                }, navController)
        }
    } else {
        androidx.compose.material.Text("Permission not Granted")
    }
}

@Composable
fun Dialog(onDialogDismissed: () -> Unit, navController: NavController ) {
    val openDialog = remember { mutableStateOf(true) }

    if (openDialog.value) {
        AlertDialog(
            onDismissRequest = { openDialog.value = false; onDialogDismissed() },
            title = {
                Text(
                    text = "Avaria",
                    textAlign = TextAlign.Center
                )
            },
            text = {
                Text(text = "Deseja colocar uma avaria ?")
            },
            confirmButton = {
                TextButton(onClick = { openDialog.value = false; navController.navigate(ScanInput.route) }) {
                    Text(text = "SIM")
                }
            },
            dismissButton = {
                TextButton(onClick = { openDialog.value = false;  onDialogDismissed()}) {
                    Text(text = "NÃO")
                }
            },
            textContentColor = md_theme_light_primaryContainer,
            titleContentColor = md_theme_light_primary

        )
    }

}

