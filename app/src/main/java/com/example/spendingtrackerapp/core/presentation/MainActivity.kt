package com.example.spendingtrackerapp.core.presentation

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.spendingtrackerapp.core.presentation.ui.theme.SpendingTrackerAppTheme
import com.example.spendingtrackerapp.home.presentation.HomeScreen
import com.example.spendingtrackerapp.my_balance.presentation.MyBalanceScreen
import com.example.spendingtrackerapp.my_balance.presentation.MyBalanceState
import com.example.spendingtrackerapp.utils.Navigation

class MainActivity : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            SpendingTrackerAppTheme {
            Navigation()

            }
        }
    }
}

