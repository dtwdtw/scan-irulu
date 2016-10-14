package com.wanfang.Activity;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import com.Login.UserLogin.R;
import com.wanfang.Activity.PassReviewActivity.sendReviewRequestToServerThread;
import com.wanfang.HttpUtils.HttpUtils;
import com.wanfang.HttpUtils.JsonUtils;
import com.wanfang.Util.Const;
import com.wanfang.application.myApplication;
import com.wanfang.view.LoadingDialog;

import android.app.Activity;
import android.content.DialogInterface;
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
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class RefuseActivity extends Activity implements OnClickListener{
	private Intent intent;
	private TextView TransNo,EditDate,EditUser,TotalAmount,DepartmentName,State;
	private myApplication app;
	private Button refuse_OK,refuse_cancle,return_btn;
	private String userName,receiverTransNo;
	private EditText editTextContent;
	private LoadingDialog proDialog;
	
	List<NameValuePair> params=new ArrayList<NameValuePair>();
	private List<Map<String, String>> returnValue;
	int ReturnSuccessCode=-1;
	 @Override
	    protected void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
	        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
	        setContentView(R.layout.refuse_activity_layout);
	        init();
	 }
	 
	 private void init(){
		 app=(myApplication) this.getApplication();
		 refuse_OK=(Button) findViewById(R.id.refuse_button);
		 refuse_OK.setOnClickListener(this);
		 refuse_cancle=(Button) findViewById(R.id.refuse_cancle_button);
		 refuse_cancle.setOnClickListener(this);
		 
		 TransNo=(TextView) findViewById(R.id.number_item);
		 EditDate=(TextView) findViewById(R.id.create_time_item);
		 EditUser=(TextView) findViewById(R.id.op_person_item);
		 TotalAmount=(TextView) findViewById(R.id.money_item);
		 DepartmentName=(TextView) findViewById(R.id.dept_name_item);
		 State=(TextView) findViewById(R.id.refuse_state);
		 intent=this.getIntent();
		 Bundle bundle=intent.getExtras();
		 
		 userName=bundle.getString("UserName");
		 receiverTransNo=bundle.getString("TransNo");
		 
		 TransNo.setText(bundle.getString("TransNo"));
		 EditDate.setText(bundle.getString("EditDate"));
		 EditUser.setText(bundle.getString("EditUser"));
		 TotalAmount.setText(bundle.getString("TotalAmount"));
		 State.setText(app.StatusValueToString(bundle.getInt("Status")));
		 DepartmentName.setText(bundle.getString("DepartmentName"));
		 
		 editTextContent=(EditText) findViewById(R.id.refuse_edit_input);
	 }

	 class sendRefuseRequestToServerThread implements Runnable{

			@Override
			public void run() {
				// TODO Auto-generated method stub
				String url="http://192.168.1.79:8088/app/RefusedPurchasePlan";
				try {
					HttpUtils.doPostForRefuse(url,params,myHandler);
					

				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					if(proDialog!=null){
						proDialog.dismiss();
					}
				}
			}
	    }
	 
	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		switch (arg0.getId()) {
		case R.id.refuse_button://提交拒绝
			Log.i("print", userName+"::::"+receiverTransNo);
			if(app.CheckNetWorkConnection(RefuseActivity.this)){
				if(userName!=null && receiverTransNo!=null){
					proDialog = new LoadingDialog(RefuseActivity.this, R.style.LoadingDialog,3);
					proDialog.setCancelable(false);
					proDialog.show();
					
					params.add(new BasicNameValuePair("user",userName));
					params.add(new BasicNameValuePair("TransNo",receiverTransNo));
					if(editTextContent.getText().toString().equals("") || editTextContent.getText().toString()==null){
						if(proDialog!=null){
							proDialog.dismiss();
						}
						Toast.makeText(RefuseActivity.this, R.string.refuse_cause_tip, Toast.LENGTH_SHORT).show();
					}else{
							params.add(new BasicNameValuePair("Refused",URLEncoder.encode(editTextContent.getText().toString())));
						new Thread(new sendRefuseRequestToServerThread()).start();
					}
					
				}
			}else{
               myHandler.sendEmptyMessage(Const.NETWORK_ERROR);
			}
		
			break;
		case R.id.refuse_cancle_button:
			finish();
			break;
		default:
			break;
		}
	}

	 Handler myHandler=new Handler(){
	    	public void handleMessage(Message msg){
	    		switch(msg.what){
	    		case Const.REFUSE_REQUEST_SUCCESS://请求成功
	    			returnValue=JsonUtils.AnalysisForReturnValue(msg.getData().getString("result"));
	    			ReturnSuccessCode=Integer.parseInt(returnValue.get(0).get("success"));
	    			Log.i("print", "refuse::::ReturnSuccessCode==>"+ReturnSuccessCode);
	    			Bundle bundle=new Bundle();
					bundle.putInt("ReturnSuccessCode", ReturnSuccessCode);
					Intent intent=new Intent();
					intent.setFlags(Const.REFUSE_REQUEST_SUCCESS_FLAG);
					intent.putExtras(bundle);
					setResult(RESULT_OK, intent);
					if(proDialog!=null){
						proDialog.dismiss();
					}
					finish();
	    			break;
	    		case Const.REFUSE_REQUES_FAIL:
	    			String result=msg.getData().getString("result");
		    		Bundle bd=new Bundle();
	    		    bd.putString("result", result);
	    		    Intent intent2=new Intent();
	    		    intent2.setFlags(Const.REFUSE_REQUEST_FAIL_FLAG);
					intent2.putExtras(bd);
					setResult(RESULT_OK, intent2);
					if(proDialog!=null){
						proDialog.dismiss();
					}
					finish();
	    			break;
	    		case Const.NETWORK_ERROR:
					Toast.makeText(RefuseActivity.this, R.string.network_error, Toast.LENGTH_SHORT).show();
					break;
	    		}
	    	}
	 };
}
