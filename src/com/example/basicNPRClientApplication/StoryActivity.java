//Group9A_HW04
//Ram Prasad Narayanaswamy
//Anusha Srivastava
//Aaron Maisto

package com.example.basicNPRClientApplication;

import java.util.Iterator;
import java.util.Map;



import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

public class StoryActivity extends Activity {

	Story story;
	String storyId;
	SharedPreferences sharedPreferences;
	ImageButton imageButtonFavourites;
	ImageButton imageButtonMediaPlayer;
	Boolean b;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_story);
		b = false;
		
		sharedPreferences = getSharedPreferences(MainActivity.MyPREFERENCES, Context.MODE_PRIVATE);
		story = (Story) getIntent().getExtras().getSerializable("ClickedStory");
		storyId = story.getStoryId();
		
		int durationInSec = 0;
		int min = 0, sec = 0;
		StringBuilder dateAiredString = new StringBuilder();
		if(story.getAiredDate() != null && story.getAiredDate().length() != 0){
			dateAiredString.append(story.getAiredDate());
			
		}
		else{
			dateAiredString.append("No Date Aired");
		}			
		dateAiredString.append(" | ");
		if(story.getAudioLength() != null && story.getAudioLength().length() != 0){
			durationInSec = Integer.parseInt(story.getAudioLength());
			min = durationInSec/60;
			sec = durationInSec%60;
			dateAiredString.append(min + " mins " + sec + " secs");
		}
		else{
			dateAiredString.append("No duration");
		}
		
		TextView textViewStoryTitle = (TextView) findViewById(R.id.textViewStoryTitle);
		textViewStoryTitle.setText(story.getStoryTitle());
		
		TextView textViewReporterName = (TextView) findViewById(R.id.textViewReporterName);
		if(story.getByline() != null && story.getByline().length() != 0){
			textViewReporterName.setText("By "+story.getByline());
		}
		else{
			textViewReporterName.setText("No Reporter");
		}
		
		
		TextView textViewDateAired = (TextView) findViewById(R.id.textViewDateAired);
		textViewDateAired.setText(dateAiredString.toString());
		
		TextView textViewTeaser = (TextView) findViewById(R.id.textViewTeaser);
		textViewTeaser.setText(story.getStoryTeaser());
		
		
		imageButtonFavourites = (ImageButton) findViewById(R.id.imageButtonFavourites);
		if(sharedPreferences != null){
			Map<String,?> favouriteStoryMap =  sharedPreferences.getAll();
			if(favouriteStoryMap != null){
				if(favouriteStoryMap.containsKey(storyId)){
					imageButtonFavourites.setImageResource(R.drawable.rating_important);
				}
				else{
					imageButtonFavourites.setImageResource(R.drawable.rating_not_important);
				}
			}
		}
		else{
			imageButtonFavourites.setImageResource(R.drawable.rating_not_important);
		}
		
		
		
		imageButtonFavourites.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				if(sharedPreferences != null){
					Map<String,?> favouriteStoryMap = sharedPreferences.getAll();
					if(favouriteStoryMap != null){
						if(favouriteStoryMap.containsKey(storyId)){
							savePreferences(storyId, story.toString(), false);							
						}
						else{
							savePreferences(storyId, story.toString(), true);
						}
					}
				}
			}
		});
		
		
		ImageButton imageButtonBack = (ImageButton) findViewById(R.id.imageButtonBack);
		imageButtonBack.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent();
				setResult(RESULT_OK, intent);
				finish();
			}
		});
		
		ImageButton imageButtonBrowser= (ImageButton) findViewById(R.id.imageButtonBrowser);
		imageButtonBrowser.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if(story.getStoryURL() != null){
					Intent intent = new Intent(Intent.ACTION_VIEW,Uri.parse(story.getStoryURL()));
					b = true;
					startActivity(intent);
				}
				else{
					Toast.makeText(StoryActivity.this,"No URL for the story",Toast.LENGTH_LONG).show();
				}
				
			}
		});
		
		
		imageButtonMediaPlayer = (ImageButton) findViewById(R.id.imageButtonMediaPlayer);
		imageButtonMediaPlayer.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if(story.getAudioURL() != null && story.getAudioURL().length() != 0){
					Log.d("audioURL",story.getAudioURL());
					new AudioPlayerAsyncTask(StoryActivity.this).execute(story.getAudioURL());
					/*AudioPlayerClass apc = new AudioPlayerClass(StoryActivity.this);
					apc.playMedia(story.getAudioURL());
					AudioPlayerClass.mediaController.hide();
				    AudioPlayerClass.mediaPlayer.stop();
				    AudioPlayerClass.mediaPlayer.release();*/
				}
				else{
					Toast.makeText(StoryActivity.this,"No audio for the story",Toast.LENGTH_LONG).show();
				}
				
			}
		});
		
		
		
	}
	
	
	public void savePreferences(String storyId, String storyDetails, Boolean toBeAdded) {
		Editor editor = sharedPreferences.edit();
		if(toBeAdded){
			editor.putString(storyId,storyDetails);
			editor.commit();
			imageButtonFavourites.setImageResource(R.drawable.rating_important);
		}
		else{
			editor.remove(storyId);
			editor.commit();
			imageButtonFavourites.setImageResource(R.drawable.rating_not_important);
		}
	}


	
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		if(AudioPlayerClass.b){
			AudioPlayerClass.mediaController.show();
		}
	    return false;
	}
	
	
	@Override
	protected void onStop() {
		// TODO Auto-generated method stub
		super.onStop();
		if(!b){
			if(AudioPlayerClass.b){
				AudioPlayerClass.mediaController.hide();
			    AudioPlayerClass.mediaPlayer.stop();
			    AudioPlayerClass.mediaPlayer.release();
			    AudioPlayerClass.b = false;
			}
			
			Intent intent = new Intent();
			setResult(RESULT_OK, intent);
			finish();
		}		
	}


	
	
	
	
	
}
