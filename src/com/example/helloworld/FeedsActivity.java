package com.example.helloworld;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

public class FeedsActivity extends Activity{
TextView text;
protected void onCreate(Bundle savedInstanceState) {
	// TODO Auto-generated method stub
	super.onCreate(savedInstanceState);
	setContentView(R.layout.activity_feeds);
	text=(TextView)findViewById(R.id.text);
	String text1=(String) getIntent().getExtras().get("mytext");
	text.setText(text1);
}
}
