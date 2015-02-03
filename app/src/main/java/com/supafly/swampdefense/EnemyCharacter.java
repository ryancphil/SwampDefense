package com.supafly.swampdefense;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.util.Log;

import java.util.Random;
import java.util.TimerTask;

/**
 * Created by Ryan on 11/24/2014.
 */
public class EnemyCharacter extends BitmapTriple {
    Context context;
    int speed;
    boolean isDead = false;

    public EnemyCharacter(Context context, String type, int difficulty){
        this.context = context;

        //Get a random speed
        speed = (new Random().nextInt(6) + 3) * difficulty;

        //Create enemy based on random mascot type
        if(type.equals("seminole")){
            bitmap = BitmapFactory.decodeResource(context.getResources(),
                    R.drawable.fsu_stand);
        }else if(type.equals("arkansas")){
            bitmap = BitmapFactory.decodeResource(context.getResources(),
                    R.drawable.ark_stand);
        }else if(type.equals("auburn")){
            bitmap = BitmapFactory.decodeResource(context.getResources(),
                    R.drawable.aub_stand);
        }else if(type.equals("alabama")){
            bitmap = BitmapFactory.decodeResource(context.getResources(),
                    R.drawable.bama_stand);
        }else if(type.equals("kentucky")){
            bitmap = BitmapFactory.decodeResource(context.getResources(),
                    R.drawable.kfc_stand);
        }else if(type.equals("georgia")){
            bitmap = BitmapFactory.decodeResource(context.getResources(),
                    R.drawable.dogs_stand);
        }else if(type.equals("southCarolina")){
            bitmap = BitmapFactory.decodeResource(context.getResources(),
                    R.drawable.cock_stand);
        }else if(type.equals("tigers")){
            bitmap = BitmapFactory.decodeResource(context.getResources(),
                    R.drawable.lsu_stand);
        }else{
            //Not a valid type
            Log.e("ENEMY FACTORY:", "INVALID TYPE!");
        }

        this.name = type;
    }

    @Override
    public void animate() {
        if(this.name.equals("seminole")){
            if(state == 0 || state == 1){
                state = 2;
                bitmap = BitmapFactory.decodeResource(context.getResources(),
                        R.drawable.fsu_left);
            }else{
                state = 1;
                bitmap = BitmapFactory.decodeResource(context.getResources(),
                        R.drawable.fsu_right);
            }
        }else if(this.name.equals("arkansas")){
            if(state == 0 || state == 1){
                state = 2;
                bitmap = BitmapFactory.decodeResource(context.getResources(),
                        R.drawable.ark_left);
            }else{
                state = 1;
                bitmap = BitmapFactory.decodeResource(context.getResources(),
                        R.drawable.ark_right);
            }
        }else if(this.name.equals("auburn")){
            if(state == 0 || state == 1){
                state = 2;
                bitmap = BitmapFactory.decodeResource(context.getResources(),
                        R.drawable.aub_left);
            }else{
                state = 1;
                bitmap = BitmapFactory.decodeResource(context.getResources(),
                        R.drawable.aub_right);
            }
        }else if(this.name.equals("alabama")){
            if(state == 0 || state == 1){
                state = 2;
                bitmap = BitmapFactory.decodeResource(context.getResources(),
                        R.drawable.bama_left);
            }else{
                state = 1;
                bitmap = BitmapFactory.decodeResource(context.getResources(),
                        R.drawable.bama_right);
            }
        }else if(this.name.equals("kentucky")){
            if(state == 0 || state == 1){
                state = 2;
                bitmap = BitmapFactory.decodeResource(context.getResources(),
                        R.drawable.kfc_left);
            }else{
                state = 1;
                bitmap = BitmapFactory.decodeResource(context.getResources(),
                        R.drawable.kfc_right);
            }
        }else if(this.name.equals("georgia")){
            if(state == 0 || state == 1){
                state = 2;
                bitmap = BitmapFactory.decodeResource(context.getResources(),
                        R.drawable.dogs_left);
            }else{
                state = 1;
                bitmap = BitmapFactory.decodeResource(context.getResources(),
                        R.drawable.dogs_right);
            }
        }else if(this.name.equals("southCarolina")){
            if(state == 0 || state == 1){
                state = 2;
                bitmap = BitmapFactory.decodeResource(context.getResources(),
                        R.drawable.cock_left);
            }else{
                state = 1;
                bitmap = BitmapFactory.decodeResource(context.getResources(),
                        R.drawable.cock_right);
            }
        }else if(this.name.equals("tigers")){
            if(state == 0 || state == 1){
                state = 2;
                bitmap = BitmapFactory.decodeResource(context.getResources(),
                        R.drawable.lsu_left);
            }else{
                state = 1;
                bitmap = BitmapFactory.decodeResource(context.getResources(),
                        R.drawable.lsu_right);
            }
        }else{
            //Not a valid type
            Log.e("ENEMY FACTORY:", "INVALID TYPE!");
        }
    }

    public void move(){
        x -= speed;
    }


    @Override
    public void setX(int x1) {
        super.setX(x1);
    }

    @Override
    public void setY(int y1) {
        super.setY(y1);
    }
}
