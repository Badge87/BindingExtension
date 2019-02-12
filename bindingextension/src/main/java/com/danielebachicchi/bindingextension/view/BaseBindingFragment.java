package com.danielebachicchi.bindingextension.view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import com.danielebachicchi.bindingextension.viewmodel.IViewModel;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.ViewDataBinding;
import androidx.fragment.app.Fragment;

/**
 * @version 1.0.0
 * @since 1.0.0
 */
public abstract class BaseBindingFragment<IVIEWMODEL extends IViewModel,BINDING extends ViewDataBinding> extends Fragment {
    private BINDING _binding;
    private IVIEWMODEL _viewModel;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //return super.onCreateView(inflater, container, savedInstanceState);
        _binding = inflateBinding(inflater,container);
        _viewModel  = retrieveViewModel();
        getLifecycle().addObserver(_viewModel);
        onInitializeViewModel(_viewModel);
        _binding.setVariable(com.danielebachicchi.bindingextension.BR.viewModel, _viewModel);
        return _binding.getRoot();
    }
    protected abstract BINDING inflateBinding(@NonNull LayoutInflater inflater, @Nullable ViewGroup container);
    protected abstract void onInitializeViewModel(IVIEWMODEL viewModel);
    protected abstract IVIEWMODEL retrieveViewModel();

    public BINDING get_binding() {
        return _binding;
    }

    public IVIEWMODEL get_viewModel() {
        return _viewModel;
    }
}
