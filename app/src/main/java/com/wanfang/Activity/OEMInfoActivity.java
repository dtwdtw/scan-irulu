package com.wanfang.Activity;

import java.util.List;

import org.json.JSONException;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.Login.UserLogin.R;
import com.wanfang.Activity.LoginActivity.LoginFailureHandler;
import com.wanfang.Adapter.PurchaPlanDetailAdapter;
import com.wanfang.Adapter.SubPurchaPlanDetailAdapter;
import com.wanfang.HttpUtils.HttpUtils;
import com.wanfang.HttpUtils.JsonUtils;
import com.wanfang.JavaBean.OEMInfo;
import com.wanfang.JavaBean.PurchaPlanDetailInfo;
import com.wanfang.Util.Const;

/**
 * 供应商信息的显示界面
 * @author yizhao
 *
 */
public class OEMInfoActivity extends Activity{
	ListView listView;
	Button exit_btn;
	SubPurchaPlanDetailAdapter adapter;
	String receiverTransNo;//接收单号信息
	String receiverSKU;//接收SKU
	private List<OEMInfo> OEMInfoLists;
	
	Intent intent;
	 Handler myHandler=new Handler(){
	    	public void handleMessage(Message msg){
	    		switch(msg.what){
	    		case Const.NETWORK_REQUEST:
	    			Log.i("yizhao", "OEMInfoLists==>"+OEMInfoLists.size());
	    			setupListView(OEMInfoLists);
//	    			if(proDialog!=null){
//						proDialog.dismiss();
//					}
	    			break;
	    		case Const.NET_REQUEST_FAIL:
	    			Toast.makeText(OEMInfoActivity.this, R.string.network_error, Toast.LENGTH_SHORT).show();
	    			break;
	    		}
	    	}
	    };
	 @Override
	    protected void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
	        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
	        setContentView(R.layout.oem_layout);
	        init();
	        new Thread(new GetPurchPlanDetailInfoHandler()).start(); 
	   }
	 //初始化组件
	 private void init(){
		    exit_btn=(Button) findViewById(R.id.exit_button);
	        exit_btn.setOnClickListener(new clickListener());
	        listView=(ListView) findViewById(R.id.ome_listview);
	        intent=this.getIntent();
	        receiverTransNo=intent.getStringExtra("TransNo");
	        receiverSKU=intent.getStringExtra("SKU");
	        Log.i("print", "OEM UI TransNo==>"+receiverTransNo);
	        Log.i("print", "OEM UI SKU==>"+receiverSKU);
	 }
	 
	 class GetPurchPlanDetailInfoHandler implements Runnable{
			@Override
			public void run() {
				// TODO Auto-generated method stub
				getOEMDetailInfo(receiverTransNo,receiverSKU);//后续需要改为当前登录的用户名
				Message message=new Message();
				message.what=Const.NETWORK_REQUEST;
				myHandler.sendMessage(message);
			}
	    }
	 
	 /**
	     * 获取采购计划的信息
	     */
	    @SuppressWarnings("unused")
		private List<OEMInfo> getOEMDetailInfo(String transNo,String sku){
	    	String data=null;
	       // String validateURL="http://192.168.1.79:8088/app/GetPurchasePlanNO?PlanNo="+userName;
	           String validateURL="http://192.168.1.79:8088/app/GetPurchasePlanSku?PlanNO="+transNo+"&SKU="+sku;              
	        		try {
						data=HttpUtils.getData(validateURL);
						Log.i("yz", "OEMData=====================>"+data.toString());
						if(data!=null || !data.equals("")){
							OEMInfoLists=JsonUtils.AnalysisForOEMDetailInfo(data);
		        		}else{
		        			Message msg=new Message();
		        			msg.what=Const.NET_REQUEST_FAIL;
		        			myHandler.sendMessage(msg);
		        		}
					} catch (Exception e) {
						// TODO Auto-generated catch block
//						if(proDialog!=null){
//							proDialog.dismiss();
//						}
						e.printStackTrace();
					}
	
	        		Log.i("yz", "OEMInfoLists=====================>"+OEMInfoLists.size());
	        		return OEMInfoLists;
	    }
	    private void setupListView(List<OEMInfo> data){
		    adapter=new SubPurchaPlanDetailAdapter(OEMInfoActivity.this,data);
		    listView.setAdapter(adapter);
	    }
	    
	    /**
	     * 按钮监听事件
	     */
	    class clickListener implements OnClickListener{

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				switch(arg0.getId()){
				case R.id.exit_button:
					finish();
				break;
				}
			}
	    }   
}
