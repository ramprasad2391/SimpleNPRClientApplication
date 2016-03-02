//Group9A_HW04
//Ram Prasad Narayanaswamy
//Anusha Srivastava
//Aaron Maisto

package com.example.basicNPRClientApplication;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

public class JSONUtil {

	static public class ItemJSONParser{
		
		static ArrayList<Item> parseItems(String in) throws JSONException {
			
			ArrayList<Item> itemsList = new ArrayList<Item>(); 
			
			JSONObject root = new JSONObject(in);
			JSONArray itemJSONArray = root.getJSONArray("item");
			for(int i = 0; i < itemJSONArray.length(); i++){
				JSONObject itemJSONObject = itemJSONArray.getJSONObject(i);
				Item item = Item.createItem(itemJSONObject);
				itemsList.add(item);
			}
			return itemsList;
		}
		
		
		static ArrayList<Story> parseStories(String in) throws JSONException{
			
			
			ArrayList<Story> storyList = new ArrayList<Story>(); 
			
			JSONObject root = new JSONObject(in);
			JSONObject listJSONObject = root.getJSONObject("list");
			JSONArray storyJSONArray = listJSONObject.getJSONArray("story");
			for(int i = 0; i < storyJSONArray.length(); i++){
				JSONObject storyJSONObject = storyJSONArray.getJSONObject(i);
				Story story = Story.createStory(storyJSONObject);
				storyList.add(story);
			}
			Log.d("demo",storyList.toString());
			return storyList;
		}
		
	}
}
