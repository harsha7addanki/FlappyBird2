package com.fztl.harsha.fb;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;

public class Obstacle {
    public int left;
    public int height;
    public int orientation;
    public Rect obRect;

    public static int BOTTOM = 0;
    //public static int TOP = 1;

    public Obstacle(int left, int height, int orientation){
        this.left = left;
        this.height = height;
        this.orientation = orientation;
    }


    public void drawObstacle(Canvas canvas, int xAxis){

        int actualXValue = xAxis + left;

        if (actualXValue > 3 && actualXValue < canvas.getWidth()) {
            if (orientation == BOTTOM) {
                obRect = new Rect(actualXValue, height, actualXValue + 15, canvas.getHeight());
            } else {
                obRect = new Rect(actualXValue, 0, actualXValue + 15, canvas.getHeight() - height);
            }
            canvas.drawRect(obRect, new Paint());
        }
    }

//    public void debug(int py, int xAxis) {
//        Log.i("TAG"," Obstacle x = " + (xAxis + this.left) + " y = " + this.height);
//        Log.i("TAG"," Player x = " + 100 + " y = " + py);
//    }
}
