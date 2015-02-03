package com.supafly.swampdefense;

import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by Ryan on 11/15/2014.
 */
public class BottomFragment extends Fragment {

    PlayFragment playFragment;
    CanvasView canvasView;
    TextView score;
    TextView highScore;
    Button chomp;
    Button growl;
    Button alberta;
    Button tebow;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_bottom, container, false);
        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        playFragment = (PlayFragment) getFragmentManager().findFragmentById(R.id.play_fragment);
        canvasView = playFragment.canvasView;
        score = (TextView) getView().findViewById(R.id.score);
        highScore = (TextView) getView().findViewById(R.id.high_score);
        highScore.setText(String.valueOf(canvasView.gameController.highScore));

        //CHOMP BUTTON
        chomp = (Button) getView().findViewById(R.id.chomp);
        chomp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!((MainActivity) getActivity()).isPaused) {
                    if (canvasView.gameController.hasStarted) {
                        canvasView.albert.chomp(canvasView);

                        for (int i = 0; i < canvasView.gameController.enemyFactory.enemies.size(); i++) {
                            if (canvasView.gameController.enemyFactory.enemies.get(i).x < canvasView.albert.bitmap.getWidth()) {
                                if ((canvasView.gameController.enemyFactory.enemies.get(i).y + canvasView.gameController.enemyFactory.enemies.get(i).bitmap.getHeight() / 2) > canvasView.albert.y) {
                                    if ((canvasView.gameController.enemyFactory.enemies.get(i).y + canvasView.gameController.enemyFactory.enemies.get(i).bitmap.getHeight() / 2) < canvasView.albert.y + canvasView.albert.bitmap.getHeight()) {
                                        //Enemy in range. Chomp him.
                                        //canvasView.gameController.enemyFactory.enemies.remove(i);
                                        canvasView.gameController.enemyFactory.enemies.get(i).isDead = true;
                                        canvasView.gameController.score++;
                                        if (canvasView.gameController.score > canvasView.gameController.highScore) {
                                            canvasView.gameController.highScore = canvasView.gameController.score;
                                        }
                                        updateScores();
                                    }
                                }
                            }
                        }
                    } else {
                        canvasView.gameController.restart();
                    }
                }
            }

        });

        //GROWL BUTTON
        growl = (Button) getView().findViewById(R.id.growl);
        growl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(((MainActivity)getActivity()).isPaused){
                    return;
                }
            if(canvasView.gameController.canGrowl) {
                if(canvasView.gameController.hasStarted) {
                    canvasView.albert.growl(canvasView);
                    growl.setBackgroundResource(R.drawable.growl_btn_pressed);
                }
                for (int i = 0; i < canvasView.gameController.enemyFactory.enemies.size(); i++) {
                    canvasView.gameController.enemyFactory.enemies.get(i).speed /= 2;
                    if(canvasView.gameController.enemyFactory.enemies.get(i).speed < 3){
                        canvasView.gameController.enemyFactory.enemies.get(i).speed = 3;
                    }
                }
            }
            canvasView.gameController.canGrowl = false;
            }
        });

        //TEBOW BUTTON
        tebow = (Button) getView().findViewById(R.id.tebow);
        tebow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(((MainActivity)getActivity()).isPaused){
                    return;
                }
                if(canvasView.gameController.canTebow) {
                    if(canvasView.gameController.hasStarted) {
                        tebow.setBackgroundResource(R.drawable.tebow_btn_pressed);
                    }
                    canvasView.gameController.tebow();
                }
                canvasView.gameController.canTebow = false;
            }
        });

        //ALBERTA BUTTON
        alberta = (Button) getView().findViewById(R.id.alberta);
        alberta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(((MainActivity)getActivity()).isPaused){
                    return;
                }
                if(canvasView.gameController.canAlberta) {
                    if(canvasView.gameController.hasStarted) {
                        alberta.setBackgroundResource(R.drawable.alberta_btn_pressed);
                        canvasView.albert.alberta();
                    }
                }
                canvasView.gameController.canAlberta = false;
            }
        });

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    public void updateScores(){

        if(canvasView.gameController.score > canvasView.gameController.highScore){
            canvasView.gameController.highScore = canvasView.gameController.score;
        }
        (getActivity()).runOnUiThread(new Runnable() {
            @Override
            public void run() {
                score.setText(String.valueOf(canvasView.gameController.score));
                highScore.setText(String.valueOf(canvasView.gameController.highScore));
            }
        });
    }

    public void resetButtons(){
        (getActivity()).runOnUiThread(new Runnable() {
            @Override
            public void run() {
                growl.setBackgroundResource(R.drawable.growl_btn_pressed);
                tebow.setBackgroundResource(R.drawable.tebow_btn_pressed);
                alberta.setBackgroundResource(R.drawable.alberta_btn_pressed);
            }
        });
    }
}
