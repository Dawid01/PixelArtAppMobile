package com.szczepaniak.dawid.pixelapp;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.GradientDrawable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    DrawView dv ;
    ImageView colorIMG;
    ImageView zoomCircle;
    ImageView AlphaBG;
    ArrayList<ColorItem> colorItems;
    RelativeLayout appLayout;
    RelativeLayout canvasLayout;
    ImageView TypeImage;
    GridLayout colorsGrid;
    boolean isZoom;

    boolean projectIsSaved;
    int projectIndex;

    FloatingActionButton projectsButton;
    Singleton singleton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle("");

        colorItems = new ArrayList<>();

        dv = findViewById(R.id.Layout);
        dv.setContext(MainActivity.this);
        colorIMG = findViewById(R.id.Color);
        colorIMG.setBackgroundColor(Color.GREEN);
        zoomCircle = findViewById(R.id.ZoomCircle);
        AlphaBG = findViewById(R.id.AlphaBG);
        AlphaBG.setVisibility(View.GONE);

        TypeImage = findViewById(R.id.TypeImage);
        colorsGrid = findViewById(R.id.Palette);

        dv.paint.setColor(Color.GREEN);
        dv.paint.setStrokeWidth(dv.rec);

        appLayout = findViewById(R.id.AppLayout);
        canvasLayout = findViewById(R.id.CanvasLayout);

        projectsButton = findViewById(R.id.Back);


        singleton = Singleton.getInstance();

        singleton.setMainActivity(this);
        projectsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, SavedProjects.class);
                startActivity(intent);
                MainActivity.this.overridePendingTransition(R.anim.righttoleft,
                        R.anim.stay);
            }
        });

        ZoomClass zoomClass = new ZoomClass(MainActivity.this, canvasLayout);

        dv.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                if (!isZoom) {
                    switch (event.getAction() & MotionEvent.ACTION_MASK) {
                        case MotionEvent.ACTION_DOWN:
                            if (singleton.isTouchZoom == true && dv.styleDraw != 2) {
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
                            if (singleton.isTouchZoom == true) {
                                circleZoomSystem((int) event.getX(), (int) event.getY());
                            }
                            break;
                    }
                }
                return false;

            }

        });


        String bitmapString = getIntent().getStringExtra("BitmapString");

        if(bitmapString != null){

            projectIsSaved = true;
            projectIndex = singleton.getProjectIndex();
            BitmapString b = new BitmapString();
            Bitmap bitmap = b.StringToBitMap(bitmapString);
            dv.DrawNewBitmap(bitmap);
        }


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
                TypeImage.setImageResource(R.mipmap.drawikon);
                isZoom = false;
                dv.isZoom = false;
                dv.rubber = false;
                dv.styleDraw = 0;
                return true;
            case R.id.Rubber:
                // dv.draw = false;
                //dv.paint.setColor(Color.WHITE);
                TypeImage.setImageResource(R.mipmap.rubberikon);
                isZoom = false;
                dv.isZoom = false;
                dv.styleDraw = 1;
                return true;
            case R.id.Options:
                optionsPopUp();
                return true;

            case R.id.Fill:
                TypeImage.setImageResource(R.mipmap.filicon);
                dv.styleDraw = 2;
                return true;
            case R.id.Move:
                TypeImage.setImageResource(R.mipmap.moveikon);
                isZoom = true;
                dv.isZoom = true;
                return true;
            case R.id.Palette:
                ColorPicker colorPicker= new ColorPicker(MainActivity.this, getLayoutInflater(), colorIMG, dv, colorItems, colorsGrid);
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

        if (singleton.isTouchZoom == true) {
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
        if(singleton.isGrid == true){
            AlphaBG.setVisibility(View.GONE);
        }else {
            AlphaBG.setVisibility(View.VISIBLE);
        }
    }

    void optionsPopUp(){

        singleton.setPorjectIsSaved(projectIsSaved);
        singleton.setDrawView(dv);
        Intent intent = new Intent(MainActivity.this, SettingsActivity.class);
        startActivity(intent);
        MainActivity.this.overridePendingTransition(R.anim.righttoleft,
                R.anim.stay);

    }




}


//https://android-arsenal.com/details/1/6282