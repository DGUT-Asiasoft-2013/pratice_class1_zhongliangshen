package com.example.fragment;

import java.io.IOException;
import java.util.List;

import com.example.helloworld.Article;
import com.example.helloworld.AvatarView;
import com.example.helloworld.FeedsActivity;
import com.example.helloworld.Page;
import com.example.helloworld.R;
import com.example.helloworld.api.Server;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import android.app.AlertDialog;
import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Request;
import okhttp3.Response;

public class SearchFragment extends Fragment {
	View view;
	ListView searchlist;
	List<Article> data;
	int page=0;
	View btnLoadMore;
	TextView textLoadMore;
	
	Button btn_search;
	EditText edit_search;
	
	@Override

	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		if(view==null){
			view=inflater.inflate(R.layout.fragment_table_view3, null);
			btnLoadMore=inflater.inflate(R.layout.load_more_btn,null);
			textLoadMore=(TextView) btnLoadMore.findViewById(R.id.text);
			searchlist=(ListView) view.findViewById(R.id.list);
			btn_search=(Button)view.findViewById(R.id.btn_search);
			edit_search=(EditText)view.findViewById(R.id.edit_search);
			
			searchlist.addFooterView(btnLoadMore);
			searchlist.setAdapter(listAdapter);
			searchlist.setOnItemClickListener(new OnItemClickListener() {

				@Override
				public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
					// TODO Auto-generated method stub
					listClicked(position);
				}
			});
			btn_search.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					onClicked();
				}
			});
		}
		return view;
	}
	void onClicked() {
		
		InputMethodManager inputMethodManager = 
				(InputMethodManager)getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
				inputMethodManager.hideSoftInputFromWindow(edit_search.getWindowToken(), 0);
				
		String keyword=edit_search.getText().toString();
		Request request =Server.requestBuilderWithApi("/article/s/"+keyword)
				.get()
				.build();

		Server.getSharedClient().newCall(request).enqueue(new Callback() {

			@Override
			public void onResponse(Call arg0, Response arg1) throws IOException {
				// TODO Auto-generated method stub
				try{
					final Page<Article> data=new ObjectMapper()
							.readValue(arg1.body().string(),new TypeReference<Page<Article>>() {} );
					getActivity().runOnUiThread(new Runnable() {

						@Override
						public void run() {
							// TODO Auto-generated method stub
							SearchFragment.this.page=data.getNumber();
							SearchFragment.this.data=data.getContent();
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
	void listClicked(int position) {
		Article article= data.get(position);
		Intent intn=new Intent(getActivity(),FeedsActivity.class);
		intn.putExtra("text", article.getText()); 
		intn.putExtra("title", article.getTitle());
		intn.putExtra("authoravatar", article.getGetAuthorAvatar());
		intn.putExtra("authorname", article.getGetAuthorName());
		intn.putExtra("article_id",article.getId());

		startActivity(intn);
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

			TextView textTitle = (TextView) view.findViewById(R.id.title);
			TextView textDate = (TextView) view.findViewById(R.id.date);
			AvatarView avatar=(AvatarView) view.findViewById(R.id.avatar);

			Article article =data.get(position);

			avatar.load(Server.serverAddress+article.getGetAuthorAvatar());
			String dateStr=DateFormat.format("yyyy-mm-dd hh:mm", article.getCreateDate()).toString();
			textDate.setText(dateStr);
			textTitle.setText(article.getGetAuthorName()+":"+article.getTitle());
			

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
