package com.jeffcailteux.ubercodingchallenge.views.extra;


import android.app.ProgressDialog;
import android.content.Context;
import android.widget.ProgressBar;

public class Spinner {

    private final Context context;
    ProgressDialog progressDialog;

    public Spinner(Context context) {
        this.context = context;
    }

    public void show() {
        if (progressDialog != null && progressDialog.isShowing())
            return;
        progressDialog = progressDialog.show(context, null, null, true, false);
        progressDialog.setContentView(new ProgressBar(context));

    }

    public void hide() {
        if (progressDialog != null) {
            progressDialog.dismiss();
            progressDialog = null;
        }
    }

    public boolean isVisible() {
        if (progressDialog == null)
            return false;
        else
            return progressDialog.isShowing();
    }

}