package com.example.nanhijaan;

import android.content.Context;
import android.speech.tts.TextToSpeech;

import java.util.Locale;

public class TTSService implements TextToSpeech.OnInitListener {
    Context context;
    TextToSpeech tts;

    public TTSService(Context c){
        context = c;
        tts = new TextToSpeech(context, this);
    }

    public void onInit(int initStatus) {
        if (initStatus == TextToSpeech.SUCCESS) {
            tts.setLanguage(Locale.getDefault());
        }
    }

    public void say(String announcement) {
        tts.speak(announcement, TextToSpeech.QUEUE_FLUSH, null);
    }
}
