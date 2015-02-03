package com.supafly.swampdefense;

import android.content.Context;
import android.content.SharedPreferences;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;


public class MainActivity extends FragmentActivity {

    CanvasView canvasView;
    String scoreString;
    boolean isPaused = false;
    MediaPlayer song;
    MediaPlayer bite;
    MediaPlayer growl;
    AudioManager audio;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        audio =  (AudioManager) this.getSystemService(Context.AUDIO_SERVICE);

        //initialize the adView by finding it in xml and making a new adRequest
        //Then build the request and load the advertisement
        AdView mAdView = (AdView) findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

        canvasView = (CanvasView)findViewById(R.id.canvasView);

    }

    @Override
    protected void onPause() {
        super.onPause();

        SharedPreferences.Editor editor = getPreferences(MODE_PRIVATE).edit();
        editor.putInt("highScore", canvasView.gameController.highScore);
        editor.commit();

        TopFragment topFragment = (TopFragment) this.getSupportFragmentManager().findFragmentById(R.id.top_fragment);
        //if not paused, pause the game
        if(!this.isPaused) {
            topFragment.pause();
        }

        song.stop();
        song.release();
        bite.release();
        growl.release();
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.e("Song resume", "song resume");
        bite = MediaPlayer.create(this,R.raw.bite);
        growl = MediaPlayer.create(this,R.raw.growl);
        song = MediaPlayer.create(this,R.raw.song);
        song.setLooping(true);
        song.start();
    }
}
