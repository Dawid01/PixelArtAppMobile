package com.szczepaniak.dawid.pixelapp;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ComposeShader;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.RadialGradient;
import android.graphics.Shader;
import android.graphics.SweepGradient;
import android.os.Build;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by dawid on 03.02.2018.
 */

public class ColorGradient extends View {

    Paint paint =  new Paint();
    Canvas newCanvas;
    Bitmap bitmap;
    private Paint circlePaint;
    private Path circlePath;
    private float mX, mY;
    int color;
    Bitmap gradientPaletteBtm;
    int loop = 0;
    float tx,ty;
    int gradientColor;

    public ColorGradient(Context context, AttributeSet attrs) {
        super(context,attrs);

        circlePaint = new Paint();
        circlePath = new Path();
        circlePaint.setAntiAlias(true);
        circlePaint.setColor(Color.DKGRAY);
        circlePaint.setStyle(Paint.Style.STROKE);
        circlePaint.setStrokeJoin(Paint.Join.MITER);
        circlePaint.setStrokeWidth(3f);
        gradientColor = Color.RED;
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        drawGradnient(canvas,gradientColor);
        canvas.drawPath(circlePath,circlePaint);
        newCanvas = canvas;

    }


    void drawGradnient(Canvas canvas, int gradientColor){


        //canvas.drawColor(android.graphics.Color.BLACK);
        bitmap = Bitmap.createBitmap(210, 210, Bitmap.Config.ALPHA_8);
        paint.setColor(android.graphics.Color.BLUE);

        Shader mShader = new LinearGradient(0, 0, 210, 0, new int[] {
                android.graphics.Color.WHITE,gradientColor, Color.BLACK},
                null, Shader.TileMode.CLAMP);
        Shader mShader2 = new LinearGradient(0, 0, 0, 210, new int[] {
                Color.TRANSPARENT, Color.BLACK},
                null, Shader.TileMode.CLAMP);
        Shader shaderMulti = new ComposeShader(mShader2, mShader, PorterDuff.Mode.OVERLAY);
        Canvas c = new Canvas(bitmap);
        paint.setShader(shaderMulti);
        c.drawRect(0, 0, 210, 210, paint);
        canvas.drawBitmap(bitmap, 0,0, paint);
        this.setDrawingCacheEnabled(true);
        this.buildDrawingCache();
        gradientPaletteBtm = this.getDrawingCache();
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

    private void startTouch(float x, float y) {
        try {
            circlePath.reset();
            circlePath.reset();
            circlePath.addCircle(x, y, 3, Path.Direction.CW);
            int pixel = gradientPaletteBtm.getPixel((int) x, (int) y);
            int r = Color.red(pixel);
            int g = Color.green(pixel);
            int b = Color.blue(pixel);
            color = Color.rgb(r, g, b);
        }catch (Exception e){}
    }

    private void moveTouch(float x, float y) {
        try{
            circlePath.reset();
            //circlePath.addCircle(x, y, 3, Path.Direction.CW);
            int pixel = gradientPaletteBtm.getPixel((int) x,(int) y);
            int r = Color.red(pixel);
            int g = Color.green(pixel);
            int b = Color.blue(pixel);
            color = Color.rgb(r,g,b);
            tx = x;
            ty = y;
        }catch (Exception e){}
    }

    private void upTouch() {

        circlePath.addCircle(tx, ty, 3, Path.Direction.CW);
    }

    void getColorBar(){
        int pixel = gradientPaletteBtm.getPixel((int) tx,(int)ty);
        int r = Color.red(pixel);
        int g = Color.green(pixel);
        int b = Color.blue(pixel);
        color = Color.rgb(r,g,b);
    }

    void refreshCanvas(){
        drawGradnient(newCanvas, color);
    }
}
