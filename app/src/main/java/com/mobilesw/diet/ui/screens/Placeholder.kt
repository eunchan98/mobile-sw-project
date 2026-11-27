package com.mobilesw.diet.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.mobilesw.diet.ui.components.AppCard
import com.mobilesw.diet.ui.components.AppTopBar

/** 아직 구현 전인 화면의 자리 표시. 각 담당자가 실제 화면으로 교체한다. */
@Composable
fun PlaceholderScreen(
    title: String,
    owner: String,
    description: String,
    onBack: (() -> Unit)? = null,
    extra: @Composable ColumnScope.() -> Unit = {},
) {
    Column(Modifier.fillMaxSize()) {
        AppTopBar(title = title, onBack = onBack)
        Column(
            modifier = Modifier.padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp),
        ) {
            AppCard {
                Text("구현 예정 · $owner", style = MaterialTheme.typography.titleMedium, color = MaterialTheme.colorScheme.primary)
                Text(description, style = MaterialTheme.typography.bodyMedium)
            }
            extra()
        }
    }
}
