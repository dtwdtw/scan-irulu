package com.wanfang.Adapter;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.zip.Inflater;

import com.Login.UserLogin.R;
import com.wanfang.JavaBean.OEMInfo;
import com.wanfang.JavaBean.PlanLogInfo;
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

public class PlanLogAdapter extends BaseAdapter {
	List<PlanLogInfo> data;
	Context context;
	myApplication app;
    public PlanLogAdapter(Context context,List<PlanLogInfo> data){
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
			arg1=infalater.inflate(R.layout.plan_log_item_layout, null);
			viewHolder=new ViewHolder();
			viewHolder.LogDate=(TextView) arg1.findViewById(R.id.plan_log_date);
			
			viewHolder.LogPerson=(TextView) arg1.findViewById(R.id.plan_log_person);
			
			viewHolder.LogState=(TextView) arg1.findViewById(R.id.plan_log_persons);
			
			viewHolder.LogRemark=(TextView) arg1.findViewById(R.id.plan_log_remark);
			
			arg1.setTag(viewHolder);
		}else{
			viewHolder=(ViewHolder) arg1.getTag();
		}
		
		
		PlanLogInfo pur=data.get(arg0);
		viewHolder.LogDate.setText(pur.getLogDate());
		viewHolder.LogPerson.setText(pur.getLogPerson());
		viewHolder.LogState.setText(setStateToString(pur.getLogState()));
		viewHolder.LogRemark.setText(pur.getLogRemark());
		return arg1;
	}
	
 class ViewHolder{
	 TextView LogDate;
	 TextView LogPerson;
	 TextView LogState;
	 TextView LogRemark;
 }
  private String setStateToString(String stateValue){
	  String state = null;
	  int value=Integer.parseInt(stateValue);
	  switch (value) {
	       case 0:
		 state="待处理";
		break;
	       case 1:
	    	   state="已提交";
	    	   break;
	       case 2:
	    	   state="已审核";
	    	   break;
	       case 3:
	    	   state="拒绝";
	    	   break;
	       case 4:
	    	   state="已完成";
	    	   break;
	       case 5:
	    	   state="已取消";
	    	   break;
	default:
		break;
	}
	return state;
  }
}
