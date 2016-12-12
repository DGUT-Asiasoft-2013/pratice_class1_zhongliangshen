package com.example.helloworld;

import java.io.IOException;

import com.example.fragment.ForGotPasswordFargment1;
import com.example.fragment.ForGotPasswordFargment1.OnGoNextListener;
import com.example.fragment.ForGotPasswordFragment2;
import com.example.fragment.ForGotPasswordFragment2.OnGoClickedListener;
import com.example.helloworld.api.Server;
import com.fasterxml.jackson.databind.ObjectMapper;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Response;

public class ForgotpasswordActivity extends Activity{
	ForGotPasswordFargment1 step1=new ForGotPasswordFargment1();
	ForGotPasswordFragment2 step2=new ForGotPasswordFragment2();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_forgot_password);

		step1.setOnGoNextListener(new OnGoNextListener() {

			@Override
			public void onGoNext() {
				goStep2();
			}
		});
		
		getFragmentManager().beginTransaction().replace(R.id.container, step1).commit();
	}

	void goStep2(){
		getFragmentManager()
		.beginTransaction()
		.setCustomAnimations(
				R.animator.slide_in_right,
				R.animator.slide_out_left,
				R.animator.slide_in_left,
				R.animator.slide_out_right)
		.replace(R.id.container, step2)		
		.addToBackStack(null)
		.commit();
		step2.setOnGoClickedListener(new OnGoClickedListener() {
			
			@Override
			public void onGoClicked() {
				// TODO Auto-generated method stub
				
				Clicked();
			}
		});
	}
	void Clicked(){
		String email=step1.getText();
		String password=step2.getText();
		password=MD5.getMD5(password);
		
		OkHttpClient client=Server.getSharedClient();
		MultipartBody request=new MultipartBody.Builder()
				.addFormDataPart("email", email)
		        .addFormDataPart("passwordHash", password)
		        .build();
		okhttp3.Request Request=Server.requestBuilderWithApi("/resetpassword")
				.method("post",null)
				.post(request)
				.build();
		client.newCall(Request).enqueue(new Callback() {
			
			@Override
			public void onResponse(final Call arg0, final Response arg1) throws IOException {
				// TODO Auto-generated method stub
				try{
					ObjectMapper opMapper =new ObjectMapper();
					final String responseBody = arg1.body().string();
					final Boolean succeed = opMapper.readValue(responseBody, Boolean.class);
					final String arg="succeed:"+(succeed?"true":"false")+" body:"+responseBody;
                    runOnUiThread(new Runnable() {
						
						@Override
						public void run() {
							if(succeed){
								ForgotpasswordActivity.this.onResponse(arg0,arg);
							}else{
								ForgotpasswordActivity.this.onFailure(arg0, new Exception("server said false"));
							}
						}
					});
				}catch (final Exception e) {
					// TODO: handle exception
                        runOnUiThread(new Runnable() {
						
						@Override
						public void run() {
							// TODO Auto-generated method stub
							ForgotpasswordActivity.this.onFailure(arg0, e);
						}
					});
				}
			}
			
			@Override
			public void onFailure(final Call arg0, final IOException arg1) {
				// TODO Auto-generated method stub
				ForgotpasswordActivity.this.runOnUiThread(new Runnable() {

					@Override
					public void run() {
						// TODO Auto-generated method stub
						ForgotpasswordActivity.this.onFailure(arg0, arg1);
					}
				});
			}
		});
	}
	protected void onResponse(Call arg0, String response) {
		// TODO Auto-generated method stub
		new AlertDialog.Builder(ForgotpasswordActivity.this)
		.setTitle("修改成功")
		.setMessage(response)
		.setPositiveButton("确认",new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				Intent intent=new Intent(ForgotpasswordActivity.this,LoginActitvity.class);
				startActivity(intent);
			}
		})
		.show();
		
	}
	void onFailure(Call arg0, Exception arg1){
		new AlertDialog.Builder(ForgotpasswordActivity.this)
		.setTitle("修改失败")
		.setMessage(arg1.getLocalizedMessage())
		.setPositiveButton("确认", null)
		.show();
	}
	
}
