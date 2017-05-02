package com.ign.agajan.agajancodefoo;

import java.util.ArrayList;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

/**
 * Created by ajumakuliyev on 4/28/17.
 */

public class ArticleAdapter extends ArrayAdapter<ArticleModel> {
    public static final int ARTICLE_ITEM_ROW = 0;
    public static final int HORIZONTAL_VIDEOS_LIST_ROW = 1;
    Typeface custom_font;
    ArrayList<VideoModel> arrayOfVideos;
    RecyclerView horizontal_recycler_view;
    HorizontalAdapter horizontalAdapter;

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
            return HORIZONTAL_VIDEOS_LIST_ROW;
        }
        else {
            return ARTICLE_ITEM_ROW;
        }
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;
        int type = getItemViewType(position);
        // Get the data item for this position
        ArticleModel article = getItem(position);

        if (v == null) {
            // Inflate the layout according to the view type
            LayoutInflater inflater = LayoutInflater.from(getContext());
            if (type == ARTICLE_ITEM_ROW) {
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
                horizontal_recycler_view = (RecyclerView) v.findViewById(R.id.horizontal_recycler_view);
                horizontalAdapter = new HorizontalAdapter(arrayOfVideos, getContext());
                LinearLayoutManager horizontalLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
                horizontal_recycler_view.setLayoutManager(horizontalLayoutManager);
                horizontal_recycler_view.setAdapter(horizontalAdapter);
            }
        }
        return v;
    }

}
