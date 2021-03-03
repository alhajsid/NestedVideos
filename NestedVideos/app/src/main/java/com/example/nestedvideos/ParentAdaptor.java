package com.example.nestedvideos;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.PagerSnapHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.master.exoplayer.MasterExoPlayerHelper;

public class CampaignDraftedAdapter extends RecyclerView.Adapter<CampaignDraftedAdapter.ViewHolder> {
    private Context context;
    MasterExoPlayerHelper masterExoPlayerHelper;

    public CampaignDraftedAdapter(Context context, MasterExoPlayerHelper masterExoPlayerHelper) {
        this.context = context;
        this.masterExoPlayerHelper = masterExoPlayerHelper;
    }

    @NonNull
    @Override
    public CampaignDraftedAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_campaign_drafted, parent, false);
        return new ViewHolder(v);
    }

    PagerSnapHelper pagerSnapHelper;

    @Override
    public void onBindViewHolder(@NonNull CampaignDraftedAdapter.ViewHolder holder, int position) {

        final ViewHolder userViewHolder = holder;

        ViewPagerAdapterForAd Adapter = new ViewPagerAdapterForAd(context);
        userViewHolder.CampViewPagerDraft.setAdapter(Adapter);

        pagerSnapHelper = new PagerSnapHelper();

        pagerSnapHelper.attachToRecyclerView(userViewHolder.CampViewPagerDraft);
        userViewHolder.CampViewPagerDraft.setLayoutManager(new LinearLayoutManager(holder.itemView.getContext(), RecyclerView.HORIZONTAL, false));

        userViewHolder.CampViewPagerDraft.addItemDecoration(new CirclePagerIndicatorDecoration());
        masterExoPlayerHelper.attachToRecyclerView(userViewHolder.CampViewPagerDraft);

    }

    @Override
    public int getItemCount() {
        return 30;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        RecyclerView CampViewPagerDraft;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            CampViewPagerDraft = itemView.findViewById(R.id.CampViewPagerDraft);

        }
    }
}
