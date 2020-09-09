package com.fztl.harsha.fb;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;

public class FakePlayer {
    private Player player;
    private Rect selfRect;

    public FakePlayer(Player player){
        this.player = player;
    }

    public void draw(Canvas canvas,int y){
        Paint paint = new Paint();
        paint.setAlpha(0);
        selfRect = new Rect(180, y + Player.PLAYER_OFFSET + 80,this.player.b.getWidth() + 20,this.player.b.getHeight() + y - 60);
        canvas.drawRect(selfRect,paint);
    }

    public boolean obstacleTouchedPlayer(Obstacle obstacle){
        return obstacle.obRect.intersect(selfRect);
        //return obstacle.obRect.intersect(selfRect) || obstacle.obRect.contains(selfRect) || selfRect.contains(obstacle.obRect);
    }
}
