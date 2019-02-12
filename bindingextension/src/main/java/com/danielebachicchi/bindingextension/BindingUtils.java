package com.danielebachicchi.bindingextension;

import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.Spinner;

import com.danielebachicchi.bindingextension.adapter.IBindableAdapter;
import com.danielebachicchi.bindingextension.adapter.IDeletableItemAdapter;
import com.danielebachicchi.bindingextension.adapter.ListItemAdapter;
import com.danielebachicchi.bindingextension.adapter.SwipeToDeleteCallback;
import com.danielebachicchi.bindingextension.handler.INavigationHandler;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.tabs.TabLayout;

import java.util.List;

import androidx.databinding.BindingAdapter;
import androidx.databinding.InverseBindingAdapter;
import androidx.databinding.InverseBindingListener;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

public class BindingUtils {

    @BindingAdapter(value = {"app:adapter","app:data","swipeToDelete"},requireAll = false)
    public static void setRecyclerViewProperties(RecyclerView recyclerView, RecyclerView.Adapter<?> adapter, List<?> data, boolean swipeToDelete) {

        recyclerView.setAdapter(adapter);
        if (recyclerView.getAdapter() instanceof IBindableAdapter) {
            ((IBindableAdapter)recyclerView.getAdapter()).setData(data);
        }
        if(swipeToDelete){
            ItemTouchHelper.Callback swipeCallback = new SwipeToDeleteCallback((IDeletableItemAdapter) recyclerView.getAdapter());
            ItemTouchHelper itemTouchHelper = new ItemTouchHelper(swipeCallback);
            itemTouchHelper.attachToRecyclerView(recyclerView);
        }
    }




    @BindingAdapter("onNavigationItemSelected")
    public static void setOnNavigationItemSelectedListener(
            BottomNavigationView view, INavigationHandler listener) {
        view.setOnNavigationItemSelectedListener(item -> (listener != null) && listener.onNavigationClick(item.getItemId()));
    }


    @BindingAdapter(value = {"app:adapter","app:data", "selectionAttrChanged", "selection"}, requireAll = false)
    public static void setSpinnerProperties(Spinner spinner, ListItemAdapter adapter, List<?> data, final InverseBindingListener bindingListener, Object newSelection){
        if(adapter != null) {
            adapter.setData(data);
            spinner.setAdapter(adapter);
            spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    if(bindingListener != null) bindingListener.onChange();
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {
                    //Nothing
                }
            });
            if(newSelection != null){
                int pos = adapter.getPositionForITEMByID(newSelection);
                spinner.setSelection(pos);
            }

        }
    }


    @BindingAdapter(value = {"android:pagerAdapter"}, requireAll = false)
    public static void setViewPager(ViewPager viewPager, FragmentPagerAdapter adapter) {
        viewPager.setAdapter(adapter);
    }
    @BindingAdapter(value = {"app:viewPager"}, requireAll = false)
    public static void setViewPager(TabLayout tabLayout, ViewPager viewPager) {
        tabLayout.setupWithViewPager(viewPager);
    }

    @InverseBindingAdapter(attribute = "selection", event = "selectionAttrChanged")
    public static Object getSelectedValue(AdapterView view) {
        return  view.getSelectedItem();
    }

    @BindingAdapter("app:srcCompat")
    public static void bindSrcCompat(ImageView imageView, Drawable drawable){
        // Your setter code goes here, like setDrawable or similar
        imageView.setImageDrawable(drawable);
    }
}
