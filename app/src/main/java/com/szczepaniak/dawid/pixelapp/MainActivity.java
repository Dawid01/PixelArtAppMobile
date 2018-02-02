package com.szczepaniak.dawid.pixelapp;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.drawable.GradientDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import java.util.zip.Inflater;

public class MainActivity extends AppCompatActivity {

    DrawView dv ;
    private Paint mPaint;
    View plane;
    ImageView color;
    ImageView zoomCircle;
    Canvas zoomCnvas;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dv = findViewById(R.id.Layout);
        dv.setContext(MainActivity.this);
        color = findViewById(R.id.Color);
        color.setBackgroundColor(Color.GREEN);
        zoomCircle = findViewById(R.id.ZoomCircle);



        dv.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {


                switch (event.getAction() & MotionEvent.ACTION_MASK) {
                    case MotionEvent.ACTION_DOWN:
                        if (dv.zoomTouch) {

                            zoomCircle.setVisibility(View.VISIBLE);
                            GradientDrawable gd = new GradientDrawable();
                           // gd.setColor(Color.TRANSPARENT);
                            gd.setCornerRadius(140);
                            gd.setStroke(2, Color.DKGRAY);
                            gd.setBounds(0,98, 0, 0);
                            gd.draw(getZoomCnvas((int)event.getX(),(int)event.getY()));
                            gd.setDither(true);
                            zoomCircle.setBackground(gd);
                        }
                    case MotionEvent.ACTION_UP:
                        zoomCircle.setVisibility(View.GONE);
                        break;
                    case MotionEvent.ACTION_POINTER_DOWN:
                        break;
                    case MotionEvent.ACTION_POINTER_UP:
                        break;
                    case MotionEvent.ACTION_MOVE:
                        if (dv.zoomTouch) {
                            zoomCircle.setVisibility(View.VISIBLE);

                            zoomCircle.setX(event.getX() - (zoomCircle.getWidth()/2));
                            zoomCircle.setY(event.getY() - 10);

                            GradientDrawable gd = new GradientDrawable();
                           // gd.setColor(Color.TRANSPARENT);
                            gd.setCornerRadius(140);
                            gd.setStroke(2, Color.DKGRAY);
                            gd.setBounds(0,98, 0, 0);
                            gd.draw(getZoomCnvas((int)event.getX(),(int)event.getY()));
                            gd.setDither(true);
                            zoomCircle.setBackground(gd);
                        }
                        break;
                }

                    return false;
                }

        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.Draw:
                dv.draw = true;
                dv.pencil.setColor(Color.GREEN);
                return true;
            case R.id.Rubber:
                dv.draw = true;
                dv.pencil.setColor(Color.TRANSPARENT);
                return true;
            case R.id.Grid:
                dv.showGrid = !dv.showGrid;
                return true;
            case R.id.Zoom:
                dv.zoomTouch = !dv.zoomTouch;
                return true;
            default:
                return false;
        }
    }


    Canvas getZoomCnvas(int x, int y){

        Canvas canvas;
       // dv.setDrawingCacheEnabled(true);
        View v = getWindow().getDecorView().getRootView();
        v.setDrawingCacheEnabled(true);
        Bitmap bitmap = Bitmap.createBitmap(v.getDrawingCache());
       // Bitmap circleBtm = Bitmap.createBitmap(bitmap, x,y, 80,80);
        canvas = new Canvas(bitmap);
        return  canvas;
    }

}