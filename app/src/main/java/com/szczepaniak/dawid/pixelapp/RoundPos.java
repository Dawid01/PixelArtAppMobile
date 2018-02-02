package com.szczepaniak.dawid.pixelapp;

import java.util.ArrayList;

/**
 * Created by dawid on 02.02.2018.
 */

public class RoundPos {


    float roundPos(float i, ArrayList<Float> list){

        ArrayList<Double> deltaList =  new ArrayList<>();

        int count = list.size();
        float newPos = 0;
        double mPos = 1000000000;
        for(int j = 0; j < count; j++){

            double delta = Math.sqrt((Math.abs(i - list.get(j))));

            if(delta < mPos){

                mPos = delta;
                newPos = list.get(j);
            }
        }

        return newPos;
    }

}
