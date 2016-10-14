package com.wanfang.Activity;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.json.JSONException;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.LayoutInflater;
import android.view.View.OnTouchListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.Login.UserLogin.R;
import com.wanfang.Adapter.PlanLogAdapter;
import com.wanfang.Adapter.PurchaPlanDetailAdapter;
import com.wanfang.HttpUtils.HttpUtils;
import com.wanfang.HttpUtils.JsonUtils;
import com.wanfang.HttpUtils.MyReceiver;
import com.wanfang.JavaBean.PlanLogInfo;
import com.wanfang.JavaBean.PurchaPlanDetailInfo;
import com.wanfang.Util.Const;
import com.wanfang.Util.MyListView;
import com.wanfang.application.myApplication;
import com.wanfang.view.LoadingDialog;

public class ReviewActivity extends Activity implements OnClickListener,OnTouchListener{
	private myApplication app;
	private List<PurchaPlanDetailInfo> purDetailInfoLists;
	private List<PlanLogInfo> PlanLogInfoLists;
	private PurchaPlanDetailAdapter adapter;
	private PlanLogAdapter logAdapter;
	private Button return_btn;
	private TextView openOEMInfo;
	MyListView listview;//自定义的listView
	ListView logListView;//日志显示
	ScrollView scroll;
	private boolean isConnectNet;//是否连接网络
	private Intent receiverIntent;
	private TextView TransNo,EditDate,EditUser,Remark,TotalAmount,DepartmentName,TrackingPlan;
	private String TransNoString;
	private int StatuValue;
	private String UserName;
	private MyReceiver mReceiver;
	private  String EditDateString;
	private LoadingDialog proDialog;
	private SharedPreferences stateShare;
	
	
	LayoutInflater inflater;
	Button ReViewButton,RefuseButton,ReviewExitButton;
	
