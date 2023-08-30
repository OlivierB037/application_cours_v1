package fr.cnam.application_cours_v1;

import android.os.Bundle;

public class Ball {

    private float radius;
    private float centerX;
    private float centerY;
    private float startX;
    private float increment;
    private Bundle ballDrawing;

    public Ball(float _centerX, float _centerY, float _radius){
        centerX = _centerX;
        centerY = _centerY;
        radius = _radius;
        increment = 1;
        startX = _centerX - _radius;
        ballDrawing.putFloat("startX",startX);
    }

    public float getEndX(float _startX){
        startX += centerX;
        return startX + increment;
    }
    public Bundle getNextDraw(){
        //ballDrawing.putFloat("startY",);
        return ballDrawing;
    }

    public float getRadius() {
        return radius;
    }

    public float getCenterX() {
        return centerX;
    }

    public float getCenterY() {
        return centerY;
    }

    public void setIncrement(float increment) {
        this.increment = increment;
    }
}
