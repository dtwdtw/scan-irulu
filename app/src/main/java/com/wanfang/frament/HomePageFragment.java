package com.wanfang.frament;

import java.text.SimpleDateFormat;
import java.util.Date;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.Login.UserLogin.R;
import com.wanfang.HttpUtils.HttpUtils;
import com.wanfang.HttpUtils.JsonUtils;
import com.wanfang.JavaBean.UserInfo;
import com.wanfang.Util.Const;
import com.wanfang.application.myApplication;
import com.wanfang.frament.MenuFragment.GetPurchPlanInfoHandler;

/**
 * 首页Fragment
 * @author yizhao
 *
 */
public class HomePageFragment extends Fragment implements OnClickListener {

	public final static String CITY_LIST_POSITION = "position";
	int mCurrentPosition = -1;
	OnMenuHomeClickedListener mCallback;
	private Intent intent;
	private TextView userName;
	private TextView home_flag;
	private TextView current_date_show;
	private TextView purchaplan_tip_show;
	private String userNM,password;
	private UserInfo user;
	private int purchaPlanCount;//采购计划未审核数目
	private myApplication app;
	
	// Container Activity must implement this interface
    public interface OnMenuHomeClickedListener {
        public void onMenuButtonClicked();
    }
    
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        
        // This makes sure that the container activity has implemented
        // the callback interface. If not, it throws an exception
        try {
            mCallback = (OnMenuHomeClickedListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnMenuButtonClickedListener");
        }
    }
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		if (savedInstanceState != null) {
			mCurrentPosition = savedInstanceState.getInt(CITY_LIST_POSITION);
		}
		Log.i("yz", "Content position==---------------------------->"+mCurrentPosition);
		View view = inflater.inflate(R.layout.content_fragment_layout, null);
		app=(myApplication) getActivity().getApplication();
		userName=(TextView) view.findViewById(R.id.user_show);
		intent=getActivity().getIntent();
		userName.setText(intent.getStringExtra("MAP_USERNAME"));
		password=intent.getStringExtra("password");
		Button bt_menu = (Button) view.findViewById(R.id.bt_menu);
		bt_menu.setOnClickListener(this);
		home_flag=(TextView) view.findViewById(R.id.home_flag);
		home_flag.setTypeface(Typeface.MONOSPACE, Typeface.ITALIC);
		current_date_show=(TextView) view.findViewById(R.id.current_date_show);
		current_date_show.setText(Const.getCurrentDate());
		purchaplan_tip_show=(TextView) view.findViewById(R.id.purchaplan_tip_show);
		

			 new Thread(new GetPurchPlanInfoHandler()).start();

			

		return view;
	}


    
    class GetPurchPlanInfoHandler implements Runnable{

		@Override
		public void run() {
			// TODO Auto-generated method stub
		    Message msg=new Message();
		    msg.what=121;
			handler.sendMessageDelayed(msg, 2000);
		}
    }
	@Override
	public void onStart() {
		super.onStart();

		Bundle args = getArguments();
		if (args != null) {
			updateContentView(args.getInt(CITY_LIST_POSITION));
		} else if (mCurrentPosition != -1) {
			updateContentView(mCurrentPosition);
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
		default:
			break;
		}
	}
	  Handler handler=new Handler(){
			@Override
			public void handleMessage(Message msg){
				super.handleMessage(msg);
				switch (msg.what) {
				case 121:
					 purchaplan_tip_show.setText(app.getpurchaPlanCount()+"");
					break;
				default:
					break;
				}
				
			}
		};
}
