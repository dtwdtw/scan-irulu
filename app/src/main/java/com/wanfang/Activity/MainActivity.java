package com.wanfang.Activity;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Fragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import com.Login.UserLogin.R;
import com.slidingmenu.lib.SlidingMenu;
import com.wanfang.Util.Const;
import com.wanfang.application.myApplication;
import com.wanfang.frament.ContentFragment;
import com.wanfang.frament.HomePageFragment;
import com.wanfang.frament.HomePageFragment.OnMenuHomeClickedListener;
import com.wanfang.frament.MenuFragment;
import com.wanfang.frament.MenuFragment.OnListSelectedListener;
import com.wanfang.frament.PlaceOrderFragment;
import com.wanfang.frament.PlaceOrderFragment.OnMenuPlaceClickedListener;
import com.wanfang.frament.PurchArrivalFragment;
import com.wanfang.frament.PurchArrivalFragment.OnMenuPurchArrivalClickedListener;
import com.wanfang.frament.PurchOrderFragment;
import com.wanfang.frament.PurchOrderFragment.OnMenuPurchOrderClickedListener;
import com.wanfang.frament.PurchaPlanFragment;
import com.wanfang.frament.PurchaPlanFragment.OnMenuTwoClickedListener;

/**
 * 操作主界面
 *
 * @author yizhao
 */
