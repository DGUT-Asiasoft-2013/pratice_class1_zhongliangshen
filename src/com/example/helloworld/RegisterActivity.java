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
	SimpleTextInputCellFragment fragInputCellemail;
@Override
protected void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);
	setContentView(R.layout.activity_register);
	fragpictrueInputCellimage=(PictureInputCellFragment)getFragmentManager().findFragmentById(R.id.frg_image);
	fragInputCellemail=(SimpleTextInputCellFragment)getFragmentManager().findFragmentById(R.id.email);
	fragInputCellAccount=(SimpleTextInputCellFragment)getFragmentManager().findFragmentById(R.id.account);
	fragInputCellPassword=(SimpleTextInputCellFragment)getFragmentManager().findFragmentById(R.id.password);
	fragInputCellPasswordre=(SimpleTextInputCellFragment)getFragmentManager().findFragmentById(R.id.password_re);
}
public void onResume(){
	super.onResume();
	fragInputCellAccount.setLabelText("用户名");
	fragInputCellAccount.setHintText("请输入用户名");
	fragInputCellemail.setLabelText("邮箱");
	fragInputCellemail.setHintText("请输入邮箱");
	fragInputCellPassword.setLabelText("密码");
	fragInputCellPassword.setHintText("请输入密码");
	fragInputCellPassword.setIsPassword(true);
	fragInputCellPasswordre.setLabelText("确认密码");
	fragInputCellPasswordre.setHintText("请再次输入密码");
	fragInputCellPasswordre.setIsPassword(true);
}
}