	 Handler myHandler=new Handler(){
	    	public void handleMessage(Message msg){
	    		switch(msg.what){
	    		case Const.NETWORK_REQUEST:
	    			Log.i("yizhao", "purDetailInfoLists==>"+purDetailInfoLists.size());
	    			setupListView(purDetailInfoLists);
	    		
	    			setupLogListView(PlanLogInfoLists);
	    			if(proDialog!=null){
						proDialog.dismiss();
					}
	    			break;
	    		case 521:
	    			 EditDate.setText(EditDateString+"");
	    			break;
	    		case Const.NET_REQUEST_FAIL:
	    			Toast.makeText(ReviewActivity.this, R.string.network_error, Toast.LENGTH_SHORT).show();
	    			break;
	    		case Const.REQUEST_FAIL:
	    			Toast.makeText(ReviewActivity.this, R.string.parameter_error, Toast.LENGTH_SHORT).show();
	    			break;
	    		}
	    	}
	    };
	 @Override
	    protected void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
	        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
	        setContentView(R.layout.review_activity_layout);
	        init();
	        registerBroadCast();
	   }
	 private void init(){
		
		 inflater=LayoutInflater.from(this);
		 
		 ReViewButton=(Button) findViewById(R.id.review_button);//审核
		 ReViewButton.setOnClickListener(this);
		 ReViewButton.setVisibility(View.VISIBLE);
		 
		 RefuseButton=(Button) findViewById(R.id.refuse_button);//拒绝
		 RefuseButton.setOnClickListener(this);
		 RefuseButton.setVisibility(View.VISIBLE);
		 
		 return_btn=(Button) findViewById(R.id.exit_button);
		 return_btn.setOnClickListener(this);
		 ReviewExitButton=(Button) findViewById(R.id.review_exit_button);//返回
		 ReviewExitButton.setOnClickListener(this);
		 ReviewExitButton.setVisibility(View.INVISIBLE);
			
		 TransNo=(TextView) findViewById(R.id.number_item);
		 EditDate=(TextView) findViewById(R.id.create_time_item);
		 EditUser=(TextView) findViewById(R.id.op_person_item);
		 Remark=(TextView) findViewById(R.id.remark_item);
		 TotalAmount=(TextView) findViewById(R.id.money_item);
		 DepartmentName=(TextView) findViewById(R.id.dept_name_item);
		 TrackingPlan=(TextView) findViewById(R.id.documentary_person);
		 initPreActivityData();
		 app=(myApplication) this.getApplication();
		 scroll=(ScrollView) findViewById(R.id.scrollview);
		 scroll.smoothScrollBy(0, 0);
		 listview=(MyListView) findViewById(R.id.detail_listview);
		 listview.setOnTouchListener(this);
		 logListView=(ListView) findViewById(R.id.plan_log_listview);
		 logListView.setOnTouchListener(this);
		
		 isConnectNet=app.CheckNetWorkConnection(ReviewActivity.this);
		 if(isConnectNet){
			 proDialog = new LoadingDialog(ReviewActivity.this, R.style.LoadingDialog,2);
			 proDialog.setCancelable(false);
			 proDialog.show();
			 new Thread(new GetPurchPlanDetailInfoHandler()).start();
		 }else{
			 Toast.makeText(this, R.string.network_fail, Toast.LENGTH_SHORT).show();
		 }
		 
	 }
	 private void initPreActivityData(){
		 receiverIntent=this.getIntent();
		 Bundle bundle=receiverIntent.getExtras();
		 if(bundle==null || bundle.equals("")){
			 TransNoString="null";
		     TransNo.setText("null");
			// TransNo.setText("A0079");
			// EditDate.setText(bundle.getString("EditDate"));
			 EditUser.setText("null");
			 Remark.setText("null");
			 TotalAmount.setText("null");
			 DepartmentName.setText("null");
			 TrackingPlan.setText("null");
			 StatuValue=-1;
			 UserName="null";
		 }else{
			 TransNoString=bundle.getString("TransNo");
		     TransNo.setText(bundle.getString("TransNo"));
			// TransNo.setText("A0079");
			// EditDate.setText(bundle.getString("EditDate"));
			 EditUser.setText(bundle.getString("EditUser"));
			 if(bundle.getString("Remark").equals("null")){
				 Remark.setText(R.string.null_value);
			 }else{
				 Remark.setText(bundle.getString("Remark"));
			 }
			 TotalAmount.setText("￥"+bundle.getString("TotalAmount"));
			 DepartmentName.setText(bundle.getString("DepartmentName"));
			 TrackingPlan.setText(bundle.getString("TrackingPlan"));
			 StatuValue=bundle.getInt("Status");
			 UserName=bundle.getString("UserName");
			 Log.i("print", "Status=======>"+StatuValue);
		 }
		 
		// EditUser.setText(app.DateChange(bundle.getString("EditUser")));
	 }
	 class GetPurchPlanDetailInfoHandler implements Runnable{

			@Override
			public void run() {
				// TODO Auto-generated method stub
				
				getPurchaPlanDetailInfo(TransNoString);//后续需要改为当前登录的用户名
				if(TransNoString==null || TransNoString.equals("")){
					Message message=new Message();
					message.what=Const.REQUEST_FAIL;
					myHandler.sendMessage(message);
				}else{
					Message message=new Message();
					message.what=Const.NETWORK_REQUEST;
					myHandler.sendMessage(message);
				}
				
			}
	    }
	 
	 /**
	     * 获取采购计划的信息
	     */
	    private List<PurchaPlanDetailInfo> getPurchaPlanDetailInfo(String transNo){
	    	String data=null;
	        String validateURL="http://192.168.1.79:8088/app/GetPurchasePlanNO?PlanNo="+transNo;
	                            
	        		try {
						data=HttpUtils.getData(validateURL);
						if(data!=null || !data.equals("")){
							Log.i("yz", "ReViewData=====================>"+data.toString());
							purDetailInfoLists=JsonUtils.AnalysisForPurchPlanDetailInfo(data);
		        			PlanLogInfoLists=JsonUtils.AnalysisForPlanLogInfo(data);
		        			EditDateString=JsonUtils.AnalysEditDateValue(data);
		        		   
		        			Message msg=new Message();
		        			Bundle bd=new Bundle();
		        			bd.putString("editDate",EditDateString);
		        			msg.setData(bd);
		        			msg.what=521;
		        			myHandler.sendMessage(msg);
		        		}else{
		        			Message msg=new Message();
		        			msg.what=Const.NET_REQUEST_FAIL;
		        			myHandler.sendMessage(msg);
		        		}
					} catch (Exception e) {
						// TODO Auto-generated catch block
						if(proDialog!=null){
							proDialog.dismiss();
						}
						e.printStackTrace();
					}
	        		return purDetailInfoLists;
	    }
	 @Override
	 public void finish(){
		 super.finish();
		 this.overridePendingTransition(R.anim.pre_current_activity_open, R.anim.into_current_activity_close);
	 }
	 
	 private void setupListView(List<PurchaPlanDetailInfo> data){
		    adapter=new PurchaPlanDetailAdapter(ReviewActivity.this,data,TransNo.getText().toString());
	    	listview.setAdapter(adapter);
	    }
	 private void setupLogListView(List<PlanLogInfo> data){
		 logAdapter=new PlanLogAdapter(ReviewActivity.this, data);
		 logAdapter.notifyDataSetChanged();
		 logListView.setAdapter(logAdapter);
	 }
	 
	 
	 @Override
	 public void onClick(View arg0){
		 switch (arg0.getId()) {
		case R.id.review_button:
			//CreateDialog();
			if(app.CheckNetWorkConnection(ReviewActivity.this)){
				Intent intentForPass=new Intent(this,PassReviewActivity.class);
				Bundle bd=new Bundle();
				bd.putString("UserName", UserName);
				bd.putString("TransNo", TransNo.getText().toString());
				intentForPass.putExtras(bd);
				startActivityForResult(intentForPass,Const.PASS_REQUEST_CODE);
			}else{
				myHandler.sendEmptyMessage(Const.NET_REQUEST_FAIL);
			}
			
			break;
        case R.id.exit_button:
			finish();
			break;
        case R.id.refuse_button:
        	if(app.CheckNetWorkConnection(ReviewActivity.this)){
        		Intent intentForRefuse=new Intent(this,RefuseActivity.class);
    			Bundle bundle=new Bundle();
    			bundle.putString("TransNo", TransNo.getText().toString());
    			bundle.putString("DepartmentName", DepartmentName.getText().toString());
    			bundle.putString("EditDate", EditDate.getText().toString());
    			bundle.putString("EditUser", EditUser.getText().toString());
    			bundle.putInt("Status", StatuValue);
    			bundle.putString("TotalAmount", TotalAmount.getText().toString());
    			bundle.putString("UserName", UserName);
    			intentForRefuse.putExtras(bundle);
    			startActivityForResult(intentForRefuse, Const.REFUSE_REQUEST_CODE);
        	}else{
				myHandler.sendEmptyMessage(Const.NET_REQUEST_FAIL);
			}
			
			break;
        case R.id.review_exit_button:
        	finish();
        	break;
		default:
			break;
		}
	 }
	 
	 /**
	  * 结果返回后的判断和执行
	  */
	 @Override
	 protected void onActivityResult(int requestCode,int resultCode,Intent data){
		 super.onActivityResult(requestCode, resultCode, data);
		 Log.i("print", "onActivityResult requestCode===>"+requestCode);
		 Log.i("print", "onActivityResult resultCode===>"+resultCode);
		 switch (requestCode) {
		   case Const.REFUSE_REQUEST_CODE:
			   if(resultCode==RESULT_OK){
				   if(data.getFlags()==Const.REFUSE_REQUEST_SUCCESS_FLAG){
					   //拒绝请求成功
					   RequestSuccess(data);
					   stateShare=getSharedPreferences("stateValue", 0);
					   stateShare.edit().putInt("stateCode", 1).commit();
				   }else if(data.getFlags()==Const.REFUSE_REQUEST_FAIL_FLAG){
					   RequestFail(data);
				   }
			   }
			break;
		   case Const.PASS_REQUEST_CODE:
			   if(resultCode==RESULT_OK){
				   if(data.getFlags()==Const.REVIEW_SUCCESS_FLAG){
					   RequestSuccess(data);
					   stateShare=getSharedPreferences("stateValue", 0);
					   stateShare.edit().putInt("stateCode", 1).commit();
				   }else if(data.getFlags()==Const.REVIEW_FAIL_FLAG){
					   //请求失败
					   RequestFail(data);
				   }
			   }
			   
			   break;
		default:
			break;
		}
	 }
	 /**
	  * 请求成功
	  */
	private void RequestSuccess(Intent data){
		  //请求成功
		   Bundle bundle=data.getExtras();
		   int sucCode=bundle.getInt("ReturnSuccessCode");
			  Intent intent2=new Intent(Const.PASS_BroadCastActionTwo);
			  intent2.putExtra("ReturnSuccessCode", sucCode);
			  Log.i("print", "------------------------>"+sucCode);
			  this.sendBroadcast(intent2);
			  if(sucCode==1){
				  ReViewButton.setVisibility(View.INVISIBLE);
				  RefuseButton.setVisibility(View.INVISIBLE);
				  ReviewExitButton.setVisibility(View.VISIBLE);
				  freshAdapter();
			  }
			
			
	}
	
	/**
	 * 请求失败
	 */
	private void RequestFail(Intent data){
		 Bundle bundle=data.getExtras();
		   String FailResult=bundle.getString("result");
		   Intent intent=new Intent(Const.PASS_BroadCastAction_FAIL);
		   intent.putExtra("FailResult", FailResult);
		   this.sendBroadcast(intent);
	}
		/**
		 * 注册广播 
		 */
	 private void registerBroadCast(){
		  mReceiver=new MyReceiver();
		 IntentFilter filter=new IntentFilter();
		 filter.addAction(Const.REFUSE_BroadCastAction);
		 filter.addAction(Const.PASS_BroadCastActionTwo);
		 registerReceiver(mReceiver, filter);
	  }
	 
	 private void freshAdapter(){
		 if(isConnectNet){
			 new Thread(new GetPurchPlanDetailInfoHandler()).start();
		 }else{
			 Toast.makeText(this, R.string.fresh_fail, Toast.LENGTH_SHORT).show();
		 }
	 }
	 @Override
	 protected void onDestroy(){
		 super.onDestroy();
		 if(mReceiver!=null){
			 unregisterReceiver(mReceiver);
		 }
	 }
	 
	 @Override
	 public boolean onTouch(View v,MotionEvent event){
		 if(event.getAction()==MotionEvent.ACTION_MOVE){
			 scroll.requestDisallowInterceptTouchEvent(true);
		 }
		return false;
		 
	 }
}
