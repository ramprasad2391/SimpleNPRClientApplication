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
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
;

public class ListActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_list);
		String ApiURL = getIntent().getExtras().getString("ApiURL");
		new GetList().execute(ApiURL);
	}
	
	public class GetList extends AsyncTask<String, Integer, ArrayList<Item>>{

		ProgressDialog dialog;
		
		@Override
		protected ArrayList<Item> doInBackground(String... params) {
			
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
					
					return JSONUtil.ItemJSONParser.parseItems(sb.toString());
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
		protected void onPostExecute(final ArrayList<Item> result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			if(dialog.isShowing()){
				dialog.dismiss();
			}
			
			ListView ll = (ListView) findViewById(R.id.listViewStories);
			ArrayAdapter<Item> adapter = new ArrayAdapter<Item>(ListActivity.this,android.R.layout.simple_list_item_1,result);
			ll.setAdapter(adapter);
			ll.setOnItemClickListener(new AdapterView.OnItemClickListener() {

				@Override
				public void onItemClick(AdapterView<?> parent, View view,
						int position, long id) {
					// TODO Auto-generated method stub					
					Item item = result.get(position);
					Intent intent;
					intent = new Intent(ListActivity.this, com.example.basicNPRClientApplication.StoriesActivity.class);				
					intent.putExtra("ClickedItemId",item.getItemId());
					intent.putExtra("ClickedItemType",item.getItemType());
					startActivity(intent);
				}			
			});				
		}

		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
			dialog = new ProgressDialog(ListActivity.this);
			dialog.setMessage("Loading Results...");
			dialog.show();
			
		}

		@Override
		protected void onProgressUpdate(Integer... values) {
			// TODO Auto-generated method stub
			super.onProgressUpdate(values);
			
			
		}
		
	}
	
}
