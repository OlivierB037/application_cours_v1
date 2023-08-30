package fr.cnam.application_cours_v1;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

/**
 * TODO: document your custom view class.
 */
public class DrawView extends View {
    private OnTouchListener onTouchListener;
    private Paint paint = new Paint();
    private Canvas myCanvas = new Canvas();
    Path startPath;
    public float endX;
    public float endY;
    public float startX;
    public float startY;
    public float radius;
    public float centerX;
    public float centerY;
    public boolean fullCircle = false;
    public boolean touched = false;
    public boolean circle = false;
    private final String TAG = "DrawView call";

    public DrawView(Context context) {
        super(context);
        Log.i("function called","drawView");
        init();
    }


    public DrawView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public DrawView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);

        init();
    }

    private void init() {
        Log.i("function called","init called");
        paint.setColor(Color.BLACK);
        paint.setStrokeWidth(5);

        //onTouchListener.onTouch(this, new MotionEvent() )
    }

    public Paint getPaint() {
        return paint;
    }

    public Canvas getMyCanvas() {
        return myCanvas;
    }

    @Override
    public void onDraw(Canvas canvas) {
        Log.i("function called", "onDraw called in drawview");
//        Path path = new Path();
//        path.moveTo(400, 200);
//        path.quadTo(0, 0, 200, 100);
        myCanvas = canvas;

//        myCanvas.drawPath(path, paint);



        if(touched){
            Log.i("function state", "touched is true");
            //Log.i("function path value", startPath.);
//            startPath.lineTo(endX,endY);
            //myCanvas.drawPath(startPath, paint);
            myCanvas.drawLine(startX, startY, endX, endY, paint);
            touched = false;
        }

        else if(circle) {
            Log.i(TAG, "onDraw: drawing circle");
            boolean endReached = false;
            float xcount = startX;
            float increment = (float) 1;
            startX = -radius;

            if (fullCircle) {
                do {

                    startY = (float) Math.sqrt((radius * radius) - (startX * startX));
                    startX += increment;
                    endX = startX;
                    endY = (float) -(Math.sqrt((radius * radius) - (endX * endX)));


                    Log.i(TAG, "drawACircle: startX = " + startX);
                    Log.i(TAG, "drawACircle: startY = " + startY);
                    Log.i(TAG, "drawACircle: endX = " + endX);
                    Log.i(TAG, "drawACircle: endY = " + endY);

                    myCanvas.drawLine(startX + centerX, startY + centerY, endX + centerX, endY + centerY, paint);


                } while (startX < radius);
            } else {
                do {
                    if (xcount == radius) {
                        Log.i(TAG, "drawACircle: xcount = radius - first half done");
                        increment = (float) -1;
                        endReached = true;
                    }
                    startX = xcount;
                    if (!endReached) {
                        startY = (float) Math.sqrt((radius * radius) - (startX * startX));
                        xcount += increment;
                        endX = xcount;
                        endY = (float) Math.sqrt((radius * radius) - (endX * endX));
                    } else {

                        startY = (float) -(Math.sqrt((radius * radius) - (startX * startX)));
                        xcount += increment;
                        endX = xcount;
                        endY = (float) -(Math.sqrt((radius * radius) - (endX * endX)));
                    }
                    Log.i(TAG, "drawACircle: startX = " + startX);
                    Log.i(TAG, "drawACircle: startY = " + startY);
                    Log.i(TAG, "drawACircle: endX = " + endX);
                    Log.i(TAG, "drawACircle: endY = " + endY);

                    myCanvas.drawLine(startX + centerX, startY + centerY, endX + centerX, endY + centerY, paint);

                } while (xcount <= radius && xcount != -radius);
            }
        }
        else{
            Log.i("function state", "touched is false");
            //myCanvas.drawLine(0,0,100,100,paint);
        }
        //drawALine(100,0, 100, 200);
        /*
        https://developer.android.com/training/gestures/multi
        https://developer.android.com/training/custom-views/custom-drawing
        https://cyrilmottier.com/2009/05/06/la-gestion-des-touchevents/
        http://tvaira.free.fr/dev/android/android-dessin.html 

         */

    }


    public void drawALine( float strtX, float strtY, float stpX, float stpY) {
        Log.i("function call", "drawLine() called");
        //startPath = new Path();
        //startPath.quadTo(strtX, strtY,stpX, stpY);
        touched = true;
        startX = strtX;
        startY = strtY;
        endX = stpX;
        endY = stpY;

        this.invalidate();
    }

    public void drawACircle(float _centerX, float _centerY, float _radius){
        circle = true;
        Log.i(TAG, "DrawView: circle constructor");

        centerX = _centerX;
        centerY = _centerY;
        radius = _radius;

        startY = 0;
        this.invalidate();
    }

    public void drawBall(Ball ball){
        circle = true;
        Log.i(TAG, "DrawView: circle constructor");

        centerX = ball.getCenterX();
        centerY = ball.getCenterY();
        radius = ball.getRadius();
        startX = -radius;
        startY = 0;
        this.invalidate();
    }

    public void doAFullCircle(float _centerX, float _centerY, float _radius){
        fullCircle=true;
        drawACircle(_centerX,_centerY,_radius);
    }

    public void moveCircleToPosition(int moveX, int moveY){
        if(circle){
            Log.i(TAG, "moveCircle: circle do exists");
            Log.i(TAG, "moveCircle: centerX =" + centerX);
            Log.i(TAG, "moveCircle: centerY = " + centerY);
            Log.i(TAG, "moveCircle: radius = " + radius);
            if (moveX == moveY) {
                Log.i(TAG, "moveCircle: moveX = moveY");
                //doAFullCircle();
            }
            else if (moveX > moveY) {
                Log.i(TAG, "moveCircle: gcd direct =" + moveX / moveY);
                int factor = moveX/moveY;
                centerX += moveX%moveY;
                for(int i = 0;i < moveY; i++){
                    doAFullCircle(centerX+factor,centerY + 1,radius );
                }
            }
            else{
                Log.i(TAG, "moveCircle: gcd direct =" + moveX / moveY);
                int factor = moveY/moveX;
                centerX += moveY%moveX;
                for(int i = 0;i < moveX; i++){
                    doAFullCircle(centerX+1,centerY + factor,radius );
                }
            }
        }
        else {
            Log.i(TAG, "moveCircle: no circle set");
        }
    }

    public void moveCircle(float moveX, float moveY){
        if(circle){
            doAFullCircle(centerX + moveX, centerY + moveY, radius);
        }
    }
    public void moveCircle(float moveX, float moveY, float moveZ){
        if(circle){//vérifier z positif et pas trop grand + x,y dans les limites de l'ecran
            doAFullCircle(centerX + moveX, centerY + moveY, radius + moveZ);
        }
    }


    private float calculateLineY(float X){
        return (float) Math.sqrt((radius * radius) - (X * X));
    }

//    @Override
//    public boolean onTouchEvent(MotionEvent event) {
//        if(event.getAction() == MotionEvent.ACTION_UP) {
//            Log.i("function called", "OntouchEvent in Drawview");
//            drawALine(300, 100, 300, 300);
//            touched = true;
//        }
//
//        return true;
//    }
}