package com.example.helloworld;

import java.io.IOException;
import java.security.GeneralSecurityException;

import com.example.helloworld.api.Server;

import android.app.Activity;
import android.app.DownloadManager.Request;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Toast;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Response;

public class BootActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_boot); 
		
	}
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		//新建客户端
		OkHttpClient client=new OkHttpClient();
		okhttp3.Request Request=Server.requestBuilderWithApi("/hello")
				.method("get", null)
				.build();
		
		client.newCall(Request).enqueue(new Callback() {
			//成功获取request
			@Override
			public void onResponse(Call arg0, final Response arg1) throws IOException {
				// TODO Auto-generated method stub
				BootActivity.this.runOnUiThread(new Runnable() {
					
					@Override
					public void run() {
						// TODO Auto-generated method stub
						try{
							Toast.makeText(BootActivity.this, arg1.body().string() , Toast.LENGTH_LONG).show();
						}catch (Exception e) {
							// TODO: handle exception
							e.printStackTrace();
						}
					}
				});
			}
			//获取失败
			@Override
			public void onFailure(Call arg0, final IOException arg1) {
				// TODO Auto-generated method stub
				BootActivity.this.runOnUiThread(new Runnable() {
					
					@Override
					public void run() {
						// TODO Auto-generated method stub
							Toast.makeText(BootActivity.this, arg1.getLocalizedMessage() , Toast.LENGTH_LONG).show();
					}
				});
			}
		});
		Handler handler=new Handler();
		handler.postDelayed(new Runnable(){
			public void run(){
				startLoginActivity();
			}
		}, 1000);
	}
	void startLoginActivity(){
		Intent itnt=new Intent(this,LoginActitvity.class);
		startActivity(itnt);
		finish();
		
	}
}
