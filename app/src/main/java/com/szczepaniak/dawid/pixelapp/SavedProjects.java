package com.szczepaniak.dawid.pixelapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.preference.PreferenceManager;
import android.support.design.widget.FloatingActionButton;
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

        projectItems = savingSystem.getProjectsList(SavedProjects.this);

       // for (int i = 0; i < 20; i++){

            //projectItems.add(new ProjectItem(null,"Project" + i));
       // }
        LayoutInflater planItem = (LayoutInflater) getSystemService(SavedProjects.LAYOUT_INFLATER_SERVICE);

        for(int i = 0; i < projectItems.size(); i++){

            final ProjectItem item = projectItems.get(i);
            final View projectView = planItem.inflate(R.layout.project, layout, false);
            TextView projectName = projectView.findViewById(R.id.ProjectName);
            ImageView img = projectView.findViewById(R.id.ProjectImage);
            BitmapString bitmapString =  new BitmapString();
            img.setImageBitmap(bitmapString.StringToBitMap(item.getArtBitmap()));
            projectName.setText(item.getNameProject());
            projectListLayout.addView(projectView);

            projectView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Intent intent = new Intent(SavedProjects.this, MainActivity.class);
                    intent.putExtra("BitmapString", item.getArtBitmap());
                    startActivity(intent);
                    SavedProjects.this.overridePendingTransition(0,
                            R.anim.lefttoright);
                }
            });

        }

    }



}
