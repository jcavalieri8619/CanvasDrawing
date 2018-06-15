package com.bignerdranch.android.draganddraw;

import android.graphics.PointF;
import android.util.Log;

import java.io.Serializable;

class Circle implements Serializable {

    private static final String TAG = "Circle";

    private PointF origin;
    private PointF circlePoint;
    private float radius;

    public Circle(final PointF origin) {
        Log.d(TAG, "Circle: setting origin: " + origin.toString());
        this.origin = origin;
        radius = 0;
    }

    public PointF getOrigin() {
        return origin;
    }

    public void setOrigin(final PointF origin) {
        this.origin = origin;
    }

    private void computeRadius() {
        radius = (float) Math.sqrt(Math.pow(origin.x - circlePoint.x, 2) + Math.pow(origin.y - circlePoint.y, 2));

        Log.d(TAG, "computeRadius: radius = " + radius);
    }

    public float getRadius() {

        return radius;
    }


    public void setCirclePoint(final PointF circlePoint) {
        Log.d(TAG, "setCirclePoint: point on circle: " + circlePoint.toString());
        this.circlePoint = circlePoint;
        computeRadius();

    }
}
