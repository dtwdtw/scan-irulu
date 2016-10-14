package com.wanfang.frament;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.Login.UserLogin.R;
import com.Login.UserLogin.R.menu;
import com.wanfang.Activity.MainActivity;
import com.wanfang.HttpUtils.HttpUtils;
import com.wanfang.HttpUtils.JsonUtils;
import com.wanfang.JavaBean.UserInfo;
import com.wanfang.Util.Const;
import com.wanfang.application.myApplication;
import com.wanfang.frament.PurchaPlanFragment.GetPurchPlanInfoHandler;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.webkit.WebView.FindListener;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;


public class MenuFragment extends ListFragment implements OnClickListener {
	
	OnListSelectedListener mCallback;
	private ListView listView;
	//public static String[] LISTS = {"采购计划","下单预估","采购订单","采购到货"};
	private TextView touchPage,touchLogOff,touchAbout;
	
	List<Map<String,Object>> mData;
	private myApplication app;
	private UserInfo user;
	private int purchaPlanCount;//采购计划未审核数目
	private String userName,password;
	Intent intent;
	private FrameLayout showCircle;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		 Log.i("yz", "onCreate!");
		 app=(myApplication) getActivity().getApplication();
		 intent=getActivity().getIntent();
//		setListAdapter(new ArrayAdapter<String>(getActivity(),
//				R.layout.menu_item_layout, LISTS));
		 if(app.CheckNetWorkConnection(getActivity())){ //开启线程获取用户数据
			 new Thread(new GetPurchPlanInfoHandler()).start();
		}else{
			
			Message msg=new Message();
			msg.what=520;
		}
		 
