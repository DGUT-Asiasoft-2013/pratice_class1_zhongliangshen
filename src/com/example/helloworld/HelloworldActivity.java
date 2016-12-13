package com.example.helloworld;

import com.example.fragment.FeedsFragment;
import com.example.fragment.MainTableFragment;
import com.example.fragment.MainTableFragment.OnSelectedListener;
import com.example.fragment.MyFragment;
import com.example.fragment.NotesFragment;
import com.example.fragment.SearchFragment;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;

public class HelloworldActivity extends Activity{
	FeedsFragment feedspage=new FeedsFragment();
	NotesFragment notespage=new NotesFragment();
	SearchFragment searchspage=new SearchFragment();
	MyFragment mypage=new MyFragment();
	MainTableFragment tab;
@Override
protected void onCreate(Bundle savedInstanceState) {
	// TODO Auto-generated method stub
	super.onCreate(savedInstanceState);
	setContentView(R.layout.activity_helloworld);
	tab=(MainTableFragment) getFragmentManager().findFragmentById(R.id.tab);
	tab.OnSelectedListener(new OnSelectedListener() {
		
		public void OnGoTabClicked(int index) {
			// TODO Auto-generated method stub
			onChange(index);
		}
	});
	
	
}
public void onResume(){
	super.onResume();
	if (tab.getSelectedIndex()<0){
	tab.setSelectedItem(0);
	}
}
   void onChange(int index) {
	// TODO Auto-generated method stub
	Fragment newfrag=null;
	switch (index) {
	case 0:newfrag=feedspage; break;
	case 1:newfrag=notespage; break;
	case 2:newfrag=searchspage; break;
	case 3:newfrag=mypage; break;
	default:break;
	}
	if(newfrag==null)return;
	getFragmentManager()
	.beginTransaction()
	.replace(R.id.content, newfrag)
	.commit();
   }
}
