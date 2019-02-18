package com.example.nanhijaan;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.os.Environment;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.io.File;
import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    private Context mContext;
    Toolbar toolbar;
    FloatingActionButton fab;
    RelativeLayout disease_rl;
    int num_diseases;
    String disease_names;
    CardView disease_cv[];
    ImageButton search_ib;

    private static final String AUDIO_RECORDER_FILE_EXT_3GP = ".3gp";
    private static final String AUDIO_RECORDER_FILE_EXT_MP4 = ".mp3";
    private static final String AUDIO_RECORDER_FOLDER = "AudioRecorder";
    public static final int RECORD_AUDIO = 0;
    private static final int REQUEST_CODE = 1;


    private MediaRecorder recorder = null;
    private int currentFormat = 0;
    private int output_formats[] = { MediaRecorder.OutputFormat.MPEG_4, MediaRecorder.OutputFormat.THREE_GPP };
    private String file_exts[] = { AUDIO_RECORDER_FILE_EXT_MP4, AUDIO_RECORDER_FILE_EXT_3GP };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mContext = getApplicationContext();
        if (checkSelfPermission(android.Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_CODE);
        }
        init();
        setListener();

        setSupportActionBar(toolbar);


    }

    private void init() {
        toolbar = findViewById(R.id.toolbar);
        fab = findViewById(R.id.fab);
        disease_rl = findViewById(R.id.cards_rl);
        search_ib = findViewById(R.id.searchib);

        num_diseases = 10; // Fetch from API disease number, disease names, and images;
        placeCards(num_diseases);
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
            tv.setText("CardView" + i); // TODO: From API
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

    private String getFilename(){
        String filepath = Environment.getExternalStorageDirectory().getPath();
        File file = new File(filepath,AUDIO_RECORDER_FOLDER);

        if(!file.exists()){
            file.mkdirs();
        }

        return (file.getAbsolutePath() + "/nanhijansound" + file_exts[currentFormat]);
    }


    private MediaRecorder.OnErrorListener errorListener = new MediaRecorder.OnErrorListener() {
        @Override
        public void onError(MediaRecorder mr, int what, int extra) {
            AppLog.logString("Error: " + what + ", " + extra);
        }
    };

    private MediaRecorder.OnInfoListener infoListener = new MediaRecorder.OnInfoListener() {
        @Override
        public void onInfo(MediaRecorder mr, int what, int extra) {
            AppLog.logString("Warning: " + what + ", " + extra);
        }
    };

    private void startRecording(){
        recorder = new MediaRecorder();
        recorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        recorder.setOutputFormat(output_formats[currentFormat]);
        recorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
        recorder.setOutputFile(getFilename());
        recorder.setOnErrorListener(errorListener);
        recorder.setOnInfoListener(infoListener);

        try {
            recorder.prepare();
            recorder.start();
        } catch (IllegalStateException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private void stopRecording(){
        if(null != recorder){
            recorder.stop();
            recorder.reset();
            recorder.release();

            recorder = null;
        }
    }

    private void setListener() {
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
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
                        startRecording();
                    }
                    return true;
                }

                else if(event.getAction() == MotionEvent.ACTION_UP)
                {
                    float x = 1;
                    float y = 1;

                    search_ib.setScaleX(x);
                    search_ib.setScaleY(y);

                    AppLog.logString("stop Recording");
                    stopRecording();
                }
                return false;
            }
        });


        for(int i=0; i<num_diseases; i++) {
            disease_cv[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    final String disease_name = "Cerebral Palsy"; // From API
                    Intent i = new Intent(MainActivity.this, DiseaseDetailsActivity.class);
                    i.putExtra("disease",disease_name);
                    startActivity(i);
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
