package com.wanfang.Util;

import java.text.SimpleDateFormat;
import java.util.Date;

import android.view.Menu;

public class Const {
     public static final int NET_REQUEST_FAIL=0x17;
     
     public static final String REFUSE_BroadCastAction="COM_WANFANG_REFUSE_SUCCESS";//拒绝成功的action
 	
     public static final String REFUSE_FAIL_BroadCastAction="COM_WANFANG_REFUSE_FAIL";//拒绝成功的action
 	
     public static final String PASS_BroadCastActionTwo="COM_WANFANG_PASS_SUCCESS";//审核成功的action
 	
     public static final String PASS_BroadCastAction_FAIL="COM_WANFANG_PASS_FAIL";//审核失败的action
 	
     public static final String PASS_BroadCastAction_TIMEOUT="COM_WANFANG_PASS_TIMOUT";//审核请求超时的action
     
     public static final int REFUSE_REQUEST_SUCCESS_FLAG=0x103;
     
     public static final int REFUSE_REQUEST_FAIL_FLAG=0x104;
 	
     public static final int REVIEW_SUCCESS_FLAG=0x101;
 	
     public static final int REVIEW_FAIL_FLAG=0x102;
     
     public static final int REFUSE_REQUEST_CODE=0;
     
     public static final int PASS_REQUEST_CODE=1;
 	
 	 public final static int NETWORK_REQUEST=0;
 	 
 	 public static final int REQUES_TIME_OUT=2;
 	 
 	 public static final int REVIEW_REQUEST_SUCCESS=1;
 	 
 	 public static final int REVIEW_REQUEST_FAIL=0;
 	 
 	 public static final int USER_PSW_NULL=100;
 	 
 	 public static final int NETWORK_ERROR=101;
 	 
 	 public static final int MENU_REFRESH=Menu.FIRST-1;
	 
 	 public static final int MENU_ABOUT=Menu.FIRST;
	 
 	 public static final int MENU_LOGOFF=Menu.FIRST+1;
 	 
 	 public static final int REFUSE_REQUEST_SUCCESS=0x11;
 	 
 	 public static final int REFUSE_REQUES_FAIL=0x12;
 	 
 	 public static final int NOT_FOUND_DATA=0X13;
 	 
 	 public static final int LAUNCH_SEARCH=0X14;
 	 
 	 public static final int SEARCH_FRESH=0X15;
 	 
 	 public static final int REQUEST_FAIL=0X16;
 	 
 	// public static final int NETWORK_ERROR=0X16;
	
 	/**
 	 * 获取本次登录时间	
 	 */
 	 public static String getCurrentDate(){
 		 SimpleDateFormat formatter=new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss");
 		 Date curDate=new Date(System.currentTimeMillis());
 		 return formatter.format(curDate);
 	 }	 

}
