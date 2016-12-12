package com.example.fragment;

import com.example.fragment.ForGotPasswordFargment1.OnGoNextListener;
import com.example.helloworld.BootActivity;
import com.example.helloworld.R;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.Toast;

public class ForGotPasswordFragment2 extends Fragment {
	View view;
	SimpleTextInputCellFragment fragInputCellPassword;
	SimpleTextInputCellFragment fragInputCellPasswordre;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

		if(view==null){
			view = inflater.inflate(R.layout.fragment_forgot_password2, null);
			fragInputCellPassword=(SimpleTextInputCellFragment)getFragmentManager().findFragmentById(R.id.password);
			fragInputCellPasswordre=(SimpleTextInputCellFragment)getFragmentManager().findFragmentById(R.id.repassword);
			view.findViewById(R.id.btn_1).setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					onClicked();
				}
			});
		}
		return view;
	}
	public void onResume(){
		super.onResume();
		fragInputCellPassword.setLabelText("�����µ�����");
		fragInputCellPassword.setHintText("������������");
		fragInputCellPassword.setIsPassword(true);
		fragInputCellPasswordre.setLabelText("ȷ������");
		fragInputCellPasswordre.setHintText("���ٴ���������");
		fragInputCellPasswordre.setIsPassword(true);
	}
	public static interface OnGoClickedListener{
		void onGoClicked();
	}

	OnGoClickedListener onGoClickedListener;

	public void setOnGoClickedListener(OnGoClickedListener onGoClickedListener) {
		this.onGoClickedListener = onGoClickedListener;
	}

	void onClicked(){
		if(fragInputCellPassword.getText().equals(fragInputCellPasswordre.getText())){
			if(onGoClickedListener!=null){
				onGoClickedListener.onGoClicked();
			}
		}else{
			Toast.makeText(getActivity(), "�����������벻һ��" , Toast.LENGTH_LONG).show();
		}
		
	}
	public String getText() {
		// TODO Auto-generated method stub
		return fragInputCellPassword.getText();
	}
}
