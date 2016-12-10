package com.example.fragment;

import java.io.IOException;

import com.example.helloworld.LoginActitvity;
import com.example.helloworld.R;
import com.example.helloworld.User;
import com.example.helloworld.api.Server;
import com.fasterxml.jackson.databind.ObjectMapper;

import android.app.AlertDialog;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Response;

public class MyFragment extends Fragment{
	View view;
	TextView text;
	
	@Override

	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		if(view==null){
			view=inflater.inflate(R.layout.fragment_table_view4, null);
			text=(TextView)view.findViewById(R.id.text);
		}
		
		return view;
		
	}
	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		OkHttpClient client=Server.getSharedClient();
		okhttp3.Request Request=Server.requestBuilderWithApi("me")
				.method("GET",null)
				.build();
		client.newCall(Request).enqueue(new Callback() {
			
			@Override
			public void onResponse(final Call arg0, final Response arg1) throws IOException {
				// TODO Auto-generated method stub
				try{
					ObjectMapper opMapper =new ObjectMapper();
					final User user= opMapper.readValue(arg1.body().string(), User.class);
					getActivity().runOnUiThread(new Runnable() {
						
						@Override
						public void run() {
							// TODO Auto-generated method stub
							MyFragment.this.onResponse(arg0, user.getAccount());
						}

						
					});
				}catch (final IOException e) {
					// TODO: handle exception
					getActivity().runOnUiThread(new Runnable() {
						
						@Override
						public void run() {
							// TODO Auto-generated method stub
							onFailure(arg0, e);
						}

					});
				}
			}
			
			@Override
			public void onFailure(final Call arg0, final IOException arg1) {
				// TODO Auto-generated method stub
				getActivity().runOnUiThread(new Runnable() {
					
					@Override
					public void run() {
						// TODO Auto-generated method stub
						onFailure(arg0, arg1);
					}
				});
			}
		});
	}
	void onResponse(Call arg0, String response) {
		// TODO Auto-generated method stub
		text.setText("hello  "+response);
		
	}
	void onFailure(Call arg0, Exception arg1) {
		// TODO Auto-generated method stub
		new AlertDialog.Builder(getActivity())
		.setMessage("数据解析出错")
		.setPositiveButton("确认", null)
		.show();
	}
}
