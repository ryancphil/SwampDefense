package com.supafly.swampdefense;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by Ryan on 11/15/2014.
 */
public class CanvasView extends View {

    Paint paint;
    Canvas canvas;
    Context context;
    boolean firstRun;
    Timer timer = new Timer();

    Albert albert;

    GameController gameController;

    public CanvasView(Context context) {
        super(context);
        this.context = context;
        init();
    }

    public CanvasView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        init();
    }

    public CanvasView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        init();
    }

    public void init(){
        firstRun = true;
        paint = new Paint();
        canvas = new Canvas();
        gameController = new GameController(context, this);

        albert = new Albert(context);
        albert.setY(450);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        //if tebow draw tebow
        if(gameController.drawTebow){
            canvas.drawBitmap(gameController.tebow.bitmap, gameController.tebow.x, gameController.tebow.y,paint);
        }
        //Draw enemies
        for(int i = 0; i < gameController.enemyFactory.enemies.size(); i++){
            canvas.drawBitmap(gameController.enemyFactory.enemies.get(i).bitmap, gameController.enemyFactory.enemies.get(i).x, gameController.enemyFactory.enemies.get(i).y, paint);
        }
        //Draw albert every time
        try {
            canvas.drawBitmap(albert.bitmap,10,albert.y,paint);
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        if(((MainActivity)context).isPaused){
            return false;
        }

        final int action = event.getActionMasked();
        gameController.maxHeight = ((MainActivity) context).findViewById(R.id.canvasView).getHeight();
        gameController.maxWidth = ((MainActivity) context).findViewById(R.id.canvasView).getWidth();
        TextView tapStart = (TextView) ((MainActivity) context).findViewById(R.id.tapStart);
        tapStart.setText("");

        if(firstRun){
            gameController.hasStarted = true;
            gameController.startClock();
            firstRun = false;
        }

        switch (action) {
            case MotionEvent.ACTION_DOWN:
                final MotionEvent e = event;
                //Log.e("COORDS: ", String.valueOf(e.getY()));
                timer = new Timer();
                timer.schedule(new TimerTask() {
                    @Override
                    public void run() {
                        albert.move(e.getY(), getHeight());
                        ((MainActivity)context).runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                invalidate();
                            }
                        });
                    }
                }
                        ,0,1);
                break;
            case MotionEvent.ACTION_UP:
                timer.cancel();
                timer.purge();




                break;
            case MotionEvent.ACTION_CANCEL:
                break;
            case MotionEvent.ACTION_MOVE:
                break;
            case MotionEvent.ACTION_POINTER_DOWN:
                break;
            case MotionEvent.ACTION_POINTER_UP:
                break;
            default:
                break;
        }
        return true;
    }

}
