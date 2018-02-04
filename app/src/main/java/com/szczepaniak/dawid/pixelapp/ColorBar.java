package com.szczepaniak.dawid.pixelapp;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Shader;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by dawid on 03.02.2018.
 */

public class ColorBar extends View {

    Paint paint =  new Paint();
    Bitmap bitmap;
    private Paint linePaint;
    private Path linePath;
    public ColorBar(Context context,AttributeSet attrs) {
        super(context,attrs);
        linePaint = new Paint();
        linePath = new Path();
        linePaint.setAntiAlias(true);
        linePaint.setColor(Color.DKGRAY);
        linePaint.setStyle(Paint.Style.STROKE);
        linePaint.setStrokeJoin(Paint.Join.MITER);
        linePaint.setStrokeWidth(3f);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);


        bitmap = Bitmap.createBitmap(200, 210, Bitmap.Config.ALPHA_8);
        paint.setColor(android.graphics.Color.BLUE);

        Shader mShader = new LinearGradient(0, 0, 0, 200, new int[] {
                0xFFFF0000, 0xFFFF00FF, 0xFF0000FF, 0xFF00FFFF, 0xFF00FF00,
                0xFFFFFF00, 0xFFFF0000},
                null, Shader.TileMode.REPEAT);
        Canvas c = new Canvas(bitmap);
        paint.setShader(mShader);
        //c.drawCircle(60, 60, 30, paint);
        c.drawRect(0, 0, 200, 200, paint);
        canvas.drawBitmap(bitmap, 10,0, paint);
        canvas.drawPath(linePath,linePaint);

    }

    public Bitmap getBitmap() {
        return bitmap;
    }


    private void startTouch(float x, float y) {
        linePath.reset();
       // linePath.addRect(0, y, 50,2, Path.Direction.CW);
        linePath.addCircle(30, y, 3, Path.Direction.CW);

    }

    private void moveTouch(float x, float y) {
        linePath.reset();
        linePath.addCircle(30, y, 3, Path.Direction.CW);
        //linePath.addRect(0, y, 50,2, Path.Direction.CW);



    }

    private void upTouch() {
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float x = event.getX();
        float y = event.getY();

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                startTouch(x, y);
                invalidate();
                break;
            case MotionEvent.ACTION_MOVE:
                moveTouch(x, y);
                invalidate();
                break;
            case MotionEvent.ACTION_UP:
                upTouch();
                invalidate();
                break;
        }
        return true;
    }
}
