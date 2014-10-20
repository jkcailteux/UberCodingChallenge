package com.jeffcailteux.ubercodingchallenge.managers;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by jeffcailteux on 10/20/14.
 */
public class SharedPrefManager {

    SharedPreferences sp;

    public SharedPrefManager(Activity activity) {
        sp = activity.getSharedPreferences(
                "com.jeffcailteux.ubercodingchallenge", Context.MODE_PRIVATE);
    }

    public void clearSearches() {
        SharedPreferences.Editor editor = sp.edit();
        editor.putStringSet("searches", null);
        editor.commit();
    }

    public void addSearchTerm(String s) {
        SharedPreferences.Editor editor = sp.edit();
        Set<String> current = getSearchTerms();
        if (current == null)
            current = new HashSet<String>();
        current.add(s);
        editor.putStringSet("searches", current);
        editor.commit();
    }

    public Set<String> getSearchTerms() {
        return sp.getStringSet("searches", null);
    }

}
