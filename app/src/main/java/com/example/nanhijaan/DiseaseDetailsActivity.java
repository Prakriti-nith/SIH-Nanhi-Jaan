package com.example.nanhijaan;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.example.nanhijaan.R;

public class DiseaseDetailsActivity extends AppCompatActivity {

    Toolbar toolbar;
    FloatingActionButton fab;
    CardView symptoms_cv, prevention_cv, mgmt_cv, special_cv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_disease_details);
        init();
        setListeners();

        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Nanhi Jaan");

    }

    private void init() {
        toolbar = findViewById(R.id.toolbar);
        fab = findViewById(R.id.fab);
        symptoms_cv = findViewById(R.id.symptomscv);
        prevention_cv = findViewById(R.id.preventioncv);
        mgmt_cv = findViewById(R.id.managementcv);
        special_cv = findViewById(R.id.specialcv);
    }

    private void setListeners() {
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        symptoms_cv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String heading = "Symptoms";
                Intent i = new Intent(DiseaseDetailsActivity.this, SymptomsDetailsActivity.class);
                i.putExtra("heading",heading);
                startActivity(i);
            }
        });
        prevention_cv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String heading = "Prevention";
                Intent i = new Intent(DiseaseDetailsActivity.this, SymptomsDetailsActivity.class);
                i.putExtra("heading",heading);
                startActivity(i);
            }
        });
        mgmt_cv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String heading = "Management";
                Intent i = new Intent(DiseaseDetailsActivity.this, SymptomsDetailsActivity.class);
                i.putExtra("heading",heading);
                startActivity(i);
            }
        });
        special_cv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String heading = "Special Needs";
                Intent i = new Intent(DiseaseDetailsActivity.this, SymptomsDetailsActivity.class);
                i.putExtra("heading",heading);
                startActivity(i);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_parent) {
            Intent i = new Intent(DiseaseDetailsActivity.this, ParentSuggestions.class);
            startActivity(i);
        }
        else if(id == R.id.action_language) {
            Intent i = new Intent(DiseaseDetailsActivity.this, LanguageActivity.class);
            startActivity(i);
        }
        else if(id == R.id.action_contact) {
            Intent i = new Intent(DiseaseDetailsActivity.this, ContactUsActivity.class);
            startActivity(i);
        }

        return super.onOptionsItemSelected(item);
    }
}
