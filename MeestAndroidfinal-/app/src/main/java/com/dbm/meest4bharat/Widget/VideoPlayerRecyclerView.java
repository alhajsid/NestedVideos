package com.example.nestedvideos;

import android.content.Context;
import android.graphics.Point;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class VideoPlayerRecyclerView extends RecyclerView {

    private static final String TAG = "VideoPlayerRecyclerView";

    // ui

    // vars
    private int videoSurfaceDefaultHeight = 0;
    private int screenDefaultHeight = 0;
    private Context context;
    private int playPosition = -1;
    private boolean isVideoViewAdded;
    int index=-1;
    ChildViewPager lastChildViewPager;


    public VideoPlayerRecyclerView(@NonNull Context context) {
        super(context);
        init(context);
    }

    public VideoPlayerRecyclerView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }


    private void init(Context context){
        this.context = context.getApplicationContext();
        Display display = ((WindowManager) getContext().getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();
        Point point = new Point();
        display.getSize(point);
        videoSurfaceDefaultHeight = point.x;
        screenDefaultHeight = point.y;

//        setVolumeControl(VolumeState.ON);

        addOnScrollListener(new OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);

                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    Log.d(TAG, "onScrollStateChanged: called.");
                    // There's a special case when the end of the list has been reached.
                    // Need to handle that with this bit of logic
                    if(!recyclerView.canScrollVertically(1)){
                        playVideo(true);
                    }
                    else{
                        playVideo(false);
                    }
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
            }
        });

        addOnChildAttachStateChangeListener(new OnChildAttachStateChangeListener() {
            @Override
            public void onChildViewAttachedToWindow(View view) {

            }

            @Override
            public void onChildViewDetachedFromWindow(View view) {


            }
        });

    }

    public void playVideo(boolean isEndOfList) {

        int targetPosition;

        if(!isEndOfList){
            int startPosition = ((LinearLayoutManager) getLayoutManager()).findFirstVisibleItemPosition();
            int endPosition = ((LinearLayoutManager) getLayoutManager()).findLastVisibleItemPosition();

            // if there is more than 2 list-items on the screen, set the difference to be 1
            if (endPosition - startPosition > 1) {
                endPosition = startPosition + 1;
            }

            // something is wrong. return.
            if (startPosition < 0 || endPosition < 0) {
                return;
            }

            // if there is more than 1 list-item on the screen
            if (startPosition != endPosition) {
                int startPositionVideoHeight = getVisibleVideoSurfaceHeight(startPosition);
                int endPositionVideoHeight = getVisibleVideoSurfaceHeight(endPosition);

                targetPosition = startPositionVideoHeight > endPositionVideoHeight ? startPosition : endPosition;
            }
            else {
                targetPosition = startPosition;
            }
        }
        else{
            targetPosition = 6 - 1;
        }

        Log.d(TAG, "playVideo: target position: " + targetPosition);

        // video is already playing so return
        if (targetPosition == playPosition) {
            return;
        }

        // set the position of the list-item that is to be played
        playPosition = targetPosition;

        // remove any old surface views from previously playing videos
        removeVideoView();

        int currentPosition = targetPosition - ((LinearLayoutManager) getLayoutManager()).findFirstVisibleItemPosition();

        View child = getChildAt(currentPosition);
        if (child == null) {
            return;
        }

        ParentAdaptor.ViewHolder holder = (ParentAdaptor.ViewHolder) child.getTag();
        if (holder == null) {
            playPosition = -1;
            return;
        }

        lastChildViewPager=holder.childViewPager;

        holder.childViewPager.playVideo();

    }

    private OnClickListener videoViewClickListener = new OnClickListener() {
        @Override
        public void onClick(View v) {
            toggleVolume();
        }
    };

    /**
     * Returns the visible region of the video surface on the screen.
     * if some is cut off, it will return less than the @videoSurfaceDefaultHeight
     * @param playPosition
     * @return
     */
    private int getVisibleVideoSurfaceHeight(int playPosition) {
        int at = playPosition - ((LinearLayoutManager) getLayoutManager()).findFirstVisibleItemPosition();
        Log.d(TAG, "getVisibleVideoSurfaceHeight: at: " + at);

        View child = getChildAt(at);
        if (child == null) {
            return 0;
        }

        int[] location = new int[2];
        child.getLocationInWindow(location);

        if (location[1] < 0) {
            return location[1] + videoSurfaceDefaultHeight;
        } else {
            return screenDefaultHeight - location[1];
        }
    }


    // Remove the old player
    private void removeVideoView() {
        if (playPosition >= 0) {
            if (lastChildViewPager!=null)
            lastChildViewPager.pauseVideo();
            isVideoViewAdded = false;
        }
    }

    private void addVideoView(){
        isVideoViewAdded = true;
    }

    private void resetVideoView(){
        if(isVideoViewAdded){
            removeVideoView();
            playPosition = -1;
        }
    }


    private void toggleVolume() {
//        if (videoPlayer != null) {
//            if (volumeState == VolumeState.OFF) {
//                Log.d(TAG, "togglePlaybackState: enabling volume.");
////                setVolumeControl(VolumeState.ON);
//
//            } else if(volumeState == VolumeState.ON) {
//                Log.d(TAG, "togglePlaybackState: disabling volume.");
////                setVolumeControl(VolumeState.OFF);
//
//            }
//        }
    }



//    public void setMediaObjects(CampaignDraftedResponse campaignDraftedResponse){
//        this.campaignDraftedResponse = campaignDraftedResponse;
//    }
}