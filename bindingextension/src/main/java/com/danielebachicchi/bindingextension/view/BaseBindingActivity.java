package com.danielebachicchi.bindingextension.view;

import android.os.Bundle;
import com.danielebachicchi.bindingextension.viewmodel.IViewModel;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;

public abstract class BaseBindingActivity<IVIEWMODEL extends IViewModel,BINDING extends ViewDataBinding> extends AppCompatActivity {
    private BINDING _binding;
    private IVIEWMODEL _viewModel;
    private int _layout;

    public BaseBindingActivity(int layoutId) {
        super();
        _layout = layoutId;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        _binding = DataBindingUtil.setContentView(this,_layout);
        _viewModel  = retrieveViewModel();
        getLifecycle().addObserver(_viewModel);
        onInitializeViewModel(_viewModel);
        _binding.setVariable(com.danielebachicchi.bindingextension.BR.viewModel, _viewModel);
    }

    protected abstract void onInitializeViewModel(IVIEWMODEL viewModel);
    protected abstract IVIEWMODEL retrieveViewModel();

    public BINDING get_binding() {
        return _binding;
    }

    public IVIEWMODEL get_viewModel() {
        return _viewModel;
    }
}
