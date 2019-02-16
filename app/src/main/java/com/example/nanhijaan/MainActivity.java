package com.example.nanhijaan;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.util.TypedValue;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private Context mContext;
    Toolbar toolbar;
    FloatingActionButton fab;
    RelativeLayout disease_rl;
    int num_diseases;
    String disease_names;
    CardView disease_cv[];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mContext = getApplicationContext();

        init();
        setListener();

        setSupportActionBar(toolbar);
    }

    private void init() {
        toolbar = findViewById(R.id.toolbar);
        fab = findViewById(R.id.fab);
        disease_rl = findViewById(R.id.cards_rl);

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

    private void setListener() {
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
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
