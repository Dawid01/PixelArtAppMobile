package com.szczepaniak.dawid.pixelapp;

import android.graphics.Bitmap;

/**
 * Created by dawid on 22.03.2018.
 */

public class ProjectItem {

    String artBitmap;
    String nameProject;

    public ProjectItem(String artBitmap, String nameProject) {
        this.artBitmap = artBitmap;
        this.nameProject = nameProject;
    }


    public String getArtBitmap() {
        return artBitmap;
    }

    public void setArtBitmap(String artBitmap) {
        this.artBitmap = artBitmap;
    }

    public String getNameProject() {
        return nameProject;
    }

    public void setNameProject(String nameProject) {
        this.nameProject = nameProject;
    }
}
