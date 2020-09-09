package com.fztl.harsha.fb;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;

public class Player{
    Bitmap b;
    public static int PLAYER_OFFSET = 20;

    public void draw(Canvas canvas, int y, Context context) {
        Paint paint = new Paint();
        b = BitmapFactory.decodeResource(context.getResources(), R.mipmap.mybird_background);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_ATOP));
        canvas.drawBitmap(b,100,y+PLAYER_OFFSET,paint);
    }


}
