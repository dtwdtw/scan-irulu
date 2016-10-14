package com.wanfang.HttpUtils;


import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import com.wanfang.JavaBean.OEMInfo;
import com.wanfang.JavaBean.PlanLogInfo;
import com.wanfang.JavaBean.PurchaPlanDetailInfo;
import com.wanfang.JavaBean.PurchaPlanInfo;
import com.wanfang.JavaBean.UserInfo;

public class JsonUtils {
	/**
	 * 用户信息解析
	 * @param data
	 * @return
	 */
     public static UserInfo parseUserFromJson(String data){
    
    	UserInfo user=new UserInfo();
    	 Gson gs=new Gson();
    	 user= gs.fromJson(data,  UserInfo.class);
		return user;
     }
     /**
      * 解析“采购计划”
      * @param data
      * @return
      */
     public static ArrayList<PurchaPlanInfo> AnalysisForPurchPlanInfo(String jsonstr) throws JSONException{
    	
    	 ArrayList<PurchaPlanInfo> lists=new ArrayList<PurchaPlanInfo>();
    	 
    	JSONObject jsonObject=new JSONObject(jsonstr);
    	
    	JSONArray jsonArray=jsonObject.getJSONArray("message");
    	 for(int i=0;i<jsonArray.length();i++){
    		 JSONObject jsonObj=jsonArray.getJSONObject(i);
    		 PurchaPlanInfo purchPlanInfo=new PurchaPlanInfo();
    		 purchPlanInfo.setTransNo(jsonObj.getString("TransNo"));
    		 purchPlanInfo.setTransDate(jsonObj.getString("TransDate"));
    		 purchPlanInfo.setStatus(jsonObj.getInt("Status"));
    		 purchPlanInfo.setEditDate(jsonObj.getString("EditDate"));
    		 purchPlanInfo.setEditUser(jsonObj.getString("EditUser"));
    		 purchPlanInfo.setRemark(jsonObj.getString("Remark"));
    		 purchPlanInfo.setTotalAmount(jsonObj.getString("TotalAmount"));
    		 purchPlanInfo.setDepartmentName(jsonObj.getString("DepartmentName"));
    		 purchPlanInfo.setTrackingPlan(jsonObj.getString("TrackingPlan"));
    		 lists.add(purchPlanInfo);
    	 }
		return lists;
    	 
     }
     
     /**
      * 解析“采购计划明细”信息
      * @param data
      * @return
      */
     public static ArrayList<PurchaPlanDetailInfo> AnalysisForPurchPlanDetailInfo(String jsonstr) throws JSONException{
    	
    	 ArrayList<PurchaPlanDetailInfo> Detaillists=new ArrayList<PurchaPlanDetailInfo>();
    	 
    	JSONObject jsonObject=new JSONObject(jsonstr).getJSONObject("message");
    	JSONArray jsonArray=jsonObject.getJSONArray("PurchasePlanDetails");
    	for(int i=0;i<jsonArray.length();i++){
    		 JSONObject jsonObj=jsonArray.getJSONObject(i);
    		 PurchaPlanDetailInfo purchPlanDetialInfo=new PurchaPlanDetailInfo();
    		 purchPlanDetialInfo.setSKU(jsonObj.getString("SKU"));
    		 purchPlanDetialInfo.setWarnQuantity(jsonObj.getString("Quantity"));
    		 purchPlanDetialInfo.setPurchaQuantity(jsonObj.getString("PurchaseQuantity"));
    		 purchPlanDetialInfo.setTotalAmount(jsonObj.getString("Amount"));
    		 purchPlanDetialInfo.setSpec(jsonObj.getString("Title"));
    		 purchPlanDetialInfo.setSaleStock(jsonObj.getString("Stock_qty"));
    		 purchPlanDetialInfo.setSevenSaleQuantity(jsonObj.getString("WeekOutStock"));
    		 Detaillists.add(purchPlanDetialInfo);
    	 }
		return Detaillists;
     }
     
     /**
      * 解析供应商详细信息
      * @param data
      * @return
      */
     public static ArrayList<OEMInfo> AnalysisForOEMDetailInfo(String jsonstr) throws JSONException{
    	
    	 ArrayList<OEMInfo> Detaillists=new ArrayList<OEMInfo>();
    	 JSONObject jsonObject=new JSONObject(jsonstr);
     	
     	JSONArray jsonArray=jsonObject.getJSONArray("message");
     	 for(int i=0;i<jsonArray.length();i++){
     		JSONObject jsonObj=jsonArray.getJSONObject(i);
     		OEMInfo OEMDetialInfo=new OEMInfo();
     		 OEMDetialInfo.setOEMName(jsonObj.getString("SupplierName"));
     		 OEMDetialInfo.setQuantity(jsonObj.getString("Quantity"));
     		 OEMDetialInfo.setUnitPrice(jsonObj.getString("Price"));
     		 OEMDetialInfo.setPredictDeliveryDate(jsonObj.getString("PredictProinDate"));
     		 OEMDetialInfo.setIsTaxFree(jsonObj.getString("Tax"));
     		 OEMDetialInfo.setCurrency(jsonObj.getString("CurrencyCode"));
     		 OEMDetialInfo.setCurrencySymbol(jsonObj.getString("CurrencySymbol"));
     		Detaillists.add(OEMDetialInfo);
     	 }
		return Detaillists;
     }
     
     /**
      * 解析日志信息
      * @param data
      * @return
      */
     public static List<PlanLogInfo> AnalysisForPlanLogInfo(String jsonstr) throws JSONException{
    	 ArrayList<PlanLogInfo> PlanLoglists=new ArrayList<PlanLogInfo>();
    	 JSONObject jsonObject=new JSONObject(jsonstr).getJSONObject("message");
    	 JSONArray jsonArray=jsonObject.getJSONArray("PlanLog");
    	 Log.i("yizhao", "PlanLog======>"+jsonArray.toString()+":::::::jsonArray.size==>"+jsonArray.length());
    	 for(int i=0;i<jsonArray.length();i++){
    		 JSONObject jsonObj=jsonArray.getJSONObject(i);
    		 PlanLogInfo logInfo=new PlanLogInfo();
    		 
    		 logInfo.setLogDate(jsonObj.getString("CreateTime"));
    		 logInfo.setLogPerson(jsonObj.getString("CreateUser"));
    		 logInfo.setLogRemark(jsonObj.getString("Remark"));
    		 logInfo.setLogState(jsonObj.getString("Status"));
    		 
    		 PlanLoglists.add(logInfo);
    	 }
		return PlanLoglists;
     }
     
    /**
     * 解析返回值 
     */
   public static List<Map<String, String>> AnalysisForReturnValue(String jsonstr){
	   ArrayList<Map<String,String>> lists=new ArrayList<Map<String,String>>();
	   try {
		JSONObject jsonObject=new JSONObject(jsonstr);
		Map<String,String> map=new HashMap<String, String>();
		map.put("success", jsonObject.getString("success"));
		map.put("message",jsonObject.getString("message"));
		lists.add(map);
	} catch (JSONException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	return lists;
	   
   }
   /**
    * 解析编辑日期值 
 * @throws JSONException 
    */
  public static String AnalysEditDateValue(String jsonstr) throws JSONException{
	   ArrayList<Map<String,String>> lists=new ArrayList<Map<String,String>>();
	   JSONObject jsonObject=new JSONObject(jsonstr).getJSONObject("message");
	   Log.i("yz", "editdate String===>"+jsonObject.getString("EditDate"));
	return jsonObject.getString("EditDate");
	   
  }
}
