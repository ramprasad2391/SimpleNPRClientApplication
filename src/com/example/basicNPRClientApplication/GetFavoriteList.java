//Group9A_HW04
//Ram Prasad Narayanaswamy
//Anusha Srivastava
//Aaron Maisto

package com.example.basicNPRClientApplication;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;



import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

public class GetFavoriteList extends AsyncTask<Void, Integer, ArrayList<Story>>{

	ProgressDialog dialog;
	Activity mActivity;
	ArrayList<Story> storyList;
	
	public GetFavoriteList(Activity mActivity) {
		super();
		this.mActivity = mActivity;
		storyList = new ArrayList<Story>();
	}
	
	public ArrayList<Story> loadSavedPreferences() {
		SharedPreferences sharedPreferences = mActivity.getSharedPreferences(MainActivity.MyPREFERENCES, Context.MODE_PRIVATE);
		if(sharedPreferences != null){
			Map<String,?> favouriteStoryMap = sharedPreferences.getAll();
			if(favouriteStoryMap != null && !(favouriteStoryMap.isEmpty())){
				Iterator<String> itr = favouriteStoryMap.keySet().iterator();
				while(itr.hasNext()){
					String storyid = itr.next();
					String storyDetails = (String) favouriteStoryMap.get(storyid);
					storyList.add(parseStoryDetails(storyid,storyDetails));
				}
			}		
		}
		
		if(storyList != null && !storyList.isEmpty()){
			Log.d("demo4",storyList.toString());
			return storyList;			
		}
		else{
			return null;
		}	
	}
	
	public Story parseStoryDetails(String storyId, String storyDetails){
		Story story =  new Story();
		story.setStoryId(storyId);
		String[] strDetails = storyDetails.split(";;;;;;");
		for(int i=0;i<strDetails.length;i++){
			if(strDetails[i].equals("null")){
				strDetails[i] = null;
			}
		}
		story.setStoryTitle(strDetails[0]);
		story.setStoryTeaser(strDetails[1]);
		story.setPubDate(strDetails[2]);
		story.setThumbnail(strDetails[3]);
		story.setByline(strDetails[4]);
		story.setAudioURL(strDetails[5]);
		story.setAudioLength(strDetails[6]);
		story.setAiredDate(strDetails[7]);
		story.setStoryURL(strDetails[8]);
		story.setStoryURL(strDetails[9]);
		return story;
	}

	@Override
	protected ArrayList<Story> doInBackground(Void... params) {
		// TODO Auto-generated method stub
		storyList = loadSavedPreferences();
		return storyList;
	}

	/* (non-Javadoc)
	 * @see android.os.AsyncTask#onPreExecute()
	 */
	@Override
	protected void onPreExecute() {
		// TODO Auto-generated method stub
		super.onPreExecute();
		dialog = new ProgressDialog(mActivity);
		dialog.setMessage("Loading Results...");
		dialog.show();
	}

	/* (non-Javadoc)
	 * @see android.os.AsyncTask#onPostExecute(java.lang.Object)
	 */
	@Override
	protected void onPostExecute(final ArrayList<Story> result) {
		// TODO Auto-generated method stub
		super.onPostExecute(result);
		if(dialog.isShowing()){
			dialog.dismiss();
		}
		
		ListView ll = (ListView) (mActivity.findViewById(R.id.listViewStories));
		if(ll != null && ll.getChildCount() > 0){
			ll.removeViews(0,ll.getChildCount());
		}
		if(result != null && !result.isEmpty()){
			StoryAdapter adapter = new StoryAdapter(mActivity,R.layout.row_item_layout,result);
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
					mActivity.startActivityForResult(intent,MainActivity.favourite_request_Code);
				}			
			});
		}
		else{
			Toast.makeText(mActivity,"No Favourite Stories!!!! ",Toast.LENGTH_LONG).show();
		}			
	}	
}
