package com.szczepaniak.dawid.pixelapp;

import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;

public class SettingsActivity extends AppCompatActivity {

    DrawView dv;
    Singleton singleton;
    MainActivity mainActivity;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        singleton = Singleton.getInstance();
        dv = singleton.getDrawView();

        Button Save = findViewById(R.id.Save);
        Button Export = findViewById(R.id.Save);
        Button New = findViewById(R.id.Save);
        FloatingActionButton back = findViewById(R.id.Back);

        final Switch gridSwitch =  findViewById(R.id.gridSwitch);
        Switch zoomSwitch =  findViewById(R.id.zoomSwitch);

        gridSwitch.setChecked(singleton.isGrid());
        zoomSwitch.setChecked(singleton.isTouchZoom());

            gridSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                    singleton.setGrid(isChecked);
                    singleton.getMainActivity().changeGrid();
                }
            });

        zoomSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                singleton.setTouchZoom(isChecked);
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                SettingsActivity.this.overridePendingTransition(0,
                        R.anim.lefttoright);
            }
        });

        Save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(singleton.porjectIsSaved == false) {
                    CreateProject();
                }else {

                    SavingSystem savingSystem =  new SavingSystem();
                    BitmapString bitmapString =  new BitmapString();
                    singleton.setGrid(false);
                    dv.invalidate();
                    dv.setDrawingCacheEnabled(true);
                    ProjectItem projectItem = new ProjectItem(bitmapString.BitMapToString(dv.getDrawingCache()),singleton.getProjectName());
                    singleton.setGrid(true);
                    dv.invalidate();
                    savingSystem.saveProject(projectItem,singleton.getProjectIndex(), SettingsActivity.this);
                    Toast.makeText(SettingsActivity.this,"" + singleton.getProjectName() + " has been saved!",Toast.LENGTH_SHORT).show();

                }
            }
        });

    }



    void CreateProject(){


        AlertDialog.Builder mBuilder = new AlertDialog.Builder(SettingsActivity.this);
        final View createProject = getLayoutInflater().inflate(R.layout.create_project, null);

        final EditText name = createProject.findViewById(R.id.ProjectName);
        Button create = createProject.findViewById(R.id.Create);
        mBuilder.setView(createProject);
        final AlertDialog dialog = mBuilder.create();
        create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String n = name.getText().toString();
                int count = 0;

                for( int i=0; i<n.length(); i++ ) {
                    count++;
                }
                if(count <= 10){

                    SavingSystem savingSystem =  new SavingSystem();
                    BitmapString bitmapString =  new BitmapString();
                    singleton.setGrid(false);
                    dv.invalidate();
                    dv.setDrawingCacheEnabled(true);
                    ProjectItem projectItem = new ProjectItem(bitmapString.BitMapToString(dv.getDrawingCache()),name.getText().toString());
                    singleton.setGrid(true);
                    dv.invalidate();
                    savingSystem.saveNewProject(projectItem, SettingsActivity.this);
                    dialog.dismiss();
                    Toast.makeText(SettingsActivity.this,"" + name.getText() + " has been created!",Toast.LENGTH_SHORT).show();


                }else {

                    Toast.makeText(SettingsActivity.this,"Max 10 chars", Toast.LENGTH_SHORT).show();
                }
            }
        });


        dialog.show();

    }
}
