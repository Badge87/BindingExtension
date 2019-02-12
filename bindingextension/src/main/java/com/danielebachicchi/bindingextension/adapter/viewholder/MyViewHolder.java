package com.danielebachicchi.bindingextension.adapter.viewholder;

import com.danielebachicchi.bindingextension.BR;
import com.danielebachicchi.bindingextension.handler.IItemClickHandler;

import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.RecyclerView;

/**
 * @version 1.0.0
 * @since 1.0.0
 */
public class MyViewHolder<ITEM,BINDING extends ViewDataBinding> extends RecyclerView.ViewHolder {
    private final BINDING binding;
    private boolean _select;


    public MyViewHolder(BINDING binding) {
        super(binding.getRoot());
        this.binding = binding;
    }

    public void bind(ITEM obj, IItemClickHandler<ITEM> clickHandler) {
        binding.setVariable(BR.obj, obj);
        binding.executePendingBindings();
        if(clickHandler != null)
            binding.getRoot().setOnClickListener(v -> clickHandler.onItemClick(obj,v, this));
    }

    public void set_select(boolean select) {
        this._select = select;
        binding.setVariable(BR.select,_select);
    }

    public boolean is_select() {
        return _select;
    }
}
