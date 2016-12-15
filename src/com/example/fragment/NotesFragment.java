package com.example.fragment;

import java.io.IOException;
import java.util.List;

import com.example.helloworld.Article;
import com.example.helloworld.AvatarView;
import com.example.helloworld.Comment;
import com.example.helloworld.Page;
import com.example.helloworld.R;
import com.example.helloworld.User;
import com.example.helloworld.api.Server;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import android.app.AlertDialog;
import android.app.Fragment;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Request;
import okhttp3.Response;

public class NotesFragment extends Fragment{
	View view;
	ListView notelist;
	List<Comment> data;
	int page=0;
	View btnLoadMore;
	TextView textLoadMore;
	@Override

	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		if(view==null){
			view=inflater.inflate(R.layout.fragment_table_view2, null);
			btnLoadMore=inflater.inflate(R.layout.load_more_btn,null);
			textLoadMore=(TextView) btnLoadMore.findViewById(R.id.text);
			notelist=(ListView) view.findViewById(R.id.list);
			
			notelist.addFooterView(btnLoadMore);
			notelist.setAdapter(listAdapter);
			
			btnLoadMore.setOnClickListener(new View.OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					loadMore();
				}
			});
		}
		return view;
	}
	
	void loadMore() {
		// TODO Auto-generated method stub
		btnLoadMore.setEnabled(false);
		textLoadMore.setText("载入中...");

		Request request=Server.requestBuilderWithApi("/user/author_id/receivedcomment/"+(page+1))
				.get()
				.build();
		Server.getSharedClient().newCall(request).enqueue(new Callback() {

			@Override
			public void onResponse(Call arg0, Response arg1) throws IOException {
				// TODO Auto-generated method stub
				getActivity().runOnUiThread(new Runnable() {

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

						getActivity().runOnUiThread(new Runnable() {

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
	public void onResume(){
		// TODO Auto-generated method stub
		super.onResume();
		reload();
	}
	
	void reload(){
		
		Request request =Server.requestBuilderWithApi("/user/author_id/receivedcomment")
				.get()
				.build();

		Server.getSharedClient().newCall(request).enqueue(new Callback() {

			@Override
			public void onResponse(Call arg0, Response arg1) throws IOException {
				// TODO Auto-generated method stub
				try{
					final Page<Comment> data=new ObjectMapper()
							.readValue(arg1.body().string(),
									new TypeReference<Page<Comment>>() {} );;
					
					getActivity().runOnUiThread(new Runnable() {

						@Override
						public void run() {
							// TODO Auto-generated method stub
							NotesFragment.this.page=data.getNumber();
							NotesFragment.this.data=data.getContent();
							listAdapter.notifyDataSetInvalidated();
						}
					});
				}catch (final Exception e) {
					// TODO: handle exception
					getActivity().runOnUiThread(new Runnable() {

						@Override
						public void run() {
							// TODO Auto-generated method stub
							new AlertDialog.Builder(getActivity())
							.setMessage(e.getMessage())
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
}
