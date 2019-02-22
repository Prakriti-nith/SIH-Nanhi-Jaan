package com.example.nanhijaan;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

public class LanguageActivity extends AppCompatActivity {

    CardView eng_cv, hindi_cv, punjabi_cv;
    TextView choose_tv;
    String language;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_language);
        init();
        getLanguage();
        setTextViewLanguages();
        add_listeners();
    }

    private void setTextViewLanguages() {
        if(language == "hindi") {
            choose_tv.setText(getString(R.string.hindi_choose_language));
        }
        else if(language == "punjabi") {
            choose_tv.setText(getString(R.string.punjabi_choose_language));
        }
        else if(language == "english") {
            choose_tv.setText(getString(R.string.eng_choose_language));
        }
    }

    private void add_listeners() {
        set_language(hindi_cv, "hindi");
        set_language(eng_cv, "english");
        set_language(punjabi_cv, "punjabi");
    }

    private void getLanguage() {
        language = SetLanguage.getDefaults(SetLanguage.LANGUAGE, LanguageActivity.this);
        Log.d("1234", "getLanguage: " + language);
    }

    private void set_language(CardView cv, final String language) {
        cv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SetLanguage.setDefaults(SetLanguage.LANGUAGE, language, LanguageActivity.this);
                Intent i = new Intent(LanguageActivity.this, MainActivity.class);
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(i);
            }
        });
    }

    private void init() {
        eng_cv = findViewById(R.id.englishcv);
        hindi_cv = findViewById(R.id.hindicv);
        punjabi_cv = findViewById(R.id.punjabicv);

        choose_tv = findViewById(R.id.language_tv);
    }
}
