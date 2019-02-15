package com.example.nanhijaan;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

public class MainActivity extends AppCompatActivity {

    Toolbar toolbar;
    FloatingActionButton fab;
    CardView disease_cp_cv, disease_au_cv, disease_ds_cv, disease_cv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();
        setListener();

        setSupportActionBar(toolbar);
    }

    private void setListener() {
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        disease_cp_cv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String disease_name = "Cerebral Palsy";
                Intent i = new Intent(MainActivity.this, DiseaseDetailsActivity.class);
                i.putExtra("disease",disease_name);
                startActivity(i);
            }
        });
        disease_au_cv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String disease_name = "Cerebral Palsy";
                Intent i = new Intent(MainActivity.this, DiseaseDetailsActivity.class);
                i.putExtra("disease",disease_name);
                startActivity(i);
            }
        });
        disease_ds_cv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String disease_name = "Cerebral Palsy";
                Intent i = new Intent(MainActivity.this, DiseaseDetailsActivity.class);
                i.putExtra("disease",disease_name);
                startActivity(i);
            }
        });
        disease_cv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String disease_name = "Cerebral Palsy";
                Intent i = new Intent(MainActivity.this, DiseaseDetailsActivity.class);
                i.putExtra("disease",disease_name);
                startActivity(i);
            }
        });
    }

    private void init() {
        toolbar = findViewById(R.id.toolbar);
        fab = findViewById(R.id.fab);
        disease_cp_cv = findViewById(R.id.diseasecv1);
        disease_au_cv = findViewById(R.id.diseasecv2);
        disease_ds_cv = findViewById(R.id.diseasecv3);
        disease_cv = findViewById(R.id.diseasecv4);
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
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
