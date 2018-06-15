package com.bignerdranch.android.draganddraw;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PointF;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class BoxDrawingView extends View {
    private static final String TAG = "BoxDrawingView";
    private static final String SAVED_DRAW_TYPE = "SAVED_DRAW_TYPE";
    private static final String SAVED_POINTS = "SAVED_POINTS";
    private static final String SAVED_BOXES = "SAVED_BOXES";
    private static final String SAVED_CIRCLES = "SAVED_CIRCLES";
    private static final String SAVED_LINES = "SAVED_LINES";
    private static final String SAVED_PARENT_VIEW = "SAVED_PARENT_VIEW";


    private Line mCurrentLine;
    private List<Line> mLines = new ArrayList<>();


    private Circle mCurrentCircle;
    private List<Circle> mCircles = new ArrayList<>();

    private List<PointF> mPoints = new ArrayList<>();

    private Box mCurrentBox;
    private List<Box> mBoxes = new ArrayList<>();


    private Paint mBoxPaint;
    private Paint mLinePaint;
    private Paint mCirclePaint;
    private Paint mPointPaint;

    private Paint mBackgroundPaint;


    public enum DrawType {LINE, BOX, CIRCLE, POINT,}


    private DrawType mDrawType = DrawType.BOX;


    public void setDrawType(DrawType type) {
        mDrawType = type;


    }


    public BoxDrawingView(final Context context) {
        this(context, null);
    }

    /**
     * Constructor that is called when inflating a view from XML. This is called
     * when a view is being constructed from an XML file, supplying attributes
     * that were specified in the XML file. This version uses a default style of
     * 0, so the only attribute values applied are those in the Context's Theme
     * and the given AttributeSet.
     * <p>
     * <p>
     * The method onFinishInflate() will be called after all children have been
     * added.
     *
     * @param context The Context the view is running in, through which it can
     *                access the current theme, resources, etc.
     * @param attrs   The attributes of the XML tag that is inflating the view.
     *                see #View(Context, AttributeSet, int)
     */
    public BoxDrawingView(final Context context, @Nullable final AttributeSet attrs) {
        super(context, attrs);


        mBoxPaint = new Paint();
        mBoxPaint.setColor(0x22ff0000);

        mCirclePaint = new Paint();
        mCirclePaint.setColor(Color.BLUE);

//        mCirclePaint.setStrokeWidth(10);


        mLinePaint = new Paint();
        mLinePaint.setColor(Color.MAGENTA);
        mLinePaint.setStrokeWidth(10);

        mPointPaint = new Paint();
        mPointPaint.setColor(Color.BLACK);
        mPointPaint.setStrokeWidth(10);

        mBackgroundPaint = new Paint();
        mBackgroundPaint.setColor(0xfff8efe0);

    }

    public void clearCanvas() {
        mBoxes.clear();
        mLines.clear();
        mCircles.clear();
        mPoints.clear();
        invalidate();

    }


    @Override
    protected void onDraw(final Canvas canvas) {
        canvas.drawPaint(mBackgroundPaint);

        for (Circle circle : mCircles) {
            canvas.drawCircle(circle.getOrigin().x, circle.getOrigin().y, circle.getRadius(), mCirclePaint);
        }


        for (PointF point : mPoints) {
            canvas.drawPoint(point.x, point.y, mPointPaint);

        }


        for (Line line : mLines) {
            canvas.drawLine(line.getStart().x, line.getStart().y,
                    line.getEnd().x, line.getEnd().y, mLinePaint);
        }

        for (Box box : mBoxes) {
            float left = Math.min(box.getOrigin().x, box.getCurrent().x);

            float right = Math.max(box.getOrigin().x, box.getCurrent().x);

            float top = Math.min(box.getOrigin().y, box.getCurrent().y);

            float bottom = Math.max(box.getOrigin().y, box.getCurrent().y);


            canvas.drawRect(left, top, right, bottom, mBoxPaint);


        }
    }

    @Override
    public boolean onTouchEvent(final MotionEvent event) {

        PointF current = new PointF(event.getX(), event.getY());

        switch (mDrawType) {
            case BOX:
                buildBoxes(event, current);

                break;


            case LINE:
                buildLines(event, current);
                break;


            case POINT:
                buildPoints(event, current);
                break;

            case CIRCLE:
                buildCircles(event, current);
                break;


        }


        Log.d(TAG, "onTouchEvent: ACTION :  at (x,y) :" + current.x + ", " + current.y);

        return true;
    }

    @Nullable
    @Override
    protected Parcelable onSaveInstanceState() {

        Log.d(TAG, "JPC onSaveInstanceState: attempting to save UI state");

        Bundle bundle = new Bundle();
        bundle.putString(SAVED_DRAW_TYPE, mDrawType.name());
        bundle.putParcelableArrayList(SAVED_POINTS, (ArrayList<? extends Parcelable>) mPoints);

        bundle.putSerializable(SAVED_BOXES, (Serializable) mBoxes);
        bundle.putSerializable(SAVED_CIRCLES, (Serializable) mCircles);
        bundle.putSerializable(SAVED_LINES, (Serializable) mLines);
        bundle.putParcelable(SAVED_PARENT_VIEW, super.onSaveInstanceState());

        return bundle;
    }

    @Override
    protected void onRestoreInstanceState(final Parcelable state) {

        if (state != null) {


            Bundle bundle = new Bundle(((Bundle) state));

            super.onRestoreInstanceState(bundle.getParcelable(SAVED_PARENT_VIEW));

            mDrawType = DrawType.valueOf(bundle.getString(SAVED_DRAW_TYPE));

            mBoxes = (List<Box>) bundle.getSerializable(SAVED_BOXES);

            mLines = (List<Line>) bundle.getSerializable(SAVED_LINES);

            mCircles = (List<Circle>) bundle.getSerializable(SAVED_CIRCLES);

            mPoints = bundle.getParcelableArrayList(SAVED_POINTS);

            Log.d(TAG, "JPC onRestoreInstanceState: mBoxes.size " + mBoxes.size());

        }


    }

    private void buildPoints(final MotionEvent event, final PointF current) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_UP:

                break;

            case MotionEvent.ACTION_MOVE:
            case MotionEvent.ACTION_DOWN:


                // reset drawing state

                mPoints.add(new PointF(current.x, current.y));
                invalidate();

                break;

            case MotionEvent.ACTION_CANCEL:


                break;


        }

    }

    private void buildLines(final MotionEvent event, final PointF current) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_UP:

                mCurrentLine = null;
                break;

            case MotionEvent.ACTION_DOWN:


                // reset drawing state

                mCurrentLine = new Line(current);
                mLines.add(mCurrentLine);


                break;

            case MotionEvent.ACTION_CANCEL:
                mCurrentLine = null;

                break;


            case MotionEvent.ACTION_MOVE:


                if (mCurrentLine != null) {
                    mCurrentLine.setEnd(current);
                    invalidate();
                }

                break;


        }

    }

    private void buildCircles(final MotionEvent event, final PointF current) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_UP:

                mCurrentCircle = null;
                break;

            case MotionEvent.ACTION_DOWN:


                // reset drawing state

                mCurrentCircle = new Circle(current);
                mCircles.add(mCurrentCircle);


                break;

            case MotionEvent.ACTION_CANCEL:
                mCurrentCircle = null;

                break;


            case MotionEvent.ACTION_MOVE:


                if (mCurrentCircle != null) {
                    mCurrentCircle.setCirclePoint(current);
                    invalidate();
                }

                break;


        }

    }

    private void buildBoxes(final MotionEvent event, final PointF current) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_UP:

                mCurrentBox = null;
                break;

            case MotionEvent.ACTION_DOWN:


                // reset drawing state

                mCurrentBox = new Box(current);
                mBoxes.add(mCurrentBox);


                break;

            case MotionEvent.ACTION_CANCEL:
                mCurrentBox = null;

                break;


            case MotionEvent.ACTION_MOVE:


                if (mCurrentBox != null) {
                    mCurrentBox.setCurrent(current);
                    invalidate();
                }

                break;


        }
    }
}
