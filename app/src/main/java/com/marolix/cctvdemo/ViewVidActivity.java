package com.marolix.cctvdemo;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;

import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.extractor.ExtractorsFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.trackselection.AdaptiveTrackSelection;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.google.android.exoplayer2.upstream.BandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultHttpDataSourceFactory;

public class ViewVidActivity extends AppCompatActivity {

    SimpleExoPlayerView exoPlayerView;
    SimpleExoPlayer exoPlayer;

    String videoURL = "http://commondatastorage.googleapis.com/gtv-videos-bucket/sample/BigBuckBunny.mp4";
    SimpleExoPlayerView exoPlayerView1;
    SimpleExoPlayer exoPlayer1;
    String videoURL1 = "http://commondatastorage.googleapis.com/gtv-videos-bucket/sample/ForBiggerMeltdowns.mp4";

    SimpleExoPlayerView exoPlayerView2;
    SimpleExoPlayer exoPlayer2;
    String videoURL2 = "http://commondatastorage.googleapis.com/gtv-videos-bucket/sample/Sintel.mp4";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_vid);
        exoPlayerView = (SimpleExoPlayerView) findViewById(R.id.exo_player_view);
        exoPlayerView1 = (SimpleExoPlayerView) findViewById(R.id.exo_player_view1);
        exoPlayerView2 = (SimpleExoPlayerView) findViewById(R.id.exo_player_view2);


        try {

            DefaultBandwidthMeter bandwidthMeter = new DefaultBandwidthMeter();
            TrackSelector trackSelector = new DefaultTrackSelector(new AdaptiveTrackSelection.Factory(bandwidthMeter));
            exoPlayer = ExoPlayerFactory.newSimpleInstance(this, trackSelector);
            exoPlayer1 = ExoPlayerFactory.newSimpleInstance(this, trackSelector);
            exoPlayer2 = ExoPlayerFactory.newSimpleInstance(this, trackSelector);

            Uri videoURI = Uri.parse(videoURL);
            Uri videoURI1 = Uri.parse(videoURL1);
            Uri videoURI2 = Uri.parse(videoURL2);

            DefaultHttpDataSourceFactory dataSourceFactory = new DefaultHttpDataSourceFactory("exoplayer_video");
            ExtractorsFactory extractorsFactory = new DefaultExtractorsFactory();
            MediaSource mediaSource = new ExtractorMediaSource(videoURI, dataSourceFactory, extractorsFactory, null, null);
            MediaSource mediaSource1 = new ExtractorMediaSource(videoURI1, dataSourceFactory, extractorsFactory, null, null);
            MediaSource mediaSource2 = new ExtractorMediaSource(videoURI2, dataSourceFactory, extractorsFactory, null, null);

            exoPlayerView.setPlayer(exoPlayer);
            exoPlayerView1.setPlayer(exoPlayer1);
            exoPlayerView2.setPlayer(exoPlayer2);
            exoPlayer.prepare(mediaSource);
            exoPlayer1.prepare(mediaSource1);
            exoPlayer2.prepare(mediaSource2);

            exoPlayer.setVolume(0.0f);
            exoPlayer1.setVolume(0.0f);
            exoPlayer2.setVolume(0.0f);

            exoPlayer.setPlayWhenReady(true);
            exoPlayer1.setPlayWhenReady(true);
            exoPlayer2.setPlayWhenReady(true);
            exoPlayerView.setUseController(false);
            exoPlayerView1.setUseController(false);
            exoPlayerView2.setUseController(false);

            exoPlayerView.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    Intent intent = new Intent(ViewVidActivity.this, VideoPlayerActivity.class);
                    intent.putExtra("VID_URL", videoURL);
                    startActivity(intent);
                    return true;
                }

            });

            exoPlayerView1.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    Intent intent = new Intent(ViewVidActivity.this, VideoPlayerActivity.class);
                    intent.putExtra("VID_URL", videoURL1);
                    startActivity(intent);
                    return true;
                }

            });

            exoPlayerView2.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    Intent intent = new Intent(ViewVidActivity.this, VideoPlayerActivity.class);
                    intent.putExtra("VID_URL", videoURL2);
                    startActivity(intent);
                    return true;
                }

            });


        } catch (Exception e) {
            Log.e("MainAcvtivity", " exoplayer error " + e.toString());

        }


    }

    @Override
    protected void onPause() {
        super.onPause();
        if (exoPlayer!=null && exoPlayer1!=null && exoPlayer2!=null) {
            exoPlayer.setPlayWhenReady(false);
            exoPlayer1.setPlayWhenReady(false);
            exoPlayer2.setPlayWhenReady(false);
        }
    }
}
