package com.szczepaniak.dawid.pixelapp;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.RadialGradient;
import android.graphics.Shader;
import android.graphics.SweepGradient;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by dawid on 03.02.2018.
 */

public class ColorGradient extends View {

    Paint paint =  new Paint();
    Canvas newCanvas;
    Bitmap bitmap;
    public ColorGradient(Context context, AttributeSet attrs) {
        super(context,attrs);

    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int i = 0;
        if(i == 0) {
            drawGradnient(canvas, Color.BLUE);
        }
        i++;
        newCanvas = canvas;

    }


    void drawGradnient(Canvas canvas, int Color){

        //canvas.drawColor(android.graphics.Color.BLACK);
        bitmap = Bitmap.createBitmap(200, 200, Bitmap.Config.ALPHA_8);
        paint.setColor(android.graphics.Color.BLUE);

        Shader mShader = new LinearGradient(0, 0, 210, 0, new int[] {
                android.graphics.Color.WHITE,Color},
                null, Shader.TileMode.REPEAT);
        //new float[]{0,0.5f,.55f,1}
        Canvas c = new Canvas(bitmap);
        paint.setShader(mShader);
        //c.drawCircle(60, 60, 30, paint);
        c.drawRect(0, 0, 210, 210, paint);
        canvas.drawBitmap(bitmap, 10,10, paint);
    }

    public Canvas getNewCanvas() {
        return newCanvas;
    }

    public Bitmap getBitmap() {
        return bitmap;
    }

}
