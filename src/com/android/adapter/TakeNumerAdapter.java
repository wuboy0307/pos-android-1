package com.android.adapter;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.TextView;

import com.android.R;
import com.android.bean.TakeNumberBean;
import com.android.singaporeanorderingsystem.DailyPayActivity;


public class TakeNumerAdapter extends BaseAdapter {

	@SuppressWarnings("unused")
	private Context context;
	private LayoutInflater inflater;
	private List<TakeNumberBean> classList;
	public static final int SET_NUM=2001;
	@SuppressWarnings("unused")
	private Handler handler;
	public TakeNumerAdapter(Context context, List<TakeNumberBean> list,
			Handler handler) {
		this.context = context;
		this.classList = list;
		inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		this.handler = handler;
	}

	public int getCount() {
		return classList.size();
	}

	public Object getItem(int position) {
		return classList.get(position);
	}

	public long getItemId(int position) {
		return position;
	}
	public View getView(final int position, View convertView, ViewGroup parent) {
		final ViewHolder viewHolder;
		final TakeNumberBean bean;
		if (convertView == null) {
			convertView = inflater.inflate(R.layout.num_list_item, null);
			viewHolder = new ViewHolder();
			viewHolder.num_id_name = (TextView) convertView.findViewById(R.id.num_id_name);
			viewHolder.id_price = (EditText) convertView.findViewById(R.id.num_id_price);
			viewHolder.num_price=(TextView) convertView.findViewById(R.id.num_price);
			viewHolder.danwei=(TextView) convertView.findViewById(R.id.danwei);
			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}

		bean = classList.get(position);
		viewHolder.num_id_name.setText(bean.getPrice());
		viewHolder.id_price.setText(bean.getNum());
		Double is_price=Double.parseDouble(bean.getPrice());
		Log.e("adpter的价格", is_price+"");
		if(is_price>1){
			viewHolder.danwei.setText("张");
		}else{
			viewHolder.danwei.setText("个");
		}
		try{
			Double price=Double.parseDouble(viewHolder.num_id_name.getText().toString());
			int num=Integer.parseInt(viewHolder.id_price.getText().toString());
			Double total_price=price*num;
			DecimalFormat df=new DecimalFormat("0.00");
			viewHolder.num_price.setText(df.format(total_price));
		}catch(Exception e){
			Log.e("err", "");
		}
		viewHolder.id_price.setOnTouchListener(new OnTouchListener(){

			@Override
			public boolean onTouch(View arg0, MotionEvent arg1) {
				// TODO Auto-generated method stub
				viewHolder.id_price.setFocusable(true);
				viewHolder.id_price.setInputType(InputType.TYPE_CLASS_NUMBER);
				return false;
			}});
		
		
		viewHolder.id_price.addTextChangedListener(new TextWatcher(){

			@Override
			public void afterTextChanged(Editable s) {
				// TODO Auto-generated method stub
				//viewHolder.id_price.setInputType(InputType.TYPE_CLASS_NUMBER);
				if(bean.getNum().equals(viewHolder.id_price.getText().toString())){
					
				}else{
				
					try{
						Double price=Double.parseDouble(viewHolder.num_id_name.getText().toString());
						String num_tv=viewHolder.id_price.getText().toString();
						bean.setNum(num_tv);
						int num=Integer.parseInt(num_tv);
						Double total_price=price*num;
						DecimalFormat df=new DecimalFormat("0.00");
						viewHolder.num_price.setText(df.format(total_price));
						DailyPayActivity.hashMap_num.put(position, viewHolder.id_price.getText().toString()); 
						DailyPayActivity.hashMap_numprice.put(position, String.valueOf(total_price));
						Message msg = new Message();
						msg.what = SET_NUM;
						msg.obj=position+String.valueOf(total_price);
						handler.sendMessage(msg);
						Log.e("计算次数", "");
					}catch(Exception e){
						Log.e("计算错误", "");
	            }
				}
			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
				// TODO Auto-generated method stub
				//viewHolder.id_price.setInputType(InputType.TYPE_CLASS_NUMBER);
			}

			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
				// TODO Auto-generated method stub
				
			}});
		
//		viewHolder.id_price.setOnKeyListener(new EditText.OnKeyListener() 
//        { 
// 
//            @Override 
//            public boolean onKey(View v, int keyCode, KeyEvent event) 
//            { 
//                //得到文字，显示在TextView中 
//
//				try{
//					Double price=Double.parseDouble(viewHolder.num_id_name.getText().toString());
//					String num_tv=viewHolder.id_price.getText().toString();
//					int num=Integer.parseInt(num_tv);
//					Double total_price=price*num;
//					DecimalFormat df=new DecimalFormat("0.00");
//					viewHolder.num_price.setText(df.format(total_price));
//					DailyPayActivity.hashMap_num.put(position, viewHolder.id_price.getText().toString()); 
//					DailyPayActivity.hashMap_numprice.put(position, String.valueOf(total_price));
//					Message msg = new Message();
//					msg.what = SET_NUM;
//					msg.obj=position+String.valueOf(total_price);
//					handler.sendMessage(msg);
//					Log.e("计算次数", "");
//				}catch(Exception e){
//					Log.e("计算错误", "");
//            }
//                return false; 
//            }
//
//		
// 
// 
//        }); 

//		  if(DailyPayActivity.hashMap_num.get(position) != null){  
//			  viewHolder.id_price.setText(DailyPayActivity.hashMap_num.get(position)); 	            
//		  }  
//		  if(DailyPayActivity.hashMap_numprice.get(position) != null){  
//			  viewHolder.num_price.setText(DailyPayActivity.hashMap_numprice.get(position));
//		  }
		return convertView;
	}

	public final class ViewHolder {
		TextView num_id_name;
		EditText id_price;
		TextView num_price;
		TextView danwei;
	}
	

}
