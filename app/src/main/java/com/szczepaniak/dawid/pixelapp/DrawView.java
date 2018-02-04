package com.szczepaniak.dawid.pixelapp;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import java.util.ArrayList;

/**
 * Created by dawid on 30.01.2018.
 */

public class DrawView extends View{

        private Bitmap mBitmap;
        Canvas mCanvas;
        private Path mPath;
        private Paint mBitmapPaint;
        Context context;
        private Paint circlePaint;
        private Path circlePath;
        float resolution =40f;
        private final Paint gridPaint = new Paint();

        boolean showGrid = true;
        boolean draw = true;
        boolean rubber = false;
        boolean zoomTouch = true;

        float tx,ty;
        ArrayList<Float> Xpos;
        ArrayList<Float> Ypos;
        float rec;

    Paint paint = new Paint();

        public DrawView(Context c,  AttributeSet attrs) {
            super(c, attrs);
            context= getContext();
            mPath = new Path();
            mBitmapPaint = new Paint(Paint.DITHER_FLAG);
            circlePaint = new Paint();
            circlePath = new Path();
            circlePaint.setAntiAlias(true);
            circlePaint.setColor(Color.DKGRAY);
            circlePaint.setStyle(Paint.Style.STROKE);
            circlePaint.setStrokeJoin(Paint.Join.MITER);
            circlePaint.setStrokeWidth(4f);
            Xpos = new ArrayList<>();
            Ypos = new ArrayList<>();
        }

        @Override
        protected void onSizeChanged(int w, int h, int oldw, int oldh) {
            super.onSizeChanged(w, h, oldw, oldh);

            mBitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
            mCanvas = new Canvas(mBitmap);
        }

        @Override
        protected void onDraw(Canvas canvas) {
            super.onDraw(canvas);
            canvas.drawBitmap( mBitmap, 0, 0, mBitmapPaint);

            if (showGrid) {
                int width = getMeasuredWidth();
                int height = getMeasuredHeight();
                for (int i = 1; i <resolution; i++) {
                    canvas.drawLine(width * i / resolution, 0, width * i / resolution, height, gridPaint);
                }

                for (int i = 1; i < resolution; i++) {
                    canvas.drawLine(0, height * i / resolution, width, height * i / resolution, gridPaint);
                }
            }
        }

        private float mX, mY;

        private void startTouch(float x, float y) {
                Draw(x, y);
        }

        private void moveTouch(float x, float y) {

                Draw(x, y);
                if(zoomTouch) {
                    circlePath.reset();
                    circlePath.addCircle(mX, mY - 100, 80, Path.Direction.CW);
                    circlePath.addCircle(mX, mY - 100, 2, Path.Direction.CW);
                    circlePath.addCircle(mX, mY - 100, 80, Path.Direction.CW);
                }

        }

        private void upTouch() {
            circlePath.reset();
            mCanvas.drawPath(mPath,  paint);
            mPath.reset();
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


    public void setContext(Context context) {
        this.context = context;
    }


    void Draw(float touchX, float touchY){

        if(Xpos.size() == 0|| Ypos.size() == 0) {
            rec = 350f/resolution;
            float pixelCount = 350f/ rec;
            float x = 0;
            float y = 0;
            for (float i = 0; i < pixelCount; i++) {

                if (i > 0) {
                    x += rec;
                    y += rec;
                    Xpos.add(x);
                    Ypos.add(y);
                }else {
                    x = rec/2f;
                    y = rec/2f;
                    Xpos.add(x);
                    Ypos.add(y);
                }

            }
        }

        mPath.reset();
        mPath.moveTo(touchX, touchY);
        tx = touchX;
        ty = touchX;

        RoundPos roundPos =  new RoundPos();
        float posX =  roundPos.roundPos(touchX,Xpos);
        float posY =  roundPos.roundPos(touchY,Ypos);
        paint.setStrokeWidth(rec);
        mCanvas.drawPoint(posX,posY,paint);
    }

    void resetCanvas(){

        mCanvas.drawBitmap( mBitmap, 0, 0, mBitmapPaint);

        if (showGrid) {
            int width = getMeasuredWidth();
            int height = getMeasuredHeight();
            for (int i = 1; i <resolution; i++) {
                mCanvas.drawLine(width * i / resolution, 0, width * i / resolution, height, gridPaint);
            }

            for (int i = 1; i < resolution; i++) {
                mCanvas.drawLine(0, height * i / resolution, width, height * i / resolution, gridPaint);
            }
        }
    }
}
