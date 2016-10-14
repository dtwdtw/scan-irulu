package com.wanfang.HttpUtils;

import com.Login.UserLogin.R;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

/**
 * 广播接收器
 * @author yizhao
 *
 */
public class MyReceiver extends BroadcastReceiver{
     private static final String tag="MyReceiver";

	@Override
	public void onReceive(Context arg0, Intent arg1) {
		// TODO Auto-generated method stub
		String action=arg1.getAction();
		if(action.equals("COM_WANFANG_REFUSE_SUCCESS")){
			int flag=arg1.getIntExtra("ReturnSuccessCode", 0);
			if(flag==1){
				Toast.makeText(arg0, R.string.refuse_success_tip, Toast.LENGTH_SHORT).show();
			}else{
				Toast.makeText(arg0, R.string.refuse_fail_tip, Toast.LENGTH_SHORT).show();
			}
			Toast.makeText(arg0, arg1.getStringExtra("message"), Toast.LENGTH_SHORT).show();
		}else if(action.equals("COM_WANFANG_PASS_SUCCESS")){
			int flag=arg1.getIntExtra("ReturnSuccessCode", 0);
			if(flag==1){
				Toast.makeText(arg0, R.string.review_success_tip, Toast.LENGTH_SHORT).show();
			}else{
				Toast.makeText(arg0, R.string.review_fail_tip, Toast.LENGTH_SHORT).show();
			}
		}else if(action.equals("COM_WANFANG_PASS_FAIL")){
		    String result=arg1.getStringExtra("FailResult");
		    Toast.makeText(arg0, result, Toast.LENGTH_SHORT).show();
		}else if(action.equals("COM_WANFANG_REFUSE_FAIL")){
			
		}
	}
     
}
