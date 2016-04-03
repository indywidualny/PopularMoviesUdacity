package org.indywidualni.popularmovies;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import org.indywidualni.popularmovies.model.Results;

public class ImageAdapter extends BaseAdapter {

    public static final String BASE_URL = "https://image.tmdb.org/t/p/w342/";

    private Context mContext;
    private Results[] mResults;

    public ImageAdapter(Context c, Results[] results) {
        mContext = c;
        mResults = results;
    }

    public int getCount() {
        return mResults.length;
    }

    public Object getItem(int position) {
        return mResults[position];
    }

    public long getItemId(int position) {
        return 0;
    }

    // create a new ImageView for each item referenced by the Adapter
    public View getView(int position, View convertView, ViewGroup parent) {
        ImageView imageView;
        if (convertView == null) {
            // if it's not recycled, initialize some attributes
            imageView = new ImageView(mContext);
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            imageView.setAdjustViewBounds(true);
        } else {
            imageView = (ImageView) convertView;
        }

        Picasso.with(mContext).load(BASE_URL + mResults[position].getPoster_path()).into(imageView);
        return imageView;
    }

}