//Group9A_HW04
//Ram Prasad Narayanaswamy
//Anusha Srivastava
//Aaron Maisto

package com.example.basicNPRClientApplication;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import org.json.JSONException;



import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

public class GetStoryList extends AsyncTask<String, Integer, ArrayList<Story>>{

	ProgressDialog dialog;
	Activity mActivity;
	String type;
	
	
	
	public GetStoryList(Activity mActivity) {
		super();
		this.mActivity = mActivity;
		type = mActivity.getIntent().getExtras().getString("ClickedItemType");
	}

	@Override
	protected ArrayList<Story> doInBackground(String... params) {
		
		try {
			URL url = new URL((String) params[0]);
			HttpURLConnection con = (HttpURLConnection) url.openConnection();
			con.setRequestMethod("GET");
			con.connect();
			int statusCode = con.getResponseCode();
			if(statusCode == HttpURLConnection.HTTP_OK){
				BufferedReader reader = new BufferedReader(new InputStreamReader(con.getInputStream()));
				StringBuilder sb= new StringBuilder();
				String line = reader.readLine();
				while(line != null){
					sb.append(line);
					line = reader.readLine();
				}
				
				return JSONUtil.ItemJSONParser.parseStories(sb.toString());
			}
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected void onPostExecute(final ArrayList<Story> result) {
		// TODO Auto-generated method stub
		super.onPostExecute(result);
		if(dialog.isShowing()){
			dialog.dismiss();
		}
		
		if(result != null && !result.isEmpty()){
			ListView ll = (ListView) mActivity.findViewById(R.id.listViewStories);
			StoryAdapter adapter = new StoryAdapter(mActivity,R.layout.row_item_layout,result);
			//ArrayAdapter<Story> adapter = new ArrayAdapter<Story>(StoriesActivity.this,android.R.layout.simple_list_item_1,result);
			ll.setAdapter(adapter);
			ll.setOnItemClickListener(new AdapterView.OnItemClickListener() {

				@Override
				public void onItemClick(AdapterView<?> parent, View view,
						int position, long id) {
					// TODO Auto-generated method stub					
					Story story = result.get(position);
					Intent intent;
					intent = new Intent(mActivity, com.example.basicNPRClientApplication.StoryActivity.class);				
					intent.putExtra("ClickedStory",story);
					mActivity.startActivityForResult(intent,MainActivity.story_request_Code);
				}			
			});
		}
		else{
			Toast.makeText(mActivity,"No Stories for this particular " + type + "!!!! ",Toast.LENGTH_LONG).show();
		}
		
		
	}

	@Override
	protected void onPreExecute() {
		// TODO Auto-generated method stub
		super.onPreExecute();
		dialog = new ProgressDialog(mActivity);
		dialog.setMessage("Loading Results...");
		dialog.show();				
	}

	@Override
	protected void onProgressUpdate(Integer... values) {
		// TODO Auto-generated method stub
		super.onProgressUpdate(values);	
	}
	
}



