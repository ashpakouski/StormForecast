package com.shpak.stormalert.presentation.forecast

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.shpak.stormalert.R

@Composable
fun MaxKpCard(maxKp: Double) {
    Card(
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(),
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(Modifier.padding(12.dp)) {
            Text(
                stringResource(R.string.max_value_title),
                fontWeight = FontWeight(550),
                color = MaterialTheme.colorScheme.onBackground
            )
            Row {
                Text(
                    "Kp $maxKp",
                    fontSize = 45.sp,
                    fontWeight = FontWeight(450),
                    color = MaterialTheme.colorScheme.onBackground
                )
            }
        }
    }
}