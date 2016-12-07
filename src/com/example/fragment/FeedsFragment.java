package com.example.fragment; 

import com.example.helloworld.R;
import android.annotation.SuppressLint;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class FeedsFragment extends Fragment{
	View view;
	ListView feedlist;
	
@SuppressLint("InflateParams")
@Override

public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
	// TODO Auto-generated method stub
	if(view==null){
		view=inflater.inflate(R.layout.fragment_table_view1, null);
		feedlist=(ListView) view.findViewById(R.id.list);
		feedlist.setAdapter(listAdapter);
	}
	return view;
	}
BaseAdapter listAdapter=new BaseAdapter() {
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
			View view = null;
			
			if(convertView==null){
				LayoutInflater inflater = LayoutInflater.from(parent.getContext());
				view = inflater.inflate(android.R.layout.simple_list_item_1, null);	
			}else{
				view = convertView;
			}
			
			TextView text1 = (TextView) view.findViewById(android.R.id.text1);
			text1.setText("THIS IS ROW "+position);
		return view;
	}
	
	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return 0;
	}
	
	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return 20;
	}
};
}
