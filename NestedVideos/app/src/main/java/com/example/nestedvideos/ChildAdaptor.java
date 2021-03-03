package com.example.nestedvideos;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ViewPagerAdapterForAd extends RecyclerView.Adapter< ViewPagerAdapterForAd.ViewHolder>{

    private Context context;
    private String id;

    public ViewPagerAdapterForAd(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public  ViewPagerAdapterForAd.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.iten_preview_campaign, parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull  ViewPagerAdapterForAd.ViewHolder holder, int position) {

       holder.playerView.setUrl("https://s3.ca-central-1.amazonaws.com/codingwithmitch/media/VideoPlayerRecyclerView/Sending+Data+to+a+New+Activity+with+Intent+Extras.mp4");

    }
    @Override
    public int getItemCount() {
        return 6;
    }
    class ViewHolder extends RecyclerView.ViewHolder{

        com.master.exoplayer.MasterExoPlayer playerView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
             playerView = itemView.findViewById(R.id.videoViewPlayer);
        }
    }
}
