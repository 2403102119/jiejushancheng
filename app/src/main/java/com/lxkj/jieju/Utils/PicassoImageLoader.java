package com.lxkj.jieju.Utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.widget.ImageView;

import com.lxkj.jieju.R;
import com.lzy.ninegrid.NineGridView;
import com.squareup.picasso.Picasso;

/**
 implementation 'com.squareup.picasso:picasso:2.5.2'
 */
public class PicassoImageLoader implements NineGridView.ImageLoader {

    Context context;

    @Override
    public void onDisplayImage(Context context, ImageView imageView, String url) {
        this.context = context;
        if (!url.isEmpty()) {
            Picasso.with(context).load(url)//
                    .placeholder(R.mipmap.logo)//
                    .error(R.mipmap.logo)//
                    .into(imageView);
        } else {
            Picasso.with(context).load(R.mipmap.logo)//
                    .into(imageView);
        }

    }

    @Override
    public Bitmap getCacheImage(String url) {
        return null;
    }
}
