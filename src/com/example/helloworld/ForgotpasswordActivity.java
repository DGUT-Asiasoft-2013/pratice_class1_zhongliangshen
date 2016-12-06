package com.example.helloworld;

import com.example.fragment.ForGotPasswordFargment1;
import com.example.fragment.ForGotPasswordFargment1.OnGoNextListener;
import com.example.fragment.ForGotPasswordFragment2;

import android.app.Activity;
import android.os.Bundle;

public class ForgotpasswordActivity extends Activity{
	ForGotPasswordFargment1 step1=new ForGotPasswordFargment1();
	ForGotPasswordFragment2 step2=new ForGotPasswordFragment2();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_forgot_password);

		step1.setOnGoNextListener(new OnGoNextListener() {

			@Override
			public void onGoNext() {
				goStep2();
			}
		});
		
		getFragmentManager().beginTransaction().replace(R.id.container, step1).commit();
	}

	void goStep2(){
		getFragmentManager()
		.beginTransaction()
		.setCustomAnimations(
				R.animator.slide_in_right,
				R.animator.slide_out_left,
				R.animator.slide_in_left,
				R.animator.slide_out_right)
		.replace(R.id.container, step2)		
		.addToBackStack(null)
		.commit();
	}
}
