package com.example.helloworld;

import java.io.IOException;
import java.util.List;
import java.util.zip.Inflater;

import com.example.helloworld.api.Server;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Request;
import okhttp3.Response;

public class FeedsActivity extends Activity{
	TextView text;
	TextView title;
	TextView name;
	AvatarView avatar;
	ListView commentlist;
	View btnLoadMore;
	TextView textLoadMore;
	List<Article> data;

	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_feeds);
		text=(TextView)findViewById(R.id.text);
		title=(TextView)findViewById(R.id.title);
		name=(TextView)findViewById(R.id.name);
		avatar=(AvatarView) findViewById(R.id.avatar);
		String text1=(String) getIntent().getExtras().get("text");
		text.setText(text1);
		String title1=(String) getIntent().getExtras().get("title");
		title.setText(title1);
		String name1=(String) getIntent().getExtras().get("authorname");
		name.setText(name1);

		

	}
	
}
