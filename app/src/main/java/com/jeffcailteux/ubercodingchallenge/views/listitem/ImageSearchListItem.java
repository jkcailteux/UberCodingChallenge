package com.jeffcailteux.ubercodingchallenge.views.listitem;

import android.content.Context;
import android.util.AttributeSet;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.LinearLayout;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.jeffcailteux.ubercodingchallenge.R;
import com.jeffcailteux.ubercodingchallenge.models.ImageModel;

import butterknife.InjectView;

/**
 * Created by jeffcailteux on 10/16/14.
 */
public class ImageSearchListItem extends LinearLayout {

    @InjectView(R.id.listitem_image_search_iv1)
    NetworkImageView iv1;

    @InjectView(R.id.listitem_image_search_iv1)
    NetworkImageView iv2;

    @InjectView(R.id.listitem_image_search_iv1)
    NetworkImageView iv3;

    int maxheight, viewwidth;

    public ImageSearchListItem(Context context) {
        super(context);
    }

    public ImageSearchListItem(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ImageSearchListItem(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public void setup(int viewwidth, int maxheight) {
        iv1 = (NetworkImageView) findViewById(R.id.listitem_image_search_iv1);
        iv2 = (NetworkImageView) findViewById(R.id.listitem_image_search_iv2);
        iv3 = (NetworkImageView) findViewById(R.id.listitem_image_search_iv3);

        //force widths
        iv1.setMinimumWidth(viewwidth);
        iv1.setMaxWidth(viewwidth);
        iv2.setMinimumWidth(viewwidth);
        iv2.setMaxWidth(viewwidth);
        iv3.setMinimumWidth(viewwidth);
        iv3.setMaxWidth(viewwidth);

        this.maxheight = maxheight;
        this.viewwidth = viewwidth;
    }

    public void setImages(ImageModel im1, ImageModel im2, ImageModel im3, ImageLoader imageLoader) {

        //set height to tallest image of the row, with maxheight as upper limit
        int height = newheight(im1);
        if (newheight(im2) > height)
            height = newheight(im2);
        if (newheight(im3) > height)
            height = newheight(im3);

        if (height > maxheight || height == 0)
            height = maxheight;

        this.setLayoutParams(new AbsListView.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, height));

        //load images into imageviews
        if (im1 != null && im1.url != null)
            iv1.setImageUrl(im1.url, imageLoader);
        if (im2 != null && im2.url != null)
            iv2.setImageUrl(im2.url, imageLoader);
        if (im3 != null && im3.url != null)
            iv3.setImageUrl(im3.url, imageLoader);
    }

    //maintain ratio of images with new widths
    private int newheight(ImageModel im) {
        return im.height * (viewwidth / im.width);
    }

}
