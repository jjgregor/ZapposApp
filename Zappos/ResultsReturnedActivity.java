package com.example.zappossearchandtext;


import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import android.os.Bundle;
import android.app.Activity;
import android.telephony.SmsManager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;
import android.support.v4.app.NavUtils;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Build;

public class ResultsReturnedActivity extends Activity {

	public static final String RETURNING ="com.example.zappossearchandtext.RETURNING";
	private ArrayList<Items> list; // list of items to monitor
	private String _message; // search value
	private Timer t;
	@SuppressLint("NewApi")
	@Override

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.results_returned);
		thread.start();

	}
	// Thread to run product search.
	Thread thread = new Thread(new Runnable(){
		@Override
		public void run() {
			Items products = new Items();
			String message;
			try {
				Intent intent = getIntent();
				//Retrieve search value.
				message = intent.getStringExtra(MainActivity.EXTRA_MESSAGE);
				//Get rid of spaces if more than one word.
				message = message.replaceAll("\\s","");
				//Search call returns array list with items;
				products = httpGet.getPro(message);
				// Creates a list view with items returned as buttons
				// if pressed added to list of products to monitor. 
				addToList(products);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	});
	// Add items to list to be monitored if they are 20% off or more.
	public void addToList(final Items item){
		ListView lv = (ListView) findViewById(R.id.action_settings);
		ArrayAdapter<Items> adapter = new ArrayAdapter<Items>(this,android.R.layout.simple_list_item_1);
		lv.setAdapter(adapter);
		lv.setOnItemClickListener(new OnItemClickListener() {
			// Make each item in the list view a button that can be clicked,
			// if clicked add item to list monitored for percent off.
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				list.add(item);
				sendSMS();
				if(t == null){
					update();
				}
			}
		});
	}
	// Timer to update the list to see if item became 20% off or more.
	private void update() {

		t = new Timer();
		t.scheduleAtFixedRate(new TimerTask() {
		
			public void run() {
				sendSMS();
			}
		}, 0, (1000 * 60 * 60));

	}
	// Method to send text message if item is >= 20% off.
	public void sendSMS() {
		for(Items item : list){
			String s = item.getPercentOff();
			int i =Integer.parseInt(s);
			Intent in = getIntent();
			String number = in.getStringExtra(MainActivity.MESSAGE);
			if( i >= 20){
				try {
					SmsManager smsManager = SmsManager.getDefault();
					smsManager.sendTextMessage(number, null, _message, null,
							null);
					Toast.makeText(getApplicationContext(), "A ZAPPOS ITEM YOU'VE SELECTED IS 20% OFF OR MORE!!!",
							Toast.LENGTH_LONG).show();
				} catch (Exception e) {

					Intent intent = new Intent(this, MainActivity.class);
					startActivity(intent);

				}
			}
		}

	}

	/**
	 *
	 * Set up the {@link android.app.ActionBar}, if the API is available.
	 */
	@TargetApi(Build.VERSION_CODES.HONEYCOMB)
	private void setupActionBar() {
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
			getActionBar().setDisplayHomeAsUpEnabled(true);
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.results_returned, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			// This ID represents the Home or Up button. In the case of this
			// activity, the Up button is shown. Use NavUtils to allow users
			// to navigate up one level in the application structure. For
			// more details, see the Navigation pattern on Android Design:
			//
			// http://developer.android.com/design/patterns/navigation.html#up-vs-back
			//
			NavUtils.navigateUpFromSameTask(this);
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

}
