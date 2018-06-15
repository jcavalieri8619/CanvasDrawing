package com.bignerdranch.android.draganddraw;

import android.graphics.PointF;

import java.io.Serializable;

class Line implements Serializable {

    private PointF start;
    private PointF end;

    public Line(final PointF start) {
        this.start = start;
        this.end = start;
    }

    public PointF getStart() {
        return start;
    }

    public void setStart(final PointF start) {
        this.start = start;
    }

    public PointF getEnd() {
        return end;
    }

    public void setEnd(final PointF end) {
        this.end = end;
    }
}
