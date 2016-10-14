package com.wanfang.JavaBean;

/**
 * 采购计划信息类
 * @author yizhao
 *
 */
public class PurchaPlanInfo {
    private String TransNo;//采购计划单号
    private String  TransDate;//采购创建时间
    private int Status;//状态
    private String EditDate;//操作日期
    private String EditUser;//操作人
    private String Remark;//备注
    private String TotalAmount;//总金额
    private String DepartmentName;//所属部门名称
    private String DepartmentIds;//所属部门编号
    private String TrackingPlan;//跟单人
    
    public PurchaPlanInfo(){
    	super();
    }
    
    public PurchaPlanInfo(String TransNo,String TransDate,int Status,String EditDate,
    		String EditUser,String Remark,String TotalAmount,String DepartmentName,String TrackingPlan){
    	super();
    	this.TransNo=TransNo;
    	this.TransDate=TransDate;
    	this.Status=Status;
    	this.EditDate=EditDate;
    	this.EditUser=EditUser;
    	this.Remark=Remark;
    	this.TotalAmount=TotalAmount;
    	this.DepartmentName=DepartmentName;
    	this.TrackingPlan=TrackingPlan;
    }
 //----------设置&&获取单号------------------------------   
    public String getTransNo(){
    	return this.TransNo;
    }
    public void setTransNo(String TransNo){
	  this.TransNo=TransNo;
    }
  
  //----------设置&&获取创建日期------------------------------
    public String getTransDate(){
    	return this.TransDate;
    }
    public void setTransDate(String TransDate){
	  this.TransDate=TransDate;
    }
    
    //----------设置&&获取状态------------------------------
    public int getStatus(){
    	return this.Status;
    }
    public void setStatus(int Status){
	  this.Status=Status;
    } 
    
    //----------设置&&获取编辑日期------------------------------
    public String getEditDate(){
    	return this.EditDate;
    }
    public void setEditDate(String EditDate){
	  this.EditDate=EditDate;
    } 
    
    //----------设置&&获取操作人------------------------------
    public String getEditUser(){
    	return this.EditUser;
    }
    public void setEditUser(String EditUser){
	  this.EditUser=EditUser;
    } 
    
    //----------设置&&获取备注信息------------------------------
    public String getRemark(){
    	return this.Remark;
    }
    public void setRemark(String Remark){
	  this.Remark=Remark;
    } 
    
  //----------设置&&获取金额------------------------------
    public String getTotalAmount(){
    	return this.TotalAmount;
    }
    public void setTotalAmount(String TotalAmount){
	  this.TotalAmount=TotalAmount;
    } 
    
  //----------设置&&获取部门名称------------------------------
    public String getDepartmentName(){
    	return this.DepartmentName;
    }
    public void setDepartmentName(String DepartmentName){
	  this.DepartmentName=DepartmentName;
    } 
    
  //----------设置&&获取跟单人姓名------------------------------   
    public String getTrackingPlan(){
    	return this.TrackingPlan;
    }
    public void setTrackingPlan(String TrackingPlan){
	  this.TrackingPlan=TrackingPlan;
    }
}
