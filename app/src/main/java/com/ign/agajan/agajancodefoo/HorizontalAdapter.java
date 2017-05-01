package com.ign.agajan.agajancodefoo;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by ajumakuliyev on 4/30/17.
 */

public class HorizontalAdapter extends RecyclerView.Adapter<HorizontalAdapter.MyViewHolder> {

    ArrayList<VideoModel> videos;
    Context context;

    public HorizontalAdapter(ArrayList<VideoModel> videos, Context context) {
        this.videos = videos;
        this.context = context;
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {

        ImageView imageView;
        TextView txtview;

        public MyViewHolder(View view) {
            super(view);
            imageView = (ImageView) view.findViewById(R.id.ivPosterImage);
            txtview = (TextView) view.findViewById(R.id.headline);
        }
    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.video_item, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        Picasso.with(context).load(videos.get(position).getPosterUrl()).into(holder.imageView);
        holder.txtview.setText(videos.get(position).getHeadline());

        holder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override

            public void onClick(View v) {
                String videoUrl = videos.get(position).getVideoUrl();
                WebViewActivity wbActivity = new WebViewActivity();
                Intent intent = new Intent(context, wbActivity.getClass());
                intent.putExtra("URL", videoUrl);
                context.startActivity(intent);
            }

        });
    }


    @Override
    public int getItemCount(){
        return videos.size();
    }
}

