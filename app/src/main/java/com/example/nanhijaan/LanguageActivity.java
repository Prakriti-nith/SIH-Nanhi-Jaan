package com.example.nanhijaan;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.View;

public class LanguageActivity extends AppCompatActivity {

    CardView eng_cv, hindi_cv, telugu_cv, punjabi_cv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_language);
        init();
        add_listeners();
    }

    private void add_listeners() {
        set_language(hindi_cv, "Hindi");
        set_language(eng_cv, "English");
        set_language(telugu_cv, "Telugu");
        set_language(punjabi_cv, "Punjabi");
    }

    private void set_language(CardView cv, final String language) {
        cv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SetLanguage.setDefaults(SetLanguage.LANGUAGE, language, LanguageActivity.this);
                Intent i = new Intent(LanguageActivity.this, MainActivity.class);
                startActivity(i);
            }
        });
    }

    private void init() {
        eng_cv = findViewById(R.id.englishcv);
        hindi_cv = findViewById(R.id.hindicv);
        telugu_cv = findViewById(R.id.telugucv);
        punjabi_cv = findViewById(R.id.punjabicv);
    }
}
