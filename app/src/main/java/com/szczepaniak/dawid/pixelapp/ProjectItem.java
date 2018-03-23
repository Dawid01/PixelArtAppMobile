package com.szczepaniak.dawid.pixelapp;

import android.graphics.Bitmap;

/**
 * Created by dawid on 22.03.2018.
 */

public class ProjectItem {

    Bitmap artBitmap;
    String nameProject;

    public ProjectItem(Bitmap artBitmap, String nameProject) {
        this.artBitmap = artBitmap;
        this.nameProject = nameProject;
    }


    public Bitmap getArtBitmap() {
        return artBitmap;
    }

    public void setArtBitmap(Bitmap artBitmap) {
        this.artBitmap = artBitmap;
    }

    public String getNameProject() {
        return nameProject;
    }

    public void setNameProject(String nameProject) {
        this.nameProject = nameProject;
    }
}
