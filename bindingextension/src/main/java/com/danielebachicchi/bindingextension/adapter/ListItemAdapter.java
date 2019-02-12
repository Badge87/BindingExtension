package com.danielebachicchi.bindingextension.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.danielebachicchi.bindingextension.BR;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;

public abstract class ListItemAdapter<ITEM,BINDING extends ViewDataBinding> extends ArrayAdapter<ITEM> implements IBindableAdapter {
    private LayoutInflater _inflater;
    private int _resource;
    private List<ITEM> _data;


    public ListItemAdapter(@NonNull Context context, int resource, List<ITEM> data) {
        super(context, resource);
        setData(data);
        _inflater = LayoutInflater.from(context);
        _resource = resource;

    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        return generateView(position, convertView, parent);
    }

    private View generateView(int position, @Nullable View convertView, @NonNull ViewGroup parent){
        BINDING binding;
        if(convertView == null){
            convertView = _inflater.inflate(_resource, null);
            binding = DataBindingUtil.bind(convertView);
            convertView.setTag(binding);
        }else{
            binding = (BINDING) convertView.getTag();
        }

        View result = null;
        if(binding != null) {
            binding.setVariable(BR.obj, getItem(position));
            result = binding.getRoot();
        }
        return result;
    }
    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        return generateView(position, convertView, parent);
    }

    @Override
    public void setData(List data) {
        _data = data;
        clear();
        addAll(_data);
    }

    public int getPositionForITEMByID(ITEM item){
        int result = -1;
        if(_data == null || item == null)
            return result;

        for(int i = 0; i < _data.size(); i++){
            if(getIDForItem(item) == getIDForItem(_data.get(i))) {
                result = i;
                break;
            }
        }
        return result;

    }

    public abstract long getIDForItem(ITEM item);


}
