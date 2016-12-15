package com.example.helloworld;

import java.io.IOException;
import java.util.List;

import com.example.fragment.FeedsFragment;
import com.example.helloworld.api.Server;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Button;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MultipartBody;
import okhttp3.Request;
import okhttp3.Response;

public class FeedsActivity extends Activity{
	TextView text;
	TextView title;
	TextView name;
	AvatarView avatar;
	Button btn_comment;
	Button btnLikes;
	Integer article_id;

	ListView feedlist;
	List<Comment> data;
	int page=0;
	View btnLoadMore;
	TextView textLoadMore;

	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_feeds);

		text=(TextView)findViewById(R.id.text);
		title=(TextView)findViewById(R.id.title);
		name=(TextView)findViewById(R.id.name);
		btn_comment=(Button) findViewById(R.id.btn_comment);
		btnLikes=(Button) findViewById(R.id.btn_like);
		avatar=(AvatarView) findViewById(R.id.avatar);

		btnLoadMore= LayoutInflater.from(this).inflate(R.layout.load_more_btn, null);
		textLoadMore=(TextView) btnLoadMore.findViewById(R.id.text);

		String text1=(String) getIntent().getExtras().get("text");
		text.setText(text1);
		String title1=(String) getIntent().getExtras().get("title");
		title.setText(title1);
		String author=(String) getIntent().getExtras().get("authorname");
		name.setText(author);
		article_id=(Integer) getIntent().getExtras().get("article_id");

		btn_comment.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				onClicked();
			}
		});
		btnLikes.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				toggleLikes();
			}
		});

		feedlist=(ListView)findViewById(R.id.list);
		feedlist.addFooterView(btnLoadMore);
		feedlist.setAdapter(listAdapter);

		btnLoadMore.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				loadMore();
			}
		});

	}

	void loadMore() {
		// TODO Auto-generated method stub
		btnLoadMore.setEnabled(false);
		textLoadMore.setText("载入中...");

		Request request=Server.requestBuilderWithApi("/article/"+article_id+"/comment/"+(page+1))
				.get()
				.build();
		Server.getSharedClient().newCall(request).enqueue(new Callback() {

			@Override
			public void onResponse(Call arg0, Response arg1) throws IOException {
				// TODO Auto-generated method stub
				runOnUiThread(new Runnable() {

					@Override
					public void run() {
						// TODO Auto-generated method stub
						btnLoadMore.setEnabled(true);
						textLoadMore.setText("加载更多");
					}
				});
				try{
					Page<Comment> comments=new ObjectMapper().readValue(arg1.body().string(), new TypeReference<Page<Comment>>(){});
					if(comments.getNumber()>page){
						if(data==null){
							data=comments.getContent();
						}else{
							data.addAll(comments.getContent());
						}
						page=comments.getNumber();

						runOnUiThread(new Runnable() {

							@Override
							public void run() {
								// TODO Auto-generated method stub
								listAdapter.notifyDataSetInvalidated();
							}
						});
					}
				}catch (Exception e) {
					// TODO: handle exception
					e.printStackTrace();
				}
			}

			@Override
			public void onFailure(Call arg0, IOException arg1) {
				// TODO Auto-generated method stub

			}
		});
	}

	void onClicked() {
		// TODO Auto-generated method stub
		Intent intn=new Intent(FeedsActivity.this,CommentActivity.class);
		intn.putExtra("data", article_id);
		startActivity(intn);
		overridePendingTransition(R.anim.slide_in_bottom,0);
	}
	void isLiked(){
		Request request =Server.requestBuilderWithApi("/article/"+article_id+"/likes")
				.get()
				.build();
	}

	BaseAdapter listAdapter=new BaseAdapter() {

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			View view = null;

			if(convertView==null){
				LayoutInflater inflater = LayoutInflater.from(parent.getContext());
				view = inflater.inflate(R.layout.listview, null);	
			}else{
				view = convertView;
			}
			TextView text = (TextView) view.findViewById(R.id.title);
			TextView textDate = (TextView) view.findViewById(R.id.date);
			AvatarView avatar=(AvatarView) view.findViewById(R.id.avatar);

			Comment comment =data.get(position);
			text.setText(comment.getAuthor().getName()+":"+comment.getText());
			String dateStr=DateFormat.format("yyyy-mm-dd hh:mm", comment.getCreateDate()).toString();
			textDate.setText(dateStr);
			avatar.load(Server.serverAddress+comment.getAuthor().getAvatar());

			return view;
		}

		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return position;
		}

		@Override
		public Object getItem(int position) {
			// TODO Auto-generated method stub
			return data.get(position);
		}

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return data==null ? 0 : data.size();
		}
	};

	public void onResume(){
		// TODO Auto-generated method stub
		super.onResume();
		reload();
	}
	void reload(){

		reloadLikes();
		checkLiked();

		Request request =Server.requestBuilderWithApi("/article/"+article_id+"/comment")
				.get()
				.build();

		Server.getSharedClient().newCall(request).enqueue(new Callback() {

			@Override
			public void onResponse(Call arg0, Response arg1) throws IOException {
				// TODO Auto-generated method stub
				try{
					final Page<Comment> data=new ObjectMapper()
							.readValue(arg1.body().string(),
									new TypeReference<Page<Comment>>() {} );
					runOnUiThread(new Runnable() {

						@Override
						public void run() {
							FeedsActivity.this.page=data.getNumber();
							FeedsActivity.this.data=data.getContent();
							// TODO Auto-generated method stub
							listAdapter.notifyDataSetInvalidated();
						}
					});
				}catch (final Exception e) {
					// TODO: handle exception
					runOnUiThread(new Runnable() {

						@Override
						public void run() {
							// TODO Auto-generated method stub
							new AlertDialog.Builder(FeedsActivity.this)
							.setMessage(e.getMessage())
							.setPositiveButton("ok", null)
							.show();
						}
					});
				}
			}

			@Override
			public void onFailure(Call arg0, IOException arg1) {
				// TODO Auto-generated method stub

			}
		});
	}

	private boolean isLiked;

	void checkLiked(){
		Request request = Server.requestBuilderWithApi("/article/"+article_id+"/isliked").get().build();
		Server.getSharedClient().newCall(request).enqueue(new Callback() {
			@Override
			public void onResponse(Call arg0, Response arg1) throws IOException {
				try{
					final String responseString = arg1.body().string();
					final Boolean result = new ObjectMapper().readValue(responseString, Boolean.class);

					runOnUiThread(new Runnable() {
						@Override
						public void run() {
							onCheckLikedResult(result);
						}
					});
				}catch(final Exception e){
					e.printStackTrace();
					runOnUiThread(new Runnable() {
						@Override
						public void run() {
							onCheckLikedResult(false);
						}
					});
				}
			}

			@Override
			public void onFailure(Call arg0, IOException e) {
				e.printStackTrace();
				runOnUiThread(new Runnable() {
					@Override
					public void run() {
						onCheckLikedResult(false);
					}
				});				
			}
		});
	}

	void onCheckLikedResult(boolean result){
		isLiked = result;
		btnLikes.setTextColor(result ? Color.BLUE : Color.BLACK);
	}

	void reloadLikes(){
		Request request = Server.requestBuilderWithApi("/article/"+article_id+"/likes")
				.get().build();

		Server.getSharedClient().newCall(request).enqueue(new Callback() {

			@Override
			public void onResponse(Call arg0, Response arg1) throws IOException {
				try{
					String responseString = arg1.body().string();
					final Integer count = new ObjectMapper().readValue(responseString, Integer.class);

					runOnUiThread(new Runnable() {
						@Override
						public void run() {
							onReloadLikesResult(count);
						}
					});
				}catch (Exception e) {
					e.printStackTrace();
					runOnUiThread(new Runnable() {
						@Override
						public void run() {
							onReloadLikesResult(0);
						}
					});
				}
			}

			@Override
			public void onFailure(Call arg0, IOException e) {
				e.printStackTrace();
				runOnUiThread(new Runnable() {
					@Override
					public void run() {
						onReloadLikesResult(0);
					}
				});
			}
		});
	}

	void onReloadLikesResult(int count){
		if(count>0){
			btnLikes.setText("赞("+count+")");
		}else{
			btnLikes.setText("赞");
		}
	}

	void toggleLikes(){
		MultipartBody body = new MultipartBody.Builder()
				.addFormDataPart("likes", String.valueOf(!isLiked))
				.build(); 

		Request request = Server.requestBuilderWithApi("/article/"+article_id+"/likes")
				.post(body).build();

		Server.getSharedClient().newCall(request).enqueue(new Callback() {

			@Override
			public void onResponse(Call arg0, Response arg1) throws IOException {
				
				try{
					Log.d("responseBody", arg1.body().string());
				}catch (Exception e) {
					e.printStackTrace();
				}
				
				runOnUiThread(new Runnable() {
					public void run() {
						
						
						
						reload();
					}
				});
			}

			@Override
			public void onFailure(Call arg0, IOException arg1) {
				runOnUiThread(new Runnable() {
					public void run() {
						reload();
					}
				});
			}
		});
	}
}
