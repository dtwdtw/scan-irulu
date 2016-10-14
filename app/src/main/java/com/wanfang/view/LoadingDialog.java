package com.wanfang.view;

import com.Login.UserLogin.R;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.widget.TextView;


public class LoadingDialog extends Dialog
{
	private RotateImage	mRotateImage;

	private TextView mContentText;

	private int mStringId;

	public LoadingDialog(Context context, int theme)
	{
		super(context, theme);
	}

	public LoadingDialog(Context context, int theme, int string)
	{
		super(context, theme);
		mStringId = string;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.progress_dialog_layout);
		mRotateImage = (RotateImage) findViewById(R.id.rotate_bar);
		mRotateImage.start();
		mContentText = (TextView) findViewById(R.id.progress_content);
		if (mStringId > 0)
		{
			if(mStringId==1){
				mContentText.setText(R.string.login_tip);
			}else if(mStringId==2){
				mContentText.setText(R.string.get_data);
			}else if(mStringId==3){
				mContentText.setText(R.string.commit_request);
			}else if(mStringId==4){
				mContentText.setText(R.string.search_request);
			}else if(mStringId==5){
				mContentText.setText(R.string.refreshing);
			}
			
		}

	}

	public void stopRotate()
	{
		mRotateImage.stop();
	}

	@Override
	public void onAttachedToWindow()
	{
		mRotateImage.start();
		super.onAttachedToWindow();
	}

	public void setContentText(int stringId)
	{
		mContentText.setText(stringId);
	}

	public void setContentText(String string)
	{
		mContentText.setText(string);
	}

}
