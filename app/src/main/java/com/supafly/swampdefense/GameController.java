package com.supafly.swampdefense;

import android.app.DialogFragment;
import android.content.Context;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.widget.TextView;

import java.util.Iterator;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by Ryan on 11/15/2014.
 */
public class GameController {

    Context context;
    int gTime = 0;
    int difficulty = 1;
    CanvasView canvasView;
    BottomFragment bottomFragment;
    PlayFragment playFragment;

    BitmapTriple tebow;
    boolean gameOver = false;

    boolean drawTebow = false;

    boolean canGrowl = false;
    boolean canAlberta = false;
    boolean canTebow = false;
    boolean hasStarted = false;
    int drawTebowCounter = 0;

    int maxHeight;
    int maxWidth;

    EnemyFactory enemyFactory;

    int score = 0;
    int highScore;


    public GameController(Context context, CanvasView canvasView) {
        this.context = context;
        enemyFactory = new EnemyFactory(context);
        this.canvasView = canvasView;
        this.highScore = ((MainActivity)context).getPreferences(((MainActivity)context).MODE_PRIVATE).getInt("highScore", 0);

        tebow = new BitmapTriple() {
            @Override
            public void animate() {
                //Tebow doesn't animate =(
            }
        };
        tebow.bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.teb);
    }

    public void restart(){
        //Reset the start flag
        gameOver = false;
        difficulty = 1;
        gTime = 0;
        enemyFactory.enemies.clear();
        ((MainActivity)context).scoreString = "You CHOMPED " + score + " mascots!";
        score = 0;
        bottomFragment = (BottomFragment)((MainActivity)context).getSupportFragmentManager().findFragmentById(R.id.bottom_fragment);
        //Update the scores
        bottomFragment.updateScores();
        //Reset the buttons to pressed state for next game.
        bottomFragment.resetButtons();
        canAlberta = false;
        canGrowl = false;
        canTebow = false;
        canvasView.albert.isAlberta = false;
        canvasView.albert.albertaCounter = 0;
        hasStarted = false;
        ((MainActivity)context).runOnUiThread(new Runnable() {
            @Override
            public void run() {
                canvasView.invalidate();
            }
        });
    }

    public void startClock(){
        //CREATE A GLOBAL GAME TIMER AT THE START OF THE GAME

        final Timer gTimer = new Timer();
        gTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                //Log.e("GLOBAL TIMER","RUNNING...");
                if(!((MainActivity)context).isPaused) {
                    //if they are dead, remove them from list
                    Iterator<EnemyCharacter> iter0 = enemyFactory.enemies.iterator();
                    while (iter0.hasNext()) {
                        EnemyCharacter enemyCharacter = iter0.next();

                        if (enemyCharacter.isDead) {
                            iter0.remove();
                        }
                    }

                    gTime++;
                    //Reset the power ups on set intervals.
                    if (gTime % 2000 == 0) {
                        canGrowl = true;
                        bottomFragment = (BottomFragment) ((MainActivity) context).getSupportFragmentManager().findFragmentById(R.id.bottom_fragment);
                        ((MainActivity) context).runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                bottomFragment.growl.setBackgroundResource(R.drawable.growl_btn);
                            }
                        });

                    }
                    if (gTime % 7000 == 0) {
                        canTebow = true;
                        bottomFragment = (BottomFragment) ((MainActivity) context).getSupportFragmentManager().findFragmentById(R.id.bottom_fragment);
                        ((MainActivity) context).runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                bottomFragment.tebow.setBackgroundResource(R.drawable.tebow_btn);
                            }
                        });
                    }
                    if (gTime % 5000 == 0) {
                        canAlberta = true;
                        bottomFragment = (BottomFragment) ((MainActivity) context).getSupportFragmentManager().findFragmentById(R.id.bottom_fragment);
                        ((MainActivity) context).runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                bottomFragment.alberta.setBackgroundResource(R.drawable.alberta_btn);
                            }
                        });
                    }

                    //INCREASE DIFFICULTY OVER TIME
                    if (gTime < 1000) {
                        difficulty = 1;
                    } else if (gTime < 3000) {
                        difficulty = 2;
                    } else if (gTime < 7000) {
                        difficulty = 3;
                    } else if (gTime < 12000) {
                        difficulty = 4;
                    } else {
                        difficulty = 5;
                    }

                    //USE DIFFICULTY TO CREATE ENEMIES
                    if (difficulty == 1) {
                        if (gTime % 400 == 0) {
                            enemyFactory.newEnemy(maxHeight, maxWidth, difficulty);
                        }
                    } else if (difficulty == 2) {
                        if (gTime % 300 == 0) {
                            enemyFactory.newEnemy(maxHeight, maxWidth, difficulty);
                        }
                    } else if (difficulty == 3) {
                        if (gTime % 300 == 0) {
                            enemyFactory.newEnemy(maxHeight, maxWidth, difficulty);
                            enemyFactory.newEnemy(maxHeight, maxWidth, difficulty);
                        }
                    } else if (difficulty == 4) {
                        if (gTime % 200 == 0) {
                            enemyFactory.newEnemy(maxHeight, maxWidth, difficulty);
                            enemyFactory.newEnemy(maxHeight, maxWidth, difficulty);
                        }
                    } else {
                        //Log.e("MAX", "Difficulty");
                        if (gTime % 100 == 0) {
                            enemyFactory.newEnemy(maxHeight, maxWidth, difficulty);
                            enemyFactory.newEnemy(maxHeight, maxWidth, difficulty);
                        }
                    }

                    //DO THE ANIMATIONS
                    Iterator<EnemyCharacter> iter3 = enemyFactory.enemies.iterator();
                    while (iter3.hasNext()) {
                        EnemyCharacter enemyCharacter = iter3.next();

                        //Do the animation and moving every ten times this timer runs
                        if (gTime % 10 == 0) {
                            enemyCharacter.animate();
                            enemyCharacter.move();
                        }
                    }

                    //TEBOW TO THE RESCUE WITH THE POWER OF JEEESUUUSS!
                    if (drawTebow) {
                        Iterator<EnemyCharacter> iter = enemyFactory.enemies.iterator();
                        while (iter.hasNext()) {
                            EnemyCharacter enemyCharacter = iter.next();
                            if (enemyCharacter.x < maxWidth / 2) {
                                tebow.setX(enemyCharacter.x - 100);
                                tebow.setY(enemyCharacter.y);
                                iter.remove();
                                drawTebowCounter++;
                            }
                            if (drawTebowCounter >= 10) {
                                drawTebow = false;
                                drawTebowCounter = 0;
                            }
                        }
                    }

                    //REDRAW EVERYTHING CONSTANTLY
                    ((MainActivity) context).runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            canvasView.invalidate();
                        }
                    });

                    //CHECK TO SEE IF THE PLAYER LOST THE GAME OR IF ENEMY GETS AUTO-MAGICALLY CHOMPED
                    Iterator<EnemyCharacter> iter1 = enemyFactory.enemies.iterator();
                    while (iter1.hasNext()) {
                        EnemyCharacter enemyCharacter = iter1.next();

                        //Check if the player lost
                        if (enemyCharacter.x < -50) {
                            gameOver = true;
                        }
                        if (enemyCharacter.x < 50) {
                            if ((enemyCharacter.y + enemyCharacter.bitmap.getHeight() / 2) > canvasView.albert.y) {
                                if ((enemyCharacter.y + enemyCharacter.bitmap.getHeight() / 2) < canvasView.albert.y + canvasView.albert.bitmap.getHeight()) {
                                    //Enemy in range. Chomp him.
                                    enemyCharacter.isDead = true;
                                    score++;
                                    canvasView.albert.chomp(canvasView);
                                    bottomFragment = (BottomFragment) ((MainActivity) context).getSupportFragmentManager().findFragmentById(R.id.bottom_fragment);
                                    bottomFragment.updateScores();
                                }
                            }
                        }
                    }

                    if (gameOver) {
                        gTimer.purge();
                        gTimer.cancel();
                        restart();

                        //launch play fragment dialog
                        DialogFragment gameOverDialog = new PlayFragment.GameOverDialog();
                        gameOverDialog.show(((MainActivity) context).getFragmentManager(), "Game Over");
                    }
                }
            }//END OF GLOBAL TIMER RUN METHOD
        },0,10);
    }

    public void tebow(){
        drawTebow = true;
    }

}



