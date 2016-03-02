//Group9A_HW04
//Ram Prasad Narayanaswamy
//Anusha Srivastava
//Aaron Maisto

package com.example.basicNPRClientApplication;





import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;


public class StoriesActivity extends Activity {

	String id;
	SharedPreferences sharedPreferences;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_stories);
		sharedPreferences = getSharedPreferences(MainActivity.MyPREFERENCES, Context.MODE_PRIVATE);
		if(getIntent().getExtras() != null && getIntent().getExtras().containsKey("favourites")){
			if(getIntent().getExtras().getBoolean("favourites")){
				new GetFavoriteList(StoriesActivity.this).execute();				
			}
		}
		else{
			if(getIntent().getExtras() != null && getIntent().getExtras().containsKey("ClickedItemId")){
				id = getIntent().getExtras().getInt("ClickedItemId")+"";			
				new GetStoryList(StoriesActivity.this).execute("http://api.npr.org/query?id="+id+"&fields=all&startNum=25&dateType=story&output=JSON&apiKey=MDE4MzQ1ODkzMDE0MjQ1NTY1NTFiOGM1Yg001");
				Log.d("demo","http://api.npr.org/query?id="+id+"&fields=all&startNum=25&dateType=story&output=JSON&apiKey=MDE4MzQ1ODkzMDE0MjQ1NTY1NTFiOGM1Yg001");
			}
		}	
	}
	
	
	@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.apps_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.clearFavorites){      	
        	clearPreferences();
        	finish();
    		startActivity(getIntent());
        	return true;
        }
        return super.onOptionsItemSelected(item);
    }
	

	/* (non-Javadoc)
	 * @see android.app.Activity#onActivityResult(int, int, android.content.Intent)
	 */
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		if(requestCode == MainActivity.favourite_request_Code){
			if(resultCode == RESULT_OK){
				finish();
				startActivity(getIntent());	
			}
		}
		
		if(requestCode == MainActivity.story_request_Code){
			if(resultCode == RESULT_OK){	
			}
		}
	}
	
	public void clearPreferences() {
		Editor editor = sharedPreferences.edit();
		editor.clear();
		editor.commit();	
	}
	
/*
	
	@Override
	protected void onStop() {
		// TODO Auto-generated method stub
		super.onStop();	
		finish();			
	}


	
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();		
		finish();		
	}*/
	
	
}
