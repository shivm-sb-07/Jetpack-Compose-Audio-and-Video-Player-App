package com.syeddev.videoapplicationmedia3.videoplayer.ui
import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.media3.common.MediaItem
import androidx.media3.common.Player
import com.syeddev.videoapplicationmedia3.videoplayer.model.VideoItem
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class MyVideoPlayerViewModel @Inject constructor(
    val player : Player,
):ViewModel() {

    private var _mediaUiState = MutableStateFlow(MyVideoPlayerState())
    val mediaUiState = _mediaUiState.asStateFlow()

    init {
        player.prepare()
    }

    override fun onCleared() {
        super.onCleared()
        player.release()
    }

    fun onEvents(events : MyVideoPlayerEvents){
        when(events){
            is MyVideoPlayerEvents.OnAddMediaUri -> {
                addVideoUri(uri = events.videoUri)
            }
            is MyVideoPlayerEvents.OnGetUriFromFile -> {

            }
            is MyVideoPlayerEvents.OnPauseVideo -> {

            }
            is MyVideoPlayerEvents.OnPlayNextVideo -> {

            }
            is MyVideoPlayerEvents.OnPlayPreviousVideo -> {

            }
            is MyVideoPlayerEvents.OnPlayVideo -> {

            }
            is MyVideoPlayerEvents.OnUrlTextChanged -> {
                _mediaUiState.update { it.copy(urlLink = events.url) }
            }

            is MyVideoPlayerEvents.OnPlayVideoFromUrl -> {
                player.setMediaItem(MediaItem.fromUri(mediaUiState.value.urlLink))
                player.playWhenReady = true
            }
        }
    }

    private fun addVideoUri(uri: List<Uri>){
        player.addMediaItems(uri.map { MediaItem.fromUri(it) })
    }
}

data class MyVideoPlayerState(
    var isLoading : Boolean = false,
    var urlLink : String = "",
    val videoItems : ArrayList<VideoItem> = arrayListOf(),
)