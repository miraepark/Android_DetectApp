package com.example.samsung.detectapp;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by NINESTAIRS on 2017. 2. 20..
 */

public class DetailCanvasView extends View {

    private float getX2;
    private float getY2;

    private Context context;

    public DetailCanvasView(Context context) {
        super(context);
        this.context = context;
    }

    public DetailCanvasView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
    }

    public DetailCanvasView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
    }

    public DetailCanvasView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        this.context = context;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        Paint paint = new Paint();

        paint.setColor(Color.RED);

        Path path = new Path();

        path.addCircle(getX2,getY2,20, Path.Direction.CW);

        canvas.drawPath(path,paint);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
            case MotionEvent.ACTION_MOVE:
            case MotionEvent.ACTION_UP:
                getX2 = event.getX();
                getY2 = event.getY();

                context.getSharedPreferences("pref",Context.MODE_PRIVATE).edit().putString("getX2",getX2+"").commit();
                context.getSharedPreferences("pref",Context.MODE_PRIVATE).edit().putString("getY2",getY2+"").commit();

                invalidate();
        }

        return super.onTouchEvent(event);
    }
}





