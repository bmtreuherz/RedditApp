package com.bradley.redditclient;

import android.content.Intent;
import android.os.Bundle;
import android.preference.PreferenceActivity;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ListView;

import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private ListView lvPosts;
    private PostsAdapter postsAdapter;
    RedditClient client;
    public static final String POST_LINK = "post";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        lvPosts = (ListView) findViewById(R.id.lvPosts);
        ArrayList<Post> aPosts = new ArrayList<Post>();
        postsAdapter = new PostsAdapter(this, aPosts);
        lvPosts.setAdapter(postsAdapter);
        client = new RedditClient();

        lvPosts.setOnScrollListener(new PostsScrollListener(this));

        // Fetch the data
        setupPostClickListener();
        fetchPosts();
    }

    public void setupPostClickListener(){
        lvPosts.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent i = new Intent(MainActivity.this, PostLinkActivity.class);
                System.out.println("POST LINK URL: " + postsAdapter.getItem(position).getPostUrl());
                String linkUrl = postsAdapter.getItem(position).getPostUrl();
                System.out.println("LINK URL IM MAIN ACTIVITY" + linkUrl);
                i.putExtra(POST_LINK, postsAdapter.getItem(position).getPostUrl());
                startActivity(i);
            }
        });
    }

    public void fetchPosts(){
        client.getRedditPosts(new JsonHttpResponseHandler(){

            @Override
            public void onSuccess(int statusCode,  org.apache.http.Header[] headers, JSONObject responseBody){
                JSONArray items = null;
                try{
                    // Get the posts json array
                    JSONObject data = responseBody.getJSONObject("data");
                    items = data.getJSONArray("children");
                    // Parse the json array into array of model objects
                    ArrayList<Post> posts = Post.fromJson(items);
                    // Load the model objects into the adapter
                    for(Post post: posts){
                        postsAdapter.add(post);
                    }
                    postsAdapter.notifyDataSetChanged();

                    // Update the client with the next page to load
                    client.setNextPage(data.getString("after"));
                } catch(JSONException e){
                    e.printStackTrace();
                }
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
