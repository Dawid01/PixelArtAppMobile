package com.szczepaniak.dawid.pixelapp;

/**
 * Created by dawid on 25.03.2018.
 */

public class Singleton {
    private static final Singleton ourInstance = new Singleton();

    int ProjectIndex;
    String ProjectName;
    DrawView drawView;

    boolean isGrid = true;
    boolean isTouchZoom = false;
    boolean porjectIsSaved;


    MainActivity mainActivity;

    public static Singleton getInstance() {
        return ourInstance;
    }

    private Singleton() {
    }

    public int getProjectIndex() {
        return ProjectIndex;
    }

    public void setProjectIndex(int projectIndex) {
        ProjectIndex = projectIndex;
    }

    public String getProjectName() {
        return ProjectName;
    }

    public void setProjectName(String projectName) {
        ProjectName = projectName;
    }

    public DrawView getDrawView() {
        return drawView;
    }

    public void setDrawView(DrawView drawView) {
        this.drawView = drawView;
    }

    public boolean isGrid() {
        return isGrid;
    }

    public void setGrid(boolean grid) {
        isGrid = grid;
    }

    public boolean isTouchZoom() {
        return isTouchZoom;
    }

    public void setTouchZoom(boolean touchZoom) {
        isTouchZoom = touchZoom;
    }

    public boolean isPorjectIsSaved() {
        return porjectIsSaved;
    }

    public void setPorjectIsSaved(boolean porjectIsSaved) {
        this.porjectIsSaved = porjectIsSaved;
    }

    public MainActivity getMainActivity() {
        return mainActivity;
    }

    public void setMainActivity(MainActivity mainActivity) {
        this.mainActivity = mainActivity;
    }
}
