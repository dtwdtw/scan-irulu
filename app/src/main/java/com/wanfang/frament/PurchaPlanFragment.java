package com.wanfang.frament;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONException;

import com.Login.UserLogin.R;
import com.wanfang.Activity.LoginActivity;
import com.wanfang.Activity.ReviewActivity;
import com.wanfang.Adapter.PurchaPlanAdapter;
import com.wanfang.HttpUtils.HttpUtils;
import com.wanfang.HttpUtils.JsonUtils;
import com.wanfang.JavaBean.PurchaPlanInfo;
import com.wanfang.Util.Const;
import com.wanfang.Util.MyPopupWindow;
import com.wanfang.application.myApplication;
import com.wanfang.view.LoadingDialog;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.webkit.WebView.FindListener;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AbsListView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

/**
 * 采购计划Fragment
 * @author yizhao
 *
 */
public class PurchaPlanFragment extends Fragment implements OnClickListener {

	public final static String CITY_LIST_POSITION = "position";
	public final static int NETWORK_REQUEST=0;
	public final static int NO_DATA=1;
	int mCurrentPosition = -1;
	OnMenuTwoClickedListener mCallback;
	private TextView UserName;
	private List<PurchaPlanInfo> purInfoLists;
	private ListView listView;
	private PurchaPlanAdapter purAdapter;
	private boolean isDataLoadFinish=false;
	private LoadingDialog proDialog;
	private TextView NoDataTipTxt,NotFindDatatip; 
	View view;
	private myApplication app;
	private SharedPreferences share;
	private int StateCode=-1;
	private Button RefreshButton;
	private boolean isRefresh=false,isRefreshSub=false;
	private MyPopupWindow pop;
	private RelativeLayout searchLayout;
	private Button searchBtn;
	private EditText searchEditText;
	private boolean isShowSearchBox=false;
	// Container Activity must implement this interface
    public interface OnMenuTwoClickedListener {
        public void onMenuButtonClicked();
    }
    
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
       // proDialog=ProgressDialog.show(getActivity(), "获取数据","正在获取网络数据..", true,true);
        // This makes sure that the container activity has implemented
        // the callback interface. If not, it throws an exception
        try {
            mCallback = (OnMenuTwoClickedListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnMenuButtonClickedListener");
        }
    }
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		//proDialog=ProgressDialog.show(getActivity(), "获取数据","正在获取网络数据..", true,true);
		Log.i("yz", "fragment state==>onCreateView()");
		if (savedInstanceState != null) {
			mCurrentPosition = savedInstanceState.getInt(CITY_LIST_POSITION);
		}
		app=(myApplication) getActivity().getApplication();
		isRefresh=false;
		Log.i("yz", "Content position==---------------------------->"+mCurrentPosition);
	    view = inflater.inflate(R.layout.purchaplan_fragment_layout, null);
	    isShowSearchBox=false;
	    searchLayout=(RelativeLayout) view.findViewById(R.id.search_layout);//搜索框
	    searchLayout.setVisibility(View.GONE);
	    searchBtn=(Button) view.findViewById(R.id.search_button);
	    searchBtn.setOnClickListener(this);
	    searchEditText=(EditText) view.findViewById(R.id.etSearch);//查询编辑框
	    listView=(ListView) view.findViewById(R.id.listview);
	    listView.setVisibility(View.INVISIBLE);
	    listView.setOnItemClickListener(itemListener);
	    listView.setOnScrollListener(listViewScrollListener);
	    RefreshButton=(Button) view.findViewById(R.id.refresh_btn);
	    RefreshButton.setOnClickListener(this);
		UserName=(TextView) view.findViewById(R.id.show_userName);
		NoDataTipTxt=(TextView) view.findViewById(R.id.no_data_tip);
		NotFindDatatip=(TextView) view.findViewById(R.id.find_no_data_tip);
		Intent intent=getActivity().getIntent();
		if(intent.getStringExtra("MAP_USERNAME")==null || intent.getStringExtra("MAP_USERNAME").equals("")){
			UserName.setText("null");
		}else{
			UserName.setText(intent.getStringExtra("MAP_USERNAME"));
		}
		
		
		proDialog = new LoadingDialog(getActivity(), R.style.LoadingDialog,2);
		proDialog.setCancelable(false);
		proDialog.show();
		if(app.CheckNetWorkConnection(getActivity())){
			 new Thread(new GetPurchPlanInfoHandler()).start();
		}else{
			if(proDialog==null){
				proDialog.dismiss();
			}
			Message msg=new Message();
			msg.what=520;
			myHandler.sendMessage(msg);
		}
		
