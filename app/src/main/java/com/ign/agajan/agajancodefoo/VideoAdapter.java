package com.ign.agajan.agajancodefoo;

import java.util.ArrayList;

import android.content.Context;
import android.graphics.Typeface;
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
public class VideoAdapter extends ArrayAdapter<VideoModel> {

    Typeface custom_font;

    public VideoAdapter(Context context, ArrayList<VideoModel> videos, Typeface custom_font) {
        super(context, 0, videos);
        this.custom_font = custom_font;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        VideoModel video = getItem(position);

        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.video_item, null);
        }
        // Lookup view for data population
        TextView headline = (TextView) convertView.findViewById(R.id.headline);
        ImageView ivPosterImage = (ImageView) convertView.findViewById(R.id.ivPosterImage);
        headline.setTypeface(custom_font);

        // Populate the data into the template view using the data object
        headline.setText(video.getHeadline());
        Picasso.with(getContext()).load(video.getPosterUrl()).into(ivPosterImage);

        // Return the completed view to render on screen
        return convertView;
    }

}
