package com.szczepaniak.dawid.pixelapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.preference.PreferenceManager;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.TypefaceSpan;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class SavedProjects extends AppCompatActivity {

    FloatingActionButton back;
    ArrayList<ProjectItem> projectItems;
    SavingSystem savingSystem;
    RelativeLayout layout;
    LinearLayout projectListLayout;
    Singleton singleton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_saved_projects);
        getSupportActionBar().setTitle("Projects");
        back = findViewById(R.id.Back);
        layout = findViewById(R.id.layout);
        projectListLayout = findViewById(R.id.ProjectList);
        savingSystem =  new SavingSystem();
        projectItems =  new ArrayList<>();
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                SavedProjects.this.overridePendingTransition(0,
                        R.anim.lefttoright);
            }
        });

        loadProjects();
        singleton = Singleton.getInstance();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event)
    {
        if ((keyCode == KeyEvent.KEYCODE_BACK))
        {
            finish();
            SavedProjects.this.overridePendingTransition(0,
                    R.anim.lefttoright);
        }
        return super.onKeyDown(keyCode, event);
    }

    void loadProjects(){

        projectListLayout.removeAllViews();
        projectItems = savingSystem.getProjectsList(SavedProjects.this);

        LayoutInflater planItem = (LayoutInflater) getSystemService(SavedProjects.LAYOUT_INFLATER_SERVICE);

        for(int i = 0; i < projectItems.size(); i++){

            final int index = i;
            final ProjectItem item = projectItems.get(i);
            final View projectView = planItem.inflate(R.layout.project, layout, false);
            TextView projectName = projectView.findViewById(R.id.ProjectName);
            ImageView img = projectView.findViewById(R.id.ProjectImage);
            ImageView delete = projectView.findViewById(R.id.Delete);

            BitmapString bitmapString =  new BitmapString();
            final Bitmap btm = bitmapString.StringToBitMap(item.getArtBitmap());
            img.setImageBitmap(btm);
            projectName.setText(item.getNameProject());
            projectListLayout.addView(projectView);


            delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    savingSystem.deleteProject(index,SavedProjects.this);
                    String name = item.getNameProject();
                    loadProjects();
                    Toast.makeText(SavedProjects.this,"" + name + " has been deleted!",Toast.LENGTH_SHORT).show();
                }
            });

            img.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    AlertDialog.Builder mBuilder = new AlertDialog.Builder(SavedProjects.this);
                    final View ArtShow = getLayoutInflater().inflate(R.layout.show_art, null);

                    ImageView image =  ArtShow.findViewById(R.id.Image);
                    mBuilder.setView(ArtShow);
                    final AlertDialog dialog = mBuilder.create();
                    image.setImageBitmap(btm);
                    dialog.show();
                }
            });

            projectView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Intent intent = new Intent(SavedProjects.this, MainActivity.class);
                    intent.putExtra("BitmapString", item.getArtBitmap());
                    singleton.setProjectIndex(index);
                    singleton.setProjectName(item.getNameProject());
                    startActivity(intent);
                    SavedProjects.this.overridePendingTransition(0,
                            R.anim.lefttoright);
                }
            });

            projectView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {



                    return false;
                }
            });

        }

    }



}
