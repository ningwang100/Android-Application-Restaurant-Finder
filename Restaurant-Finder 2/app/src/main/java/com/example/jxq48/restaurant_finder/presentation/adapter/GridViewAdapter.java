/**
 * This class is used to show images which implement the functionality of photo wall.
 */
package com.example.jxq48.restaurant_finder.presentation.adapter;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.example.jxq48.restaurant_finder.R;
import com.example.jxq48.restaurant_finder.presentation.UserHost.Myphotos.showPhotosActivity;
import com.example.jxq48.restaurant_finder.presentation.adapter.ZoomTutorial.OnZoomListener;
import java.util.ArrayList;

/**
 * Created by jxq48 on 8/1/15.
 */
public class GridViewAdapter extends BaseAdapter{

    ArrayList<Bitmap> photos;
    Context context;
    private static LayoutInflater inflater=null;

    public GridViewAdapter(showPhotosActivity activity, ArrayList<Bitmap> photos) {
        this.photos = photos;
        context = activity;
        inflater = (LayoutInflater)context.
                getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return photos.size();
    }

    @Override
    public Object getItem(int position) {
        return photos.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    /**
     *
     *This method set the image view and set listening on these image view.When click it
     * the selected image will zoom in.
     */
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final ImageView imageView;
        if (convertView == null) {
            imageView = new ImageView(context);
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        } else {
            imageView = (ImageView) convertView;
        }

        imageView.setImageBitmap(photos.get(position));
        imageView.setTag(photos.get(position));
        imageView.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                setViewPagerAndZoom(imageView, position);
            }
        });

        return imageView;
    }

    /**
     *This method is used to implement the zoom in fuctionality of an image
     */
    public void setViewPagerAndZoom(View v ,int position) {

        ViewPager expandedView = (ViewPager)((Activity)context).findViewById(R.id.detail_view);

        View containerView = (FrameLayout)((Activity)context).findViewById(R.id.container);

        ZoomTutorial mZoomTutorial = new ZoomTutorial(containerView, expandedView);

        ViewPagerAdapter adapter = new ViewPagerAdapter(context,
                showPhotosActivity.largePicIds,mZoomTutorial);
        expandedView.setAdapter(adapter);
        expandedView.setCurrentItem(position);

        mZoomTutorial.zoomImageFromThumb(v);
        mZoomTutorial.setOnZoomListener(new OnZoomListener() {

            @Override
            public void onThumbed() {

                System.out.println("Now-------------------> little picture");
            }

            @Override
            public void onExpanded() {

                System.out.println("Now-------------------> larger picture");
            }
        });
    }
}
