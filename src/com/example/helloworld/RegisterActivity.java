package com.example.helloworld;

import java.io.IOException;

import com.example.fragment.PictureInputCellFragment;
import com.example.fragment.SimpleTextInputCellFragment;
import com.example.helloworld.api.Server;

import android.R.fraction;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DownloadManager.Request;
import android.content.DialogInterface;
import android.content.Intent;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.Toast;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import okhttp3.Response;
import okio.BufferedSink;

public class RegisterActivity extends Activity{
	SimpleTextInputCellFragment fragInputCellAccount;
	SimpleTextInputCellFragment fragInputCellname;
	SimpleTextInputCellFragment fragInputCellPassword;
	SimpleTextInputCellFragment fragInputCellPasswordre;
	PictureInputCellFragment fragpictrueInputCellimage;
	SimpleTextInputCellFragment fragInputCellemail;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_register);
		fragpictrueInputCellimage=(PictureInputCellFragment)getFragmentManager().findFragmentById(R.id.frg_image);
		fragInputCellemail=(SimpleTextInputCellFragment)getFragmentManager().findFragmentById(R.id.email);
		fragInputCellAccount=(SimpleTextInputCellFragment)getFragmentManager().findFragmentById(R.id.account);
		fragInputCellname=(SimpleTextInputCellFragment)getFragmentManager().findFragmentById(R.id.name);
		fragInputCellPassword=(SimpleTextInputCellFragment)getFragmentManager().findFragmentById(R.id.password);
		fragInputCellPasswordre=(SimpleTextInputCellFragment)getFragmentManager().findFragmentById(R.id.password_re);

		findViewById(R.id.btn_submit).setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				submit();
			}
		});
	}
	public void onResume(){
		super.onResume();
		fragInputCellAccount.setLabelText("�û���");
		fragInputCellAccount.setHintText("�������û���");
		fragInputCellname.setLabelText("�ǳ�");
		fragInputCellname.setHintText("�������ǳ�");
		fragInputCellemail.setLabelText("����");
		fragInputCellemail.setHintText("����������");
		fragInputCellPassword.setLabelText("����");
		fragInputCellPassword.setHintText("����������");
		fragInputCellPassword.setIsPassword(true);
		fragInputCellPasswordre.setLabelText("ȷ������");
		fragInputCellPasswordre.setHintText("���ٴ���������");
		fragInputCellPasswordre.setIsPassword(true);
	}

	void submit(){

		String account=fragInputCellAccount.getText();
		String name=fragInputCellname.getText();
		String email=fragInputCellemail.getText();
		String password=fragInputCellPassword.getText();
		String passwordre=fragInputCellPasswordre.getText();

		if(!password.equals(passwordre)){
			Toast.makeText(RegisterActivity.this, "�����������벻��ͬ��������" , Toast.LENGTH_LONG).show();
			return;
		}
		password=MD5.getMD5(password);
		

		OkHttpClient client=new OkHttpClient();
		
		MultipartBody.Builder requestBodyBuilder = new MultipartBody.Builder()
				.addFormDataPart("account", account)
				.addFormDataPart("name", name)
				.addFormDataPart("email", email)
				.addFormDataPart("passwordHash", password);
		
		byte[] pngData = fragpictrueInputCellimage.getPngData();
		if(pngData != null){
			RequestBody pndDataBody = RequestBody.create(MediaType.parse("image/png"), pngData);
			requestBodyBuilder.addFormDataPart("avatar", "avatar.png", pndDataBody);
		}
		
		MultipartBody postBody = requestBodyBuilder.build();
		okhttp3.Request Request= Server.requestBuilderWithApi("/register")
				.method("post",null)
				.post(postBody)
				.build();
		final ProgressDialog progressdialog=new ProgressDialog(RegisterActivity.this);
		progressdialog.setMessage("ע����");
		progressdialog.setCancelable(false);
		progressdialog.setCanceledOnTouchOutside(false);

		client.newCall(Request).enqueue(new Callback() {

			@Override
			public void onResponse(final Call arg0, final Response arg1) throws IOException {
				// TODO Auto-generated method stub
				
				final String responseString = arg1.body().string();
				RegisterActivity.this.runOnUiThread(new Runnable() {

					@Override
					public void run() {
						progressdialog.dismiss();
						
						try{
							RegisterActivity.this.onResponse(arg0,responseString);
						}catch (Exception e) {
							// TODO: handle exception
							e.printStackTrace();
						}
					}
				});
			}

			@Override
			public void onFailure(final Call arg0, final IOException arg1) {
				// TODO Auto-generated method stub
				
				RegisterActivity.this.runOnUiThread(new Runnable() {

					@Override
					public void run() {
						progressdialog.dismiss();
						RegisterActivity.this.onFailure(arg0, arg1);
					}
				});
			}
		});
	}

	void onResponse(Call arg0, String response){
		new AlertDialog.Builder(RegisterActivity.this)
		.setTitle("ע��ɹ�")
		.setMessage(response)
		.setPositiveButton("ȷ��", new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				finish();
			}
		})
		.show();
	}
	void onFailure(Call arg0, IOException arg1){
		new AlertDialog.Builder(RegisterActivity.this)
		.setTitle("ע��ʧ��")
		.setMessage(arg1.getLocalizedMessage())
		.setPositiveButton("ȷ��", null)
		.show();
	}
}
