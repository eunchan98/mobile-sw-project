package com.mobilesw.diet.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.mobilesw.diet.domain.model.MealType
import com.mobilesw.diet.ui.theme.DietTheme

/** 선택 칩 (Figma: Chip). 선택되면 핑크 채움, 아니면 흰 바탕 + 회색 테두리. */
@Composable
fun SelectChip(
    label: String,
    selected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val colors = MaterialTheme.colorScheme
    Surface(
        modifier = modifier.selectable(selected = selected, onClick = onClick, role = Role.RadioButton),
        shape = CircleShape,
        color = if (selected) colors.primary else colors.surface,
        contentColor = if (selected) colors.onPrimary else colors.onSurface,
        border = if (selected) null else BorderStroke(1.dp, colors.outline),
    ) {
        Text(
            text = label,
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
        )
    }
}

/** 끼니 종류 4개 중 하나를 고르는 칩 묶음. */
@Composable
fun MealTypeSelector(
    selected: MealType?,
    onSelect: (MealType) -> Unit,
    modifier: Modifier = Modifier,
) {
    Row(modifier = modifier, horizontalArrangement = Arrangement.spacedBy(8.dp)) {
        MealType.entries.forEach { type ->
            SelectChip(label = type.label, selected = type == selected, onClick = { onSelect(type) })
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun MealTypeSelectorPreview() {
    DietTheme { MealTypeSelector(selected = MealType.LUNCH, onSelect = {}) }
}
