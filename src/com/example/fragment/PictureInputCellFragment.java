package com.example.fragment;

import com.example.helloworld.R;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Fragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

public class PictureInputCellFragment extends Fragment {
	final int REQUEST_CAMERA = 1;
	final int REQUEST_ALBUM = 2;
	ImageView image;
	TextView label;
	TextView hint;

	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View view = inflater.inflate(R.layout.fragmen_input_picture, container);
		label = (TextView) view.findViewById(R.id.label);
		hint = (TextView) view.findViewById(R.id.hint);
		image = (ImageView) view.findViewById(R.id.image);
		image.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				onImageViewClicked();
			}
			void onImageViewClicked(){
				String[] items = new String[] { "≈ƒ’’", "œ‡≤·" };
				new AlertDialog.Builder(getActivity()).setTitle(label.getText())
						.setItems(items, new android.content.DialogInterface.OnClickListener() {

							public void onClick(DialogInterface dialog, int which) {
								// TODO Auto-generated method stub
								switch (which) {
								case 0:
									takePhoto();
									break;
								case 1:
									pickFromAlbum();
								default:
									break;
								}
							}
						}).setNegativeButton("»°œ˚", null).show();
			}

			void takePhoto() {
				Intent itnt = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
				startActivityForResult(itnt, REQUEST_CAMERA);
			}

			void pickFromAlbum() {
				Intent itnt = new Intent(Intent.ACTION_GET_CONTENT);
				itnt.setType("image/*");
				startActivityForResult(itnt, REQUEST_ALBUM);
			}
		});
		return view;
	}

	public void onActivtyForResult(int requestCode, int resultCode, Intent data) {
		if (requestCode == Activity.RESULT_CANCELED)
			return;
		if (requestCode == REQUEST_CAMERA) {
			Bitmap bmp = (Bitmap) data.getExtras().get("data");
			image.setImageBitmap(bmp);
		} else if (requestCode == REQUEST_ALBUM) {
			try {
				Bitmap bmp = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), data.getData());
				image.setImageBitmap(bmp);
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}
		}
	}

	public void setLabelText(String labelText) {
		this.label.setText(labelText);
	}

	public void setHintText(String hintText) {
		this.hint.setHint(hintText);
	}
}
