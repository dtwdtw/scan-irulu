package com.wanfang.frament;

import com.Login.UserLogin.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

/**
 * 采购到货Fragment
 * @author yizhao
 *
 */

public class PurchArrivalFragment extends Fragment implements OnClickListener {

	public final static String CITY_LIST_POSITION = "position";
	int mCurrentPosition = -1;
	OnMenuPurchArrivalClickedListener mCallback;
	private TextView UserName;
	
	// Container Activity must implement this interface
    public interface OnMenuPurchArrivalClickedListener {
        public void onMenuButtonClicked();
    }
    
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        
        // This makes sure that the container activity has implemented
        // the callback interface. If not, it throws an exception
        try {
            mCallback = (OnMenuPurchArrivalClickedListener) activity;
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
		View view = inflater.inflate(R.layout.purcharrival_fragment_layout, null);
		Intent intent=getActivity().getIntent();
		UserName= (TextView) view.findViewById(R.id.show_userName);
		UserName.setText(intent.getStringExtra("MAP_USERNAME"));
		Button bt_menu = (Button) view.findViewById(R.id.bt_menu);
		bt_menu.setOnClickListener(this);
		return view;
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

}
