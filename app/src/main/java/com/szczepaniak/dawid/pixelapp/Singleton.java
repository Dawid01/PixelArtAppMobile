package com.szczepaniak.dawid.pixelapp;

/**
 * Created by dawid on 25.03.2018.
 */

public class Singleton {
    private static final Singleton ourInstance = new Singleton();

    int ProjectIndex;
    String ProjectName;

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
}
