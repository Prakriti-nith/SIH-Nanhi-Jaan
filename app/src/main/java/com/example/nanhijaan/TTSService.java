package com.example.nanhijaan;

import android.content.Context;
import android.speech.tts.TextToSpeech;

import java.util.Locale;

public class TTSService implements TextToSpeech.OnInitListener {
    Context context;
    TextToSpeech tts;
    Locale locale;

    public TTSService(Context c, String language){
        context = c;
        tts = new TextToSpeech(context, this);
        locale = new Locale(language);
    }

    public void onInit(int initStatus) {
        if (initStatus == TextToSpeech.SUCCESS) {
            tts.setLanguage(locale);
        }
    }

    public void say(String announcement) {
        tts.speak(announcement, TextToSpeech.QUEUE_FLUSH, null);
    }
}
