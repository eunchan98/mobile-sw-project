package com.mobilesw.diet

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.mobilesw.diet.ui.navigation.DietApp
import com.mobilesw.diet.ui.theme.DietTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            DietTheme {
                DietApp()
            }
        }
    }
}
