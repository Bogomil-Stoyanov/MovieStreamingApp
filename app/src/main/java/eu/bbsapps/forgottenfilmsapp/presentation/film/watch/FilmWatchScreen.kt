package eu.bbsapps.forgottenfilmsapp.presentation.film.watch

import android.annotation.SuppressLint
import android.content.pm.ActivityInfo
import android.net.Uri
import android.view.WindowManager
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Surface
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.viewinterop.AndroidView
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleOwner
import androidx.navigation.NavController
import com.google.android.exoplayer2.SimpleExoPlayer
import com.google.android.exoplayer2.source.ProgressiveMediaSource
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector
import com.google.android.exoplayer2.ui.PlayerView
import com.google.android.exoplayer2.upstream.DataSource
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory
import com.google.android.exoplayer2.util.Util
import eu.bbsapps.forgottenfilmsapp.presentation.LockScreenOrientation
import eu.bbsapps.forgottenfilmsapp.presentation.findActivity
import kotlinx.coroutines.delay

@SuppressLint("SourceLockedOrientationActivity")
@Composable
fun FilmWatchScreen(
    navController: NavController,
    viewModel: FilmWatchViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    var isComposableRunning = true

    val activity = context.findActivity()
    activity?.window?.setFlags(
        WindowManager.LayoutParams.FLAG_FULLSCREEN,
        WindowManager.LayoutParams.FLAG_FULLSCREEN
    )

    KeepScreenOn()
    LockScreenOrientation(ActivityInfo.SCREEN_ORIENTATION_USER_LANDSCAPE)

    val customCoroutineState = remember {
        mutableStateOf(1)
    }

    val exoPlayer: SimpleExoPlayer?
    if (viewModel.exoPlayer == null) {
        exoPlayer = remember(context) {
            val trackSelector = DefaultTrackSelector(context)
            trackSelector.setParameters(trackSelector.buildUponParameters().setMaxVideoSizeSd())

            SimpleExoPlayer.Builder(context).setTrackSelector(trackSelector).build().apply {
                val dataSourceFactory: DataSource.Factory = DefaultDataSourceFactory(
                    context,
                    Util.getUserAgent(context, context.packageName)
                )

                val source = ProgressiveMediaSource.Factory(dataSourceFactory)
                    .createMediaSource(
                        Uri.parse(
                            viewModel.movieUrl
                        )
                    )
                prepare(source)
            }
        }
    } else {
        exoPlayer = viewModel.exoPlayer
    }
    LaunchedEffect(key1 = exoPlayer) {
        viewModel.exoPlayer = exoPlayer
    }

    val lifecycleOwner: LifecycleOwner = LocalLifecycleOwner.current

    DisposableEffect(lifecycleOwner) {
        val observer = LifecycleEventObserver { _, event ->
            if (event == Lifecycle.Event.ON_START) {
                exoPlayer?.playWhenReady = true
                customCoroutineState.value++
            } else if (event == Lifecycle.Event.ON_PAUSE) {
                if (isComposableRunning) {
                    exoPlayer?.playWhenReady = false
                    viewModel.uploadWatchTime()
                }
            }
        }
        lifecycleOwner.lifecycle.addObserver(observer)
        onDispose {
            lifecycleOwner.lifecycle.removeObserver(observer)
        }
    }

    LaunchedEffect(Unit) {
        while (isComposableRunning) {
            delay(1000)
            if (exoPlayer?.isPlaying == true) {
                viewModel.elapsedSecondWatching()
            }
        }
    }

    Surface(color = Color.Black) {
        Box(modifier = Modifier
            .fillMaxSize()
            .clickable { viewModel.uploadWatchTime() }) {
            BackHandler {
                viewModel.uploadWatchTime()
                exoPlayer?.stop()
                isComposableRunning = false
                activity?.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
                navController.navigateUp()
            }
            AndroidView(factory = { context ->
                PlayerView(context).apply {
                    player = exoPlayer
                    player?.playWhenReady = true
                    this.setShowBuffering(PlayerView.SHOW_BUFFERING_ALWAYS)
                }
            }, modifier = Modifier.fillMaxSize())
        }
    }
}