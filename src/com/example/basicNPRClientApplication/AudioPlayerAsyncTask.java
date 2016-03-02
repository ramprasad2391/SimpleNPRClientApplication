//Group9A_HW04
//Ram Prasad Narayanaswamy
//Anusha Srivastava
//Aaron Maisto


package com.example.basicNPRClientApplication;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import android.os.AsyncTask;
import android.util.Log;

public class AudioPlayerAsyncTask extends AsyncTask<String, Void, String> {

	private StoryActivity storyActivity;
	
	public AudioPlayerAsyncTask(StoryActivity storyActivity) {
		super();
		this.storyActivity = storyActivity;
	}



	@Override
	protected String doInBackground(String... params) {
		String line = "";
		try {
			URL url = new URL(params[0]);
			HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
			urlConnection.setRequestMethod("GET");
			urlConnection.connect();
			if (urlConnection.getResponseCode() == HttpURLConnection.HTTP_OK) {
				InputStream in = urlConnection.getInputStream();
				BufferedReader br = new BufferedReader(new InputStreamReader(in));
				
				line = br.readLine();
				String streamurl = "";
				while (line != null) {
					streamurl = streamurl + line;
					line = br.readLine();
				}
				Log.d("streamingURL",streamurl);
				return streamurl;
				
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	@Override
	protected void onPostExecute(String result) {
		
			AudioPlayerClass apc = new AudioPlayerClass(storyActivity,result);
			//apc.playMedia(result);

	}

}
