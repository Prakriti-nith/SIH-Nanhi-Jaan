package com.example.nanhijaan;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class ParentSuggestions extends AppCompatActivity {

    TextView suggestions_tv;
    Button submit_btn;
    String language;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parent_suggestions);
        init();
        getLanguage();
        setTextViewLanguages();
        submit_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    private void setTextViewLanguages() {
        if(language == "hindi") {
            suggestions_tv.setText(getString(R.string.hindi_suggestions));
            submit_btn.setText(getString(R.string.hindi_submit));
        }
        else if(language == "punjabi") {
            suggestions_tv.setText(getString(R.string.punjabi_suggestions));
            submit_btn.setText(getString(R.string.punjabi_submit));
        }
        else if(language == "english") {
            suggestions_tv.setText(getString(R.string.eng_suggestions));
            submit_btn.setText(getString(R.string.eng_submit));
        }
    }

    private void getLanguage() {
        language = SetLanguage.getDefaults(SetLanguage.LANGUAGE, ParentSuggestions.this);
        Log.d("1234", "getLanguage: " + language);
    }


    private void init() {
        suggestions_tv = findViewById(R.id.suggestions_tv);
        submit_btn = findViewById(R.id.suggestions_bt);
    }
}
