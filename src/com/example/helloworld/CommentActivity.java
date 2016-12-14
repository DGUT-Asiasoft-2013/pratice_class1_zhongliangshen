package com.example.helloworld;

import java.io.IOException;

import com.example.helloworld.api.Server;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.Toast;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Response;

public class CommentActivity extends Activity{
	EditText comment;
	@Override

	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_comment);
		comment=(EditText)findViewById(R.id.edit_comment);
		findViewById(R.id.btn).setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				clicked();
			}
		});
	}
	void clicked() {
		// TODO Auto-generated method stub
		String text=CommentActivity.this.comment.getText().toString();
		Integer article_id=(Integer) getIntent().getExtras().get("data");
		OkHttpClient client=Server.getSharedClient();
		MultipartBody request=new MultipartBody.Builder()
				.addFormDataPart("text", text)
				.build();
		okhttp3.Request Request=Server.requestBuilderWithApi("/article/"+article_id+"/comments")
				.method("post",null)
				.post(request)
				.build();

		client.newCall(Request).enqueue(new Callback() {

			@Override
			public void onResponse(final Call arg0, Response arg1) throws IOException {
				// TODO Auto-generated method stub
				try{
					final String arg = arg1.body().string();
					CommentActivity.this.runOnUiThread(new Runnable() {

						@Override
						public void run() {
							// TODO Auto-generated method stub
							CommentActivity.this.onResponse(arg0,arg);
						}
					});
				}catch (Exception e) {
					// TODO: handle exception
					e.printStackTrace();
				}
			}

			@Override
			public void onFailure(Call arg0, IOException arg1) {
				// TODO Auto-generated method stub
				Toast.makeText(CommentActivity.this, arg1.getLocalizedMessage() , Toast.LENGTH_LONG).show();
			}
		});

	}
	void onResponse(Call arg0, String response) {
		// TODO Auto-generated method stub
		new AlertDialog.Builder(CommentActivity.this)
		.setMessage(response)
		.setPositiveButton("х╥хо",new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				finish();
				overridePendingTransition(R.anim.slide_out_bottom,0);
			}
		})
		.show();
	}

}
