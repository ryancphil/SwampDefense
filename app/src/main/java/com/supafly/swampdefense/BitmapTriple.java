package com.supafly.swampdefense;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import java.util.Random;

/**
 * Created by Ryan on 11/15/2014.
 */
public abstract class BitmapTriple {
    Bitmap bitmap;
    int state;
    int x;
    int y;
    String name;
    
    public BitmapTriple(){

    }

    public BitmapTriple(Bitmap bitmap, String name){
        this.bitmap = bitmap;
        this.name = name;
        this.state = 0;
    }

    public void setX(int x1){
        this.x = x1;
    }

    public void setY(int y1){
        this.y = y1;
    }

    public abstract void animate();
}
