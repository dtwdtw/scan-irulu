package com.wanfang.Activity;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

import com.Login.UserLogin.R;
import com.wanfang.API;
import com.wanfang.HttpUtils.HttpUtils;
import com.wanfang.HttpUtils.JsonUtils;
import com.wanfang.JavaBean.UserInfo;
import com.wanfang.Util.Const;
import com.wanfang.application.myApplication;
import com.wanfang.view.LoadingDialog;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.Toast;
/**
 * 登录界面
 * @author yizhao
 *
 */
public class LoginActivity extends Activity {
	private String userName;
	private String password;

	private EditText view_userName,view_password;
	private CheckBox view_rememberPassword;
	private Button view_loginSubmit,jump;
	private final String SHARE_LOGIN_TAG="MAP_SHARE_LOGIN_TAG";

	private String SHARE_LOGIN_USERNAME="MAP_LOGIN_USERNAME";
	private String SHARE_LOGIN_PASSWORD="MAP_LOGIN_PASSWORD";

	private boolean isNetError,isServiceError=false;

	private LoadingDialog proDialog;
	private myApplication app;
	private StringBuffer sBuf;
	private String json;
	private UserInfo user;
	private int purchaPlanCount;//采购计划未审核数目
	Handler loginHandler=new Handler(){
		public void handleMessage(Message msg){
			switch (msg.what) {
			case Const.USER_PSW_NULL:
				Toast.makeText(LoginActivity.this, R.string.user_pws_null, Toast.LENGTH_SHORT).show();
				break;
			case Const.NETWORK_ERROR:
				isNetError=msg.getData().getBoolean("isNetError");
				isServiceError=msg.getData().getBoolean("isServiceError");
				if(proDialog!=null){
					proDialog.dismiss();
				}
				if(isServiceError){
					Toast.makeText(LoginActivity.this, R.string.service_error, Toast.LENGTH_SHORT).show();
				}else
				if(isNetError){
					Toast.makeText(LoginActivity.this, R.string.login_network_error, Toast.LENGTH_SHORT).show();
				}else{
					Toast.makeText(LoginActivity.this, R.string.user_pws_error, Toast.LENGTH_SHORT).show();
					clearSharePassword();
				}
				break;
			case Const.NET_REQUEST_FAIL:
				Toast.makeText(LoginActivity.this, R.string.network_error, Toast.LENGTH_SHORT).show();
				break;

			default:
				break;
			}
		}
	};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
//        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.login);
        app=(myApplication) getApplication();
        findViewsById();
        initView(false);

