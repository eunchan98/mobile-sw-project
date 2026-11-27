package com.mobilesw.diet.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.mobilesw.diet.R
import com.mobilesw.diet.ui.theme.DietTheme
import com.mobilesw.diet.ui.theme.MintGreen

/**
 * 한 끼 요약 카드 (Figma: MealCard). 홈과 식사 목록에서 쓴다.
 * 표시할 문자열은 호출하는 쪽에서 만들어 넘긴다.
 *
 * @param mealTypeLabel 예: "중식"
 * @param time 예: "12:30"
 * @param foods 예: "제육볶음, 쌀밥, 미역국"
 * @param macros 예: "탄 98g · 단 34g · 지 21g"
 * @param kcal 예: "720"
 * @param photoUri 사진이 없으면 null (그릇 아이콘 표시)
 */
@Composable
fun MealCard(
    mealTypeLabel: String,
    time: String,
    foods: String,
    macros: String,
    kcal: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    photoUri: String? = null,
) {
    Surface(
        onClick = onClick,
        modifier = modifier.fillMaxWidth(),
        shape = MaterialTheme.shapes.medium,
        color = MaterialTheme.colorScheme.surface,
        border = BorderStroke(1.dp, MaterialTheme.colorScheme.outline),
    ) {
        Row(
            modifier = Modifier.padding(12.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(12.dp),
        ) {
            MealPhoto(photoUri = photoUri, modifier = Modifier.size(64.dp))
            Column(modifier = Modifier.weight(1f), verticalArrangement = Arrangement.spacedBy(2.dp)) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    MealTypeTag(mealTypeLabel)
                    Spacer(Modifier.width(6.dp))
                    Text(time, style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
                }
                Text(foods, style = MaterialTheme.typography.bodyLarge, maxLines = 1, overflow = TextOverflow.Ellipsis)
                Text(macros, style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
            }
            Column(horizontalAlignment = Alignment.End) {
                Text(kcal, style = MaterialTheme.typography.titleMedium)
                Text("kcal", style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
            }
        }
    }
}

/** 연한 핑크 바탕의 끼니 태그 (예: "중식"). */
@Composable
fun MealTypeTag(label: String, modifier: Modifier = Modifier) {
    Text(
        text = label,
        style = MaterialTheme.typography.labelSmall,
        color = MaterialTheme.colorScheme.primary,
        modifier = modifier
            .clip(RoundedCornerShape(8.dp))
            .background(MaterialTheme.colorScheme.primaryContainer)
            .padding(horizontal = 8.dp, vertical = 2.dp),
    )
}

/** 식사 사진. 사진이 없으면 민트 바탕에 그릇 아이콘을 보여준다. */
@Composable
fun MealPhoto(photoUri: String?, modifier: Modifier = Modifier, iconSize: Int = 24) {
    val shape = RoundedCornerShape(12.dp)
    if (photoUri != null) {
        AsyncImage(
            model = photoUri,
            contentDescription = "식사 사진",
            contentScale = ContentScale.Crop,
            modifier = modifier.clip(shape),
        )
    } else {
        Box(modifier = modifier.clip(shape).background(MintGreen), contentAlignment = Alignment.Center) {
            Icon(
                painterResource(R.drawable.ic_bowl),
                contentDescription = "사진 없음",
                tint = MaterialTheme.colorScheme.onSurface,
                modifier = Modifier.size(iconSize.dp),
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun MealCardPreview() {
    DietTheme {
        MealCard(
            mealTypeLabel = "중식",
            time = "12:30",
            foods = "제육볶음, 쌀밥, 미역국",
            macros = "탄 98g · 단 34g · 지 21g",
            kcal = "720",
            onClick = {},
            modifier = Modifier.padding(16.dp),
        )
    }
}
