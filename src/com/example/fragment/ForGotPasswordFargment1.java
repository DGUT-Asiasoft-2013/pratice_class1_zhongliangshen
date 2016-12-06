package com.example.fragment;

import com.example.helloworld.R;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.Button;

public class ForGotPasswordFargment1 extends Fragment{
	SimpleTextInputCellFragment fragInputCellemail;
	View view;

	@Override

	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		if(view==null){
			view=inflater.inflate(R.layout.fragment_forgot_password1, null)
					;
			fragInputCellemail=(SimpleTextInputCellFragment)getFragmentManager().findFragmentById(R.id.email);
			view.findViewById(R.id.btn_1).setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					goNext();
				}
			});
		}
		return view;
	}

	public void onResume(){
		super.onResume();
		fragInputCellemail.setLabelText("����");
		fragInputCellemail.setHintText("����������");
	}
	public static interface OnGoNextListener{
		void onGoNext();
	}

	OnGoNextListener onGoNextListener;

	public void setOnGoNextListener(OnGoNextListener onGoNextListener) {
		this.onGoNextListener = onGoNextListener;
	}

	void goNext(){
		if(onGoNextListener!=null){
			onGoNextListener.onGoNext();
		}
	}
}
