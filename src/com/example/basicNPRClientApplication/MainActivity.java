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
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

public class MainActivity extends Activity {

	Intent intent;
	public final static int favourite_request_Code = 100;
	public final static int story_request_Code = 101;
	public static final String MyPREFERENCES = "MyPrefs";
    //public final static int display_request_Code = 101;
    //public final static int edit_request_Code = 102;
	SharedPreferences sharedPreferences;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		sharedPreferences = getSharedPreferences(MainActivity.MyPREFERENCES, Context.MODE_PRIVATE);
		Button programsBtn = (Button) findViewById(R.id.button_Programs);
		Button topicsBtn = (Button) findViewById(R.id.button_Topics);
		Button favouritesBtn = (Button) findViewById(R.id.buttonFavourites);
		Button exitBtn = (Button) findViewById(R.id.button_Exit);
		
		programsBtn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				intent = new Intent(MainActivity.this,com.example.basicNPRClientApplication.ListActivity.class);
				intent.putExtra("ApiURL", "http://api.npr.org/list?id=3004&output=JSON");
				startActivity(intent);
			}
		});
		
		topicsBtn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				intent = new Intent(MainActivity.this,com.example.basicNPRClientApplication.ListActivity.class);
				intent.putExtra("ApiURL", "http://api.npr.org/list?id=3002&output=JSON");
				startActivity(intent);
			}
		});
		
		favouritesBtn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				/*Editor editor = sharedPreferences.edit();
				editor.clear();
				editor.commit();*/
				
				intent = new Intent(MainActivity.this,com.example.basicNPRClientApplication.StoriesActivity.class);
				intent.putExtra("favourites",true);
				startActivity(intent);
			}
		});
		
		exitBtn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
			}
		});
		
	}
}
