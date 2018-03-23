package com.szczepaniak.dawid.pixelapp;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.DiscretePathEffect;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.drawable.BitmapDrawable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

/**
 * Created by dawid on 30.01.2018.
 */

public class DrawView extends View{

        Bitmap mBitmap;
        Bitmap newBitmap;

          Canvas mCanvas;
        private Path mPath;
        private Paint mBitmapPaint;
        Context context;
        private Paint circlePaint;
        private Path circlePath;
        float resolution =40f;
        private final Paint gridPaint = new Paint();
        int mColor = Color.GREEN;

        boolean showGrid = true;
        boolean draw = true;
        boolean rubber = false;
        boolean zoomTouch = true;

        int styleDraw;

        boolean isZoom;

        float tx,ty;
        ArrayList<Float> Xpos;
        ArrayList<Float> Ypos;
        float rec;

        boolean isBackDraws;

        ArrayList<Canvas> savedDraws;

        boolean isNewBitmap;

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
            savedDraws =  new ArrayList<>();

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
            if(newBitmap!= null){
                Paint paint = new Paint();
                paint.setAntiAlias(true);
                paint.setFilterBitmap(true);
                paint.setDither(true);
                canvas.drawBitmap(newBitmap,0,0,paint);
            }
                canvas.drawBitmap(mBitmap, 0, 0, mBitmapPaint);
                Paint cr = new Paint();
                cr.setColor(Color.BLACK);
                cr.setStrokeWidth(2);
                canvas.drawPath(circlePath, cr);
                savedDraws.add(canvas);



                if(isBackDraws){

                    isBackDraws = false;
                    Canvas can = savedDraws.get(savedDraws.size()-1);
                    canvas = can;
                }

                if (showGrid) {
                    int width = getMeasuredWidth();
                    int height = getMeasuredHeight();
                    for (int i = 1; i < resolution; i++) {
                        canvas.drawLine(width * i / resolution, 0, width * i / resolution, height, gridPaint);
                    }

                    for (int i = 1; i < resolution; i++) {
                        canvas.drawLine(0, height * i / resolution, width, height * i / resolution, gridPaint);
                    }
                }

        }

        private float mX, mY;

        public void DrawNewBitmap(Bitmap bitmap){

            newBitmap = bitmap;
            invalidate();
        }

        private void startTouch(float x, float y) {

            Draw(x, y);

        }

        private void moveTouch(float x, float y) {

            if(styleDraw != 2) {
                Draw(x, y);
                if (zoomTouch) {
                    circlePath.reset();
                    circlePath.reset();
                    //circlePath.addCircle(mX, mY - 100, 80, Path.Direction.CW);
                    circlePath.addCircle(x, y, 2, Path.Direction.CCW);

                    // circlePath.addCircle(mX, mY - 100, 80, Path.Direction.CW);
                }
            }

        }

        private void upTouch() {
            circlePath.reset();
            mCanvas.drawPath(mPath,  paint);
            mPath.reset();
            circlePath.reset();
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
                case MotionEvent.ACTION_HOVER_ENTER:
                    moveTouch(x, y);
                    invalidate();
                    break;
                case MotionEvent.ACTION_HOVER_MOVE:
                    moveTouch(x, y);
                    invalidate();
                    break;
            }
            return true;
        }


    public void setContext(Context context) {
        this.context = context;
    }


    void Draw(float touchX, float touchY){


        if(!isZoom) {

                if (Xpos.size() == 0 || Ypos.size() == 0) {
                    rec = 350f / resolution;
                    float pixelCount = 350f / rec;
                    float x = 0;
                    float y = 0;
                    for (float i = 0; i < pixelCount; i++) {

                        if (i > 0) {
                            x += rec;
                            y += rec;
                            Xpos.add(x);
                            Ypos.add(y);
                        } else {
                            x = rec / 2f;
                            y = rec / 2f;
                            Xpos.add(x);
                            Ypos.add(y);
                        }

                    }
                }

                mPath.reset();
                mPath.moveTo(touchX, touchY);
                tx = touchX;
                ty = touchX;

                RoundPos roundPos = new RoundPos();
                float posX = roundPos.roundPos(touchX, Xpos);
                float posY = roundPos.roundPos(touchY, Ypos);
                paint.setStrokeWidth(rec);
            switch (styleDraw) {
                case 0:
                    paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC));
                   // paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.OVERLAY));
                    mCanvas.drawPoint(posX, posY, paint);
                    break;
                case 1:
                    Paint rubberPaint = paint;
                    rubberPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.CLEAR));
                    mCanvas.drawPoint(posX, posY, rubberPaint);
                    break;
                case 2:
                   // DiscretePathEffect discretePathEffect =
                          //  new DiscretePathEffect(segmentLength, deviation);

//                    paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_OVER));
//                    Paint fillPaint = paint;
//                    fillPaint.setStyle(Paint.Style.FILL);
//                    //fillPaint.setPathEffect(discretePathEffect);
//                    mCanvas.drawPaint(fillPaint);

                    showGrid = false;
                    invalidate();
                    this.setDrawingCacheEnabled(true);
                    this.buildDrawingCache();
                    Bitmap btm = this.getDrawingCache(false);
                   // Bitmap btm = Bitmap.createBitmap(this.getWidth(),this.getHeight(), Bitmap.Config.ARGB_8888);
                    //mCanvas.setBitmap(btm);
                    int pixel = btm.getPixel((int)touchX,(int)touchY);
                    int targetColor = Color.argb(Color.alpha(pixel),Color.red(pixel),Color.green(pixel),Color.blue(pixel));
                    showGrid = true;
                    invalidate();
                    mCanvas.drawBitmap(btm,0,0,null);
                    Point point = new Point((int)touchX, (int)touchY);
                    floodFill(btm,point,targetColor,paint.getColor(), mCanvas);
                    break;
            }
        }
    }

    public void floodFill(Bitmap  image, Point node, int targetColor,
                          int replacementColor, Canvas canvas) {
        int width = image.getWidth();
        int height = image.getHeight();
        int target = targetColor;
        int replacement = replacementColor;
        if (target != replacement) {
            Queue<Point> queue = new LinkedList<>();
            do {
                int x = node.x;
                int y = node.y;
                while (x > 0 && image.getPixel(x - 1, y) == target) {
                    x--;
                }
                boolean spanUp = false;
                boolean spanDown = false;
                while (x < width && image.getPixel(x, y) == target) {
                    image.setPixel(x, y, replacement);
                    if (!spanUp && y > 0 && image.getPixel(x, y - 1) == target) {
                        queue.add(new Point(x, y - 1));
                        spanUp = true;
                    } else if (spanUp && y > 0
                            && image.getPixel(x, y - 1) != target) {
                        spanUp = false;
                    }
                    if (!spanDown && y < height - 1
                            && image.getPixel(x, y + 1) == target) {
                        queue.add(new Point(x, y + 1));
                        spanDown = true;
                    } else if (spanDown && y < height - 1
                            && image.getPixel(x, y + 1) != target) {
                        spanDown = false;
                    }
                    x++;
                }
            } while ((node = queue.poll()) != null);
        }

        canvas.drawBitmap(image,0,0,null);
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

    void loadBackDraw(){

        if(savedDraws.size() != 0){

            savedDraws.remove(savedDraws.size() -1);
            isBackDraws = true;
            invalidate();

        }
    }
}
