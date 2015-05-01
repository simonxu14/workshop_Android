package com.itdog.activity;

import android.app.Activity;
import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.util.DisplayMetrics;
import android.util.Log;
import android.widget.ImageView;

public class ActionBar {
	private ImageView actionImage;
	private int screenWidth;
	private int imageWidth;
	private int offset;
	private Activity context;	

	public ActionBar(ImageView view, int imageId, Activity context) {
		actionImage = view;
		this.context = context;
		imageWidth = BitmapFactory.decodeResource(this.context.getResources(),
				imageId).getWidth();
		DisplayMetrics dm = new DisplayMetrics();
		context.getWindowManager().getDefaultDisplay().getMetrics(dm);
		screenWidth = dm.widthPixels;
		offset = (screenWidth / 4 - imageWidth) / 2;
	}

	public void Translate(float percent, int begin) {
				
		Matrix matrix = new Matrix();
		switch (begin) {
		case 0:
			matrix.setTranslate(offset, 0);
			break;
		case 1:
			matrix.setTranslate(offset*3 + imageWidth, 0);
			break;
		case 2:
			matrix.setTranslate(offset*5 + imageWidth*2, 0);
			break;
		case 3:
			matrix.setTranslate(offset*7 + imageWidth*3, 0);
			break;
		default:
			break;
		}
		float t = (this.offset * 2 + this.imageWidth) * percent;
		matrix.postTranslate(t, 0);
		actionImage.setImageMatrix(matrix);
		Log.i("Scrolled", "==="+t);
	}
}