      //  setListener();
    }
    /**
     * 初始化组件
     */
    private void findViewsById(){
    	view_userName=(EditText) findViewById(R.id.loginUserNameEdit);
    	view_password=(EditText) findViewById(R.id.loginPwsEdit);
    	view_rememberPassword=(CheckBox) findViewById(R.id.loginRememberCheckBox);
    	view_rememberPassword.setOnCheckedChangeListener(rememberListener);
    	view_loginSubmit=(Button) findViewById(R.id.loginSubmit);
    	view_loginSubmit.setOnClickListener(new clickListener());
		jump= (Button) findViewById(R.id.jump);
		jump.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent =new Intent();
				intent.setClass(LoginActivity.this, MainActivity.class);
				Bundle bundle=new Bundle();
				bundle.putString("MAP_USERNAME", "test");
				bundle.putInt("purchaPlanCount", 0);
				bundle.putString("password", "test");
				bundle.putString("MAP_CONTENT","");
				intent.putExtras(bundle);
				startActivity(intent);
				LoginActivity.this.finish();
			}
		});
    }
    /**
     * 初始化界面
     */
    private void initView(boolean isRemember){
    	SharedPreferences share=getSharedPreferences(SHARE_LOGIN_TAG, 0);
    	final String userName=share.getString(SHARE_LOGIN_USERNAME, "");
    	final String password=share.getString(SHARE_LOGIN_PASSWORD,"");
    	Log.i("yz", "userName==>"+userName+"::::password==>"+password);
    	if(!"".equals(userName)){
    		view_userName.setText(userName);
    	}
    	if(!"".equals(password)){
    		view_password.setText(password);
    		view_rememberPassword.setChecked(true);
    	}
    	share=null;
    	view_userName.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
				// TODO Auto-generated method stub
				Log.i("yizhao", "onTextChanged");
			}

			@Override
			public void beforeTextChanged(CharSequence arg0, int arg1, int arg2,
					int arg3) {
				// TODO Auto-generated method stub
				Log.i("yizhao", "beforeTextChanged");
			}

			@Override
			public void afterTextChanged(Editable arg0) {
				// TODO Auto-generated method stub
				Log.i("yizhao", "afterTextChanged");
				if(!view_userName.getText().toString().equalsIgnoreCase(userName)){
					view_password.setText("");
				}else{
					view_password.setText(password);
				}
			}
		});
    }

    /**
     *
     * @param userName 用户名
     * @param password 密码
     * @param validateUrl 检查登录的地址
     * @return
     */
    private boolean validateLocalLogin(String userName,String password,String validateUrl){
    	boolean loginState=false;
    	HttpURLConnection conn=null;
    	DataInputStream dis=null;
        sBuf=new StringBuffer();
        StringBuilder sb=new StringBuilder("");
    	try{

    		URL url=new URL(validateUrl);
    		conn=(HttpURLConnection) url.openConnection();
    		conn.setConnectTimeout(5000);
    		conn.setRequestMethod("GET");
    		conn.setRequestProperty("Content-Type", "application/json;charset=utf-8");
    		conn.connect();
    		dis=new DataInputStream(conn.getInputStream());
    		Log.i("yz", "conn.getResponseCode()==>"+conn.getResponseCode());
    		Log.i("yz", "HttpURLConnection.HTTP_OK==>"+HttpURLConnection.HTTP_OK);
    		if(conn.getResponseCode()!=HttpURLConnection.HTTP_OK){//网络异常
    			isNetError=true;
    			return false;
    		}
    		else{
    			loginState=getUserInfo();

    		}
//    		int loginStateInt=dis.readInt();
//    		Log.i("yz", "loginStateInt==>"+loginStateInt);
//    		if(loginStateInt>0){
//    			loginState=true;
//    		}
    	}catch(Exception e){
    		isServiceError=true;
    		Log.i("yz", "Exception==>"+e.getMessage().toString());
    		e.printStackTrace();
    		return false;
    	}finally{
    		if(conn!=null){
    			conn.disconnect();
    		}
    	}
    	if(loginState){
    		if(isRemember()){
    			saveSharePreferences(true,true);
    		}else{
    			saveSharePreferences(true,false);
    		}
    	}else{
    		if(!isNetError || !isServiceError){
    			clearSharePassword();
    		}
    	}
    	if(!view_rememberPassword.isChecked()){
    		clearSharePassword();
    	}


    	return loginState;
    }

    /**
     * 获取用户的信息
     */
    private boolean getUserInfo(){
    	String data=null;
    	boolean status=false;
        String validateURL="http://192.168.1.79:8088/app/LoginCheck?user="+userName+"&pwd="+password;
        Log.i("yz", "Login user===>"+userName+":::::::pws===>"+password);
        		try {
					data=HttpUtils.getData2(validateURL,loginHandler);
					Log.i("yz", "Login data"+data);
					if(data!=null || !data.equals("")){
	        			 user=JsonUtils.parseUserFromJson(data);
	        			 purchaPlanCount=user.getpurchasePlanCount();
	        			 Log.i("yz", "status==>"+user.getSuccess());
	        			 if(user.getSuccess()==1){
	        				 return status=true;
	        			 }else{
	        				 return status=false;
	        			 }
	        		}else{
	        			Message msg=new Message();
	        			msg.what=Const.NET_REQUEST_FAIL;
	        			loginHandler.sendMessage(msg);
	        		}
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		return status;


    }
    /**
     *
     * @param saveUserName 是否保存用户名
     * @param savePassword 是否保存密码
     */
    private void saveSharePreferences(boolean saveUserName,boolean savePassword){
    	SharedPreferences share=getSharedPreferences(SHARE_LOGIN_TAG, 0);
    	if(saveUserName){
    		Log.i("yz", "SaveName==>"+view_userName.getText().toString());
    		share.edit().putString(SHARE_LOGIN_USERNAME, view_userName.getText().toString()).commit();
    	}
    	if(savePassword){
    		share.edit().putString(SHARE_LOGIN_PASSWORD, view_password.getText().toString()).commit();
    	}
    	share=null;
    }

    /**
     * 用于记录是否勾选"记住密码"
     */
    private boolean isRemember(){
    	if(view_rememberPassword.isChecked()){
    		return true;
    	}
    	return false;
    }

    /**
     * 按钮监听事件
     */
    class clickListener implements OnClickListener{

		@Override
		public void onClick(View arg0) {
			// TODO Auto-generated method stub
			switch(arg0.getId()){
			case R.id.loginSubmit:
				proDialog = new LoadingDialog(LoginActivity.this, R.style.LoadingDialog,1);
				proDialog.setCancelable(false);
				proDialog.show();
				Thread logintThread=new Thread(new LoginFailureHandler());
				logintThread.start();
			break;
			}
		}
    }

    /**
     * "记住密码"事件监听
     */
    private OnCheckedChangeListener rememberListener=new OnCheckedChangeListener() {

		@Override
		public void onCheckedChanged(CompoundButton arg0, boolean arg1) {
			// TODO Auto-generated method stub
//			if(view_rememberPassword.isChecked()){
//				Toast.makeText(LoginActivity.this, "登录成功后账户和密码会自动输入", Toast.LENGTH_SHORT).show();
//			}
		}
	};

    /**
     * 登录失败时清除密码
     */
    private void clearSharePassword(){
    	SharedPreferences share=getSharedPreferences(SHARE_LOGIN_TAG, 0);
    	share.edit().putString(SHARE_LOGIN_PASSWORD, "").commit();
    	share=null;
    }


   class LoginFailureHandler implements Runnable{

	@Override
	public void run() {
		// TODO Auto-generated method stub
		userName=view_userName.getText().toString();
		password=view_password.getText().toString();
		boolean loginState = false;
		String validateURL= API.loginCheck+"?user="+userName+"&pwd="+password;
		//String validateURL="http://xxx.xxx.xxx.xxx:xxxx?userName="+userName+"&password="+password;
		Log.i("yz", "登录用户名："+userName);
		Log.i("yz", "登录密码："+password);
		if(userName.equals("") || userName==null || password.equals("") || password==null){//用户名密码不能为空
			Message message=new Message();
			message.what=Const.USER_PSW_NULL;
			loginHandler.sendMessage(message);
			if(proDialog!=null){
				proDialog.dismiss();
			}
		}else{
			if(app.CheckNetWorkConnection(LoginActivity.this)){

				loginState=validateLocalLogin(userName, password, validateURL);

				isNetError=false;
		}else{
			isNetError=true;
		}

		Log.i("yz", "loginState==>"+loginState);
		//如果登录成功
		if(loginState){
			Intent intent =new Intent();
			intent.setClass(LoginActivity.this, MainActivity.class);
			Bundle bundle=new Bundle();
			bundle.putString("MAP_USERNAME", userName);
			bundle.putInt("purchaPlanCount", purchaPlanCount);
			bundle.putString("password", password);
			bundle.putString("MAP_CONTENT",json);
			intent.putExtras(bundle);
			startActivity(intent);
			LoginActivity.this.overridePendingTransition(R.anim.next_activity_open, R.anim.current_activity_close);
			proDialog.dismiss();
			LoginActivity.this.finish();
		}else{
			Message message=new Message();
			Bundle bundle=new Bundle();
			bundle.putBoolean("isNetError", isNetError);
			bundle.putBoolean("isServiceError", isServiceError);
			message.setData(bundle);
			message.what=Const.NETWORK_ERROR;
			loginHandler.sendMessage(message);
		  }
		}
	}
   }
}
