package com.example.fragment;

import com.example.helloworld.R;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

public class PictureInputCellFragment extends Fragment {

ImageView image;
TextView label;
TextView hint;
public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
	// TODO Auto-generated method stub
	View view=inflater.inflate(R.layout.fragmen_input_picture, container);
	label=(TextView)view.findViewById(R.id.label);
	hint=(TextView)view.findViewById(R.id.hint);
	image=(ImageView)view.findViewById(R.id.image);
	
	return view;
}
public void setLabelText(String labelText){
	this.label.setText(labelText);
}
public void setHintText(String hintText){
	this.hint.setHint(hintText);
}
}
