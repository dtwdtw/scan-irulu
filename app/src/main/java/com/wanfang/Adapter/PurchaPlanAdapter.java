package com.wanfang.Adapter;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.zip.Inflater;

import com.Login.UserLogin.R;
import com.wanfang.JavaBean.PurchaPlanInfo;
import com.wanfang.application.myApplication;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class PurchaPlanAdapter extends BaseAdapter {
	List<PurchaPlanInfo> data;
	Context context;
	//myApplication app;
    public PurchaPlanAdapter(Context context,List<PurchaPlanInfo> data){
    	this.data=data;
    	this.context=context;
    	//app=(myApplication) context.getApplicationContext();
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
			arg1=infalater.inflate(R.layout.purchaplan_adapter_layout, null);
			viewHolder=new ViewHolder();
			viewHolder.TransNo=(TextView) arg1.findViewById(R.id.number_item);
			
			viewHolder.DepartmentName=(TextView) arg1.findViewById(R.id.dept_name_item);
			
			viewHolder.TransDate=(TextView) arg1.findViewById(R.id.create_time_item);
			
			viewHolder.EditUser=(TextView) arg1.findViewById(R.id.op_person_item);
			
			viewHolder.Status=(TextView) arg1.findViewById(R.id.state_item);
			
			viewHolder.TotalAmount=(TextView) arg1.findViewById(R.id.money_item);
			
			viewHolder.Remark=(TextView) arg1.findViewById(R.id.remark_item);
			
			arg1.setTag(viewHolder);
		}else{
			viewHolder=(ViewHolder) arg1.getTag();
		}
		
		PurchaPlanInfo pur=data.get(arg0);
		viewHolder.TransNo.setText(pur.getTransNo());
		Log.i("yizhao", "TransNo>>>"+pur.getTransNo());
		viewHolder.DepartmentName.setText(pur.getDepartmentName());
		viewHolder.TransDate.setText(pur.getTransDate());
		viewHolder.EditUser.setText(pur.getEditUser());
		if(pur.getStatus()==2){
			viewHolder.Status.setText(R.string.unaudited);
		}else if(pur.getStatus()==3){
			viewHolder.Status.setText(R.string.audit);
		}
		
		//viewHolder.Status.setText(""+pur.getStatus());
		viewHolder.TotalAmount.setText("￥"+pur.getTotalAmount());
		Log.i("yz", "pur.getRemark()>>>>"+pur.getRemark());
		if(!pur.getRemark().equals("null")){
			viewHolder.Remark.setText(pur.getRemark());
		}else{
			viewHolder.Remark.setText(R.string.none);
		}
		
		return arg1;
	}
	
 class ViewHolder{
	 TextView TransNo;//采购计划单号
	 TextView DepartmentName;//所属部门名称
	 TextView TransDate;//采购创建时间
	 TextView EditUser;//操作人
	 TextView Status;//状态
	 TextView TotalAmount;//总金额
	 TextView Remark;//备注
	// TextView EditDate;//操作日期
	 
	// TextView DepartmentIds;//所属部门编号
 }
 public String DateChange(String date){
	  date= GetNumber(date); 
	  Date da=new Date(Long.valueOf(date));
	  SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm");
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
}
