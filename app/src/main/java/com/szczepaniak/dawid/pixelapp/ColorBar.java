package com.szczepaniak.dawid.pixelapp;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Shader;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by dawid on 03.02.2018.
 */

public class ColorBar extends View {

    Paint paint =  new Paint();
    Bitmap bitmap;

    public ColorBar(Context context,AttributeSet attrs) {
        super(context,attrs);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        bitmap = Bitmap.createBitmap(200, 200, Bitmap.Config.ALPHA_8);
        paint.setColor(android.graphics.Color.BLUE);

        Shader mShader = new LinearGradient(0, 0, 0, 200, new int[] {
                0xFFFF0000, 0xFFFF00FF, 0xFF0000FF, 0xFF00FFFF, 0xFF00FF00,
                0xFFFFFF00, 0xFFFF0000},
                null, Shader.TileMode.REPEAT);
        Canvas c = new Canvas(bitmap);
        paint.setShader(mShader);
        //c.drawCircle(60, 60, 30, paint);
        c.drawRect(0, 0, 200, 200, paint);
        canvas.drawBitmap(bitmap, 10,10, paint);
    }

    public Bitmap getBitmap() {
        return bitmap;
    }

}
