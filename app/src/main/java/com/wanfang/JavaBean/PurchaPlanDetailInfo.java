package com.wanfang.JavaBean;

/**
 * 采购计划明细信息类
 * @author yizhao
 *
 */
public class PurchaPlanDetailInfo {
    private String SKU;//SKU
    private String  WarnQuantity;//预警数量
    private String PurchaQuantity;//采购数量
    private String TotalAmount;//总金额
    private String Spec;//规格
    private String SaleStock;//销售库存
    private String SevenSaleQuantity;//七天销售数
    private String OEMName;//供应商名称
    private String Quantity;//数量
    private String UnitPrice;//单价
    private String PredictDeliveryDate;//预计到货日期
    private String isTaxFree;//是否含税判断
    private String Currency;//币种
    private String EditDate;//编辑日期
    
    public PurchaPlanDetailInfo(){
    	super();
    }
    
    public PurchaPlanDetailInfo(String SKU,String WarnQuantity,String PurchaQuantity,String TotalAmount,String Spec,
    		String SaleStock,String SevenSaleQuantity,String OEMName,String Quantity,String UnitPrice,String PredictDeliveryDate
    		,String isTaxFree,String Currency,String EditDate){
    	super();
    	this.SKU=SKU;
    	this.WarnQuantity=WarnQuantity;
    	this.PurchaQuantity=PurchaQuantity;
    	this.TotalAmount=TotalAmount;
    	this.Spec=Spec;
    	this.SaleStock=SaleStock;
    	this.SevenSaleQuantity=SevenSaleQuantity;
    	this.OEMName=OEMName;
    	this.Quantity=Quantity;
    	this.UnitPrice=UnitPrice;
    	this.PredictDeliveryDate=PredictDeliveryDate;
    	this.isTaxFree=isTaxFree;
    	this.Currency=Currency;
    	this.EditDate=EditDate;
    }
  //----------设置&&获取编辑日期------------------------------   
    public String getEditDate(){
    	return this.EditDate;
    }
    public void setEditDate(String EditDate){
	  this.EditDate=EditDate;
    } 
    
 //----------设置&&获取SKU------------------------------   
    public String getSKU(){
    	return this.SKU;
    }
    public void setSKU(String SKU){
	  this.SKU=SKU;
    }
  
  //----------设置&&获取预警数量------------------------------
    public String getWarnQuantity(){
    	return this.WarnQuantity;
    }
    public void setWarnQuantity(String WarnQuantity){
	  this.WarnQuantity=WarnQuantity;
    }
    
    //----------设置&&获取采购数量------------------------------
    public String getPurchaQuantity(){
    	return this.PurchaQuantity;
    }
    public void setPurchaQuantity(String PurchaQuantity){
	  this.PurchaQuantity=PurchaQuantity;
    } 
    
    //----------设置&&获取金额------------------------------
    public String getTotalAmount(){
    	return this.TotalAmount;
    }
    public void setTotalAmount(String TotalAmount){
	  this.TotalAmount=TotalAmount;
    } 
    
    //----------设置&&获取规格------------------------------
    public String getSpec(){
    	return this.Spec;
    }
    public void setSpec(String Spec){
	  this.Spec=Spec;
    } 
    
    //----------设置&&获取销售库存------------------------------
    public String getSaleStock(){
    	return this.SaleStock;
    }
    public void setSaleStock(String SaleStock){
	  this.SaleStock=SaleStock;
    } 
    
    //----------设置&&获取七天销售数量------------------------------
    public String getSevenSaleQuantity(){
    	return this.SevenSaleQuantity;
    }
    public void setSevenSaleQuantity(String SevenSaleQuantity){
	  this.SevenSaleQuantity=SevenSaleQuantity;
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
}
