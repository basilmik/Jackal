package com.basilgames.android.jackal;




import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.PointF;
import android.os.Build;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import android.view.WindowManager;
import android.widget.Toast;

import androidx.annotation.RequiresApi;

import com.basilgames.android.jackal.database.Tile;
import com.basilgames.android.jackal.database.TileRepository;

import java.util.List;

public class ZoomableView extends ViewGroup {

    // States.
    private static final byte NONE = 0;
    private static final byte DRAG = 1;
    private static final byte ZOOM = 2;
    private static final byte DRAG2 = 3;

    private byte mode = NONE;
    private byte lastMode = NONE;

    // Matrices used to move and zoom image.
    private Matrix matrix = new Matrix();
    private Matrix matrixInverse = new Matrix();
    private Matrix savedMatrix = new Matrix();

    // Parameters for zooming.
    private PointF start = new PointF();
    private PointF mid = new PointF();
    private float oldDist = 1f;
    private float[] lastEvent = null;
    private long lastDownTime = 0L;
    int elementTouched = 0;

    private float[] mDispatchTouchEventWorkingArray = new float[2];
    private float[] mOnTouchEventWorkingArray = new float[2];


    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        mDispatchTouchEventWorkingArray[0] = ev.getX();
        mDispatchTouchEventWorkingArray[1] = ev.getY();
        screenPointsToScaledPoints(mDispatchTouchEventWorkingArray);
        ev.setLocation(mDispatchTouchEventWorkingArray[0], mDispatchTouchEventWorkingArray[1]);
        return super.dispatchTouchEvent(ev);
    }

    public ZoomableView(Context context) {
        super(context);
        init(context);
    }

    public ZoomableView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public ZoomableView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }


    private void init(Context context) {

    }


    /**
     * Determine the space between the first two fingers
     */
    private float spacing(MotionEvent event) {
        float x = event.getX(0) - event.getX(1);
        float y = event.getY(0) - event.getY(1);
        return (float) Math.sqrt(x * x + y * y);
    }

    /**
     * Calculate the mid point of the first two fingers
     */
    private void midPoint(PointF point, MotionEvent event) {
        float x = event.getX(0) + event.getX(1);
        float y = event.getY(0) + event.getY(1);
        point.set(x / 2, y / 2);
    }

    private void scaledPointsToScreenPoints(float[] a) {
        matrix.mapPoints(a);
    }

    private float[] screenPointsToScaledPoints(float[] a) {
        matrixInverse.mapPoints(a);
        return a;
    }



    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        int childCount = getChildCount();
        for (int i = 0; i < childCount; i++) {
            View child = getChildAt(i);
            if (child.getVisibility() != GONE)
            {
                measureChild(child, widthMeasureSpec, heightMeasureSpec);
            }
        }
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        int childCount = getChildCount();
        for (int i = 0; i < childCount; i++) {
            View child = getChildAt(i);

            int offsetL =  child.getLeft();
            int offsetT =  child.getTop();

            if (child.getVisibility() != GONE)
            {
                child.layout(left + offsetL, top + offsetT, left + child.getMeasuredWidth() + offsetL, top + child.getMeasuredHeight() + offsetT);

            }
        }
    }

    @Override
    protected void dispatchDraw(Canvas canvas) {
        float[] values = new float[9];
        matrix.getValues(values);
        canvas.save();
        canvas.translate(values[Matrix.MTRANS_X], values[Matrix.MTRANS_Y]);

        canvas.scale(values[Matrix.MSCALE_X], values[Matrix.MSCALE_Y]);
        super.dispatchDraw(canvas);
        canvas.restore();
    }


    public void addChild(android.view.View child, int _x, int _y, int _w, int _h)
    {
        int x = (int) (_x * getResources().getDisplayMetrics().density);
        int y = (int) (_y * getResources().getDisplayMetrics().density);
        int w = (int) (_w * getResources().getDisplayMetrics().density);
        int h = (int) (_h * getResources().getDisplayMetrics().density);

        child.setLeft(x);
        child.setTop(y);
        ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(w, h);

        Log.d("cats", "addChild x:" + _x + " y:" + _y + " w:"  + _w + " h:" + _h + " " + x + " " + y + " " + w + " " + h );

        addView(child, params);
    }


    int i;
    int j;



    @RequiresApi(api = Build.VERSION_CODES.R)
    void calcTileId(float  x, float y)
    {

        int w;

        final WindowManager wm = (WindowManager) getContext()
                .getSystemService(Context.WINDOW_SERVICE);
        final Display d = wm.getDefaultDisplay();
        final DisplayMetrics met = new DisplayMetrics();
        d.getMetrics(met);
        w = met.widthPixels;
        int sideLen = w/13;
        float den = getResources().getDisplayMetrics().density;
        screenPointsToScaledPoints(new float[] {x, y});
        Toast.makeText(getContext().getApplicationContext(),"w = " + w + "x = " + x +" y = "+ y, Toast.LENGTH_LONG).show();
        if (x < 0 || x > w /* * den*/ || y < 0 || y > w /* * den*/)
            i = -1;
        else {
            for (i = 0; i < 13; i++) {
                if (i * (sideLen)  <= x && (i + 1) * (sideLen)  >= x) {
                    //Toast.makeText(getContext().getApplicationContext()," i" + i, Toast.LENGTH_LONG).show();
                    break;
                }
            }

            for (j = 0; j < 13; j++) {
                if (j * (sideLen)  <= y && (j + 1) * (sideLen)  >= y) {
                    //Toast.makeText(getContext().getApplicationContext()," j" + j, Toast.LENGTH_LONG).show();
                    break;
                }
            }
        }
    }

    int getWhichChildTouched(float  x, float y)
    {
        screenPointsToScaledPoints(new float[] {x, y});
        int childCount = getChildCount();
        float offsetX = 0;
        float offsetY = 0;
        float w = 0;
        float h = 0;

        for (int i = childCount-1; i >= 0; i--) {
            View child = getChildAt(i);
            if (child.getVisibility() != GONE)
            {
                h = child.getMeasuredHeight();
                w = child.getMeasuredWidth();
                offsetX = child.getLeft();
                offsetY = child.getTop();

                if (x <= offsetX + w && x >= offsetX && y <= offsetY + h && y >= offsetY)
                {
                    return i;
                }

            }
        }

        return 0;
    }

    int leftM;
    int topM;
    boolean movedFlag = false;

    @RequiresApi(api = Build.VERSION_CODES.R)
    @SuppressLint("ClickableViewAccessibility")
    @Override
    public boolean onTouchEvent(MotionEvent event)
    {

        mOnTouchEventWorkingArray[0] = event.getX();
        mOnTouchEventWorkingArray[1] = event.getY();


        if (mode != DRAG2)
            elementTouched = getWhichChildTouched(mOnTouchEventWorkingArray[0], mOnTouchEventWorkingArray[1]);
        if (elementTouched == 0)
            calcTileId(mOnTouchEventWorkingArray[0], mOnTouchEventWorkingArray[1]);

        scaledPointsToScreenPoints(mOnTouchEventWorkingArray);

        event.setLocation(mOnTouchEventWorkingArray[0], mOnTouchEventWorkingArray[1]);

        if (elementTouched == 0)
            switch (event.getAction() & MotionEvent.ACTION_MASK) {
                case MotionEvent.ACTION_DOWN:
                    savedMatrix.set(matrix);

                    mode = DRAG;
                    lastEvent = null;

                    long downTime = event.getDownTime();
                    if (downTime - lastDownTime < 300L)
                    {
                        float density = getResources().getDisplayMetrics().density;
                        if (Math.max(Math.abs(start.x - event.getX()), Math.abs(start.y - event.getY())) < 40.f * density)
                        {
                            savedMatrix.set(matrix);

                            mid.set(event.getX(), event.getY());
                            mode = ZOOM;
                            lastEvent = new float[4];
                            lastEvent[0] = lastEvent[1] = event.getX();
                            lastEvent[2] = lastEvent[3] = event.getY();
                        }
                        lastDownTime = 0L;
                    }
                    else
                    {
                        lastDownTime = downTime;
                    }

                    start.set(event.getX(), event.getY());
                    break;

                case MotionEvent.ACTION_POINTER_DOWN:
                    oldDist = spacing(event);
                    if (oldDist > 10f) {
                        savedMatrix.set(matrix);

                        midPoint(mid, event);
                        mode = ZOOM;
                    }
                    lastEvent = new float[4];
                    lastEvent[0] = event.getX(0);
                    lastEvent[1] = event.getX(1);
                    lastEvent[2] = event.getY(0);
                    lastEvent[3] = event.getY(1);

                    break;

                case MotionEvent.ACTION_UP:
                    long upTime = event.getEventTime();
                    if (!movedFlag)
                        if (upTime - lastDownTime > android.view.ViewConfiguration.getLongPressTimeout() * 2L)
                        {
                            //Toast.makeText(getContext().getApplicationContext()," long press i "+ i + " j " + j, Toast.LENGTH_LONG).show();

                            if (i != -1)
                            {
                                if ((i == 0 || j == 0 || i == 12 || j == 12) ||(i == 1 && j == 1 || i == 1 && j == 11
                                    || i == 11 && j == 1 || i == 11 && j == 11))
                                { }
                                else
                                {
                                    int tileTouchedId = TileRepository.get().getTile2(i, j).getViewId();
                                    TileView tileTouched = findViewById(tileTouchedId);
                                    tileTouched.flipTile();
                                }
                            }
                        }
                    movedFlag = false;
                case MotionEvent.ACTION_POINTER_UP:

                    lastMode = mode;
                    mode = NONE;
                    lastEvent = null;
                    break;

                case MotionEvent.ACTION_MOVE:
                    final float density = getResources().getDisplayMetrics().density;
                    if (mode == DRAG)
                    {
                        Toast.makeText(getContext().getApplicationContext()," action move", Toast.LENGTH_LONG).show();

                        matrix.set(savedMatrix);

                        float dx = event.getX() - start.x;
                        float dy = event.getY() - start.y;
                        //if (dx > 100 || dy > 100)
                        movedFlag = true;
                        matrix.postTranslate(dx, dy);
                        matrix.invert(matrixInverse);
                        if (Math.max(Math.abs(start.x - event.getX()), Math.abs(start.y - event.getY())) > 20.f * density)
                        {
                            lastDownTime = 0L;
                        }
                    } else
                    if (mode == ZOOM)
                    {
                        matrix.set(savedMatrix);
                        float scale = 1F;
                        if (event.getPointerCount() > 1)
                        {
                            float newDist = spacing(event);
                            if (newDist > 10f * density)
                            {
                                scale = (newDist / oldDist);
                            }
                        }
                        else
                        {
                            scale = event.getY() / start.y;
                        }
                        matrix.postScale(scale, scale, mid.x, mid.y);
                        matrix.invert(matrixInverse);
                    }
                    movedFlag = true;
                    break;
            }
        else
        {
            View child = getChildAt(elementTouched);
            float x = event.getX();
            float y = event.getY();

            switch (event.getAction() & MotionEvent.ACTION_MASK) {
                case MotionEvent.ACTION_DOWN:
                    start.set(event.getX(), event.getY());

                    leftM = child.getLeft();
                    topM = child.getTop();

                    mode = DRAG2;
                    break;

                case MotionEvent.ACTION_UP:
                case MotionEvent.ACTION_POINTER_UP:
                    lastMode = mode;
                    mode = NONE;
                    lastEvent = null;
                    break;

                case MotionEvent.ACTION_MOVE:

                    if (mode == DRAG2)
                    {

                        float[] values = new float[9];
                        matrix.getValues(values);

                        float tX =  values[Matrix.MTRANS_X];
                        float tY =  values[Matrix.MTRANS_Y];

                        float dx = x - start.x;
                        float dy = y - start.y;

                        float deltas[] = screenPointsToScaledPoints(new float[] {dx + tX, dy + tY});


                        child.setLeft((int) (leftM + deltas[0]));
                        child.setRight(child.getLeft() + child.getMeasuredWidth());
                        child.setTop((int) (topM + deltas[1]));
                        child.setBottom(child.getTop() + child.getMeasuredHeight());
                    }
                    break;
            }

        }


        invalidate();
        return true;
    }

}
