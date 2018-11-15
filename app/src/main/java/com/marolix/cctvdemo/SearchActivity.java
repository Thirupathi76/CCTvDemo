package com.marolix.cctvdemo;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

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
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultHttpDataSourceFactory;

import java.util.Arrays;
import java.util.Random;

public class SearchActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    LinearLayout searchLayout;
    LinearLayout camLayout;
    Spinner country_spin, language_spin, state_spin, hero_spin, director_spin, heroine_spin;
    Button submit;
    ImageView searchImage;

    String country_selected = "", language_selected = "", state_selected = "",
            hero_selected = "", director_selected = "", heroine_selected = "";

    SimpleExoPlayerView exoPlayerView;
    SimpleExoPlayer exoPlayer;
    int[] array = {0,1,2,3,4,5,6,7};
    ProgressDialog dialog;

    String[] vidUrl = {"http://commondatastorage.googleapis.com/gtv-videos-bucket/sample/ForBiggerMeltdowns.mp4",
            "http://commondatastorage.googleapis.com/gtv-videos-bucket/sample/BigBuckBunny.mp4",
            "http://commondatastorage.googleapis.com/gtv-videos-bucket/sample/TearsOfSteel.mp4",
            "http://commondatastorage.googleapis.com/gtv-videos-bucket/sample/images/ForBiggerJoyrides.jpg",
            "http://commondatastorage.googleapis.com/gtv-videos-bucket/sample/ElephantsDream.mp4",
            "http://commondatastorage.googleapis.com/gtv-videos-bucket/sample/ForBiggerBlazes.mp4",
            "http://commondatastorage.googleapis.com/gtv-videos-bucket/sample/VolkswagenGTIReview.mp4",
            "http://commondatastorage.googleapis.com/gtv-videos-bucket/sample/WeAreGoingOnBullrun.mp4",
            "http://commondatastorage.googleapis.com/gtv-videos-bucket/sample/WhatCarCanYouGetForAGrand.mp4"};

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
        setContentView(R.layout.activity_search);

        country_spin = findViewById(R.id.spinner_country);
        language_spin = findViewById(R.id.spinner_language);
        state_spin = findViewById(R.id.spinner_state);
        hero_spin = findViewById(R.id.spinner_hero);
        director_spin = findViewById(R.id.spinner_director);
        heroine_spin = findViewById(R.id.spinner_heroine);

        submit = findViewById(R.id.button_submit);
        country_spin.setOnItemSelectedListener(this);
        language_spin.setOnItemSelectedListener(this);
        state_spin.setOnItemSelectedListener(this);
        hero_spin.setOnItemSelectedListener(this);
        director_spin.setOnItemSelectedListener(this);
        heroine_spin.setOnItemSelectedListener(this);

        camLayout = findViewById(R.id.videoLayout);
        searchLayout = findViewById(R.id.search_layout);
        searchImage = findViewById(R.id.search_image);

        exoPlayerView = (SimpleExoPlayerView) findViewById(R.id.exo_player_view);
        exoPlayerView1 = (SimpleExoPlayerView) findViewById(R.id.exo_player_view1);
        exoPlayerView2 = (SimpleExoPlayerView) findViewById(R.id.exo_player_view2);

        dialog = new ProgressDialog(SearchActivity.this);

        dialog.setTitle("Please wait");
        dialog.setMessage("Preparing resources...");
        dialog.setCancelable(false);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validSpinners()) {


                    searchLayout.setVisibility(View.GONE);

                   final int one =  array[new Random().nextInt(array.length)];
                   final int two =  array[new Random().nextInt(array.length)];
                   final int three =  array[new Random().nextInt(array.length)];

                    dialog.show();
                    new Handler().postDelayed(new Runnable() {
                        public void run() {
                            dialog.dismiss();
                            searchImage.setVisibility(View.VISIBLE);
                            camLayout.setVisibility(View.VISIBLE);
                            showCams(one, two, three);
                        }
                    }, 3000);


                }
            }
        });

        searchImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchLayout.setVisibility(View.VISIBLE);
                camLayout.setVisibility(View.GONE);
                searchImage.setVisibility(View.GONE);
            }
        });
        setSpinners();
    }

    private void showCams(final int one, final int two, final int three) {
        try {

            DefaultBandwidthMeter bandwidthMeter = new DefaultBandwidthMeter();
            TrackSelector trackSelector = new DefaultTrackSelector(new AdaptiveTrackSelection.Factory(bandwidthMeter));
            exoPlayer = ExoPlayerFactory.newSimpleInstance(this, trackSelector);
            exoPlayer1 = ExoPlayerFactory.newSimpleInstance(this, trackSelector);
            exoPlayer2 = ExoPlayerFactory.newSimpleInstance(this, trackSelector);

            Uri videoURI = Uri.parse(vidUrl[one]);
            Uri videoURI1 = Uri.parse(vidUrl[two]);
            Uri videoURI2 = Uri.parse(vidUrl[three]);

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
                    Intent intent = new Intent(SearchActivity.this, VideoPlayerActivity.class);
                    intent.putExtra("VID_URL", vidUrl[one]);
                    startActivity(intent);
                    return true;
                }

            });

            exoPlayerView1.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    Intent intent = new Intent(SearchActivity.this, VideoPlayerActivity.class);
                    intent.putExtra("VID_URL", vidUrl[two]);
                    startActivity(intent);
                    return true;
                }

            });

            exoPlayerView2.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    Intent intent = new Intent(SearchActivity.this, VideoPlayerActivity.class);
                    intent.putExtra("VID_URL", vidUrl[three]);
                    startActivity(intent);
                    return true;
                }

            });


        } catch (Exception e) {
            Log.e("MainAcvtivity", " exoplayer error " + e.toString());

        }
    }

    private boolean validSpinners() {
        if (country_spin.getSelectedItemPosition() == 0) {
            Toast.makeText(this, "Select Country", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (state_spin.getSelectedItemPosition() == 0) {
            Toast.makeText(this, "Select State", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (language_spin.getSelectedItemPosition() == 0) {
            Toast.makeText(this, "Select Language", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (hero_spin.getSelectedItemPosition() == 0) {
            Toast.makeText(this, "Select Hero", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (director_spin.getSelectedItemPosition() == 0) {
            Toast.makeText(this, "Select Director", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (heroine_spin.getSelectedItemPosition() == 0) {
            Toast.makeText(this, "Select Heroine", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    private void setSpinners() {
        ArrayAdapter<String> spinnerCountShoesArrayAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_dropdown_item, getResources().getStringArray(R.array.array_country));
        country_spin.setAdapter(spinnerCountShoesArrayAdapter);
        /*ArrayAdapter<String> spin_language = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_dropdown_item, getResources().getStringArray(R.array.string_array));
        language_spin.setAdapter(spin_language);

        ArrayAdapter<String> spin_state = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_dropdown_item, getResources().getStringArray(R.array.string_array));
        state_spin.setAdapter(spin_state);

        ArrayAdapter<String> spin_hero = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_dropdown_item, getResources().getStringArray(R.array.string_array));
        hero_spin.setAdapter(spin_hero);
        ArrayAdapter<String> spin_director = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_dropdown_item, getResources().getStringArray(R.array.string_array));
        director_spin.setAdapter(spin_director);

        ArrayAdapter<String> spin_heroine = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_dropdown_item, getResources().getStringArray(R.array.string_array));
        heroine_spin.setAdapter(spin_heroine);*/
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        switch (parent.getId()) {
            case R.id.spinner_country:
                ArrayAdapter<String> spin_language = null;
                if (country_spin.getSelectedItemPosition() == 0) {
                    spin_language = new ArrayAdapter<String>(this,
                            android.R.layout.simple_spinner_dropdown_item, getResources().getStringArray(R.array.string_array));

//                    Toast.makeText(this, "Please select the language", Toast.LENGTH_SHORT).show();
                } else if (country_spin.getSelectedItemPosition() == 1) {
                    spin_language = new ArrayAdapter<String>(this,
                            android.R.layout.simple_spinner_dropdown_item, getResources().getStringArray(R.array.language_india));

                } else if (country_spin.getSelectedItemPosition() == 2) {
                    spin_language = new ArrayAdapter<String>(this,
                            android.R.layout.simple_spinner_dropdown_item, getResources().getStringArray(R.array.language_english));

                } else if (country_spin.getSelectedItemPosition() == 3) {
                    spin_language = new ArrayAdapter<String>(this,
                            android.R.layout.simple_spinner_dropdown_item, getResources().getStringArray(R.array.language_english));

                } else {
                    spin_language = new ArrayAdapter<String>(this,
                            android.R.layout.simple_spinner_dropdown_item, getResources().getStringArray(R.array.string_array));

                }

//                language_spin.setSelection(0);
               /* state_spin.setSelection(0);
                hero_spin.setSelection(0);
                director_spin.setSelection(0);
                heroine_spin.setSelection(0);*/

                country_selected = country_spin.getSelectedItem().toString();
                language_spin.setAdapter(spin_language);
                break;

            case R.id.spinner_language:

                ArrayAdapter<String> spin_state = null;
                if (language_spin.getSelectedItemPosition() == 0) {
//                    Toast.makeText(this, "Please select the state", Toast.LENGTH_SHORT).show();
                    spin_state = new ArrayAdapter<String>(this,
                            android.R.layout.simple_spinner_dropdown_item, getResources().getStringArray(R.array.string_array));
                } else if (language_spin.getSelectedItemPosition() == 1 && country_selected.equals("India")) {
                    spin_state = new ArrayAdapter<String>(this,
                            android.R.layout.simple_spinner_dropdown_item, getResources().getStringArray(R.array.state_india_hindi));

                } else if (language_spin.getSelectedItemPosition() == 1 && country_selected.equals("Australia")) {
                    spin_state = new ArrayAdapter<String>(this,
                            android.R.layout.simple_spinner_dropdown_item, getResources().getStringArray(R.array.state_australia));

                } else if (language_spin.getSelectedItemPosition() == 1 && country_selected.equals("USA")) {
                    spin_state = new ArrayAdapter<String>(this,
                            android.R.layout.simple_spinner_dropdown_item, getResources().getStringArray(R.array.state_usa));

                } else if (language_spin.getSelectedItemPosition() == 2) {
                    spin_state = new ArrayAdapter<String>(this,
                            android.R.layout.simple_spinner_dropdown_item, getResources().getStringArray(R.array.state_india_telugu));

                } else {
                    spin_state = new ArrayAdapter<String>(this,
                            android.R.layout.simple_spinner_dropdown_item, getResources().getStringArray(R.array.string_array));

                }
               /* state_spin.setSelection(0);
                hero_spin.setSelection(0);
                director_spin.setSelection(0);
                heroine_spin.setSelection(0);*/
                language_selected = language_spin.getSelectedItem().toString();
                state_spin.setAdapter(spin_state);

                break;
            case R.id.spinner_state:

                if (language_selected != null) {

                    ArrayAdapter<String> spin_hero = null;
                    state_selected = state_spin.getSelectedItem().toString();
                    if (state_spin.getSelectedItemPosition() == 0) {
//                        Toast.makeText(this, "Select the state", Toast.LENGTH_SHORT).show();
                        spin_hero = new ArrayAdapter<String>(this,
                                android.R.layout.simple_spinner_dropdown_item, getResources().getStringArray(R.array.string_array));
                    } else if (Arrays.asList("Delhi", "Rajasthan", "Utther Pradesh").contains(state_selected) && country_selected.equals("India")) {
                        spin_hero = new ArrayAdapter<String>(this,
                                android.R.layout.simple_spinner_dropdown_item, getResources().getStringArray(R.array.hero_hindi));
                    } else if (Arrays.asList("Andhra Pradesh", "Telangana").contains(state_selected) && country_selected.equals("India")) {
                        spin_hero = new ArrayAdapter<String>(this,
                                android.R.layout.simple_spinner_dropdown_item, getResources().getStringArray(R.array.hero_telugu));
                    } else if (Arrays.asList("California", "NYC", "LasVegas", "Sydney", "Melbourne", "Canberra").contains(state_selected) && (country_selected.equals("Australia") || country_selected.equals("USA"))) {
                        spin_hero = new ArrayAdapter<String>(this,
                                android.R.layout.simple_spinner_dropdown_item, getResources().getStringArray(R.array.hero_english));
                    } else {
                        spin_hero = new ArrayAdapter<String>(this,
                                android.R.layout.simple_spinner_dropdown_item, getResources().getStringArray(R.array.string_array));

                    }
                    /*hero_spin.setSelection(0);
                    director_spin.setSelection(0);
                    heroine_spin.setSelection(0);*/
                    hero_spin.setAdapter(spin_hero);
                }


                break;
            case R.id.spinner_hero:

                if (language_selected != null) {

                    ArrayAdapter<String> spin_director = null;
                    state_selected = state_spin.getSelectedItem().toString();
                    if (state_spin.getSelectedItemPosition() == 0) {
//                        Toast.makeText(this, "Select the state", Toast.LENGTH_SHORT).show();
                        spin_director = new ArrayAdapter<String>(this,
                                android.R.layout.simple_spinner_dropdown_item, getResources().getStringArray(R.array.string_array));
                    } else if (Arrays.asList("Delhi", "Rajasthan", "Utther Pradesh").contains(state_selected) && country_selected.equals("India")) {
                        spin_director = new ArrayAdapter<String>(this,
                                android.R.layout.simple_spinner_dropdown_item, getResources().getStringArray(R.array.directors_hindi));
                    } else if (Arrays.asList("Andhra Pradesh", "Telangana").contains(state_selected) && country_selected.equals("India")) {
                        spin_director = new ArrayAdapter<String>(this,
                                android.R.layout.simple_spinner_dropdown_item, getResources().getStringArray(R.array.directors_telugu));
                    } else if (Arrays.asList("California", "NYC", "LasVegas", "Sydney", "Melbourne", "Canberra").contains(state_selected) && (country_selected.equals("Australia") || country_selected.equals("USA"))) {
                        spin_director = new ArrayAdapter<String>(this,
                                android.R.layout.simple_spinner_dropdown_item, getResources().getStringArray(R.array.directors_english));
                    } else {
                        spin_director = new ArrayAdapter<String>(this,
                                android.R.layout.simple_spinner_dropdown_item, getResources().getStringArray(R.array.string_array));

                    }
                   /* director_spin.setSelection(0);
                    heroine_spin.setSelection(0);*/
                    hero_selected = hero_spin.getSelectedItem().toString();
                    director_spin.setAdapter(spin_director);
                }


                break;
            case R.id.spinner_director:

                if (language_selected != null) {

                    ArrayAdapter<String> spin_heroine = null;
                    state_selected = state_spin.getSelectedItem().toString();
                    if (state_spin.getSelectedItemPosition() == 0) {
//                        Toast.makeText(this, "Select the state", Toast.LENGTH_SHORT).show();
                        spin_heroine = new ArrayAdapter<String>(this,
                                android.R.layout.simple_spinner_dropdown_item, getResources().getStringArray(R.array.string_array));
                    } else if (Arrays.asList("Delhi", "Rajasthan", "Utther Pradesh").contains(state_selected) && country_selected.equals("India")) {
                        spin_heroine = new ArrayAdapter<String>(this,
                                android.R.layout.simple_spinner_dropdown_item, getResources().getStringArray(R.array.heroine_hindi));
                    } else if (Arrays.asList("Andhra Pradesh", "Telangana").contains(state_selected) && country_selected.equals("India")) {
                        spin_heroine = new ArrayAdapter<String>(this,
                                android.R.layout.simple_spinner_dropdown_item, getResources().getStringArray(R.array.heroine_telugu));
                    } else if (Arrays.asList("California", "NYC", "LasVegas", "Sydney", "Melbourne", "Canberra").contains(state_selected) && (country_selected.equals("Australia") || country_selected.equals("USA"))) {
                        spin_heroine = new ArrayAdapter<String>(this,
                                android.R.layout.simple_spinner_dropdown_item, getResources().getStringArray(R.array.heroine_english));
                    } else {
                        spin_heroine = new ArrayAdapter<String>(this,
                                android.R.layout.simple_spinner_dropdown_item, getResources().getStringArray(R.array.string_array));

                    }
//                    heroine_spin.setSelection(0);
                    director_selected = director_spin.getSelectedItem().toString();
                    heroine_spin.setAdapter(spin_heroine);
                }


                break;
            case R.id.spinner_heroine:
                heroine_selected = heroine_spin.getSelectedItem().toString();
                break;
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    @Override
    protected void onPause() {
        super.onPause();
        if (exoPlayer != null && exoPlayer1 != null && exoPlayer2 != null) {
            exoPlayer.setPlayWhenReady(false);
            exoPlayer1.setPlayWhenReady(false);
            exoPlayer2.setPlayWhenReady(false);
        }
    }
}
