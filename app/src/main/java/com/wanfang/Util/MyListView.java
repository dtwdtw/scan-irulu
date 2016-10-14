package com.wanfang.Util;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.ListView;

public class MyListView extends ListView{

	public MyListView(Context context){
		super(context);
	}
   public MyListView(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
	}
  @Override
   public void onMeasure(int widthMeasureSpec,int heightMeasureSpec){
	   int expandSpec=MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >>2, MeasureSpec.AT_MOST);
	   super.onMeasure(widthMeasureSpec, heightMeasureSpec);
   }
//@Override
//   public boolean onInterceptTouchEvent(MotionEvent ev){
//	   switch(ev.getAction()){
//	   case MotionEvent.ACTION_DOWN:
//		   setParentScrollAble(false);
//		   break;
//	   case MotionEvent.ACTION_MOVE:
//		   
//		   break;
//	   case MotionEvent.ACTION_UP:
//		   
//		   break;
//	   case MotionEvent.ACTION_CANCEL:
//		   setParentScrollAble(true);
//		   break;
//		   default:
//			   break;
//	   }
//	return super.onInterceptTouchEvent(ev);
//   }
   
   private void setParentScrollAble(boolean flag){
	   
   }
}
