package com.supafly.swampdefense;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.util.Log;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by Ryan on 11/17/2014.
 */
public class EnemyFactory {

    ArrayList<EnemyCharacter> enemies;
    Context context;
    String[] types = new String[8];

    public EnemyFactory(Context context){
        enemies = new ArrayList<EnemyCharacter>();
        this.context = context;
        types[0] = "seminole";
        types[1] = "arkansas";
        types[2] = "auburn";
        types[3] = "alabama";
        types[4] = "kentucky";
        types[5] = "georgia";
        types[6] = "southCarolina";
        types[7] = "tigers";
    }

    public void newEnemy(int height, int width, int difficulty)
    {
        //Get a random mascot
        String type = types[new Random().nextInt(types.length)];

        enemies.add(new EnemyCharacter(context,type, difficulty));
        //Set enemy starting location
        enemies.get(enemies.size()-1).setY(new Random().nextInt(height-200));
        enemies.get(enemies.size()-1).setX(width - 100);
    }

}
