package com.wanfang.Util;

import java.util.ArrayList;

import android.app.ActionBar.LayoutParams;
import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.Login.UserLogin.R;

public class MyPopupWindow {
    private ArrayList<String> itemList;
    private Context context;
    private PopupWindow popupWindow;
    private ListView listView;
    
    public MyPopupWindow(Context context){
    	this.context=context;
    	itemList=new ArrayList<String>();
    	
    	View view=LayoutInflater.from(context).inflate(R.layout.popup, null);
    	
    	listView=(ListView) view.findViewById(R.id.listview_popup);
    	listView.setAdapter(new PopAdapter());
    	listView.setFocusableInTouchMode(true);
    	listView.setFocusable(true);
    	
    	popupWindow=new PopupWindow(view, 150, LayoutParams.WRAP_CONTENT);
    	//popupWindow=new PopupWindow(view, context.getResources().getDimensionPixelSize(R.dimen.), LayoutParams.WRAP_CONTENT);
    	
    	popupWindow.setBackgroundDrawable(new BitmapDrawable());
    	
    }
    
   public void setOnItemClickListener(OnItemClickListener listener){
	   listView.setOnItemClickListener(listener);
   }
   /**
    * 批量添加
    * @param items
    */
   public void addItems(String[] items){
	   for(String s:items){
		   itemList.add(s);
	   }
   }
   /**
    * 单个添加
    * 
    */
   public void addItem(String item){
	   itemList.add(item);
   }
   
   /**
    * 下拉菜单的显示
    *
    */
   public void showAsDropDown(View parent){
	   popupWindow.showAsDropDown(parent);
	   popupWindow.setFocusable(true);
	   popupWindow.setOutsideTouchable(true);
	   popupWindow.update();
   }
    
  public void dismss(){
	  popupWindow.dismiss();
  }
private final class PopAdapter extends BaseAdapter{

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return itemList.size();
	}

	@Override
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return itemList.get(arg0);
	}

	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return arg0;
	}

	@Override
	public View getView(int arg0, View arg1, ViewGroup arg2) {
		// TODO Auto-generated method stub
		ViewHolder holder=null;
		if(arg1==null){
			arg1=LayoutInflater.from(context).inflate(R.layout.popup_item, null);
			holder=new ViewHolder();
			arg1.setTag(holder);
			holder.groupItem=(TextView) arg1.findViewById(R.id.textview_popup);
		}else{
			holder=(ViewHolder) arg1.getTag();
		}
		holder.groupItem.setText(itemList.get(arg0));
		return arg1;
	}
	private final class ViewHolder{
		TextView groupItem;
	}
	
  }
}
