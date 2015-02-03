package com.supafly.swampdefense;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by Ryan on 11/24/2014.
 */
public class Albert extends BitmapTriple {
    Bitmap bitmap;
    int state;
    int y;
    int x = 10;
    Context context;
    int growlCount = 0;
    boolean isAlberta = false;
    int albertaCounter = 0;

    String name;

    public Albert(Context context) {
        bitmap = BitmapFactory.decodeResource(context.getResources(),
                R.drawable.al_stand);
        name = "albert";
        this.context = context;
    }

    @Override
    public void setX(int x1) {
        //Albert's X is static, he only moves on the y axis
    }

    @Override
    public void setY(int y1) {
        this.y = y1;
    }

    @Override
    public void animate() {
        if(state == 0){
            state = 1;
            if(isAlberta){
                bitmap = BitmapFactory.decodeResource(context.getResources(),
                        R.drawable.berta_close);
            }else {
                bitmap = BitmapFactory.decodeResource(context.getResources(),
                        R.drawable.al_close);
            }
        }else if(state == 1){
            state = 2;
            if(isAlberta){
                bitmap = BitmapFactory.decodeResource(context.getResources(),
                        R.drawable.berta_stand);
            }else {
                bitmap = BitmapFactory.decodeResource(context.getResources(),
                        R.drawable.al_stand);
            }
        }else if(state == 2){
            state = 0;
            if(isAlberta){
                bitmap = BitmapFactory.decodeResource(context.getResources(),
                        R.drawable.berta_open);
            }else {
                bitmap = BitmapFactory.decodeResource(context.getResources(),
                        R.drawable.al_open);
            }
        }else{
            //Do nothing
        }
    }

    public void chomp(final CanvasView canvasView){
        //Do this for albert chomping
        ((MainActivity)context).bite.start();
        if(isAlberta) {
            albertaCounter++;
        }
        final Timer chompAnimation = new Timer();
        chompAnimation.schedule(new TimerTask() {
            @Override
            public void run() {
                animate();
                ((MainActivity)canvasView.context).runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        canvasView.invalidate();
                    }
                });
                if (state == 2) {
                    chompAnimation.purge();
                    chompAnimation.cancel();
                }
            }
        }, 0, 200);
        if(albertaCounter >= 10){
            albertaCounter = 0;
            isAlberta = false;
        }
    }

    public void growl(final CanvasView canvasView){
        //Do this for albert growl
        ((MainActivity)context).growl.start();
        final Timer growlAnimation = new Timer();
        growlAnimation.schedule(new TimerTask() {
            @Override
            public void run() {
                if(isAlberta){
                    bitmap = BitmapFactory.decodeResource(context.getResources(),
                            R.drawable.berta_growl);
                }else {
                    bitmap = BitmapFactory.decodeResource(context.getResources(),
                            R.drawable.al_growl);
                }
                growlCount++;
                ((MainActivity)canvasView.context).runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        canvasView.invalidate();
                    }
                });
                if (growlCount == 2) {
                    if(isAlberta){
                        bitmap = BitmapFactory.decodeResource(context.getResources(),
                                R.drawable.berta_stand);
                    }else {
                        bitmap = BitmapFactory.decodeResource(context.getResources(),
                                R.drawable.al_stand);
                    }
                    growlCount = 0;
                    growlAnimation.purge();
                    growlAnimation.cancel();
                }

            }
        }, 0, 1000);
    }

    public void move(float press, int maxHeight){
        if(press > y + (bitmap.getHeight()*1.5)){
            if(y + bitmap.getHeight() >= maxHeight) {

            }else {
                ++y;
            }
        }else if(press < y + (bitmap.getHeight()*1.5)){
            --y;
            if(y < 0){
                y = 0;
            }
        }else{
            //do nothing
        }
        //Log.e("ALBERT YPOS: ",String.valueOf(albert.y));

    }
    public void alberta(){
        isAlberta = true;
        this.bitmap = BitmapFactory.decodeResource(context.getResources(),
                R.drawable.berta_stand);
    }
}
