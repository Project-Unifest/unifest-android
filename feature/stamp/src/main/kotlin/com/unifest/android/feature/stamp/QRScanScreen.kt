package com.unifest.android.feature.stamp

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.hilt.navigation.compose.hiltViewModel
import com.journeyapps.barcodescanner.DecoratedBarcodeView
import com.unifest.android.core.common.ObserveAsEvents
import com.unifest.android.core.designsystem.R
import com.unifest.android.core.designsystem.component.UnifestScaffold
import com.unifest.android.core.designsystem.theme.BoothTitle2
import com.unifest.android.core.designsystem.theme.QRDescription
import com.unifest.android.core.designsystem.theme.Title0
import com.unifest.android.feature.stamp.viewmodel.QRErrorType
import com.unifest.android.feature.stamp.viewmodel.QRScanEvent
import com.unifest.android.feature.stamp.viewmodel.QRScanUiAction
import com.unifest.android.feature.stamp.viewmodel.QRScanViewModel

@Composable
fun QRScanScreen(
    barcodeView: DecoratedBarcodeView,
    popBackStack: () -> Unit,
    onAction: (QRScanUiAction) -> Unit,
    viewModel: QRScanViewModel = hiltViewModel(),
) {
    val context = LocalContext.current

    LaunchedEffect(barcodeView) {
        barcodeView.resume()
    }

    ObserveAsEvents(flow = viewModel.uiEvent) { event ->
        when (event) {
            is QRScanEvent.NavigateBack -> popBackStack()
            is QRScanEvent.ScanError -> {
                when (event.errorType) {
                    QRErrorType.ShowNotToday -> Toast.makeText(context, "", Toast.LENGTH_SHORT).show()
                    QRErrorType.UsedTicket -> Toast.makeText(context, "", Toast.LENGTH_SHORT).show()
                    QRErrorType.TicketNotFound -> Toast.makeText(context, "", Toast.LENGTH_SHORT).show()
                }
            }

            is QRScanEvent.ScanSuccess -> Toast.makeText(context, "", Toast.LENGTH_SHORT).show()
        }
    }

    UnifestScaffold(
        topBar = {
            QRTopBar(onAction = onAction)
        },
        bottomBar = {
            QRScanBottomBar()
        },
    ) { innerPadding ->
        AndroidView(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize(),
            factory = { barcodeView },
        )
    }
}

@Composable
private fun QRTopBar(
    onAction: (QRScanUiAction) -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.Start,
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Spacer(modifier = Modifier.width(21.dp))
            Icon(
                imageVector = ImageVector.vectorResource(R.drawable.ic_arrow_back_dark_gray),
                contentDescription = "Arrow Back Icon",
                tint = MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier.clickable { onAction(QRScanUiAction.OnBackClick) },
            )
            Spacer(modifier = Modifier.width(16.dp))
            Text(
                text = stringResource(id = R.string.stamp_qr_scan_title),
                modifier = Modifier.padding(vertical = 18.dp),
                color = MaterialTheme.colorScheme.onBackground,
                style = BoothTitle2,
            )
        }
        Spacer(modifier = Modifier.height(40.dp))
    }
}

@Composable
private fun QRScanBottomBar(
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.background),
        contentAlignment = Alignment.Center,
    ) {
        Column(
            modifier = Modifier.padding(horizontal = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Spacer(modifier = Modifier.height(92.dp))
            Text(
                text = stringResource(R.string.stamp_qr_scan_description1),
                color = MaterialTheme.colorScheme.onBackground,
                style = Title0,
            )
            Text(
                text = stringResource(R.string.stamp_qr_scan_description2),
                color = MaterialTheme.colorScheme.onBackground,
                style = QRDescription,
            )
            Spacer(modifier = Modifier.height(110.dp))
        }
    }
}