public class MainActivity extends FragmentActivity implements OnListSelectedListener, OnMenuTwoClickedListener, OnMenuPlaceClickedListener
        , OnMenuPurchOrderClickedListener, OnMenuPurchArrivalClickedListener, OnMenuHomeClickedListener {


    private SlidingMenu menu;
    private TextView show_name;
    private static boolean isExit = false;
    private myApplication app;
    AlertDialog dialog = null;
    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 0:
                    isExit = false;
                    break;
                case Const.NETWORK_ERROR:
                    Log.i("net", "Const.NETWORK_ERROR");
                    Toast.makeText(MainActivity.this, R.string.network_error, Toast.LENGTH_SHORT).show();
                    break;
                default:
                    break;
            }

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//	        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
//	        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        //   this.requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);//使用自定义标题
        //  this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.frame_content);
        app = (myApplication) getApplication();
        //  getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE, R.layout.title_layout);


        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        MenuFragment menuFragment = new MenuFragment();

        fragmentTransaction.replace(R.id.menu, menuFragment);
        fragmentTransaction.replace(R.id.content, new HomePageFragment());

        fragmentTransaction.commit();

        initMenu();
    }

    private void initMenu() {
        menu = new SlidingMenu(this);
        menu.setMode(SlidingMenu.LEFT);    //设置菜单 滑动模式，左滑还是右滑
        menu.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
        menu.setShadowWidthRes(R.dimen.slidingmenu_shadowWidth);
        menu.setShadowDrawable(R.drawable.shadow);
        menu.setBehindOffsetRes(R.dimen.slidingmenu_behindOffset);
        menu.setFadeDegree(0.35f);
        menu.attachToActivity(this, SlidingMenu.SLIDING_CONTENT);
        menu.setMenu(R.layout.frame_menu);
    }

    @Override
    public void onHomeSelected(View view) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        MenuFragment menuFragment = new MenuFragment();

        fragmentTransaction.replace(R.id.menu, menuFragment);
        fragmentTransaction.replace(R.id.content, new HomePageFragment());

        fragmentTransaction.commit();
        menu.showContent();
    }

    @Override
    public void onListItemSelected(int position) {
        Log.i("yz", "onCityListSelected------------->" + position);
        //找到布局
        if (position == 0) {
            Log.i("net", "app.CheckNetWorkConnection(MainActivity.this)==>" + app.CheckNetWorkConnection(MainActivity.this));
            if (app.CheckNetWorkConnection(MainActivity.this)) {
                PurchaPlanFragment purPlan = new PurchaPlanFragment();
                Bundle args = new Bundle();
                args.putInt(PurchaPlanFragment.CITY_LIST_POSITION, position);
                purPlan.setArguments(args);
                FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.content, purPlan);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            } else {
                handler.sendEmptyMessage(Const.NETWORK_ERROR);
            }

        } else if (position == 1) {
            PlaceOrderFragment purPlan = new PlaceOrderFragment();
            Bundle args = new Bundle();
            args.putInt(PlaceOrderFragment.CITY_LIST_POSITION, position);
            purPlan.setArguments(args);
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.content, purPlan);
            fragmentTransaction.addToBackStack(null);
            fragmentTransaction.commit();
        } else if (position == 2) {
            PurchOrderFragment purPlan = new PurchOrderFragment();
            Bundle args = new Bundle();
            args.putInt(PurchOrderFragment.CITY_LIST_POSITION, position);
            purPlan.setArguments(args);
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.content, purPlan);
            fragmentTransaction.addToBackStack(null);
            fragmentTransaction.commit();
        } else if (position == 3) {
            PurchArrivalFragment purPlan = new PurchArrivalFragment();
            Bundle args = new Bundle();
            args.putInt(PurchArrivalFragment.CITY_LIST_POSITION, position);
            purPlan.setArguments(args);
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.content, purPlan);
            fragmentTransaction.addToBackStack(null);
            fragmentTransaction.commit();
        }
        //显示 主界面
        if (app.CheckNetWorkConnection(MainActivity.this)) {
            menu.showContent();
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        //  menu.add(0, MENU_REFRESH, 0, getResources().getText(R.string.Page_refresh));
        menu.add(0, Const.MENU_ABOUT, 0, getResources().getText(R.string.Menu_About));
        //menu.add(0, MENU_LOGOFF, 0, getResources().getText(R.string.Menu_LogOff));
        return true;
    }

    @Override
    public boolean onMenuItemSelected(int featureId, MenuItem item) {
        super.onMenuItemSelected(featureId, item);
        switch (item.getItemId()) {
//    	case MENU_REFRESH:
//    		 PurchaPlanFragment purPlan=new PurchaPlanFragment();
//    		 purPlan.LaunchThread();
//    		break;
            case Const.MENU_ABOUT:
                //alertAbout();
                break;
            case Const.MENU_LOGOFF:

                break;
        }
        return true;
    }

    /**
     * 弹出框
     */
    private void alertAbout(String title, String content, int flag) {

        if (flag == 1) {

            dialog = new AlertDialog.Builder(this).setTitle(title)
                    .setMessage(content)
                    .setPositiveButton(R.string.OK, new DialogInterface.OnClickListener() {

                        @Override
                        public void onClick(DialogInterface arg0, int arg1) {
                            // TODO Auto-generated method stub
                            Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                            startActivity(intent);
                            MainActivity.this.overridePendingTransition(R.anim.pre_current_activity_open, R.anim.into_current_activity_close);
                            dialog.dismiss();
                            MainActivity.this.finish();
                        }
                    })
                    .setNegativeButton(R.string.cancle, new DialogInterface.OnClickListener() {

                        @Override
                        public void onClick(DialogInterface arg0, int arg1) {
                            // TODO Auto-generated method stub
                            dialog.dismiss();
                        }
                    }).show();
        } else if (flag == 2) {//  关于功能
            dialog = new AlertDialog.Builder(this).setTitle(title)
                    .setMessage(content)
                    .setPositiveButton(R.string.OK, new DialogInterface.OnClickListener() {

                        @Override
                        public void onClick(DialogInterface arg0, int arg1) {
                            // TODO Auto-generated method stub
                            dialog.dismiss();
                        }
                    })
                    .show();
        }

    }

    /**
     * 显示或隐藏菜单
     */
    @Override
    public void onMenuButtonClicked() {
        // TODO Auto-generated method stub
        if (menu.isMenuShowing()) {
            menu.showContent();
        } else {
            menu.showMenu();
        }
    }

    /**
     * 捕捉onkeyDown事件
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        Log.i("yizhao", "onkeyDown");
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (menu.isMenuShowing()) {
                menu.showContent();
            } else {
                exit();
            }

            return false;
        }
        return super.onKeyDown(keyCode, event);
    }

    private void exit() {
        if (!isExit) {
            isExit = true;
            Toast.makeText(MainActivity.this, R.string.exit_tip, Toast.LENGTH_SHORT).show();
            handler.sendEmptyMessageDelayed(0, 2000);
        } else {
            finish();
            System.exit(0);
        }
    }

    @Override
    public void onLogOffSelected(View view) {
        // TODO Auto-generated method stub
        alertAbout("注销", "您确定注销该用户吗？", 1);
    }

    @Override
    public void onAboutSelected(View view) {
        // TODO Auto-generated method stub
        alertAbout("关于", "万方网络科技有限公司", 2);
    }
}
