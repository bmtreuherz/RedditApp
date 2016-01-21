package com.bradley.redditclient;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

/**
 * Created by Bradley on 1/19/2016.
 */
public class RedditClient {
    private AsyncHttpClient client;
    private final String API_BASE_URL = "http://reddit.com/";
    private String nextPage = ""; // This is the reddit "after" property used to find the next page

    public RedditClient() {
        this.client = new AsyncHttpClient();
    }

    private String getApiUrl(String subreddit){
        // If a specific sub-reddit is to be viewed be sure to include /r/ in the url
        if(subreddit.length() > 0){
            subreddit = "r/" + subreddit;
        }
        return API_BASE_URL + subreddit + ".json?count=25&after=" + this.nextPage;
    }

    public void getRedditPosts(JsonHttpResponseHandler handler){

        // TODO: Provide a way to change the subreddit.
        String url = getApiUrl("");
        client.get(url, handler);
    }

    public void setNextPage(String np){
        this.nextPage = np;
    }
}
