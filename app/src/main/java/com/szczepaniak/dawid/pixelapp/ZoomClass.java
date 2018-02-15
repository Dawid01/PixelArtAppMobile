package com.szczepaniak.dawid.pixelapp;

import android.content.Context;
import android.support.v4.view.GestureDetectorCompat;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.widget.RelativeLayout;

/**
 * Created by dawid on 15.02.2018.
 */

public class ZoomClass extends ScaleGestureDetector.SimpleOnScaleGestureListener {

    RelativeLayout layout;
    Context context;
    public final static float MAX_Z00M = 4f;
    public final static float MIN_Z00M = 1f;
    public  float mScaleFactor = 1f;
    private ScaleGestureDetector scaleGestureDetector;

    public ZoomClass(Context context, RelativeLayout layout) {
        this.layout = layout;
        this.context = context;

    }

    @Override
    public boolean onScale(ScaleGestureDetector detector) {
        mScaleFactor += detector.getScaleFactor();
        mScaleFactor = Math.max(MIN_Z00M, Math.min(MAX_Z00M, mScaleFactor));
        layout.setScaleX(mScaleFactor);
        layout.setScaleY(mScaleFactor);
        return super.onScale(detector);
    }


}
