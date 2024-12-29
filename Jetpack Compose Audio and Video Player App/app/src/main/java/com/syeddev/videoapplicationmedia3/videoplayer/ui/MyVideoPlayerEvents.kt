package com.syeddev.videoapplicationmedia3.videoplayer.ui

import android.net.Uri

sealed class MyVideoPlayerEvents {
    data class OnUrlTextChanged(val url : String) : MyVideoPlayerEvents()
    data object OnGetUriFromFile : MyVideoPlayerEvents()
    data class OnAddMediaUri(val videoUri : List<Uri>) : MyVideoPlayerEvents()

    data class OnPlayVideo(val currentVideoIndex : Int) : MyVideoPlayerEvents()
    data class OnPauseVideo(val currentVideoIndex : Int) : MyVideoPlayerEvents()
    data class OnPlayNextVideo(val currentVideoIndex : Int) : MyVideoPlayerEvents()
    data class OnPlayPreviousVideo(val currentVideoIndex : Int) : MyVideoPlayerEvents()

    data object OnPlayVideoFromUrl : MyVideoPlayerEvents()
}