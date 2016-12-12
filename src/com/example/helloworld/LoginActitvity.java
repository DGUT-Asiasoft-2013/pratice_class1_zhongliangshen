package com.example.helloworld;

import java.io.IOException;

import com.example.fragment.SimpleTextInputCellFragment;
import com.example.helloworld.api.Server;
import com.fasterxml.jackson.databind.ObjectMapper;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Response;

public class LoginActitvity extends Activity{
	Button btn;
	Button btn2;
	TextView btn_forgot_password;
	SimpleTextInputCellFragment fragInputCellAccount;
	SimpleTextInputCellFragment fragInputCellPassword;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login); 
		btn=(Button)findViewById(R.id.btn_1);
		btn2=(Button)findViewById(R.id.btn_2);
		btn_forgot_password=(TextView)findViewById(R.id.btn_forgot_password);
		btn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent=new Intent(LoginActitvity.this,RegisterActivity.class);
				startActivity(intent);
			}
		});
		btn2.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				onLogin();
			}
		});
		btn_forgot_password.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent=new Intent(LoginActitvity.this,ForgotpasswordActivity.class);
				startActivity(intent);
			}
		});
		fragInputCellAccount=(SimpleTextInputCellFragment)getFragmentManager().findFragmentById(R.id.account);
		fragInputCellPassword=(SimpleTextInputCellFragment)getFragmentManager().findFragmentById(R.id.password);
	}
	public void onResume(){
		super.onResume();
		fragInputCellAccount.setLabelText("用户名");
		fragInputCellAccount.setHintText("请输入用户名");
		fragInputCellPassword.setLabelText("密码");
		fragInputCellPassword.setHintText("请输入密码");
		fragInputCellPassword.setIsPassword(true);
		}
	void onLogin(){
		String account=fragInputCellAccount.getText();
		String password=fragInputCellPassword.getText();
        password=MD5.getMD5(password);

		OkHttpClient client=Server.getSharedClient();
		MultipartBody request=new MultipartBody.Builder()
				.addFormDataPart("account", account)
		        .addFormDataPart("passwordHash", password)
		        .build();
		okhttp3.Request Request=Server.requestBuilderWithApi("/login")
				.method("post",null)
				.post(request)
				.build();
		client.newCall(Request).enqueue(new Callback() {

			@Override
			public void onResponse(final Call arg0, final Response arg1) throws IOException {
				try {
					ObjectMapper opMapper =new ObjectMapper();
					final User user= opMapper.readValue(arg1.body().string(), User.class);
					runOnUiThread(new Runnable() {
						
						@Override
						public void run() {
							// TODO Auto-generated method stub
							LoginActitvity.this.onResponse(arg0, user.account);
						}
					});
				} catch (final Exception e) {
					runOnUiThread(new Runnable() {
						
						@Override
						public void run() {
							// TODO Auto-generated method stub
							LoginActitvity.this.onFailure(arg0, e);
						}
					});
				}
				
				
			}

			@Override
			public void onFailure(final Call arg0, final IOException arg1) {
				// TODO Auto-generated method stub
				LoginActitvity.this.runOnUiThread(new Runnable() {

					@Override
					public void run() {
						// TODO Auto-generated method stub
						LoginActitvity.this.onFailure(arg0, arg1);
					}
				});
			}
		});
	}
	protected void onResponse(Call arg0, String response) {
		// TODO Auto-generated method stub
		new AlertDialog.Builder(LoginActitvity.this)
		.setTitle("登录成功")
		.setMessage("欢迎回来  "+response)
		.setPositiveButton("确认",new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				Intent intent=new Intent(LoginActitvity.this,HelloworldActivity.class);
				startActivity(intent);
			}
		})
		.show();
		
	}
	void onFailure(Call arg0, Exception arg1){
		new AlertDialog.Builder(LoginActitvity.this)
		.setTitle("登录失败")
		.setMessage(arg1.getLocalizedMessage())
		.setPositiveButton("确认", null)
		.show();
	}
}
