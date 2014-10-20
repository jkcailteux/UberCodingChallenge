package com.jeffcailteux.ubercodingchallenge.views.activities;

import android.app.Activity;
import android.graphics.Point;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.Display;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.google.gson.Gson;
import com.jeffcailteux.ubercodingchallenge.R;
import com.jeffcailteux.ubercodingchallenge.adapters.ImageSearchAdapter;
import com.jeffcailteux.ubercodingchallenge.managers.NetworkingManager;
import com.jeffcailteux.ubercodingchallenge.models.ImageModel;
import com.jeffcailteux.ubercodingchallenge.views.extra.Spinner;

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

//    @InjectView(R.id.search_listview)
//    PullToRefreshListView listView;

    @InjectView(R.id.search_listview)
    ListView listView;

    Spinner spinner = new Spinner(this);
    ArrayList<ImageModel> images = new ArrayList<ImageModel>();

    @OnClick(R.id.search_button)
    public void search() {
        if (searchedit.getText().length() > 0) {
            searchForImages();
        }

    }

    NetworkingManager networkingManager;
    int count = 0;
    ImageSearchAdapter adapter;
    int viewwidth, maxheight;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle("Image Search");
        ButterKnife.inject(this);
        networkingManager = new NetworkingManager(this);
        //listView.setShowDividers(LinearLayout.SHOW_DIVIDER_NONE);
        // listView.setMode(PullToRefreshBase.Mode.PULL_FROM_END);
        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        int width = size.x;
        int height = size.y;
        float onedp = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 1, getResources().getDisplayMetrics());
        viewwidth = width - (int) (50 * onedp);
        maxheight = height / 3;
        listView.setAdapter(adapter);

    }

    @Override
    public void onResume() {
        super.onResume();

    }

    public void searchForImages() {
        spinner.show();
        images.clear();
        networkingManager.searchForImages(searchedit.getText().toString(), count, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject jsonObject) {
                spinner.hide();
                try {
                    JSONArray results = jsonObject.getJSONObject("responseData").getJSONArray("results");
                    for (int x = 0; x != results.length(); x++) {
                        Gson gson = new Gson();
                        ImageModel model = gson.fromJson(String.valueOf(results.getJSONObject(x)), ImageModel.class);
                        images.add(model);
                    }
                    adapter = new ImageSearchAdapter(MainActivity.this, networkingManager.imageLoader, viewwidth, maxheight, images);
                    listView.setAdapter(adapter);
                    count += 6;
                } catch (JSONException e) {
                    Log.e("imagesearch", e.getLocalizedMessage());
                    Toast.makeText(MainActivity.this, "Error searching for images", Toast.LENGTH_SHORT).show();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                spinner.hide();
                Log.e("imagesearch", volleyError.getMessage());
                Toast.makeText(MainActivity.this, "Error searching for images", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
