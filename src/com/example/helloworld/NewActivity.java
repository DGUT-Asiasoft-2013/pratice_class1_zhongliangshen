package com.example.helloworld;

import java.io.IOException;

import com.example.helloworld.api.Server;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Response;

public class NewActivity extends Activity{
	EditText title;
	EditText article;
@Override
protected void onCreate(Bundle savedInstanceState) {
	// TODO Auto-generated method stub
	super.onCreate(savedInstanceState);
	setContentView(R.layout.activity_new);
	title=(EditText)findViewById(R.id.title);
	article=(EditText)findViewById(R.id.article);
	findViewById(R.id.btn).setOnClickListener(new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			clicked();
			// TODO Auto-generated method stub
//			finish();
//			overridePendingTransition(R.anim.slide_out_bottom,0);
		}
	});
}
void clicked(){
	String title=NewActivity.this.title.getText().toString();
	String article=NewActivity.this.article.getText().toString();
	
	OkHttpClient client=Server.getSharedClient();
	MultipartBody request=new MultipartBody.Builder()
			.addFormDataPart("title", title)
	        .addFormDataPart("text", article)
	        .build();
	
	okhttp3.Request Request=Server.requestBuilderWithApi("/article")
			.method("post",null)
			.post(request)
			.build();
	
	client.newCall(Request).enqueue(new Callback() {
		
		@Override
		public void onResponse(final Call arg0, final Response arg1) throws IOException {
			// TODO Auto-generated method stub
			NewActivity.this.runOnUiThread(new Runnable() {
				
				@Override
				public void run() {
					// TODO Auto-generated method stub
					try{
						Toast.makeText(NewActivity.this, arg1.body().string() , Toast.LENGTH_LONG).show();
					}catch (Exception e) {
						// TODO: handle exception
						e.printStackTrace();
					}
				}
			});
		}
		
		@Override
		public void onFailure(Call arg0, final IOException arg1) {
			// TODO Auto-generated method stub
			NewActivity.this.runOnUiThread(new Runnable() {
				
				@Override
				public void run() {
					// TODO Auto-generated method stub
						Toast.makeText(NewActivity.this, arg1.getLocalizedMessage() , Toast.LENGTH_LONG).show();
				}
			});
		}
	});
}
}
