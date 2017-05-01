package com.ign.agajan.agajancodefoo;

import java.util.ArrayList;

import android.content.Context;
import android.graphics.Typeface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

/**
 * Created by ajumakuliyev on 4/28/17.
 */

public class ArticleAdapter extends ArrayAdapter<ArticleModel> {
    Typeface custom_font;
    VideoAdapter videoAdapter;
    ArrayList<VideoModel> arrayOfVideos;

    public ArticleAdapter(Context context, ArrayList<ArticleModel> articles, Typeface custom_font, ArrayList<VideoModel> arrayOfVideos) {
        super(context, 0, articles);
        this.custom_font = custom_font;
        this.arrayOfVideos = arrayOfVideos;
    }

    @Override
    public int getViewTypeCount() { return 2; }

    @Override
    public int getItemViewType(int position) {
        if((position%3 == 0) && (position != 0)) {
            return 1;
        }
        else {
            return 0;
        }
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;
        int type = getItemViewType(position);
        // Get the data item for this position
        ArticleModel article = getItem(position);
        videoAdapter = new VideoAdapter(getContext(), arrayOfVideos, custom_font);

        if (v == null) {
            // Inflate the layout according to the view type
            LayoutInflater inflater = LayoutInflater.from(getContext());
            if (type == 0) {
                // Inflate the layout with image
                v = inflater.inflate(R.layout.list_item, null);

                // Lookup view for data population
                TextView headline = (TextView) v.findViewById(R.id.headline);
                TextView publishDate = (TextView) v.findViewById(R.id.publishDate);
                ImageView ivPosterImage = (ImageView) v.findViewById(R.id.ivPosterImage);

                headline.setTypeface(custom_font);
                publishDate.setTypeface(custom_font);

                // Populate the data into the template view using the data object
                headline.setText(article.getHeadline());
                publishDate.setText(article.getPublishDate());
                Picasso.with(getContext()).load(article.getPosterUrl()).into(ivPosterImage);
            }

            else {
                v = inflater.inflate(R.layout.video_list, null);
                ListView vlistView = (ListView) v.findViewById(R.id.videoListview);
                vlistView.setAdapter(videoAdapter);

                String TAG = MainActivity.class.getSimpleName();
                Log.e(TAG, "Video adapter size: " + videoAdapter.getCount());
            }
        }

        return v;
    }

}
