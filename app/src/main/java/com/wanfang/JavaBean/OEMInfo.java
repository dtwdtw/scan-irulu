package com.wanfang.JavaBean;

/**
 * 采购计划明细信息类
 * @author yizhao
 *
 */
public class OEMInfo {
  
    private String OEMName;//供应商名称
    private String Quantity;//数量
    private String UnitPrice;//单价
    private String PredictDeliveryDate;//预计到货日期
    private String isTaxFree;//是否含税判断
    private String Currency;//币种
    private String CurrencySymbol;//货币符号
    
    public OEMInfo(){
    	super();
    }
    
    public OEMInfo(String SKU,String WarnQuantity,String PurchaQuantity,String TotalAmount,String Spec,
    		String SaleStock,String SevenSaleQuantity,String OEMName,String Quantity,String UnitPrice,String PredictDeliveryDate
    		,String isTaxFree,String Currency){
    	super();
    	
    	this.OEMName=OEMName;
    	this.Quantity=Quantity;
    	this.UnitPrice=UnitPrice;
    	this.PredictDeliveryDate=PredictDeliveryDate;
    	this.isTaxFree=isTaxFree;
    	this.Currency=Currency;
    }
  //----------设置&&获取供应商名称------------------------------
    public String getOEMName(){
    	return this.OEMName;
    }
    public void setOEMName(String OEMName){
	  this.OEMName=OEMName;
    } 
  
    //----------设置&&获取数量------------------------------
    public String getQuantity(){
    	return this.Quantity;
    }
    public void setQuantity(String Quantity){
	  this.Quantity=Quantity;
    } 
    
    //----------设置&&获取单价------------------------------
    public String getUnitPrice(){
    	return this.UnitPrice;
    }
    public void setUnitPrice(String UnitPrice){
	  this.UnitPrice=UnitPrice;
    } 
    
  //----------设置&&获取预计到货日期------------------------------
    public String getPredictDeliveryDate(){
    	return this.PredictDeliveryDate;
    }
    public void setPredictDeliveryDate(String PredictDeliveryDate){
	  this.PredictDeliveryDate=PredictDeliveryDate;
    } 
  
  //----------设置&&获取是否含税信息------------------------------
    public String getIsTaxFree(){
    	return this.isTaxFree;
    }
    public void setIsTaxFree(String isTaxFree){
	  this.isTaxFree=isTaxFree;
    } 
    
  //----------设置&&获取币种------------------------------
    public String getCurrency(){
    	return this.Currency;
    }
    public void setCurrency(String Currency){
	  this.Currency=Currency;
    } 
    
  //----------设置&&获取币种符号------------------------------
    public String getCurrencySymbol(){
    	return this.CurrencySymbol;
    }
    public void setCurrencySymbol(String CurrencySymbol){
	  this.CurrencySymbol=CurrencySymbol;
    } 
}
