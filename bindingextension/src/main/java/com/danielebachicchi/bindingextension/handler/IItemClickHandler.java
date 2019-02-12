package com.danielebachicchi.bindingextension.handler;

import android.view.View;

import com.danielebachicchi.bindingextension.adapter.viewholder.MyViewHolder;

public interface IItemClickHandler<ITEM> {
    void onItemClick(ITEM item, View v, MyViewHolder holder);
}
