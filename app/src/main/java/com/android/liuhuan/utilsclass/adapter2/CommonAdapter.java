package com.android.liuhuan.utilsclass.adapter2;

import java.util.List;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

/**
 * @author 万能适配器
 *
 * @param <T>
 */
public abstract class CommonAdapter<T> extends BaseAdapter {
	protected static Context context;
	protected List<T> datas;
	protected static int layoutId;
	protected int mItemId;
	public CommonAdapter(Context context,List<T> datas,int layoutId,int mItemId){
		this.context=context;
		this.datas=datas;
		this.layoutId=layoutId;
		this.mItemId=mItemId;
	}
	@Override
	public int getCount() {
		
		return datas.size();
	}

	@Override
	public T getItem(int position) {
		
		return datas.get(position);
	}

	@Override
	public long getItemId(int position) {
		
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder vHolder=getHolder(position, convertView, parent);
		//向外提供接口供实现
		convert(vHolder, getItem(position));
		return vHolder.getConvertView();
	}
	
	public abstract void convert(ViewHolder vHolder,T item);
	
	private static ViewHolder getHolder(int position, View convertView, ViewGroup parent){
		return ViewHolder.get(context, convertView, layoutId, parent, position);
	}
	
}
