package com.syeddev.videoapplicationmedia3

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.ui.Modifier
import com.syeddev.videoapplicationmedia3.ui.theme.VideoApplicationMedia3Theme
import com.syeddev.videoapplicationmedia3.videoplayer.ui.MyVideoPlayerScreen
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            VideoApplicationMedia3Theme {
                MyVideoPlayerScreen(
                    modifier = Modifier,
                )
            }
        }
    }
}

