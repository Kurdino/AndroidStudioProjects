package com.example.myconnect4

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.myconnect4.ui.theme.MyConnect4Theme


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyConnect4Theme {
                AppNavigation()
            }
        }
    }
}






