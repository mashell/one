package com.mashell.one.base;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.mashell.one.common.OnItemClickListener;

import butterknife.ButterKnife;

/**
 * Created by mashell on 16/11/30.
 * Email: mashell624@163.com
 * Github: https://github.com/mashell
 */

public abstract class BaseViewHolder<T> extends RecyclerView.ViewHolder implements View.OnClickListener{

    private OnItemClickListener mOnItemClickListener;

    public BaseViewHolder(View itemView, OnItemClickListener mOnItemClickListener) {
        super(itemView);
        ButterKnife.bind(this,itemView);
        this.mOnItemClickListener = mOnItemClickListener;
        itemView.setOnClickListener(this);
    }

    public abstract void bindData(T t);

    @Override
    public void onClick(View v) {
        if ( mOnItemClickListener != null)
            mOnItemClickListener.onClick(itemView,getAdapterPosition());
    }

    public Context getContext(){
        return itemView.getContext();
    }
}