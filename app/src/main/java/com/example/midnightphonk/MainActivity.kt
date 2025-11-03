package com.example.midnightphonk

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.Surface
import com.example.midnightphonk.ui.theme.MidnightPhonkTheme
import navigation.AppNavigation

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MidnightPhonkTheme {
                Surface {
                    AppNavigation()
                }
            }
        }
    }
}