		Button bt_menu = (Button) view.findViewById(R.id.bt_menu);
		bt_menu.setOnClickListener(this);
		NoDataTipTxt.setVisibility(View.INVISIBLE);
		NotFindDatatip.setVisibility(View.INVISIBLE);
		pop=new MyPopupWindow(getActivity());
		pop.addItems(new String[]{"查询","排序","刷新"});
		pop.setOnItemClickListener(popListener);
		return view;
	}
	OnItemClickListener popListener=new OnItemClickListener() {

		@Override
		public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
				long arg3) {
			// TODO Auto-generated method stub
			if(arg2==2){ //刷新
				if(app.CheckNetWorkConnection(getActivity())){
					if(isShowSearchBox){
						Log.i("yizhao", "isShowSearchBox>>>>>"+isShowSearchBox);
						LaunchSearch();
						//LaunchThread();
					}else{
						LaunchThread();
					}
				}else{
					myHandler.sendEmptyMessage(520);
				}
			
				pop.dismss();
			}else if(arg2==0){
				searchLayout.setVisibility(View.VISIBLE);
				isShowSearchBox=true;
				pop.dismss();
			}
		}
	};
	
	public void LaunchSearch(){
		isRefreshSub=true;
		
		search(5);
	}
   public void LaunchThread(){
	    isRefresh=true;
	    proDialog = new LoadingDialog(getActivity(), R.style.LoadingDialog,5);
		proDialog.setCancelable(false);
		proDialog.show();
	   new Thread(new GetPurchPlanInfoHandler()).start();
   }
	@Override
	public void onStart() {
		super.onStart();
		Log.i("yz", "fragment state==>onStart()");
		Bundle args = getArguments();
		if (args != null) {
			updateContentView(args.getInt(CITY_LIST_POSITION));
		} else if (mCurrentPosition != -1) {
			updateContentView(mCurrentPosition);
		}
		
		StateCode=share.getInt("stateCode", -1);
		Log.i("print","StateCode==>"+StateCode);
		if(StateCode==1){
			new Thread(new GetPurchPlanInfoHandler()).start();
		}
	}

	@Override
	public void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);

		outState.putInt(CITY_LIST_POSITION, mCurrentPosition);
	}

	public void updateContentView(int position) {
		Log.i("yz", "update position----->"+position);
		
		TextView article = (TextView) getActivity()
				.findViewById(R.id.tv_result);
		//article.setText(MenuFragment.LISTS[position]);
		mCurrentPosition = position;
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.bt_menu:
			mCallback.onMenuButtonClicked();
			break;
		case R.id.refresh_btn: //刷新操作
			pop.showAsDropDown(v);
	        //LaunchThread();
			break;
		case R.id.search_button:
			 InputMethodManager imm=(InputMethodManager) getActivity().getSystemService(getActivity().INPUT_METHOD_SERVICE);
			   imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
			   if(app.CheckNetWorkConnection(getActivity())){
				   search(4);
			   }else{
				   myHandler.sendEmptyMessage(520);
			   }
			
			break;
		default:
			break;
		}
	}
	/**
	 * 搜索功能
	 */
   private void search(int flag){
	if(searchEditText.getText().toString().equals("")|| searchEditText.getText().toString()==null){
		 proDialog = new LoadingDialog(getActivity(), R.style.LoadingDialog,flag);
		 proDialog.setCancelable(false);
		 proDialog.show();
		   new Thread(new GetPurchPlanInfoHandler()).start();
	}else{
		proDialog = new LoadingDialog(getActivity(), R.style.LoadingDialog,flag);
		proDialog.setCancelable(false);
		proDialog.show();
		if(purInfoLists!=null || purInfoLists.size()!=0){
			List<PurchaPlanInfo> newData=searchData(searchEditText.getText().toString(), purInfoLists);
			Log.i("yz", "new list size===>"+newData.size());
			if(newData==null || newData.size()==0){
				myHandler.sendEmptyMessage(Const.NOT_FOUND_DATA);
			}else{
				 NoDataTipTxt.setVisibility(View.INVISIBLE);
   			     listView.setVisibility(View.VISIBLE);
   			     NotFindDatatip.setVisibility(View.INVISIBLE);
   			     if(isRefreshSub){
   			    	myHandler.sendEmptyMessage(Const.SEARCH_FRESH);
   			     }
   			     if(proDialog!=null){
   			    	 proDialog.dismiss();
   			     }
				setupListView(newData);
				purAdapter.notifyDataSetChanged();
			}
		}
	}
   }
