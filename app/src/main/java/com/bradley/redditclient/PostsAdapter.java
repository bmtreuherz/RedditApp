package com.bradley.redditclient;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by Bradley on 1/20/16.
 */
public class PostsAdapter extends ArrayAdapter<Post> {
    public PostsAdapter(Context context, ArrayList<Post> aPosts){
        super(context, 0, aPosts);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        // Get the data item for this position
        Post post = getItem(position);

        // Check if the existing view is being reused, otherwise infalte the view
        if(convertView == null){
//            LayoutInflater inflater = LayoutInflater.from(getContext());
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.post_item, parent, false);
        }

        // Lookup views within the layout
        TextView tvUpvotes = (TextView) convertView.findViewById(R.id.tvUpvotes);
        TextView tvTitle = (TextView) convertView.findViewById(R.id.tvTitle);
        TextView tvDomain = (TextView) convertView.findViewById(R.id.tvDomain);
        TextView tvComments = (TextView) convertView.findViewById(R.id.tvComments);
        TextView tvSubreddit = (TextView) convertView.findViewById(R.id.tvSubreddit);
        ImageView ivThumbnail = (ImageView) convertView.findViewById(R.id.ivThumbnail);


        // Populate the data
        tvUpvotes.setText("" + post.getUpvotes());
        tvTitle.setText(post.getTitle());
        tvDomain.setText("(" + post.getDomain() + ")");
        tvComments.setText(post.getNumComments() + " comments");
        tvSubreddit.setText(post.getSubreddit());

        // Display the thumbnail if there is one
        String thumbnailUrl = post.getThumbnailUrl();
        if(thumbnailUrl.length() < 1 || thumbnailUrl.equals("self")){
            ivThumbnail.setImageResource(R.drawable.reddit_alien);
        }else{
            Picasso.with(getContext()).load(post.getThumbnailUrl()).into(ivThumbnail);
        }

        return convertView;
    }
}
