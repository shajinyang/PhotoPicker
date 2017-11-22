package com.sjy.pickphotos.pickphotos.ui;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * recycleview基础适配器
 * Created by sjy on 2017/6/7.
 */

public abstract class CommonAdapter<T> extends RecyclerView.Adapter<BaseViewHolder> {
    protected Context mContext;
    protected int mLayoutId;
    protected List<T> mDatas;
    protected LayoutInflater mInflater;

    public void setmDatas(List<T> mDatas) {
        if(mDatas==null){
            mDatas=new ArrayList<>();
        }
        this.mDatas = mDatas;
    }

    public CommonAdapter(Context context, int layoutId, List<T> datas)
    {
        mContext = context;
        mInflater = LayoutInflater.from(context);
        mLayoutId = layoutId;
        mDatas = datas;
    }

    @Override
    public void onBindViewHolder(BaseViewHolder holder, int position) {
        convert(holder, mDatas.get(position),position);
    }

    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            BaseViewHolder viewHolder = BaseViewHolder.get(mContext, parent, mLayoutId);
            return viewHolder;
    }


    @Override
    public int getItemCount() {
        return mDatas.size();
    }

    public abstract void convert(BaseViewHolder holder, T t,int position);



}
