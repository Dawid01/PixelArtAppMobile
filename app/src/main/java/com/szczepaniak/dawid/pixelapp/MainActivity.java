package com.szczepaniak.dawid.pixelapp;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.drawable.GradientDrawable;
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
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import java.io.EOFException;
import java.util.zip.Inflater;

public class MainActivity extends AppCompatActivity {

    DrawView dv ;
    private Paint mPaint;
    View plane;
    ImageView colorIMG;
    ImageView zoomCircle;
    Canvas zoomCnvas;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setTitle("");

        dv = findViewById(R.id.Layout);
        dv.setContext(MainActivity.this);
        colorIMG = findViewById(R.id.Color);
        colorIMG.setBackgroundColor(Color.GREEN);
        zoomCircle = findViewById(R.id.ZoomCircle);

        dv.paint.setColor(Color.GREEN);
        dv.paint.setStrokeWidth(dv.rec);

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
                dv.paint.setColor(Color.GREEN);
                return true;
            case R.id.Rubber:
                dv.draw = false;
                dv.paint.setColor(Color.WHITE);
                return true;
            case R.id.Grid:
                dv.showGrid = !dv.showGrid;
//                dv.onDraw(dv.mCanvas);
                return true;
            case R.id.Palette:
                colorPickerFunctions();
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

    void colorPickerFunctions() {

        AlertDialog.Builder mBuilder = new AlertDialog.Builder(MainActivity.this);
        View colorPicker = getLayoutInflater().inflate(R.layout.color_picker, null);

        final ColorBar colorBar = colorPicker.findViewById(R.id.colorBar);
        final ColorGradient colorGradient = colorPicker.findViewById(R.id.ColorGradient);
        final ImageView imageColor = colorPicker.findViewById(R.id.ColorImage);
        imageColor.setBackgroundColor(Color.GREEN);
        final TextView alphaText = colorPicker.findViewById(R.id.AlphaText);
        final SeekBar seekBarAlpha = colorPicker.findViewById(R.id.seekBarAlpha);
       // colorGradient.drawGradnient(colorGradient.getNewCanvas(),Color.GREEN);

        alphaText.setText("" +  ((255-seekBarAlpha.getProgress()) *100/255) + "%");

        seekBarAlpha.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                alphaText.setText("" +  ((255-seekBarAlpha.getProgress()) *100/255) + "%");
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        colorGradient.setDrawingCacheEnabled(true);
        colorBar.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction() & MotionEvent.ACTION_MASK) {
                    case MotionEvent.ACTION_DOWN:
                        colorBar.setDrawingCacheEnabled(true);
                        colorBar.buildDrawingCache();
                        Bitmap bitmap = colorBar.getDrawingCache();
                        int x = (int)event.getX();
                        int y = (int)event.getY();
                        int pixel = bitmap.getPixel(x,y);
                        int r = Color.red(pixel);
                        int g = Color.green(pixel);
                        int b = Color.blue(pixel);
                        int color = Color.rgb(r,g,b);
                        colorGradient.drawGradnient(colorGradient.getNewCanvas(),color);
                        imageColor.setBackgroundColor(color);
                        dv.paint.setColor(color);
                        colorIMG.setBackgroundColor(color);

                        break;
                    case MotionEvent.ACTION_MOVE:
                        break;
                }

                return false;
            }
        });

        colorGradient.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                colorGradient.setDrawingCacheEnabled(true);
                colorGradient.buildDrawingCache();
                final Bitmap gradientPaletteBtm = colorGradient.getDrawingCache();


                switch (event.getAction() & MotionEvent.ACTION_MASK) {
                    case MotionEvent.ACTION_DOWN:
                        imageColor.setBackgroundColor(colorGradient.color);
                        colorIMG.setBackgroundColor(colorGradient.color);

                        break;
                    case MotionEvent.ACTION_MOVE:
                        try {
                            imageColor.setBackgroundColor(colorGradient.color);
                            dv.paint.setColor(colorGradient.color);
                            colorIMG.setBackgroundColor(colorGradient.color);
                        }catch (Exception e){

                        }

                        break;
                }

                return false;
            }
        });

        mBuilder.setView(colorPicker);
        final AlertDialog colorPickerDialog = mBuilder.create();



        colorPickerDialog.show();
    }
}