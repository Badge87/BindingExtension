package com.danielebachicchi.bindingextension.adapter;

public interface IAdapterItemListener<I> {
    void onItemSelected(I item);
    void onItemDeselected(I item);
}
