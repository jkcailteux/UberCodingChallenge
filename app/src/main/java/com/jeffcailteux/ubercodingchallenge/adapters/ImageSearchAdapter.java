package com.jeffcailteux.ubercodingchallenge.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.toolbox.ImageLoader;
import com.jeffcailteux.ubercodingchallenge.R;
import com.jeffcailteux.ubercodingchallenge.models.ImageModel;
import com.jeffcailteux.ubercodingchallenge.views.listitem.ImageSearchListItem;

import java.util.ArrayList;

/**
 * Created by jeffcailteux on 10/16/14.
 */
public class ImageSearchAdapter extends BaseAdapter {

    public ArrayList<ImageModel> images;
    Context context;
    ImageLoader imageLoader;
    int viewwidth;
    int maxheight;

    public ImageSearchAdapter(Context context, ImageLoader imageLoader, int viewwidth, int maxheight, ArrayList<ImageModel> images) {
        this.context = context;
        this.imageLoader = imageLoader;
        this.images = images;
        this.viewwidth = viewwidth;
        this.maxheight = maxheight;
    }

    @Override
    public int getCount() {
        if (images == null)
            return 0;
        else
            //return images.size();
            return (int) (images.size() / 3);
    }

    @Override
    public Object getItem(int position) {
        if (images == null)
            return null;
        else
            return images.get(position);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (images == null || images.size() == 0)
            return null;

        ImageSearchListItem listItem;
        //to keep inflations to a minimum, try to typecast
        if (convertView == null || convertView.getClass() != ImageSearchListItem.class)
            listItem = (ImageSearchListItem) LayoutInflater.from(context).inflate(R.layout.listitem_image_search, null);
        else
            listItem = (ImageSearchListItem) convertView;
        listItem.setup(viewwidth, maxheight);
        listItem.setImages((ImageModel) getItem(position * 3), (ImageModel) getItem(position * 3 + 1), (ImageModel) getItem(position * 3 + 2), imageLoader);
        return listItem;
    }


    @Override
    public boolean isEmpty() {
        if (images == null)
            return true;
        else
            return images.size() == 0 ? true : false;
    }

    @Override
    public boolean isEnabled(int position) {
        if (images.size() > position)
            return true;
        else
            return false;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }


}
