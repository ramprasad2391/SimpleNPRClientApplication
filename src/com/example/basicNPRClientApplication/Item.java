//Group9A_HW04
//Ram Prasad Narayanaswamy
//Anusha Srivastava
//Aaron Maisto

package com.example.basicNPRClientApplication;

import java.io.Serializable;

import org.json.JSONException;
import org.json.JSONObject;

public class Item implements Serializable{
	int itemId;
	String itemTitle,itemType;
	

	
	
	public Item(int itemId, String itemTitle, String itemType) {
		super();
		this.itemId = itemId;
		this.itemTitle = itemTitle;
		this.itemType = itemType;
	}




	/**
	 * @return the itemId
	 */
	public int getItemId() {
		return itemId;
	}




	/**
	 * @param itemId the itemId to set
	 */
	public void setItemId(int itemId) {
		this.itemId = itemId;
	}




	/**
	 * @return the itemTitle
	 */
	public String getItemTitle() {
		return itemTitle;
	}




	/**
	 * @param itemTitle the itemTitle to set
	 */
	public void setItemTitle(String itemTitle) {
		this.itemTitle = itemTitle;
	}




	/**
	 * @return the itemType
	 */
	public String getItemType() {
		return itemType;
	}




	/**
	 * @param itemType the itemType to set
	 */
	public void setItemType(String itemType) {
		this.itemType = itemType;
	}


	

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return itemTitle;
	}




	public static Item createItem(JSONObject js) throws JSONException
	{
		return new Item(Integer.parseInt(js.getString("id")),(js.getJSONObject("title")).getString("$text"),js.getString("type"));
	}

}
