package com.wanfang.Activity;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;

import com.Login.UserLogin.R;
import com.wanfang.Activity.ReviewActivity.GetPurchPlanDetailInfoHandler;
import com.wanfang.HttpUtils.HttpUtils;
import com.wanfang.HttpUtils.JsonUtils;
import com.wanfang.JavaBean.PlanLogInfo;
import com.wanfang.Util.Const;
import com.wanfang.application.myApplication;
import com.wanfang.view.LoadingDialog;

public class PassReviewActivity extends Activity implements OnClickListener{
	Button pass_btn,cancle_btn;
	String UserName,TransNo;
	int ReturnSuccessCode=-1;
	myApplication app;
	private List<Map<String, String>> returnValue;
	List<NameValuePair> params=new ArrayList<NameValuePair>();
	private LoadingDialog proDialog;
	@Override
    protected void onCreate(Bundle savedInstanceState) { 
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.pass_review_layout);
        init();
      //  registerBroadCast();
   }
	
	public void init(){
		app=(myApplication) this.getApplication();
		Intent intent=this.getIntent();
		Bundle bundle=intent.getExtras();
		UserName=bundle.getString("UserName");
		TransNo=bundle.getString("TransNo");
		pass_btn=(Button) findViewById(R.id.pass_button);
		pass_btn.setOnClickListener(this);
		cancle_btn=(Button) findViewById(R.id.pass_cancle_button);
		cancle_btn.setOnClickListener(this);
	}
	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		switch (arg0.getId()) {
		case R.id.pass_button:
			Log.i("print", UserName+"::::"+TransNo);
			if(app.CheckNetWorkConnection(PassReviewActivity.this)){
				if(UserName!=null && TransNo!=null){
					proDialog = new LoadingDialog(PassReviewActivity.this, R.style.LoadingDialog,3);
					proDialog.setCancelable(false);
					proDialog.show();
					
					params.add(new BasicNameValuePair("user",UserName));
					params.add(new BasicNameValuePair("TransNo",TransNo));
					new Thread(new sendReviewRequestToServerThread()).start();
				}
			}else{
				myHandler.sendEmptyMessage(Const.NETWORK_ERROR);
			}
			
			break;
        case R.id.pass_cancle_button:
			finish();
			break;
		default:
			break;
		}
	}
	 class sendReviewRequestToServerThread implements Runnable{

			@Override
			public void run() {
				// TODO Auto-generated method stub
				String url="http://192.168.1.79:8088/app/AuditPurchasePlan";
				try {
					HttpUtils.doPostForReview(url, params,myHandler);
					

				} catch (Exception e) {
					// TODO Auto-generated catch block
					if(proDialog!=null){
						proDialog.dismiss();
					}
					e.printStackTrace();
				}
			}
	    }
	 
	 Handler myHandler=new Handler(){
	    	public void handleMessage(Message msg){
	    		switch(msg.what){
	    		case Const.REVIEW_REQUEST_SUCCESS:
	    		returnValue=JsonUtils.AnalysisForReturnValue(msg.getData().getString("result"));
				ReturnSuccessCode=Integer.parseInt(returnValue.get(0).get("success"));
				Log.i("print", "ReturnSuccessCode==>"+ReturnSuccessCode);
				Bundle bundle=new Bundle();
				bundle.putInt("ReturnSuccessCode", ReturnSuccessCode);
				Intent intent=new Intent();
				intent.setFlags(Const.REVIEW_SUCCESS_FLAG);
				intent.putExtras(bundle);
				setResult(RESULT_OK, intent);
				if(proDialog!=null){
					proDialog.dismiss();
				}
				finish();
	    		break;
	    		case Const.REVIEW_REQUEST_FAIL:
	    		String result=msg.getData().getString("result");
	    		Bundle bd=new Bundle();
    		    bd.putString("result", result);
    		    Intent intent2=new Intent();
    		    intent2.setFlags(Const.REVIEW_FAIL_FLAG);
				intent2.putExtras(bd);
				setResult(RESULT_OK, intent2);
				if(proDialog!=null){
					proDialog.dismiss();
				}
				finish();
	    		break;
	    		case Const.NETWORK_ERROR:
	    			Toast.makeText(PassReviewActivity.this, R.string.network_error, Toast.LENGTH_SHORT).show();
	    			break;
	    		}
	    	}
	    };
}
