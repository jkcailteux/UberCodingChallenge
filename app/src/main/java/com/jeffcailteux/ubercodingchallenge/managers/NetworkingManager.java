package com.jeffcailteux.ubercodingchallenge.managers;

import android.app.Activity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

/**
 * Created by jeffcailteux on 10/16/14.
 */
public class NetworkingManager {

    private static final String imagesAPIUrl = "https://ajax.googleapis.com/ajax/services/search/images?v=1.0";

    RequestQueue mRequestQueue;

    public NetworkingManager(Activity activity) {
        mRequestQueue = Volley.newRequestQueue(activity);
    }


    public void searchForImages(String searchTerm, Response.Listener<JSONObject> callback, Response.ErrorListener errorListener) {
        String url = buildURL(searchTerm);
        sendRequest(new JsonObjectRequest(url, null, callback, errorListener));
    }

    private String buildURL(String searchTerm) {
        String url = imagesAPIUrl;
        url += "&q=" + searchTerm.replaceAll(" ", "%20");


        
        return url;
    }


    private void sendRequest(Request request) {
        mRequestQueue.add(request);
        mRequestQueue.start();
    }


}
