package com.unifest.android.feature.stamp

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.KeyEvent
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.google.zxing.BarcodeFormat
import com.journeyapps.barcodescanner.BarcodeCallback
import com.journeyapps.barcodescanner.BarcodeResult
import com.journeyapps.barcodescanner.DecoratedBarcodeView
import com.journeyapps.barcodescanner.DefaultDecoderFactory
import com.unifest.android.core.designsystem.theme.UnifestTheme
import com.unifest.android.feature.stamp.viewmodel.qrscan.QRScanViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@AndroidEntryPoint
internal class QRScanActivity : ComponentActivity() {
    private val barcodeView: DecoratedBarcodeView by lazy {
        DecoratedBarcodeView(this).apply {
            barcodeView.decoderFactory = DefaultDecoderFactory(listOf(BarcodeFormat.QR_CODE))
            initializeFromIntent(intent)
            decodeContinuous(callback)
            viewFinder.setLaserVisibility(false)
            statusView.isVisible = false
        }
    }

    private val viewModel: QRScanViewModel by viewModels()

    private val callback = BarcodeCallback { result: BarcodeResult ->
        result.text ?: return@BarcodeCallback
        viewModel.scan(result.text)
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                barcodeView.pause()
                delay(1000)
                barcodeView.resume()
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)

        val festivalId = intent.getLongExtra("festivalId", 0L)

        setContent {
            UnifestTheme {
                QRScanScreen(
                    barcodeView = barcodeView,
                    festivalId = festivalId,
                    popBackStack = {
                        setResult(RESULT_CANCELED)
                        finish()
                    },
                    complete = {
                        setResult(RESULT_OK)
                        finish()
                    },
                    onAction = viewModel::onAction,
                )
            }
        }
    }

    override fun onResume() {
        super.onResume()
        if (checkCameraPermission()) {
            barcodeView.resume()
        } else {
            finish()
        }
    }

    override fun onPause() {
        super.onPause()
        barcodeView.pause()
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        return barcodeView.onKeyDown(keyCode, event) || super.onKeyDown(keyCode, event)
    }

    private fun checkCameraPermission(): Boolean {
        return ContextCompat.checkSelfPermission(
            this,
            Manifest.permission.CAMERA,
        ) == PackageManager.PERMISSION_GRANTED
    }
}
