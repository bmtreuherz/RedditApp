package com.bradley.redditclient;

import android.widget.AbsListView;

/**
 * Created by Bradley on 1/20/16.
 */
public class PostsScrollListener implements AbsListView.OnScrollListener {
    private int threshold = 5;     // How many posts must be at the bottom before loading more
    private int previousTotal = 0;
    private boolean loading = true;
    private String nextPage = ""; // This is the reddit "after" parameter that is used to find the next page
    MainActivity activity;

    public PostsScrollListener(MainActivity activity){
        this.activity = activity;
    }


    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount){
        if(loading){
            if(totalItemCount > previousTotal){
                // The additional posts have completed loaded
                loading = false;
                previousTotal = totalItemCount;
            }
        }
        if(!loading && (firstVisibleItem + threshold) >= (totalItemCount - visibleItemCount)){
            loading = true;
            activity.fetchPosts();
        }
    }

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState){}


}
