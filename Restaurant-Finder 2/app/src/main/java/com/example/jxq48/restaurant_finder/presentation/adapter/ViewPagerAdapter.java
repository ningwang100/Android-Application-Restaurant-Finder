/**
 * This class is used to implement slide the photo wall
 */
package com.example.jxq48.restaurant_finder.presentation.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager.LayoutParams;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.view.View.OnClickListener;


import java.util.ArrayList;

/**
 * Created by jxq48 on 8/1/15.
 */
public class ViewPagerAdapter extends PagerAdapter {
    private ArrayList<Bitmap> photos;
    private Context mContext;
    private ZoomTutorial mZoomTutorial;

    public ViewPagerAdapter( Context context ,ArrayList<Bitmap> photos,ZoomTutorial zoomTutorial) {
        this.photos = photos;
        this.mContext = context;
        this.mZoomTutorial = zoomTutorial;
    }

    @Override
    public int getCount() {
        return photos.size();
    }

    /**
     *
     * Get the images and container about photo wall
     */
    @Override
    public View instantiateItem(ViewGroup container, final int position) {

        final ImageView imageView = new ImageView(mContext);
        imageView.setImageBitmap(photos.get(position));
        container.addView(imageView, LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);

        imageView.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View view) {
                mZoomTutorial.closeZoomAnim(position);
            }
        });
        return imageView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }


    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }
}