		     mData=getData();
		     MenuListAdapter menuAdapter=new MenuListAdapter(getActivity());
		     setListAdapter(menuAdapter);
		 
	}

	private List<Map<String,Object>> getData(){
		List<Map<String,Object>> list=new ArrayList<Map<String,Object>>();
		Map<String, Object> map=new HashMap<String, Object>();
		map.put("title", "采购计划");
		list.add(map);
		map=new HashMap<String, Object>();
		map.put("title", "下单预估");
		list.add(map);
		map=new HashMap<String, Object>();
		map.put("title", "采购订单");
		list.add(map);
		
		return list;
		
	}
    // Container Activity must implement this interface
    public interface OnListSelectedListener {
        public void onListItemSelected(int position);
        public void onHomeSelected(View view);
        public void onLogOffSelected(View view);
        public void onAboutSelected(View view);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        Log.i("yz", "onAttach");
        try {
            mCallback = (OnListSelectedListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnHeadlineSelectedListener");
        }
    }
    
    @Override
    public void onStart() {
        super.onStart();
        Log.i("yz", " Menu onStart()");
        if (getFragmentManager().findFragmentById(R.id.menu) != null) {
            getListView().setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        }
    }

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
	     Log.i("yz", "onCreateView");
	     View view = inflater.inflate(R.layout.menu_fragment_layout, null);
	     touchPage=(TextView) view.findViewById(R.id.touch_page);
	     touchPage.setOnClickListener(this);
	     touchLogOff=(TextView) view.findViewById(R.id.logoff);
	     touchLogOff.setOnClickListener(this);
	      touchAbout=(TextView) view.findViewById(R.id.about);
	      touchAbout.setOnClickListener(this);
		return view;
	}

	public void onListItemClick(ListView parent, View v, int position, long id) {
		 
        mCallback.onListItemSelected(position);
        getListView().setItemChecked(position, true);
        for(int i=0;i<getListView().getCount();i++){
        	
        	if(position==i){
        		//View view=getListView().getChildAt(getListView().getCount()-1-i);
        		Log.i("yz", "into::"+position+"::::::"+i);
        		getListView().getChildAt(i).setBackgroundColor(R.drawable.login_in_btn_default);
        		
        	}else{
        		//View view=getListView().getChildAt(getListView().getCount()-1-i);
        		getListView().getChildAt(i).setBackgroundDrawable(null);
        	}
        }
        
//		Toast.makeText(getActivity(),
//				"You have selected " + LISTS[position], Toast.LENGTH_SHORT).show();
	}
	 
	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		switch (arg0.getId()) {
		case R.id.touch_page:
			 mCallback.onHomeSelected(arg0);
			break;
		case R.id.logoff:
			 mCallback.onLogOffSelected(arg0);
			break;
		case R.id.about:
			mCallback.onAboutSelected(arg0);
			break;
		default:
			break;
		}
		
		 
		
	}

	public final class ViewHolder{
		public ImageView img;
		public TextView title;
		public TextView number;
	}
	
	public class MenuListAdapter extends BaseAdapter{
		private LayoutInflater mInflater;
		public MenuListAdapter(Context context){
			this.mInflater=LayoutInflater.from(context);
		}

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return mData.size();
		}

		@Override
		public Object getItem(int arg0) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public long getItemId(int arg0) {
			// TODO Auto-generated method stub
			return 0;
		}

		@Override
		public View getView(int arg0, View arg1, ViewGroup arg2) {
			// TODO Auto-generated method stub
			ViewHolder holder=null;
			if(arg1==null){
						holder=new ViewHolder();
						arg1=mInflater.inflate(R.layout.menu_item_layout_yi, null);
						 showCircle=(FrameLayout) arg1.findViewById(R.id.show_number);
					     showCircle.setVisibility(View.INVISIBLE);
						holder.title=(TextView) arg1.findViewById(R.id.menu_title);
						holder.number=(TextView) arg1.findViewById(R.id.show_number_txt);
						arg1.setTag(holder);
				}else{
						holder=(ViewHolder) arg1.getTag();
					}
					holder.title.setText((String) mData.get(arg0).get("title"));
					if(mData.get(arg0).get("title").equals("采购计划")){
						if(purchaPlanCount!=0){
							 showCircle.setVisibility(View.VISIBLE);
							holder.number.setText(purchaPlanCount+"");
							
						}
					}else{
						showCircle.setVisibility(View.INVISIBLE);
					}
					
			return arg1;
		}
	}
	class GetPurchPlanInfoHandler implements Runnable{

		@Override
		public void run() {
			// TODO Auto-generated method stub
		
			 userName=intent.getStringExtra("MAP_USERNAME");
		     password=intent.getStringExtra("password");
		     getOddInfo(userName, password);
		}
    }
	/**
     * 获取未审核的订单数量
     */
    private void getOddInfo(String name,String psw){
    	String data=null;
    	name=name.trim();
    	psw=psw.trim();
        String validateURL="http://192.168.1.79:8088/app/LoginCheck?user="+name+"&pwd="+psw;
        
        		try {
					data=HttpUtils.getData(validateURL);
					Log.i("yz", "DATA==>"+data);
					if(data!=null || !data.equals("")){
	        			 user=JsonUtils.parseUserFromJson(data);
	        			 purchaPlanCount=user.getpurchasePlanCount();
	        			 app.setpurchaPlanCount(purchaPlanCount);
	        			 Log.i("yz", "purchaPlanCount===>"+purchaPlanCount);
	        		}else{
	        			Message msg=new Message();
	        			msg.what=Const.NET_REQUEST_FAIL;
	        			handler.sendMessage(msg);
	        		}
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
    }
    Handler handler=new Handler(){
		@Override
		public void handleMessage(Message msg){
			super.handleMessage(msg);
			switch (msg.what) {
			
			case Const.NET_REQUEST_FAIL:
				Toast.makeText(getActivity(), R.string.requset_fial, Toast.LENGTH_SHORT).show();
				break;
			case 520:
				Toast.makeText(getActivity(), R.string.network_error, Toast.LENGTH_SHORT).show();
				break;
			default:
				break;
			}
			
		}
	};
}
