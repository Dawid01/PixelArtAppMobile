package com.szczepaniak.dawid.pixelapp;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.ScaleDrawable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import java.io.EOFException;
import java.util.ArrayList;
import java.util.zip.Inflater;

public class MainActivity extends AppCompatActivity {

    DrawView dv ;
    ImageView colorIMG;
    ImageView zoomCircle;
    ImageView AlphaBG;
    ArrayList<ColorItem> colorItems;
    RelativeLayout appLayout;
    RelativeLayout canvasLayout;
    ImageView TypeImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle("");

        colorItems =  new ArrayList<>();

        dv = findViewById(R.id.Layout);
        dv.setContext(MainActivity.this);
        colorIMG = findViewById(R.id.Color);
        colorIMG.setBackgroundColor(Color.GREEN);
        zoomCircle = findViewById(R.id.ZoomCircle);
        AlphaBG = findViewById(R.id.AlphaBG);
        AlphaBG.setVisibility(View.GONE);

        TypeImage = findViewById(R.id.TypeImage);


        dv.paint.setColor(Color.GREEN);
        dv.paint.setStrokeWidth(dv.rec);

        appLayout = findViewById(R.id.AppLayout);
        canvasLayout = findViewById(R.id.CanvasLayout);

        dv.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {


                switch (event.getAction() & MotionEvent.ACTION_MASK) {
                    case MotionEvent.ACTION_DOWN:
                        if (dv.zoomTouch) {
                            zoomCircle.setVisibility(View.VISIBLE);
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
                            circleZoomSystem((int)event.getX(), (int)event.getY());
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
                dv.paint.setColor(Color.GREEN);
                TypeImage.setImageResource(R.mipmap.drawikon);
                return true;
            case R.id.Rubber:
                dv.draw = false;
                dv.paint.setColor(Color.WHITE);
                TypeImage.setImageResource(R.mipmap.rubberikon);
                return true;
            case R.id.Options:

                optionsPopUp();
                return true;

            case R.id.Fill:
                TypeImage.setImageResource(R.mipmap.filicon);
                return true;
            case R.id.Move:
                TypeImage.setImageResource(R.mipmap.moveikon);
                return true;
            case R.id.Palette:
                ColorPicker colorPicker= new ColorPicker(MainActivity.this, getLayoutInflater(), colorIMG, dv, colorItems);
                colorPicker.createColorPicker();
                return true;
            default:
                return false;
        }
    }



    Canvas getZoomCnvas(int x, int y){

        Canvas canvas;
        dv.setDrawingCacheEnabled(true);
        dv.buildDrawingCache(true);
        Bitmap bitmap = Bitmap.createBitmap(dv.getDrawingCache());
        canvas = new Canvas(bitmap);
        return  canvas;
    }

    void circleZoomSystem(int x, int y){

        if (dv.zoomTouch) {
            try {
                zoomCircle.setVisibility(View.VISIBLE);
                zoomCircle.setX(x - (zoomCircle.getWidth() / 2));
                zoomCircle.setY(y - 20);
                View v = this.getWindow().getDecorView();
                v.setDrawingCacheEnabled(true);
                v.buildDrawingCache();
                canvasLayout.setDrawingCacheEnabled(true);
                canvasLayout.buildDrawingCache();
                Bitmap bitmap = canvasLayout.getDrawingCache();
//                int scale = 2;
//                x = x*scale;
//                y = y* scale;
              //  if(y < canvasLayout.g) {
                    bitmap = Bitmap.createBitmap(bitmap, x - 25, y - 25, 50, 50);
               // }
                GradientDrawable gd = new GradientDrawable();
                Canvas canvas =  new Canvas(bitmap);
               // zoomCircle.setImageBitmap(Bitmap.createScaledBitmap(bitmap,200,200,false));

                // gd.setColor(Color.TRANSPARENT);
                gd.setCornerRadius(140);
                gd.setStroke(2, Color.DKGRAY);
                gd.setBounds(0, 98, 0, 0);
                gd.draw(getZoomCnvas(x, y));
                gd.setDither(true);
                gd.draw(canvas);
                zoomCircle.setBackground(gd);
                BitmapDrawable ob = new BitmapDrawable(getResources(), Bitmap.createScaledBitmap(bitmap, 120, 120, false));
                ob.setBounds(0, 98, 0, 0);
                //zoomCircle.setBackgroundColor(Color.WHITE);
                zoomCircle.setImageBitmap(bitmap);

                //zoomCircle.setBackgroundColor(Color.WHITE);
            }catch (Exception e){}
        }
    }

    void changeGrid(){
        dv.showGrid = !dv.showGrid;
        if(dv.showGrid){
            AlphaBG.setVisibility(View.GONE);
        }else {
            AlphaBG.setVisibility(View.VISIBLE);
        }
    }

    void optionsPopUp(){

        AlertDialog.Builder mBuilder = new AlertDialog.Builder(MainActivity.this);
        final View options = getLayoutInflater().inflate(R.layout.options_layout, null);


        mBuilder.setView(options);
        final AlertDialog optionsDialog = mBuilder.create();

        optionsDialog.show();
    }

}