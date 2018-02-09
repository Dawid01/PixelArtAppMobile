package com.szczepaniak.dawid.pixelapp;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.view.View;
import android.widget.AbsListView;
import android.widget.GridLayout;
import android.widget.ImageView;

/**
 * Created by dawid on 05.02.2018.
 */

public class ColorItem extends View{

    int color;

    public ColorItem(Context context, int color) {
        super(context);
        this.color = color;
        setLayoutParams(new AbsListView.LayoutParams(55, 55));
        GradientDrawable gd = new GradientDrawable();
        gd.setColor(color);
        gd.setCornerRadius(10);
        gd.setStroke(2, Color.rgb(79,91,98));
        setBackgroundDrawable(gd);
        setPadding(5,5,5,5);

        AbsListView.LayoutParams params = (AbsListView.LayoutParams)this.getLayoutParams();
        this.setMargins(this,2, 2, 2, 2);
        setLayoutParams(params);

    }

    public int getColor() {
        return color;
    }

    public static void setMargins (View v, int l, int t, int r, int b) {
        if (v.getLayoutParams() instanceof AbsListView.MarginLayoutParams) {
            AbsListView.MarginLayoutParams p = (AbsListView.MarginLayoutParams) v.getLayoutParams();
            p.setMargins(l, t, r, b);
            v.requestLayout();
        }
    }
}
