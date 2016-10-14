package com.wanfang.Adapter;

import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.sax.StartElementListener;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.Login.UserLogin.R;
import com.wanfang.Activity.OEMInfoActivity;
import com.wanfang.JavaBean.PurchaPlanDetailInfo;
import com.wanfang.application.myApplication;

public class PurchaPlanDetailAdapter extends BaseAdapter {
	List<PurchaPlanDetailInfo> data;
	Context context;
	private Button openOEMInfo;
	private String TransNo;
	private myApplication app;
    public PurchaPlanDetailAdapter(Context context,List<PurchaPlanDetailInfo> data,String TransNo){
    	this.data=data;
    	this.context=context;
    	this.TransNo=TransNo;
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
			arg1=infalater.inflate(R.layout.purchaplan_detail_adapter_layout, null);
			
			viewHolder=new ViewHolder();
			viewHolder.SKU=(TextView) arg1.findViewById(R.id.sku_item);
			
			viewHolder.WarnQuantity=(TextView) arg1.findViewById(R.id.WarnQuantity_item);
			
			viewHolder.PurchaQuantity=(TextView) arg1.findViewById(R.id.PurchaQuantity_item);
			
			viewHolder.TotalAmount=(TextView) arg1.findViewById(R.id.TotalAmount_item);
			
			viewHolder.Spec=(TextView) arg1.findViewById(R.id.Spec_item);
			
			viewHolder.TotalAmount=(TextView) arg1.findViewById(R.id.TotalAmount_item);
			
			viewHolder.SaleStock=(TextView) arg1.findViewById(R.id.SaleStock_item);
			
			viewHolder.SevenSaleQuantity=(TextView) arg1.findViewById(R.id.SevenSaleQuantity_item);
			
			arg1.setTag(viewHolder);
		}else{
			viewHolder=(ViewHolder) arg1.getTag();
		}
		
		final PurchaPlanDetailInfo pur=data.get(arg0);
		viewHolder.SKU.setText(pur.getSKU());
		Log.i("yizhao", "TransNo>>>"+pur.getSKU());
		viewHolder.WarnQuantity.setText(pur.getWarnQuantity());
		viewHolder.PurchaQuantity.setText(pur.getPurchaQuantity());
		viewHolder.TotalAmount.setText(pur.getTotalAmount());
		
		
		viewHolder.Spec.setText(""+pur.getSpec());
		viewHolder.TotalAmount.setText("￥"+pur.getTotalAmount());
		viewHolder.SaleStock.setText(pur.getSaleStock());
		
		viewHolder.SevenSaleQuantity.setText(pur.getSevenSaleQuantity());
		
		openOEMInfo=(Button) arg1.findViewById(R.id.OEM_button_item_table);
		openOEMInfo.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				if(app.CheckNetWorkConnection(context)){
					Intent intent=new Intent(context,OEMInfoActivity.class);
					intent.putExtra("TransNo", TransNo);
					intent.putExtra("SKU", pur.getSKU());
					context.startActivity(intent);
				}else{
					Toast.makeText(context, R.string.network_error, Toast.LENGTH_SHORT).show();
				}
				
			}
		});
		return arg1;
	}
	
 class ViewHolder{
	 TextView SKU;//SKU
	 TextView WarnQuantity;//预警数量
	 TextView PurchaQuantity;//采购数量
	 TextView TotalAmount;//总金额
	 TextView Spec;//规格
	 TextView SaleStock;//销售库存
	 TextView SevenSaleQuantity;//七天销售数

 }
 
}
