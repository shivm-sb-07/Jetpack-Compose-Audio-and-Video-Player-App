package com.syeddev.videoapplicationmedia3.videoplayer.ui

import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.media3.ui.PlayerView

@Composable
fun MyVideoPlayerScreen(
    modifier: Modifier = Modifier,
    myVideoPlayerViewModel: MyVideoPlayerViewModel = hiltViewModel(),
) {
    val uiState by myVideoPlayerViewModel.mediaUiState.collectAsState()

    var lifecycle by remember { mutableStateOf(Lifecycle.Event.ON_CREATE) }

    val lifecycleOwner = LocalLifecycleOwner.current

    DisposableEffect(lifecycleOwner) {
        val observer = LifecycleEventObserver { _, event ->
            lifecycle = event
        }
        lifecycleOwner.lifecycle.addObserver(observer)

        onDispose {
            lifecycleOwner.lifecycle.removeObserver(observer)
        }
    }

    val getMultipleVideos = rememberLauncherForActivityResult(contract = ActivityResultContracts.GetMultipleContents()) { uris ->
        if (uris.isNotEmpty()){
            myVideoPlayerViewModel.onEvents(MyVideoPlayerEvents.OnAddMediaUri(videoUri = uris))
        } else {
            Log.e("getContent","Please Select Files..")
        }
    }
    Column(
        modifier = modifier
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        AndroidView(
            factory = { context ->
                PlayerView(context).also {
                    it.player = myVideoPlayerViewModel.player
                }
            },
            update = {
                when(lifecycle){
                    Lifecycle.Event.ON_PAUSE -> {
                        it.onPause()
                        it.player?.pause()
                    }

                    Lifecycle.Event.ON_RESUME -> {
                        it.onResume()
                    }

                    else -> Unit
                }
            },
            modifier = modifier
                .fillMaxWidth()
                .aspectRatio(16 / 9f)
        )
        Spacer(modifier = modifier.padding(10.dp))
        OutlinedTextField(
            value = uiState.urlLink,
            onValueChange = { url ->
                myVideoPlayerViewModel.onEvents(MyVideoPlayerEvents.OnUrlTextChanged(url = url))
            }
        )
        Spacer(modifier = modifier.padding(10.dp))
        TextButton(onClick = {
            myVideoPlayerViewModel.onEvents(MyVideoPlayerEvents.OnPlayVideoFromUrl)
        }) {
            Text(text = "Play Video")
        }
        Spacer(modifier = modifier.padding(10.dp))
        TextButton(onClick = {
            getMultipleVideos.launch("video/*")
        }) {
            Text(text = "Get Videos")
        }
        LazyColumn {
            itemsIndexed(uiState.videoItems){ index,item ->
                Card(
                    modifier = modifier
                        .fillMaxWidth(),
                    shape = RoundedCornerShape(10.dp),
                    border = BorderStroke(.5.dp, color = Color.LightGray),
                ) {
                    Row (
                        modifier = modifier
                            .fillMaxWidth()
                            .padding(10.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        Text(
                            text = "${index+1}.",
                            fontWeight = FontWeight.Bold,
                            fontSize = 16.sp
                        )
                        Column (
                            modifier = modifier
                                .fillMaxWidth()
                        ){
                            Text(text = "Name : ${item.name}")
                            Text(text = "Size : ")
                        }
                        IconButton(onClick = {
                            myVideoPlayerViewModel.onEvents(MyVideoPlayerEvents.OnPlayVideo(currentVideoIndex = index))
                        }) {
                            Icon(
                                Icons.Default.PlayArrow,
                                contentDescription = "Play"
                            )
                        }
                    }
                }
            }
        }
    }
}