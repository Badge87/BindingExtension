package com.danielebachicchi.bindingextension.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;

import com.danielebachicchi.bindingextension.adapter.viewholder.MyViewHolder;
import com.danielebachicchi.bindingextension.handler.IItemClickHandler;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.RecyclerView;

/**
 * @version 1.0.0
 * @since 1.0.0
 */
public abstract class DeletableItemAdapter<ITEM,VIEWHOLDER extends MyViewHolder<ITEM,BINDING>, BINDING extends ViewDataBinding> extends RecyclerView.Adapter<VIEWHOLDER> implements IDeletableItemAdapter<ITEM>, IBindableAdapter, IItemClickHandler<ITEM> {
    private ITEM _onDeletingItem;
    private List<ITEM> _deletedItems;
    private int _onDeletingItemIndex;
    private Context _context;
    private View _rootSnackbar;
    private List<ITEM> _data;
    private LayoutInflater _inflater;
    private int _maxSelectable = 1;
    private List<ITEM> _selectedItems;

    private View _lastViewClicked;


     public DeletableItemAdapter(List<ITEM> data, Context context, View rootSnackBar) {
        super();
        _context = context;
        _rootSnackbar = rootSnackBar;
        _deletedItems = new ArrayList<>();
        setData(data);
        _inflater = LayoutInflater.from(context);
        _selectedItems = new ArrayList<>();

    }
    public DeletableItemAdapter(List<ITEM> data, Context context) {
         this(data,context,null);
    }

    @Override
    public void deleteItem(int position) {
        _onDeletingItem = getItemForPosition(position);
        _onDeletingItemIndex = position;
        _data.remove(position);
        _deletedItems.add(_onDeletingItem);
        notifyItemRemoved(position);
        showUndoSnackBar(_onDeletingItem);


    }

    public List<ITEM> get_selectedItems() {
        return _selectedItems;
    }

    @Override
    public void onBindViewHolder(@NonNull VIEWHOLDER holder, int position) {
        ITEM item = getItemForPosition(position);
        holder.bind(item, this);
        holder.set_select(isItemSelected(item));


    }
    protected abstract void onViewHolderBind(VIEWHOLDER viewHolder);

    public ITEM getItemForPosition(int position){
        return (_data != null && _data.size() > 0 && _data.size() > position) ?  _data.get(position) : null;

    }
    @Override
    public int getItemCount() {
        return _data != null ? _data.size() : 0;
    }
    @Override
    public List<ITEM> getDeletedItems() {
        return _deletedItems;
    }

    private void undoDelete() {
        _data.add(_onDeletingItemIndex,_onDeletingItem);
        _deletedItems.remove(_onDeletingItem);
        notifyItemInserted(_onDeletingItemIndex);
    }

    private void showUndoSnackBar(ITEM item) {

        //TODO make labels
        if(_rootSnackbar != null) {
            Snackbar snackbar = Snackbar.make(_rootSnackbar, getDeleteMessage(item),
                    Snackbar.LENGTH_LONG);
            snackbar.setAction("UNDO!", v -> undoDelete());
            snackbar.show();
        }
    }

    @Override
    @SuppressWarnings("unchecked")
    public void setData(List data) {
        _data = (List<ITEM>)data;
        notifyDataSetChanged();
    }

   protected abstract String getDeleteMessage(ITEM item);

    @Override
    public Context getContext() {
        return _context;
    }

    public int get_maxSelectable() {
        return _maxSelectable;
    }

    public void set_maxSelectable(int _maxSelectable) {
        this._maxSelectable = _maxSelectable;
    }

    @Override
    public void onItemClick(ITEM item, View v, MyViewHolder holder) {
        _lastViewClicked = v;
        if(_maxSelectable < 0 || _selectedItems.size() < _maxSelectable){
            if(isItemSelected(item))
                removeSelected(item, holder);
            else
                addSelected(item, holder);
        }
    }
    public void addSelected(ITEM item){
        addSelected(item, null);

    }
    private void addSelected(ITEM item, @Nullable MyViewHolder currentHolder){
        _selectedItems.add(item);
        if(currentHolder != null)
            currentHolder.set_select(true);

    }
    private void removeSelected(ITEM item, @Nullable MyViewHolder currentHolder){
        _selectedItems.remove(item);
        if(currentHolder != null)
            currentHolder.set_select(false);


    }
    private boolean isItemSelected(ITEM item){
        for(int i = 0; i < _selectedItems.size(); i++ ){
            if(areItemsEquals(_selectedItems.get(i),item)){
                return true;
            }
        }
        return false;
    }

    protected boolean areItemsEquals(ITEM i1, ITEM i2){
        return i1.equals(i2);

    }

    public View get_lastViewClicked() {
        return _lastViewClicked;
    }

    public LayoutInflater get_inflater() {
        return _inflater;
    }
}
