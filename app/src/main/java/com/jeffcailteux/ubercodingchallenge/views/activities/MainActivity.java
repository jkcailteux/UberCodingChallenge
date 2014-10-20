package com.jeffcailteux.ubercodingchallenge.views.activities;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Point;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.Display;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.google.gson.Gson;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.jeffcailteux.ubercodingchallenge.R;
import com.jeffcailteux.ubercodingchallenge.adapters.ImageSearchAdapter;
import com.jeffcailteux.ubercodingchallenge.managers.NetworkingManager;
import com.jeffcailteux.ubercodingchallenge.managers.SharedPrefManager;
import com.jeffcailteux.ubercodingchallenge.models.ImageModel;
import com.jeffcailteux.ubercodingchallenge.views.extra.Spinner;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Set;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;


public class MainActivity extends Activity {

    @InjectView(R.id.search_button)
    Button searchbutton;

    @InjectView(R.id.search_edittext)
    AutoCompleteTextView searchedit;

    @InjectView(R.id.search_listview)
    PullToRefreshListView listView;

    @OnClick(R.id.search_button)
    public void search() {
        if (searchedit.getText().length() > 0) {
            searchForImages(6, true);
        }
    }

    //Set up Singletons and values
    NetworkingManager networkingManager;
    ImageSearchAdapter adapter;
    int viewwidth, maxheight;
    Spinner spinner = new Spinner(this);
    ArrayList<ImageModel> images = new ArrayList<ImageModel>();
    SharedPrefManager sharedPrefManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle("Image Search");
        ButterKnife.inject(this);
        networkingManager = new NetworkingManager(this);

        //setup listview and screen dimensions
        listView.setShowDividers(LinearLayout.SHOW_DIVIDER_NONE);
        listView.setMode(PullToRefreshBase.Mode.PULL_FROM_END);
        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        int width = size.x;
        int height = size.y;
        float onedp = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 1, getResources().getDisplayMetrics());
        viewwidth = width - (int) (50 * onedp);
        sharedPrefManager = new SharedPrefManager(this);
        maxheight = height / 3;
        //setup listview refresh
        listView.setAdapter(adapter);
        listView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener<ListView>() {
            @Override
            public void onRefresh(PullToRefreshBase<ListView> refreshView) {
                refreshView.onRefreshComplete();
                if (searchedit.getText().length() > 0) {
                    searchForImages(3, false);
                }
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();

    }

    public void searchForImages(int resultCount, boolean clear) {
        spinner.show();
        //if loading new set or additional loading
        if (clear) {
            images.clear();
            sharedPrefManager.addSearchTerm(searchedit.getText().toString());
        }
        networkingManager.searchForImages(searchedit.getText().toString(), images.size(), resultCount, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject jsonObject) {
                spinner.hide();
                try {
                    //add images to images list
                    JSONArray results = jsonObject.getJSONObject("responseData").getJSONArray("results");
                    for (int x = 0; x != results.length(); x++) {
                        Gson gson = new Gson();
                        ImageModel model = gson.fromJson(String.valueOf(results.getJSONObject(x)), ImageModel.class);
                        images.add(model);
                    }
                    adapter = new ImageSearchAdapter(MainActivity.this, networkingManager.imageLoader, viewwidth, maxheight, images);
                    listView.setAdapter(adapter);
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main, menu);
        return super.onCreateOptionsMenu(menu);
    }


    //load previous searches in dialog
    public void previousSearch(MenuItem menuItem) {
        final Set<String> searches = sharedPrefManager.getSearchTerms();
        if (searches == null)
            return;
        final ArrayList<CharSequence> items = new ArrayList<CharSequence>();
        for (String s : searches) {
            items.add(s);
        }
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setSingleChoiceItems(searches.toArray(new CharSequence[searches.size()]), -1, new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface d, int n) {
                searchedit.setText(items.get(n));
                searchForImages(6, true);
                d.dismiss();
            }

        });
        builder.setNegativeButton("Cancel", null);
        builder.setTitle("Previous Searches");
        builder.show();

    }

    //clear out old searches
    public void clearHistory(MenuItem menuItem) {
        sharedPrefManager.clearSearches();
        Toast.makeText(MainActivity.this, "Search history cleared", Toast.LENGTH_SHORT).show();
    }
}
