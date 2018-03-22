package com.szczepaniak.dawid.pixelapp;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class SavedProjects extends AppCompatActivity {

    FloatingActionButton back;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_saved_projects);
        getSupportActionBar().setTitle("Projects");
        back = findViewById(R.id.Back);


        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                SavedProjects.this.overridePendingTransition(0,
                        R.anim.lefttoright);
            }
        });
    }
}
