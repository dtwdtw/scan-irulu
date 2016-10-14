package com.wanfang.Adapter;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.zip.Inflater;

import com.Login.UserLogin.R;
import com.wanfang.JavaBean.OEMInfo;
import com.wanfang.JavaBean.PurchaPlanDetailInfo;
import com.wanfang.JavaBean.PurchaPlanInfo;
import com.wanfang.application.myApplication;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.TextView;
/**
 * 供应商详细信息
 * @author yizhao
 *
 */
public class SubPurchaPlanDetailAdapter extends BaseAdapter {
	List<OEMInfo> data;
	Context context;
	myApplication app;
    public SubPurchaPlanDetailAdapter(Context context,List<OEMInfo> data){
    	this.data=data;
    	this.context=context;
    	app=(myApplication) context.getApplicationContext();
    }
	
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return this.data.size();
	}

	@Override
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return this.data.get(arg0);
	}

	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return arg0;
	}

	@Override
	public View getView(int arg0, View arg1, ViewGroup arg2) {
		// TODO Auto-generated method stub
		LayoutInflater infalater=LayoutInflater.from(context);
		ViewHolder viewHolder=null;
		if(arg1==null){
			arg1=infalater.inflate(R.layout.oem_item_layout, null);
			viewHolder=new ViewHolder();
			viewHolder.OEMName=(TextView) arg1.findViewById(R.id.ome_name_item);
			
			viewHolder.Quantity=(TextView) arg1.findViewById(R.id.oem_Quantity_item);
			
			viewHolder.UnitPrice=(TextView) arg1.findViewById(R.id.oem_unitprice_item);
			
			viewHolder.PredictDeliveryDate=(TextView) arg1.findViewById(R.id.oem_PredictDeliveryDate_item);
			
			viewHolder.isTaxFree=(CheckBox) arg1.findViewById(R.id.isTaxFree_item);
			
			viewHolder.Currency=(TextView) arg1.findViewById(R.id.Currency_item);
			
			arg1.setTag(viewHolder);
		}else{
			viewHolder=(ViewHolder) arg1.getTag();
		}
		
		OEMInfo pur=data.get(arg0);
		Log.i("OEMADAPTER", "pur.getOEMName()==="+pur.getOEMName());
		viewHolder.OEMName.setText(pur.getOEMName());
		viewHolder.Quantity.setText(pur.getQuantity());
		viewHolder.UnitPrice.setText(pur.getCurrencySymbol()+pur.getUnitPrice());
		viewHolder.PredictDeliveryDate.setText(pur.getPredictDeliveryDate().substring(0, 10));
		//viewHolder.isTaxFree.setText(pur.getIsTaxFree());
		if(pur.getIsTaxFree().equals("false")){
			viewHolder.isTaxFree.setChecked(false);
		}else{
			viewHolder.isTaxFree.setChecked(true);
		}
		viewHolder.Currency.setText(pur.getCurrency());
		return arg1;
	}
	
 class ViewHolder{
	 TextView OEMName;//供应商名称
	 TextView Quantity;//数量
	 TextView UnitPrice;//单价
	 TextView PredictDeliveryDate;//预计到货日期
	 CheckBox isTaxFree;//是否含税判断
	 TextView Currency;//币种
 }
  
}
