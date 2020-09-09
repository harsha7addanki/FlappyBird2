package com.fztl.harsha.fb;

//Add All Needed Parts In Android Core
import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.WindowManager;
//Add All Needed Parts In Android X
import androidx.annotation.RequiresApi;
//Add All Of Needed GameRain Classes
import com.fztl.harsha.gamerain.GRCode;
import com.fztl.harsha.gamerain.GRGame;
//Add All Needed Parts In "java.util"
import java.util.ArrayList;
import java.util.List;
import java.util.Random;


public class MainActivity extends Activity {

    private int xAxis =-1, yAxis = -1;
    private List<Obstacle> obstacleList;
    public static String TAG = "TAG";
    int y = 100;
    private Player player;
    private FakePlayer fakePlayer;
    boolean lost = false;
    int score = 0;
    int level = 1;
    double[] levelData = { 1.0, 1.2, 1.3, 1.4, 1.5, 1.6 };
    int cton = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        player = new Player();
        fakePlayer = new FakePlayer(player);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(new GRGame(this, new GRCode() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void run(Canvas canvas){
                canvas.drawColor(Color.WHITE);
                //drawBackgroundImage(canvas);
                if(lost){
                    canvas.drawColor(Color.RED);
                    Paint paint = new Paint();
                    paint.setColor(Color.WHITE);
                    paint.setTextSize(80);
                    canvas.drawText("You Lose!",canvas.getWidth()/2,canvas.getHeight()/2,paint);
                    canvas.drawText("Score: " + String.valueOf(score),canvas.getWidth()/2,canvas.getHeight()/2 + 70,paint);
                }else{
                    drawObstacles(canvas);
                    player.draw(canvas,y,MainActivity.this);
                    fakePlayer.draw(canvas,y);

                    boolean first = true;
                    // check if bird hit obstacle
                    for(Obstacle o : obstacleList) {
                        o.drawObstacle(canvas, xAxis);
                        if (first) {
                            //o.debug(y, xAxis);
                            first = false;
                        }
                        if (fakePlayer.obstacleTouchedPlayer(o)) {
                            //Toast.makeText(MainActivity.this,"Player Hit An Obstacle!",Toast.LENGTH_LONG).show();
                            Log.i(TAG, "Player Hit An Obstacle!");

                            lost = true;
                        }
                    }
                }
                //drawBird(canvas,y);
            }

            public void update() {
                y+=10;
                //Log.i("Value Of Y:",Integer.toString(y));
                if(y > 1890){
                    y=100;
                }
                if(!lost){
                    score++;
                }
                if(score == 100){
                    level++;
                }
                if(score == 200){
                    level++;
                }
                if(score == 300){
                    level++;
                }
                if(score == 400){
                    level++;
                }
            }

            public void onTouch(boolean clicked){
                for(int i = 0 ; i <= 10;i+=5){
                  y-=50;
               }
            }
        }));
    }


    /*@RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    protected void drawBackgroundImage(Canvas canvas) {
        Bitmap b=BitmapFactory.decodeResource(getResources(), R.mipmap.background_background);

        int outWidth;
        int outHeight;
        int inWidth = b.getWidth();
            outHeight = canvas.getHeight();
            outWidth = (inWidth * canvas.getWidth()) / inHeight;
        }
        Bitmap scaledBitmap = Bitmap.createScaledBitmap(b, outWidth, outHeight, false);
        canvas.drawBitmap(scaledBitmap, 0, 0, new Paint());
        canvas.drawColor(Color.BLUE);



        // code for scaling
        //int scaleX = (int) (canvas.getWidth() / b.getWidth() );
        //int scaleY = (int) (canvas.getHeight() / b.getHeight() );
        //Log.i("Canvas width / height :",canvas.getWidth()  + " / " + canvas.getHeight());
        //Log.i("Scale X / Y           :",scaleX  + " / " + scaleY);
        //Bitmap scaledBitmap = Bitmap.createScaledBitmap(b, scaleX, scaleY, false);
        //canvas.drawBitmap(scaledBitmap, 0, 0, new Paint());

        // just display image
        //canvas.drawBitmap(b, 0, 0, new Paint());

//        BitmapFactory.Options options = new BitmapFactory.Options();
//        options.inPreferredConfig = Bitmap.Config.ARGB_8888;
//
//        Bitmap background = BitmapFactory.decodeResource(getResources(), R.mipmap.background_background, options);
//        canvas.drawBitmap(background, 0, 0, null);

//        Drawable d = getResources().getDrawable(R.mipmap.background_background, null);
//        d.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
//        d.draw(canvas);

//        Resources res = getResources();
//        Bitmap bitmap = BitmapFactory.decodeResource(res, R.mipmap.background_background);
//        canvas.drawBitmap(bitmap, 0, 0, new Paint());

    }*/

    protected void drawObstacles(Canvas canvas) {
        Obstacle o;
        int easy = 234;
        int bounded = 0;
        if (xAxis == -1 && yAxis == -1) {
            xAxis = canvas.getWidth() - 9;
            yAxis = canvas.getHeight() / 2;

            obstacleList = new ArrayList<>();
            Random r = new Random();

            // levels are based on these
            int obstacleGap = xAxis / (2+level);
            int baseYAxisValue = (canvas.getHeight() / 2) - 300;


            int x = 0;
            int gap = obstacleGap;
            int obsxval = 1;
            // initialize obstacles
            for (int i=0; i<100; i++) {

                //baseYAxisValue + r.nextInt(level * 100)
                if (x == 0) {
                    x = 1;
                } else {
                    x = 0;
                }
                if(level == 1) {
                    bounded = 500;
                }else if(level == 2){
                    bounded = 333;
                }else if(level == 3){
                    bounded = 250;
                }else if(level == 4){
                    bounded = 200;
                }else if (level == 5){
                    bounded = 166;
                }
                o = new Obstacle(obsxval, baseYAxisValue + r.nextInt(bounded), x);
                Log.i(TAG,"x = " + o.left + " y = " + o.height + " Orientation = " + o.orientation);
                obstacleList.add(o);
                obsxval += obstacleGap;
            }

//            for (int i=1; i<=xAxis; i+=obstacleGap) {
//                Obstacle o = new Obstacle(i, baseYAxisValue + r.nextInt(300), x);
//                if (x == 0) {
//                    x = 1;
//                } else {
//                    x = 0;
//                }
//                //Log.i(TAG,"x = " + o.left + " y = " + o.height);
//                obstacleList.add(o);
//            }

        } else {
            xAxis -= 3;

//            if (xAxis < 5) {
//                xAxis = canvas.getWidth() - 9;
//            }
        }

        Bitmap backGround = generateBackground(canvas);
        //canvas.drawBitmap(backGround, 0, 0, null);
        canvas.drawColor(Color.rgb(0, 199, 255));

        // actual obstacle
        //canvas.drawRect(xAxis, yAxis, xAxis + 10 , canvas.getHeight(), new Paint());

//        for (Obstacle ob : obstacleList) {
//            //ob.drawObstacle(canvas, xAxis);
//        }
    }

    protected void drawBird(Canvas canvas,int y) {
        Paint paint = new Paint();
        //paint.setAlpha(42);
        //canvas.drawColor(Color.BLUE);
        //paint.setColor(Color.rgb(250, 0, 0));
        //canvas.drawRect(100,  y - 20, 200, y - 100, paint);
        Bitmap b = BitmapFactory.decodeResource(getResources(), R.mipmap.mybird_background);

//        int outWidth;
//        int outHeight;
//        int inWidth = b.getWidth();
//        int inHeight = b.getHeight();
//        if(inWidth > inHeight){
//            outWidth = canvas.getWidth();
//            outHeight = (inHeight * canvas.getHeight()) / inWidth;
//        } else {
//            outHeight = canvas.getHeight();
//            outWidth = (inWidth * canvas.getWidth()) / inHeight;
//        }
//        Bitmap scaledBitmap = Bitmap.createScaledBitmap(b, outWidth, outHeight, false);
//
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_ATOP));
        canvas.drawBitmap(b,100,y+20,paint);

    }

    protected  Bitmap generateBackground(Canvas mainCanvas) {

        Bitmap myBitmap = getSkyline(mainCanvas);
        Canvas c = new Canvas(myBitmap);

        Paint myPaint = new Paint();
        myPaint.setColor(Color.rgb(0, 0, 0));
        myPaint.setStrokeWidth(10);
        c.drawRect(xAxis, yAxis, xAxis + 10, mainCanvas.getHeight(), myPaint);

        return  myBitmap;
    }


    protected Bitmap getSkyline(Canvas canvas) {

        Bitmap b=BitmapFactory.decodeResource(getResources(), R.mipmap.background_background);

        int outWidth;
        int outHeight;
        int inWidth = b.getWidth();
        int inHeight = b.getHeight();
        if(inWidth > inHeight){
            outWidth = canvas.getWidth();
            outHeight = (inHeight * canvas.getHeight()) / inWidth;
        } else {
            outHeight = canvas.getHeight();
            outWidth = (inWidth * canvas.getWidth()) / inHeight;
        }

        return Bitmap.createScaledBitmap(b, outWidth, outHeight, false);
        //return Bitmap.createScaledBitmap(b, outWidth , outHeight, false);
        //return Bitmap.createScaledBitmap(b, canvas.getWidth(), canvas.getHeight(), false);
    }


}