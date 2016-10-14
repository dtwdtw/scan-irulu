package com.wanfang.application;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.app.Application;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.NetworkInfo.State;

public class myApplication extends Application{
	int purchaPlanCount=0;
	
  @Override
  public void onCreate(){
	  super.onCreate();
  }
  
  public byte[] readInputStream(InputStream instream) throws Exception{
	  ByteArrayOutputStream outStream =new ByteArrayOutputStream();
	  byte[] buffer=new byte[1024];
	  int len=0;
	  while((len=instream.read(buffer))!=-1){
		 outStream.write(buffer,0,len); 
	  }
	  instream.close();
	return outStream.toByteArray();
	  
  }
  
  public boolean CheckNetWorkConnection(Context context){
	  
	  final ConnectivityManager connMgr=(ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
	 // final NetworkInfo wifi=connMgr.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
	  NetworkInfo wifiState=connMgr.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
	  NetworkInfo mobileState=connMgr.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
	 // final NetworkInfo mobile=connMgr.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
	  if(wifiState.isConnected()| mobileState.isConnected()){
		  return true;
	  }else{
			return false;
	  }
  }
  
  public String DateChange(String date){
	  date= GetNumber(date); 
	  Date da=new Date(Long.valueOf(date));
	  SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd hh:mm");
	  String s=sdf.format(da);
	  return s;
  }
  public String DateChange3(String date){
	  date= GetNumber(date); 
	  Date da=new Date(Long.valueOf(date));
	  SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
	  String s=sdf.format(da);
	  return s;
  }
  public String DateChange2(String date){
	  date= GetNumber(date); 
	  Date da=new Date(Long.valueOf(date));
	  SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
	  String s=sdf.format(da);
	  return s;
  }
  
 public String GetNumber(String str){
	 String regex="\\d*";
	 Pattern p=Pattern.compile(regex);
	 Matcher m=p.matcher(str);
	 while (m.find()) {
	if(!"".equals(m.group())){
		return m.group();
	   }
	}
	return null;
 }
 
 public String StatusValueToString(int value){
		String str=null;
		switch (value) {
		case 2:
			str="未审核";
			break;
		case 3:
			str="审核中";
		default:
			break;
		}
		return str;
	}
 public void setpurchaPlanCount(int purchaPlanCount){
	 this.purchaPlanCount=purchaPlanCount;
 }
 
 public int getpurchaPlanCount(){
	 return this.purchaPlanCount;
 }
}
