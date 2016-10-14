package com.wanfang.JavaBean;

/**
 * 操作日志信息类
 * @author yizhao
 *
 */
public class PlanLogInfo {
  
    private String LogDate;//日期
    private String LogPerson;//操作人
    private String LogState;//状态
    private String LogRemark;//日志备注
   
    public PlanLogInfo(){
    	super();
    }
    
    public PlanLogInfo(String LogDate,String LogPerson,String LogState,String LogRemark
    		){
    	super();
    	
    	this.LogDate=LogDate;
    	this.LogPerson=LogPerson;
    	this.LogState=LogState;
    	this.LogRemark=LogRemark;
    }
  //----------设置&&获取操作日期------------------------------
    public String getLogDate(){
    	return this.LogDate;
    }
    public void setLogDate(String LogDate){
	  this.LogDate=LogDate;
    } 
  
    //----------设置&&获取操作人------------------------------
    public String getLogPerson(){
    	return this.LogPerson;
    }
    public void setLogPerson(String LogPerson){
	  this.LogPerson=LogPerson;
    } 
    
    //----------设置&&获取操作状态------------------------------
    public String getLogState(){
    	return this.LogState;
    }
    public void setLogState(String LogState){
	  this.LogState=LogState;
    } 
    
  //----------设置&&获取操作日志备注------------------------------
    public String getLogRemark(){
    	return this.LogRemark;
    }
    public void setLogRemark(String LogRemark){
	  this.LogRemark=LogRemark;
    } 
}
