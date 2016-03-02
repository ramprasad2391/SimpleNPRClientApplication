//Group9A_HW04
//Ram Prasad Narayanaswamy
//Anusha Srivastava
//Aaron Maisto

package com.example.basicNPRClientApplication;

import java.util.List;


import com.squareup.picasso.Picasso;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView.FindListener;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class StoryAdapter extends ArrayAdapter<Story> {

		List<Story> mdata;
		Context mContext;
		int mResource;
		
		public StoryAdapter(Context context, int resource, List<Story> objects) {
			super(context, resource, objects);			
			this.mContext = context;
			this.mdata = objects;
			this.mResource = resource;
			// TODO Auto-generated constructor stub
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			// TODO Auto-generated method stub
						
			if(convertView == null){
				LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
				convertView = inflater.inflate(mResource, parent, false);
			}
			
			Story story = mdata.get(position);
			
			ImageView img = (ImageView) convertView.findViewById(R.id.imageViewthumbnail);
			if(story.getThumbnail() != null){
				Picasso.with(mContext).load(story.getThumbnail()).into(img);
			}
			else{
				img.setImageResource(R.drawable.default_thumbnail);
			}
			
			
			TextView storyTitleText = (TextView) convertView.findViewById(R.id.textViewRowTitle);
			storyTitleText.setText(story.getStoryTitle());
			
			TextView storyPubDate = (TextView) convertView.findViewById(R.id.textViewRowPubDate);
			storyPubDate.setText(story.getPubDate());
			
			TextView storyMiniTeaser = (TextView) convertView.findViewById(R.id.textViewrowMiniTeaser);
			storyMiniTeaser.setText(story.getStoryTeaser());
			
			
			
			return convertView;
		}

	}



