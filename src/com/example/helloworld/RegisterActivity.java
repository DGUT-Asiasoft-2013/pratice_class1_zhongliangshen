package com.example.helloworld;

import com.example.fragment.PictureInputCellFragment;
import com.example.fragment.SimpleTextInputCellFragment;

import android.app.Activity;
import android.os.Bundle;

public class RegisterActivity extends Activity{
	SimpleTextInputCellFragment fragInputCellAccount;
	SimpleTextInputCellFragment fragInputCellPassword;
	SimpleTextInputCellFragment fragInputCellPasswordre;
	PictureInputCellFragment fragpictrueInputCellimage;
@Override
protected void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);
	setContentView(R.layout.activity_register);
	fragpictrueInputCellimage=(PictureInputCellFragment)getFragmentManager().findFragmentById(R.id.frg_image);
	fragInputCellAccount=(SimpleTextInputCellFragment)getFragmentManager().findFragmentById(R.id.account);
	fragInputCellPassword=(SimpleTextInputCellFragment)getFragmentManager().findFragmentById(R.id.password);
	fragInputCellPasswordre=(SimpleTextInputCellFragment)getFragmentManager().findFragmentById(R.id.password_re);
}
public void onResume(){
	super.onResume();
	fragInputCellAccount.setLabelText("�û���");
	fragInputCellAccount.setHintText("�������û���");
	fragInputCellPassword.setLabelText("����");
	fragInputCellPassword.setHintText("����������");
	fragInputCellPassword.setIsPassword(true);
	fragInputCellPasswordre.setLabelText("ȷ������");
	fragInputCellPasswordre.setHintText("���ٴ���������");
	fragInputCellPasswordre.setIsPassword(true);
}
}
