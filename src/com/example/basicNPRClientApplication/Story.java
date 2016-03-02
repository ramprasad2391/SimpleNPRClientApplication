//Group9A_HW04
//Ram Prasad Narayanaswamy
//Anusha Srivastava
//Aaron Maisto

package com.example.basicNPRClientApplication;

import java.io.Serializable;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Story implements Serializable{
	String storyId, storyTitle, storyTeaser, pubDate, image, thumbnail, audioURL, byline, audioLength, airedDate, storyURL, storyText;
	
	public Story() {
		super();
	}
	
	public Story(String storyId, String storyTitle, String storyTeaser, String pubDate, String thumbnail, String byline, String audioURL, String audioLength, String airedDate, String storyURL, String storyText) {
		super();
		this.storyId = storyId;
		this.storyTitle = storyTitle;
		this.storyTeaser = storyTeaser;
		this.pubDate = pubDate;
		this.thumbnail = thumbnail;
		this.byline = byline;
		this.audioURL = audioURL;
		this.audioLength = audioLength;
		this.airedDate = airedDate;
		this.storyURL = storyURL;
		this.storyText = storyText;
	}

	
	
	/**
	 * @return the storyURL
	 */
	public String getStoryURL() {
		return storyURL;
	}

	/**
	 * @param storyURL the storyURL to set
	 */
	public void setStoryURL(String storyURL) {
		this.storyURL = storyURL;
	}

	public String getStoryId() {
		return storyId;
	}

	public void setStoryId(String storyId) {
		this.storyId = storyId;
	}

	public String getStoryTitle() {
		return storyTitle;
	}

	public void setStoryTitle(String storyTitle) {
		this.storyTitle = storyTitle;
	}

	public String getStoryTeaser() {
		return storyTeaser;
	}

	public void setStoryTeaser(String storyTeaser) {
		this.storyTeaser = storyTeaser;
	}

	public String getPubDate() {
		return pubDate;
	}

	public void setPubDate(String pubDate) {
		this.pubDate = pubDate;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public String getThumbnail() {
		return thumbnail;
	}

	public void setThumbnail(String thumbnail) {
		this.thumbnail = thumbnail;
	}

	public String getAudioURL() {
		return audioURL;
	}

	public void setAudioURL(String audioURL) {
		this.audioURL = audioURL;
	}

	public String getByline() {
		return byline;
	}

	public void setByline(String byline) {
		this.byline = byline;
	}

	public String getAudioLength() {
		return audioLength;
	}

	public void setAudioLength(String audioLength) {
		this.audioLength = audioLength;
	}

	public String getAiredDate() {
		return airedDate;
	}

	public void setAiredDate(String airedDate) {
		this.airedDate = airedDate;
	}

	
	
	/**
	 * @return the storyText
	 */
	public String getStoryText() {
		return storyText;
	}

	/**
	 * @param storyText the storyText to set
	 */
	public void setStoryText(String storyText) {
		this.storyText = storyText;
	}

	@Override
	public String toString() {
		
		return storyTitle + ";;;;;;" + 
				storyTeaser + ";;;;;;" + 
				pubDate + ";;;;;;" + 
				thumbnail + ";;;;;;" + 
				byline + ";;;;;;" + 
				audioURL + ";;;;;;" + 
				audioLength + ";;;;;;" + 
				airedDate + ";;;;;;" +
				storyURL + ";;;;;;" +
				storyText; 
	}

	public static Story createStory(JSONObject js) throws JSONException
	{
		String thumbnail = null;
		if(js.has("thumbnail")){
			JSONObject obj = js.getJSONObject("thumbnail");
			if(obj.length() != 0){
				thumbnail = (obj.getJSONObject("medium")).getString("$text");
			}			
		}
		
		String byline = null;
		
		if(js.has("byline")){
			JSONArray arr = js.getJSONArray("byline");
			if(arr.length() != 0){
				JSONObject obj = arr.getJSONObject(0);
				if(obj.length() != 0 && obj.has("name")){
					if(obj.getJSONObject("name") != null && obj.getJSONObject("name").length() != 0 && obj.getJSONObject("name").has("$text")){
						byline = obj.getJSONObject("name").getString("$text");
					}					
				}
			}
		}
		
		
		String audioURL = null;
		String audioLength = null;
		if(js.has("audio")){
			JSONArray arr = js.getJSONArray("audio");
			if(arr.length() != 0){
				JSONObject obj = arr.getJSONObject(0);
				if(obj.length() != 0 && obj.has("duration")){
					if(obj.getJSONObject("duration") != null &&  obj.getJSONObject("duration").length() != 0 && obj.getJSONObject("duration").has("$text")){
						audioLength = obj.getJSONObject("duration").getString("$text");
					}										
				}
				if(obj.length() != 0 && obj.has("format")){
					if(obj.getJSONObject("format") != null &&  obj.getJSONObject("format").length() != 0 && obj.getJSONObject("format").has("mp3") && obj.getJSONObject("format").getJSONArray("mp3") != null && obj.getJSONObject("format").getJSONArray("mp3").length() != 0){
						audioURL = obj.getJSONObject("format").getJSONArray("mp3").getJSONObject(0).getString("$text");
					}
				}				
			}
		}
		
		
		String airedDate = null;
		if(js.has("show") && js.getJSONArray("show").length() != 0){
			JSONArray arr = js.getJSONArray("show");
			if(arr.length() != 0){
				JSONObject obj = arr.getJSONObject(0);
				if(obj != null && obj.length() != 0 && obj.has("showDate") && obj.getJSONObject("showDate") != null && obj.getJSONObject("showDate").has("$text")){
					airedDate = obj.getJSONObject("showDate").getString("$text");
				}
			}	
		}
		
		String storyUrl = null;
		if(js.has("link") && js.getJSONArray("link").length() != 0){
			JSONArray arr = js.getJSONArray("link");
			for(int i=0;i<arr.length();i++){
				JSONObject obj = arr.getJSONObject(i);
				if(obj.has("type") && obj.getString("type").equals("html")){
					storyUrl = obj.getString("$text");
					break;
				}
			}
		}
		
		StringBuilder sb = new StringBuilder("");
		String storyText = null;
		if(js.has("text") && js.getJSONObject("text") != null && js.getJSONObject("text").length() != 0){
			JSONArray arr = js.getJSONObject("text").getJSONArray("paragraph");
			if(arr != null && arr.length() != 0){
				for(int i=0;i<arr.length();i++){
					JSONObject obj = arr.getJSONObject(i);
					if(obj != null && obj.length() != 0 && obj.has("$text")){
						sb.append(obj.getString("$text"));
						sb.append("\n \n");
					}
				}
				if(sb.toString().length() != 0){
					storyText = sb.toString();
					sb.setLength(0);
				}

			}
		}
		
		
		return new Story(js.getString("id"),
				(js.getJSONObject("title")).getString("$text"),
				(js.getJSONObject("teaser")).getString("$text"),
				(js.getJSONObject("pubDate")).getString("$text"),
				thumbnail,
				byline,
				audioURL,
				audioLength,
				airedDate,
				storyUrl,
				storyText);
	}

}
