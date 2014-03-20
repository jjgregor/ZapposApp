package com.example.zappossearchandtext;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;

public class MainActivity extends Activity {

	private String _product;
	public static final String EXTRA_MESSAGE = "com.example.zappossearchandtext";
	public static final String MESSAGE = "com.example.zappossearchandtext.MESSAGE";
	public static final String RETURNING ="com.example.zappossearchandtext.RETURNING";
	private String _number;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Bundle extras = getIntent().getExtras();
		if(extras != null){
			// Main activity call do search with or with out phone number send message.
			int i = extras.getInt(MainActivity.RETURNING);
			if (i == 1){
				setContentView(R.layout.activity_main);
			}
			//Phone number valid, goes to search screen;
			if(i == 2){
				setContentView(R.layout.number_added);
			}
			//Invalid phone number.
			if(i == 3){
				setContentView(R.layout.invalid_number);
			}
		}	
		else {
			setContentView(R.layout.activity_main);
		}
	}
	//Search button to start search.
	public void Search(View view){
		try{
			EditText editText = (EditText) findViewById(R.id.edit_message);
			_product = editText.getText().toString();
		} catch(Exception e) {
			Intent RRA = new Intent(this, MainActivity.class);
			startActivity(RRA);
		}
		Intent RRA = new Intent(this, ResultsReturnedActivity.class);	
		RRA.putExtra(EXTRA_MESSAGE, _product);
		RRA.putExtra(MESSAGE, _number);
		startActivity(RRA);
	}
	//Add phone number to send update to.
	public void  numberAdded(View view){
		try{
			EditText editText = (EditText) findViewById(R.id.edit_message);
			_number = editText.getText().toString();
		} catch(Exception e) {
			// Error if phone number isn't 10 digits or numbers.
			if (!_number.contains("[0-9]+") || !(_number.length() == 9)) {
				Intent intent = new Intent(this, MainActivity.class);
				intent.putExtra(RETURNING, 3);
				startActivity(intent);
			}
		}
		// Go to search page
		Intent intent = new Intent(this, MainActivity.class);
		intent.putExtra(RETURNING, 2);
		startActivity(intent);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	public String getText() {
		return _number;
	}
	public void setText(String number) {
		_number = number;
	}

}
