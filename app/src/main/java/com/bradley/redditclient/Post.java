package com.bradley.redditclient;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Bradley on 1/20/2016.
 */
public class Post implements Serializable{
    private static final long serialVersionUID = 1234512345;
    private int upvotes;
    private String title;
    private String domain;
    private int numComments;
    private String subreddit;
    private String thumbnailUrl;
    private String postUrl;


    public int getUpvotes(){
        return upvotes;
    }

    public String getTitle(){
        return title;
    }

    public String getDomain(){
        return domain;
    }

    public int getNumComments(){
        return numComments;
    }

    public String getSubreddit(){
        return subreddit;
    }

    public String getThumbnailUrl(){
        return thumbnailUrl;
    }

    public String getPostUrl(){
        return postUrl;
    }

    // Returns a Post object from Json
    public static Post fromJson(JSONObject jsonObject){
        Post p = new Post();
        try{
            p.upvotes = jsonObject.getInt("score");
            p.title = jsonObject.getString("title");
            p.domain = jsonObject.getString("domain");
            p.numComments = jsonObject.getInt("num_comments");
            p.subreddit = jsonObject.getString("subreddit");
            p.postUrl = jsonObject.getString("url");


            // Try to get the thumbnailUrl (not all posts have thumbnails)
            try{
                p.thumbnailUrl = jsonObject.getString("thumbnail");
            } catch (JSONException e){
                // There is no thumbnail
                p.thumbnailUrl = "";
            }


            System.out.println(p.thumbnailUrl);

        }catch(JSONException e){
            e.printStackTrace();
            return null;
        }

        return p;
    }

    public static ArrayList<Post> fromJson(JSONArray jsonArray){
        ArrayList<Post> posts = new ArrayList<Post>(jsonArray.length());

        // Convert each element in the json array to a json object, then to a Post
        for(int i=0; i<jsonArray.length(); i++){
            JSONObject postJson = null;
            try{
                postJson = jsonArray.getJSONObject(i).getJSONObject("data");
            } catch(Exception e){
                e.printStackTrace();
                continue;
            }

            Post post = Post.fromJson(postJson);
            if(post != null){
                posts.add(post);
            }
        }

        return posts;

    }
}
