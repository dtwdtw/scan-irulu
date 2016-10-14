package com.wanfang.HttpUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ConnectTimeoutException;
import org.apache.http.conn.HttpHostConnectException;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.CoreConnectionPNames;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.json.JSONStringer;

import com.google.gson.Gson;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
/**
 * 网络传输封装类
 * @author yizhao
 *
 */
public class HttpUtils {
	private static String TIME_OUT="操作超时";
	private static final int REQUES_TIME_OUT=2;
	private static final int REVIEW_REQUEST_SUCCESS=1;
	private static final int REVIEW_REQUEST_FAIL=0;
	private static final int REFUSE_REQUEST_SUCCESS=0x11;
	private static final int REFUSE_REQUES_FAIL=0x12;
	/**
	 * get获取数据方法
	 * @param url 请求地址
	 * @return
	 */
    public static String getData(String url) {
    	StringBuilder sb=new StringBuilder();
    	String result=null;
    	HttpClient httpClient=new DefaultHttpClient();
    	url=url.trim();
    	HttpGet httpGet=new HttpGet(url);
    	httpClient.getParams().setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, 5000);
    	HttpResponse httpResponse;
		try {
			httpResponse = httpClient.execute(httpGet);
			HttpEntity httpEntity=httpResponse.getEntity();

	    	if(httpResponse.getStatusLine().getStatusCode()==200){
	    		result=EntityUtils.toString(httpResponse.getEntity(),"UTF-8");
	    		return result;
	    	}
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			Log.e("yz", "ClientProtocolException!!!!!");
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			Log.e("yz", "IOException!!!!!");
			e.printStackTrace();
		}
		return null;
    }
    
    /**
     * HTTP POST发送审核数据到服务器
     * @throws Exception 
     */
    public static String doPostForReview(String url,List<NameValuePair> params,Handler handler) throws Exception{
    	String result=null;
    	 url=url.trim();
    	 HttpPost httpPostRequest=new HttpPost(url);
    	// StringEntity reqEntity=new StringEntity(s, charset)
    	 try {
			HttpEntity entity=new UrlEncodedFormEntity(params,HTTP.UTF_8);
			httpPostRequest.setEntity(entity);
			HttpClient httpClient=new DefaultHttpClient();
			httpClient.getParams().setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, 30000);
			httpClient.getParams().setParameter(CoreConnectionPNames.SO_TIMEOUT, 30000);
			HttpResponse httpResp=httpClient.execute(httpPostRequest);
			Log.i("print", "httpResp.getStatusLine().getStatusCode()=="+httpResp.getStatusLine().getStatusCode());
			if(httpResp.getStatusLine().getStatusCode()==200){
				result=EntityUtils.toString(httpResp.getEntity(),"UTF-8");
			    Message msg=CreateMessage(REVIEW_REQUEST_SUCCESS, result);
				handler.sendMessage(msg);
				Log.i("print", "审核请求成功！！！"+result.toString());
			}else{
				Log.i("print", "审核请求失败！！");
				
				result="审核请求失败";
				Message msg=CreateMessage(REVIEW_REQUEST_FAIL, result);
				handler.sendMessage(msg);
			}
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch(ConnectTimeoutException e){
			result=TIME_OUT;
			Message msg=CreateMessage(REQUES_TIME_OUT, result);
			handler.sendMessage(msg);
		}
		return result;
    }
    private static Message CreateMessage(int flag,String result){
    	Message msg=new Message();
		Bundle bd=new Bundle();
		bd.putString("result", result);
		msg.setData(bd);
		msg.what=flag;
		return msg;
    }
    
    /**
     * HTTP POST发送拒绝请求数据到服务器
     * @throws Exception 
     */
    public static String doPostForRefuse(String url,List<NameValuePair> params,Handler handler) throws Exception{
    	String result=null;
   	 url=url.trim();
   	 HttpPost httpPostRequest=new HttpPost(url);
   	// StringEntity reqEntity=new StringEntity(s, charset)
   	 try {
			HttpEntity entity=new UrlEncodedFormEntity(params,HTTP.UTF_8);
			httpPostRequest.setEntity(entity);
			HttpClient httpClient=new DefaultHttpClient();
			httpClient.getParams().setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, 30000);
			httpClient.getParams().setParameter(CoreConnectionPNames.SO_TIMEOUT, 30000);
			HttpResponse httpResp=httpClient.execute(httpPostRequest);
			Log.i("print", "httpResp.getStatusLine().getStatusCode()=="+httpResp.getStatusLine().getStatusCode());
			
			Log.i("print", "httpResp.getStatusLine().getStatusCode()=="+httpResp.getStatusLine().getStatusCode());
			if(httpResp.getStatusLine().getStatusCode()==200){
				result=EntityUtils.toString(httpResp.getEntity(),"UTF-8");
			    Message msg=CreateMessage(REFUSE_REQUEST_SUCCESS, result);
				handler.sendMessage(msg);
				System.out.println(httpPostRequest.getHeaders("Android"));
				Log.i("print", "拒绝请求成功！！！"+result.toString());
			}else{
				Log.i("print", "拒绝请求失败！！");
				
				result="拒绝请求失败";
				Message msg=CreateMessage(REFUSE_REQUES_FAIL, result);
				handler.sendMessage(msg);
			}
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch(ConnectTimeoutException e){
			result=TIME_OUT;
			Message msg=CreateMessage(REQUES_TIME_OUT, result);
			handler.sendMessage(msg);
		}
		return result;
    }
    
    public static String getData2(String url,Handler handler) {
    	StringBuilder sb=new StringBuilder();
    	String result=null;
    	HttpClient httpClient=new DefaultHttpClient();
    	url=url.trim();
    	HttpGet httpGet=new HttpGet(url);
    	httpClient.getParams().setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, 5000);
    	HttpResponse httpResponse;
		try {
			httpResponse = httpClient.execute(httpGet);
			HttpEntity httpEntity=httpResponse.getEntity();

	    	if(httpResponse.getStatusLine().getStatusCode()==200){
	    		result=EntityUtils.toString(httpResponse.getEntity(),"UTF-8");
	    		return result;
	    	}
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			Log.e("yz", "ClientProtocolException!!!!!");
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			Log.e("yz", "IOException!!!!!");
			handler.sendEmptyMessage(101);
			e.printStackTrace();
		}
		return null;
    }
    
}
