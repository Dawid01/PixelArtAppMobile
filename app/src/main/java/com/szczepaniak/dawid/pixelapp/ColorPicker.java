package com.szczepaniak.dawid.pixelapp;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AbsListView;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by dawid on 06.02.2018.
 */

public class ColorPicker {

    int createdColor;
    Context context;
    LayoutInflater inflater;
    final ImageView colorIMG;
    final DrawView dv;
    ArrayList<ColorItem> colorItems;
    GridLayout colorsPalette;
    public ColorPicker(Context context, LayoutInflater inflater, final ImageView colorIMG, final DrawView dv, ArrayList<ColorItem> colorItems, GridLayout colorsGrid) {

        this.context = context;
        this.inflater = inflater;
        this.colorIMG = colorIMG;
        this.dv = dv;
        this.colorItems = colorItems;
        this.colorsPalette = colorsGrid;

    }

    public void createColorPicker(){
        AlertDialog.Builder mBuilder = new AlertDialog.Builder(context);
        final View colorPicker = inflater.inflate(R.layout.color_picker, null);

        final ColorBar colorBar = colorPicker.findViewById(R.id.colorBar);
        final ColorGradient colorGradient = colorPicker.findViewById(R.id.ColorGradient);
        final ImageView imageColor = colorPicker.findViewById(R.id.ColorImage);
        imageColor.setBackgroundColor(Color.GREEN);
        final TextView alphaText = colorPicker.findViewById(R.id.AlphaText);
        final TextView colorText = colorPicker.findViewById(R.id.ColorText);
        final Button ok = colorPicker.findViewById(R.id.OK);
        final Button save = colorPicker.findViewById(R.id.Save);
        final Button back = colorPicker.findViewById(R.id.Back);
        final GridLayout colorsGrid = colorPicker.findViewById(R.id.Colors);
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

        colorBar.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction() & MotionEvent.ACTION_MASK) {
                    case MotionEvent.ACTION_DOWN:
                        colorGradient.refreshCanvas();
                        colorGradient.getColorBar();
                        colorGradient.gradientColor = colorBar.color;
                        imageColor.setBackgroundColor(colorGradient.color);
                        createdColor = colorGradient.color;
                        String hexColor = String.format("#%06X", (0xFFFFFF & colorGradient.color));
                        colorText.setText(hexColor);

                        break;
                    case MotionEvent.ACTION_MOVE:
                        colorGradient.refreshCanvas();
                        colorGradient.getColorBar();
                        colorGradient.gradientColor = colorBar.color;
                        imageColor.setBackgroundColor(colorGradient.color);
                        createdColor = colorGradient.color;
                        String hexColor2 = String.format("#%06X", (0xFFFFFF & colorGradient.color));
                        colorText.setText(hexColor2);
                        break;
                }

                return false;
            }
        });

        colorGradient.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                switch (event.getAction() & MotionEvent.ACTION_MASK) {
                    case MotionEvent.ACTION_DOWN:
                        imageColor.setBackgroundColor(colorGradient.color);
                        createdColor = colorBar.color;
                        break;
                    case MotionEvent.ACTION_MOVE:

                        imageColor.setBackgroundColor(colorGradient.color);
                        createdColor = colorGradient.color;
                        String hexColor = String.format("#%06X", (0xFFFFFF & colorGradient.color));
                        colorText.setText(hexColor);

                        break;
                }

                return false;
            }
        });

        mBuilder.setView(colorPicker);
        final AlertDialog colorPickerDialog = mBuilder.create();
        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dv.paint.setColor(createdColor);
                colorIMG.setBackgroundColor(colorGradient.color);
                colorPickerDialog.dismiss();
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                colorPickerDialog.dismiss();
            }
        });

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int size = colorsGrid.getHeight()/6;
                final ColorItem colorItem = new ColorItem(context, createdColor);
                colorItems.add(colorItem);
              //  colorsGrid.removeAllViews();
                //reloadColorItems(colorsGrid);

                colorItem.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        colorIMG.setBackgroundColor(colorItem.getColor());
                        dv.paint.setColor(colorItem.getColor());
                    }
                });

                colorsPalette.addView(colorItem);
            }
        });

        //colorsGrid.removeAllViews();
       // reloadColorItems(colorsGrid);
        colorPickerDialog.show();
    }

    void reloadColorItems(GridLayout gridLayout){

        int count = colorItems.size();
        gridLayout.removeAllViews();
        
        for(int i = 0; i < count; i++){

           final ColorItem colorItem = colorItems.get(i);

            colorItem.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {

                    createdColor = colorItem.getColor();
                    colorIMG.setBackgroundColor(createdColor);
                    return false;
                }
            });

            gridLayout.addView(colorItem);

        }
    }


}
