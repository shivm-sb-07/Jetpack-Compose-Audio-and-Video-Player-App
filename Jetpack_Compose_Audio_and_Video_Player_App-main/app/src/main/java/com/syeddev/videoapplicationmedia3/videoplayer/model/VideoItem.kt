package com.syeddev.videoapplicationmedia3.videoplayer.model

import android.net.Uri
import androidx.media3.common.MediaItem

data class VideoItem(
    val name : String,
    val mediaItem: MediaItem,
    val contentUri : Uri,
)
