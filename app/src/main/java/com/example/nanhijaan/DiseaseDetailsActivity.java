package com.example.nanhijaan;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.nanhijaan.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.Vector;

public class DiseaseDetailsActivity extends AppCompatActivity {

    Toolbar toolbar;
    TextView introContent_tv;
    FloatingActionButton fab;
    CardView symptoms_cv, prevention_cv, mgmt_cv, special_cv;
    String language, general_info;
    JSONArray  special_needs, foods, physical_ex, mental_ex, symptoms, prevention;
    JSONObject object;
    int disease_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_disease_details);
        init();
        getLanguage();
        Intent mIntent = getIntent();
        disease_id = mIntent.getIntExtra("id", 1);
        fetchDataFromServer();

        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Nanhi Jaan");

    }

    private void fetchDataFromServer() {
        final ProgressDialog dialog = new ProgressDialog(this);
        dialog.setMessage("Loading...");
        dialog.show();

        RequestQueue queue = Volley.newRequestQueue(this);

        StringRequest request =
                new StringRequest(Request.Method.GET, UrlHelper.SIHAPI_DISEASE_URL + disease_id,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                Toast.makeText(DiseaseDetailsActivity.this, "Chal raha hai baki", Toast.LENGTH_SHORT).show();
                                response= fixEncoding(response);
                                parseDiseaseDetailsJSON(response);
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
                                Toast.makeText(DiseaseDetailsActivity.this, "Check your internet connection", Toast.LENGTH_SHORT).show();
                            }
                        });

        queue.add(request);
    }

    private void parseDiseaseDetailsJSON(String response) {
        try {
            object = new JSONObject(response);
            general_info = object.getString("general_info");
            symptoms = object.getJSONArray("symptoms");
            prevention = object.getJSONArray("preventions");
            foods = object.getJSONArray("foods");
            physical_ex = object.getJSONArray("physical_exercises");
            mental_ex = object.getJSONArray("mental_exercises");
            special_needs = object.getJSONArray("special_attentions");

            introContent_tv.setText(general_info);
            setListeners();

        }
        catch (JSONException e) {
            e.printStackTrace();
        }

    }

    private void init() {
        toolbar = findViewById(R.id.toolbar);
        fab = findViewById(R.id.fab);
        symptoms_cv = findViewById(R.id.symptomscv);
        prevention_cv = findViewById(R.id.preventioncv);
        mgmt_cv = findViewById(R.id.managementcv);
        special_cv = findViewById(R.id.specialcv);
        introContent_tv = findViewById(R.id.intro_content);
    }

    private void getLanguage() {
        language = SetLanguage.getDefaults(SetLanguage.LANGUAGE, DiseaseDetailsActivity.this);
        Log.d("1234", "getLanguage: " + language);
    }

    private void setListeners() {
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        setClickListener(symptoms_cv, "Symptoms");
        setClickListener(prevention_cv, "Prevention");
        setClickListener(mgmt_cv, "Management");
        setClickListener(special_cv, "Special Needs");
    }

    private void setClickListener(CardView cv, final String title) {

        cv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String heading = title;
                Intent i = new Intent(DiseaseDetailsActivity.this, SymptomsDetailsActivity.class);
                if(title == "Symptoms") {
                    String[] symptoms_str = new String[symptoms.length()];
                    convertJSONArrayToStringArray(symptoms, symptoms_str);
                    i.putExtra("content", symptoms_str);
                    i.putExtra("length", symptoms.length());

                    if(language == "hindi") {
                        heading = getString(R.string.hindi_symptoms);
                    }
                    else if(language == "punjabi") {
                        heading = getString(R.string.punjabi_symptoms);
                    }
                }
                else if(title == "Prevention") {
                    String[] preventions_str = new String[prevention.length()];
                    convertJSONArrayToStringArray(prevention, preventions_str);
                    i.putExtra("content", preventions_str);
                    i.putExtra("length", prevention.length());

                    if(language == "hindi") {
                        heading = getString(R.string.hindi_prevention);
                    }
                    else if(language == "punjabi") {
                        heading = getString(R.string.punjabi_prevention);
                    }
                }
                else if(title == "Special Needs") {
                    String[] special_str = new String[special_needs.length()];
                    convertJSONArrayToStringArray(special_needs, special_str);
                    i.putExtra("content", special_str);
                    i.putExtra("length", special_needs.length());

                    if(language == "hindi") {
                        heading = getString(R.string.hindi_special);
                    }
                    else if(language == "punjabi") {
                        heading = getString(R.string.punjabi_special);
                    }
                }
                else {
                    String[] food_str = new String[foods.length()];
                    String[] mental_str = new String[mental_ex.length()];
                    String[] physical_str = new String[physical_ex.length()];
                    convertJSONArrayToStringArray(foods, food_str);
                    convertJSONArrayToStringArray(mental_ex, mental_str);
                    convertJSONArrayToStringArray(physical_ex, physical_str);
                    String[] mgmt_str = (String[])ArrayUtils.addAll(first, second);
                }
                i.putExtra("heading", heading);
                startActivity(i);
            }

            private void convertJSONArrayToStringArray(JSONArray JSONarray, String[] stringArray) {
                try {
                    for (int j = 0; j < JSONarray.length(); j++)
                        stringArray[j] = JSONarray.getString(j);
                }
                catch(JSONException e) {
                    Log.d("1234", "onClick: " + e);
                }
            }
        });
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
            Intent i = new Intent(DiseaseDetailsActivity.this, ParentSuggestions.class);
            startActivity(i);
        }
        else if(id == R.id.action_language) {
            Intent i = new Intent(DiseaseDetailsActivity.this, LanguageActivity.class);
            startActivity(i);
        }
        else if(id == R.id.action_contact) {
            Intent i = new Intent(DiseaseDetailsActivity.this, ContactUsActivity.class);
            startActivity(i);
        }

        return super.onOptionsItemSelected(item);
    }
}
