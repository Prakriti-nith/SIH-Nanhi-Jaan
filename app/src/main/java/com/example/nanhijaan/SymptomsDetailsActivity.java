package com.example.nanhijaan;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

public class SymptomsDetailsActivity extends AppCompatActivity {

    Toolbar toolbar;
    String heading, content[], details = "";
    TextView heading_tv, content_tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_symptoms_details);
        init();
        Intent mIntent = getIntent();
        heading = mIntent.getStringExtra("heading");
        heading_tv.setText(heading);
        int length = mIntent.getIntExtra("length", 0);
        content = mIntent.getStringArrayExtra("content");

        for(int i=0; i<length; i++) {
            details = details + content[i] + "\n";
        }
        content_tv.setText(details);
    }

    private void init() {
        heading_tv = findViewById(R.id.intro);
        content_tv = findViewById(R.id.intro_content);
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
            Intent i = new Intent(SymptomsDetailsActivity.this, ParentSuggestions.class);
            startActivity(i);
        }
        else if(id == R.id.action_language) {
            Intent i = new Intent(SymptomsDetailsActivity.this, LanguageActivity.class);
            startActivity(i);
        }
        else if(id == R.id.action_contact) {
            Intent i = new Intent(SymptomsDetailsActivity.this, ContactUsActivity.class);
            startActivity(i);
        }

        return super.onOptionsItemSelected(item);
    }
}
