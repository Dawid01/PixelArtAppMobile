package com.szczepaniak.dawid.pixelapp;

import android.content.Context;
import android.view.View;

/**
 * Created by dawid on 05.02.2018.
 */

public class ColorItem extends View {

    int color;


    public ColorItem(Context context, int color) {
        super(context);
        this.color = color;
    }

    public int getColor() {
        return color;
    }
}
