package com.jeffcailteux.ubercodingchallenge.views.listitem;

import android.content.Context;
import android.util.AttributeSet;
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

    public ImageSearchListItem(Context context) {
        super(context);
    }

    public ImageSearchListItem(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ImageSearchListItem(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public void setImages(ImageModel im1, ImageModel im2, ImageModel im3, ImageLoader imageLoader) {
        iv1.setImageUrl(im1.unescapedUrl,imageLoader);
        iv2.setImageUrl(im2.unescapedUrl,imageLoader);
        iv3.setImageUrl(im3.unescapedUrl,imageLoader);
    }

}