/**
 * 
 * @param str 查询的条件字符
 * @param dataSource 数据源
 * @return 
 */
	private List<PurchaPlanInfo> searchData(String str,List<PurchaPlanInfo> dataSource){
		List<PurchaPlanInfo> newLists=new ArrayList<PurchaPlanInfo>();;
		for(int i=0;i<dataSource.size();i++){
			
			Log.i("yz", "dataSource==>"+dataSource.size());
			Log.i("yz", "dataSource.get(i).getTransNo()==>"+dataSource.get(i).getTransNo());
			Log.i("yz", "str==>"+str);
			if(dataSource.get(i).getTransNo().contains(str) || 
					dataSource.get(i).getDepartmentName().contains(str)
					|| dataSource.get(i).getEditUser().contains(str) ||
					dataSource.get(i).getRemark().contains(str) ||
					String.valueOf(dataSource.get(i).getStatus()).contains(str)
					|| dataSource.get(i).getTrackingPlan().contains(str) ||
					dataSource.get(i).getTransDate().contains(str) || 
					dataSource.get(i).getTotalAmount().equals(str) 
					){
				PurchaPlanInfo newInfo=new PurchaPlanInfo();
				    newInfo.setDepartmentName(dataSource.get(i).getDepartmentName());
				    newInfo.setEditDate(dataSource.get(i).getEditDate());
				    newInfo.setEditUser(dataSource.get(i).getEditUser());
				    newInfo.setRemark(dataSource.get(i).getRemark());
				    newInfo.setStatus(dataSource.get(i).getStatus());
				    newInfo.setTotalAmount(dataSource.get(i).getTotalAmount());
				    newInfo.setTrackingPlan(dataSource.get(i).getTrackingPlan());
				    newInfo.setTransDate(dataSource.get(i).getTransDate());
				    newInfo.setTransNo(dataSource.get(i).getTransNo());
				newLists.add(newInfo);
			}
		 }
		return newLists;
	}
	/**
     * 获取采购计划的信息
     */
    private List<PurchaPlanInfo> getUserInfo(String userName){
    	Log.i("yz", "请求参数用户名:::"+userName);
    	String data=null;
        String validateURL="http://192.168.1.79:8088/app/GetPurchasePlanAuditInfo?user="+userName;
        		try {
					data=HttpUtils.getData2(validateURL,myHandler);
					if(data!=null || !data.equals("")){
						Log.i("yz", "data=====================>"+data.toString());
						if(data.contains("没有订单数据")){
							return null;
						}else{
						purInfoLists=JsonUtils.AnalysisForPurchPlanInfo(data);
						
						Log.i("yz", "purInfoLists=====================>"+purInfoLists.size());
						}
	        		}else{
	        			Message msg=new Message();
	        			msg.what=Const.NET_REQUEST_FAIL;
	        			myHandler.sendMessage(msg);
	        		}
					
				} catch (Exception e) {
					// TODO Auto-generated catch block
					if(proDialog!=null){
						proDialog.dismiss();
					}
					e.printStackTrace();
				}
        		
        		return purInfoLists;
    }
    
    class GetPurchPlanInfoHandler implements Runnable{

		@Override
		public void run() {
			// TODO Auto-generated method stub
			
		   if( getUserInfo(UserName.getText().toString())==null){//后续需要改为当前登录的用户名 jonny.m
			   Message message=new Message();
				message.what=NO_DATA;
				myHandler.sendMessage(message);
			   return;
		   }else{
			   Message message=new Message();
				message.what=NETWORK_REQUEST;
				myHandler.sendMessage(message);
		   }
			
		}
    }
    private void setupListView(List<PurchaPlanInfo> data){
    	purAdapter=new PurchaPlanAdapter(getActivity(),data );
    	
    	listView.setAdapter(purAdapter);
    	
    }
    
    @Override
    public void onCreate(Bundle savedInstanceState){
    	super.onCreate(savedInstanceState);
    //	proDialog=ProgressDialog.show(getActivity(), "获取数据","正在获取网络数据..", true,true);
    	share=getActivity().getSharedPreferences("stateValue", 0);
    	share.edit().putInt("stateCode", -1).commit();
    }
    Handler myHandler=new Handler(){
    	public void handleMessage(Message msg){
    		switch(msg.what){
    		case NETWORK_REQUEST:
    			Log.i("yizhao", "purInfoLists==>"+purInfoLists.size());
    		   NoDataTipTxt.setVisibility(View.INVISIBLE);
    		   NotFindDatatip.setVisibility(View.INVISIBLE);
  			   listView.setVisibility(View.VISIBLE);
    			setupListView(purInfoLists);
    			if(isRefresh==true){
    				Toast.makeText(getActivity(), R.string.refresh_success, Toast.LENGTH_SHORT).show();
    			}
    			if(proDialog!=null){
					proDialog.dismiss();
				}
    			break;
    		case NO_DATA:
    			 if(proDialog!=null){
 					proDialog.dismiss();
 				}
    			  NoDataTipTxt.setVisibility(View.VISIBLE);
   			      listView.setVisibility(View.INVISIBLE);
   			      NotFindDatatip.setVisibility(View.INVISIBLE);
    			break;
    		case 520:
    			Toast.makeText(getActivity(), R.string.network_error, Toast.LENGTH_SHORT).show();
    			break;
    		case Const.NET_REQUEST_FAIL:
    			Toast.makeText(getActivity(), R.string.network_error, Toast.LENGTH_SHORT).show();
    			break;
    		case Const.NOT_FOUND_DATA:
    			if(proDialog!=null){
  					proDialog.dismiss();
  				}
    			  NoDataTipTxt.setVisibility(View.INVISIBLE);
 			      listView.setVisibility(View.INVISIBLE);
 			      NotFindDatatip.setVisibility(View.VISIBLE);
    			break;
//    		case Const.LAUNCH_SEARCH:
//    			search();
//    			break;
    		case Const.SEARCH_FRESH:
    			 if(proDialog!=null){
  					proDialog.dismiss();
  				}
    			Toast.makeText(getActivity(), R.string.refresh_success, Toast.LENGTH_SHORT).show();
    			break;
    		case 101:
    			 if(proDialog!=null){
   					proDialog.dismiss();
   				}
     			Toast.makeText(getActivity(), R.string.requset_fial, Toast.LENGTH_SHORT).show();
     			break;
    		}
    	}
    };
    
    /**
     * 点击事件的处理
     */
    OnItemClickListener itemListener=new OnItemClickListener(){

		@Override
		public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
				long arg3) {
			// TODO Auto-generated method stub
			Log.i("yizhao", "current click position===>"+arg2);
		if(app.CheckNetWorkConnection(getActivity())){
			Intent intent=new Intent(getActivity(),ReviewActivity.class);
			Bundle bundle=new Bundle();
			Log.i("yizhao", "data no===>"+purInfoLists.get(arg2).getTransNo());
			bundle.putString("TransNo", purInfoLists.get(arg2).getTransNo());
			bundle.putString("EditDate", app.DateChange(purInfoLists.get(arg2).getEditDate()));
			bundle.putString("EditUser", purInfoLists.get(arg2).getEditUser());
			bundle.putString("Remark", purInfoLists.get(arg2).getRemark());
			bundle.putString("TotalAmount", purInfoLists.get(arg2).getTotalAmount());
			bundle.putString("DepartmentName", purInfoLists.get(arg2).getDepartmentName());
			bundle.putString("TrackingPlan", purInfoLists.get(arg2).getTrackingPlan());
			bundle.putInt("Status", purInfoLists.get(arg2).getStatus());
			bundle.putString("UserName",UserName.getText().toString());
			intent.putExtras(bundle);
			startActivity(intent);
			getActivity().overridePendingTransition(R.anim.next_activity_open, R.anim.current_activity_close);
		
		}else{
			myHandler.sendEmptyMessage(520);
		}
				
		}
    	
    };
    
    OnScrollListener listViewScrollListener=new OnScrollListener() {
		
		@Override
		public void onScrollStateChanged(AbsListView arg0, int arg1) {
			// TODO Auto-generated method stub
			switch (arg1) {
			case OnScrollListener.SCROLL_STATE_FLING:
				break;
			case OnScrollListener.SCROLL_STATE_IDLE:
				break;
			case OnScrollListener.SCROLL_STATE_TOUCH_SCROLL:
				searchLayout.setVisibility(View.GONE);
				isShowSearchBox=false;
				break;
			default:
				break;
			}
		}
		@Override
		public void onScroll(AbsListView arg0, int arg1, int arg2, int arg3) {
			// TODO Auto-generated method stub
		}
	};
}
