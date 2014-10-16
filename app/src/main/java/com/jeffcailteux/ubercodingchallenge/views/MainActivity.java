package com.jeffcailteux.ubercodingchallenge.views;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.google.gson.Gson;
import com.jeffcailteux.ubercodingchallenge.R;
import com.jeffcailteux.ubercodingchallenge.managers.NetworkingManager;
import com.jeffcailteux.ubercodingchallenge.models.ImageModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;


public class MainActivity extends Activity {

    @InjectView(R.id.search_button)
    Button searchbutton;

    @InjectView(R.id.search_edittext)
    EditText searchedit;

    @OnClick(R.id.search_button)
    public void search() {
        if (searchedit.getText().length() > 0) {
            searchForImages();
        }

    }

    NetworkingManager networkingManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle("Image Search");
        ButterKnife.inject(this);
        networkingManager = new NetworkingManager(this);

    }

    public void searchForImages() {
        networkingManager.searchForImages(searchedit.getText().toString(), new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject jsonObject) {
                ArrayList<ImageModel> images = new ArrayList<ImageModel>();
                try {
                    JSONArray results = jsonObject.getJSONObject("responseData").getJSONArray("results");
                    for (int x = 0; x != results.length(); x++) {
                        Gson gson = new Gson();
                        ImageModel model = gson.fromJson(String.valueOf(results.getJSONObject(x)), ImageModel.class);
                        images.add(model);
                    }

                } catch (JSONException e) {
                    Log.e("imagesearch", e.getLocalizedMessage());
                    Toast.makeText(MainActivity.this, "Error searching for images", Toast.LENGTH_SHORT).show();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Log.e("imagesearch", volleyError.getMessage());
                Toast.makeText(MainActivity.this, "Error searching for images", Toast.LENGTH_SHORT).show();
            }
        });
    }


}
