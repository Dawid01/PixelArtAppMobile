package com.szczepaniak.dawid.pixelapp;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

/**
 * Created by dawid on 22.03.2018.
 */

public class SavingSystem {

    ArrayList<ProjectItem> projectItems;

    public ArrayList<ProjectItem> getProjectItems() {
        return projectItems;
    }


    public void saveNewProject(ProjectItem item, Context context){
        if(projectItems != null) {
            projectItems.clear();
        }
        projectItems = getProjectsList(context);
        projectItems.add(item);
        SaveProjects(projectItems,context);
    }

    public void saveProject(ProjectItem item, int index, Context context){
        if(projectItems != null) {
            projectItems.clear();
        }
        projectItems = getProjectsList(context);
        projectItems.remove(index);
        projectItems.add(index,item);
        SaveProjects(projectItems,context);
    }

    public void deleteProject(int index, Context context){
        if(projectItems != null) {
            projectItems.clear();
        }
        projectItems = getProjectsList(context);
        projectItems.remove(index);
        SaveProjects(projectItems,context);

    }


    public void SaveProjects(ArrayList<ProjectItem> projectItems, Context context){

        SharedPreferences sharedPreferences = PreferenceManager
                .getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson gson = new Gson();

        String json = gson.toJson(projectItems);
        editor.putString("projects",json);
        editor.apply();
    }

    ArrayList<ProjectItem>  getProjectsList(Context context){
        ArrayList<ProjectItem> projectItems;

        SharedPreferences sharedPreferences = PreferenceManager
                .getDefaultSharedPreferences(context);
        ArrayList<ProjectItem> planClasses1 =  new ArrayList<>();
        Gson gson = new Gson();
        String newList = gson.toJson(planClasses1);
        String json = sharedPreferences.getString("projects", newList);
        Type type = new TypeToken<ArrayList<ProjectItem>>() {}.getType();
        projectItems = gson.fromJson(json, type);
        return projectItems;
    }
}
