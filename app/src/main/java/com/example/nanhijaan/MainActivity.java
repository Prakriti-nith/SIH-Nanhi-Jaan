package com.example.nanhijaan;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Bundle;
import android.speech.RecognitionListener;
import android.speech.SpeechRecognizer;
import android.speech.tts.TextToSpeech;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.speech.RecognizerIntent;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Locale;
import java.util.Vector;

public class MainActivity extends AppCompatActivity {

    private Context mContext;
    Toolbar toolbar;
    FloatingActionButton fab;
    RelativeLayout disease_rl;
    int num_diseases, disease_IDs[];
    Vector<String> disease_names;
    CardView disease_cv[];
    ImageButton search_ib;
    EditText search_et;
    String language;
    JSONObject object;

    public static final int RECORD_AUDIO = 0;
    final SpeechRecognizer mSpeechRecognizer = SpeechRecognizer.createSpeechRecognizer(this);

    final Intent mSpeechRecognizerIntent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);

    private TTSService tts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mContext = getApplicationContext();

        init();
        getLanguage();
        fetchDataFromServer();

        tts = new TTSService(mContext);
        
        mSpeechRecognizerIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        mSpeechRecognizerIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());

        mSpeechRecognizer.setRecognitionListener(new RecognitionListener() {
            @Override
            public void onReadyForSpeech(Bundle bundle) {

            }

            @Override
            public void onBeginningOfSpeech() {

            }

            @Override
            public void onRmsChanged(float v) {

            }

            @Override
            public void onBufferReceived(byte[] bytes) {

            }

            @Override
            public void onEndOfSpeech() {

            }

            @Override
            public void onError(int i) {

            }

            @Override
            public void onResults(Bundle bundle) {
                //getting all the matches
                ArrayList<String> matches = bundle
                        .getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION);

                //displaying the first match
                if (matches != null) {
                    search_for_disease(matches.get(0));
                }
            }

            @Override
            public void onPartialResults(Bundle bundle) {

            }

            @Override
            public void onEvent(int i, Bundle bundle) {

            }
        });

        search_et.setOnKeyListener(new View.OnKeyListener(){
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event){
                if(keyCode == event.KEYCODE_ENTER || keyCode == event.KEYCODE_SEARCH || keyCode == event.KEYCODE_ESCAPE){
                    search_for_disease(search_et.getText().toString());
                }
                return true;
            }
        });

        setSupportActionBar(toolbar);
    }

    private void getLanguage() {
        language = SetLanguage.getDefaults(SetLanguage.LANGUAGE, MainActivity.this);
        Log.d("1234", "getLanguage: " + language);
    }

    private void init() {
        toolbar = findViewById(R.id.toolbar);
        fab = findViewById(R.id.fab);
        disease_rl = findViewById(R.id.cards_rl);
        search_ib = findViewById(R.id.searchib);
        search_et = findViewById(R.id.searchet);
    }

    private void fetchDataFromServer() {

        final ProgressDialog dialog = new ProgressDialog(this);
        dialog.setMessage("Loading...");
        dialog.show();

        RequestQueue queue = Volley.newRequestQueue(this);

        final StringRequest request =
                new StringRequest(Request.Method.GET, UrlHelper.SIHAPI_URL + language,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                Toast.makeText(MainActivity.this, "Chal raha hai baki", Toast.LENGTH_SHORT).show();

                                response= fixEncoding(response);

                                parseDiseaseJSON(response);
                                Log.d("1234", "onResponse: " + response);
                                dialog.cancel();
                            }

                            private String fixEncoding(String response) {
                                try {
                                    byte[] u = response.toString().getBytes(
                                            "ISO-8859-1");
                                    response = new String(u, "UTF-8");
                                } catch (UnsupportedEncodingException e) {
                                    e.printStackTrace();
                                    return null;
                                }
                                return response;
                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                dialog.cancel();
                                Log.d("1234", "onErrorResponse: " + error);
                                Toast.makeText(MainActivity.this, "Check your internet connection", Toast.LENGTH_SHORT).show();
                            }
                        });

        queue.add(request);
    }

    private void parseDiseaseJSON(String response) {
        try {
            object = new JSONObject(response);
            num_diseases = object.getInt("length");
            JSONArray disease_json_array = object.getJSONArray("data");
            Log.d("1234", "num_diseases: " + num_diseases);

            disease_IDs = new int[num_diseases];
            disease_names=new Vector<>(num_diseases);

            for(int i=0; i<disease_json_array.length(); i++) {
                disease_names.add(disease_json_array.getJSONObject(i).getString("name"));
                disease_IDs[i] = disease_json_array.getJSONObject(i).getInt("object_id");
                Log.d("1234", "disease names: " + disease_names.get(i) + " " + i);
            }
            placeCards(num_diseases);
            setListener();
        }
        catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void search_for_disease(String input_text){
        for(int i=0;i<disease_names.size();i++){
            Log.d("speech or search tag","searched for "+disease_names.get(i));
            if(input_text.contains(disease_names.get(i))) {
                disease_cv[i].performClick();
                return;
            }
        }
        Toast.makeText(getApplicationContext(), "No search results found!", Toast.LENGTH_SHORT).show();
    }

    private void placeCards(int num_diseases) {
        RelativeLayout.LayoutParams relParams[] = new RelativeLayout.LayoutParams[num_diseases];
        disease_cv = new CardView[num_diseases];

        for(int i=0; i<num_diseases; i++) {
            disease_cv[i] = new CardView(mContext);
            disease_cv[i].setCardBackgroundColor(Color.parseColor("#FDFDFD"));
            disease_cv[i].setCardElevation(4);
            disease_cv[i].setId(i+1);
            disease_cv[i].setRadius(10);


            relParams[i] = new RelativeLayout.LayoutParams(500, 500);
            relParams[i].setMargins(10, 10, 10, 10);
            if(i%2 == 1) {
                relParams[i].addRule(RelativeLayout.RIGHT_OF, disease_cv[i-1].getId());
                if(i != 1) {
                    relParams[i].addRule(RelativeLayout.BELOW, disease_cv[i-2].getId());
                }
            }
            else if(i%2 == 0 && i != 0) {
                relParams[i].addRule(RelativeLayout.BELOW, disease_cv[i-2].getId());
            }


            //            Put ImageView on CardView
            ImageView iv = new ImageView(mContext);
            iv.setId(i + 10000);
            iv.setImageResource(R.mipmap.ic_launcher);  // TODO: From API
            RelativeLayout.LayoutParams ivparams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
            ivparams.addRule(RelativeLayout.CENTER_IN_PARENT, RelativeLayout.TRUE);
            ivparams.setMargins(150,150,100,100);
            iv.setLayoutParams(ivparams);


            //            Put the TextView in CardView
            TextView tv = new TextView(mContext);
            RelativeLayout.LayoutParams tvparams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
            tvparams.addRule(RelativeLayout.BELOW, iv.getId());
            tv.setText(disease_names.get(i));
            tvparams.setMargins(150,350,100,100); // TODO: NEED TO CHANGE... SHOULD BE BELOW IMAGEVIEW
            //tv.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 30);
            tv.setTextColor(Color.BLACK);
            tv.setLayoutParams(tvparams);


            // Finally, add the CardView in root layout
            disease_cv[i].addView(iv, ivparams);
            disease_cv[i].addView(tv, tvparams);
            disease_rl.addView(disease_cv[i], relParams[i]);
        }
    }


    private void setListener() {
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                StringBuilder diseases = new StringBuilder();
                for(String dn:disease_names){
                    diseases.append(dn + ". ");
                }
                tts.say(diseases.toString());
            }
        });


        search_ib.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN)
                {
                    float x = (float) 2;
                    float y = (float) 2;

                    search_ib.setScaleX(x);
                    search_ib.setScaleY(y);
                    //search_ib.setBackgroundResource(R.drawable.blue_220);

                    AppLog.logString("Start Recording");
                    if (ActivityCompat.checkSelfPermission(MainActivity.this, Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED) {

                        ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.RECORD_AUDIO}, RECORD_AUDIO);

                    } else{
                        mSpeechRecognizer.startListening(mSpeechRecognizerIntent);
                    }
                    return true;
                }

                else if(event.getAction() == MotionEvent.ACTION_UP)
                {
                    float x = 1;
                    float y = 1;

                    search_ib.setScaleX(x);
                    search_ib.setScaleY(y);

                    mSpeechRecognizer.stopListening();
                }
                return false;
            }
        });

        for(int i=0; i<num_diseases; i++) {
            final int index = i;
            disease_cv[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(MainActivity.this, DiseaseDetailsActivity.class);
                    intent.putExtra("disease",disease_names.get(index));
                    intent.putExtra("id", disease_IDs[index]);
                    startActivity(intent);
                }
            });
        }
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
            Intent i = new Intent(MainActivity.this, ParentSuggestions.class);
            startActivity(i);
        }
        else if(id == R.id.action_language) {
            Intent i = new Intent(MainActivity.this, LanguageActivity.class);
            startActivity(i);
        }
        else if(id == R.id.action_contact) {
            Intent i = new Intent(MainActivity.this, ContactUsActivity.class);
            startActivity(i);
        }


        return super.onOptionsItemSelected(item);
    }
}
