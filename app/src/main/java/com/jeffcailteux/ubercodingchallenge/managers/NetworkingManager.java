package com.jeffcailteux.ubercodingchallenge.managers;

import android.app.Activity;
import android.text.format.Formatter;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;

/**
 * Created by jeffcailteux on 10/16/14.
 */
public class NetworkingManager {

    private static final String imagesAPIUrl = "https://ajax.googleapis.com/ajax/services/search/images?v=1.0";

    RequestQueue mRequestQueue;
    String ipAddr;

    public NetworkingManager(Activity activity) {
        mRequestQueue = Volley.newRequestQueue(activity);
        this.ipAddr = getLocalIpAddress();
    }


    public void searchForImages(String searchTerm, Response.Listener<JSONObject> callback, Response.ErrorListener errorListener) {
        String url = buildURL(searchTerm);
        sendRequest(new JsonObjectRequest(url, null, callback, errorListener));
    }

    private String buildURL(String searchTerm) {
        String url = imagesAPIUrl;
        url += "&q=" + searchTerm.replaceAll(" ", "%20");
        url += "&userip=" + ipAddr;

        return url;
    }

    private void sendRequest(Request request) {
        mRequestQueue.add(request);
        mRequestQueue.start();
    }

    private String getLocalIpAddress() {
        try {
            for (Enumeration<NetworkInterface> en = NetworkInterface.getNetworkInterfaces(); en.hasMoreElements(); ) {
                NetworkInterface intf = en.nextElement();
                for (Enumeration<InetAddress> enumIpAddr = intf.getInetAddresses(); enumIpAddr.hasMoreElements(); ) {
                    InetAddress inetAddress = enumIpAddr.nextElement();
                    if (!inetAddress.isLoopbackAddress()) {
                        String ip = Formatter.formatIpAddress(inetAddress.hashCode());
                        return ip;
                    }
                }
            }
        } catch (SocketException ex) {
            //
        }
        return null;
    }


}
