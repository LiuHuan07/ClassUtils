package com.android.liuhuan.utilsclass.adapter2;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * @author Administrator
 *	万能ViewHolder
 */
public class ViewHolder {
	private final SparseArray<View> mViews;
	private View mConvertView;
	private int mPosition;
	//构造函数
	private ViewHolder(Context context,int layoutId,ViewGroup parent,int position){
		mViews=new SparseArray<View>();
		mConvertView=LayoutInflater.from(context).inflate(layoutId, parent,false);
		mConvertView.setTag(this);
		mPosition=position;
	}
	public static ViewHolder get(Context context,View convertView,int layoutId,ViewGroup parent,int position){
		if(convertView==null){
			return new ViewHolder(context, layoutId, parent, position);
		}
		return (ViewHolder)convertView.getTag();
	}
	
	public <T extends View>T getView(int viewId){
		View v=mViews.get(viewId);
		if(v==null){
			View view=mConvertView.findViewById(viewId);
			mViews.put(viewId, view);
		}
		return (T) v;
	}
	
	public View getConvertView(){
		return mConvertView;
	}
	
	/**
	 * 为TextView设置字符串
	 *
	 */
	public ViewHolder setText(int textId,String txt){
		TextView tv=getView(textId);
		tv.setText(txt);
		return this;
	}
	/**
	 * 为ImageView设置图片
	 *
	 */
	public ViewHolder setImageBitmap(int imgId,Bitmap bitmap){
		ImageView img=getView(imgId);
		img.setImageBitmap(bitmap);
		return this;
	}
	/**
	 * 为ImageView设置图片
	 *
	 */
	public ViewHolder setImageResource(int imgId,int imageResourceId){
		ImageView img=getView(imgId);
		img.setImageResource(imageResourceId);
		return this;
	}
	
	public int getPosition(){
		return mPosition;
	}

}
