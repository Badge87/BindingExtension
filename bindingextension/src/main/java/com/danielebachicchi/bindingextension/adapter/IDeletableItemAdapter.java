package com.danielebachicchi.bindingextension.adapter;

import android.content.Context;

import java.util.List;

/**
 * @version 1.0.0
 * @since 1.0.0
 */
public interface IDeletableItemAdapter<ITEM> {
    void deleteItem(int position);
    List<ITEM> getDeletedItems();
    Context getContext();
}
