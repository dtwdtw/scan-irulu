package com.wanfang.JavaBean;

public class UserInfo {
    private String adminName;
    private int success;
    private String message;
    private int purchasePlanCount;//未处理的单数
    
    public UserInfo(){
    	super();
    }
    
    public UserInfo(String name,int success,String message,int purchasePlanCount){
    	super();
    	this.adminName=name;
    	this.success=success;
    	this.message=message;
    	this.purchasePlanCount=purchasePlanCount;
    }
 //----------设置&&获取用户名------------------------------   
    public String getUserName(){
    	return this.adminName;
    }
    public void setUserName(String name){
	  this.adminName=name;
    }
  
  //----------设置&&获取未审核的采购订单------------------------------
    public int getpurchasePlanCount(){
    	return this.purchasePlanCount;
    }
    public void setpurchasePlanCount(int purchasePlanCount){
	  this.purchasePlanCount=purchasePlanCount;
    }
    
  //----------设置&&获取未处理单数------------------------------
    public int getSuccess(){
    	return this.success;
    }
    public void setSuccess(int success){
	  this.success=success;
    }
    
    //----------设置&&获取Message------------------------------
    public String getMessage(){
    	return this.message;
    }
    public void setMessage(String message){
	  this.message=message;
    } 
}
