package com.example.smallfish.ui;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.paging.PagedList;
import androidx.paging.PagedListAdapter;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.libcommon.EmptyView;
import com.example.smallfish.AbsViewModel;
import com.example.smallfish.R;
import com.example.smallfish.databinding.LayoutRefreshViewBinding;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.constant.RefreshState;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * author : Iwen大大怪
 * create : 2020/10/27 17:06
 */
public abstract class AbsListFragment<T, M extends AbsViewModel<T>> extends Fragment implements OnRefreshListener, OnLoadMoreListener {
    protected LayoutRefreshViewBinding binding;
    protected RecyclerView mRecyclerView;
    protected SmartRefreshLayout mRefreshLayout;
    protected EmptyView mEmptyView;
    protected PagedListAdapter<T, RecyclerView.ViewHolder> adapter;
    protected M mViewModel;
    protected DividerItemDecoration decoration;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = LayoutRefreshViewBinding.inflate(inflater, container, false);
        mRecyclerView = binding.recyclerView;
        mEmptyView = binding.emptyView;
        mRefreshLayout = binding.refreshLayout;

        mRefreshLayout.setEnableRefresh(true);
        mRefreshLayout.setEnableAutoLoadMore(true);
        mRefreshLayout.setOnRefreshListener(this);
        mRefreshLayout.setOnLoadMoreListener(this);

        adapter = getAdapter();
        mRecyclerView.setAdapter(adapter);
        // RecyclerView使用垂直线性布局
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        mRecyclerView.setItemAnimator(null);
        //默认给列表中的Item 一个 10dp的ItemDecoration
        decoration = new DividerItemDecoration(getContext(), LinearLayoutManager.VERTICAL);
        decoration.setDrawable(ContextCompat.getDrawable(getContext(), R.drawable.ic_launcher_foreground));
        mRecyclerView.addItemDecoration(decoration);

        afterCreateView();

        return binding.getRoot();
    }

    protected abstract void afterCreateView();

    @SuppressLint("FragmentLiveDataObserve")
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ParameterizedType type = (ParameterizedType) getClass().getGenericSuperclass();
        Type[] arguments = type.getActualTypeArguments();
        if (arguments.length>1){
            Type argument = arguments[1];
            Class modelClaz = ((Class) argument).asSubclass(AbsViewModel.class);
            mViewModel = (M) ViewModelProviders.of(this).get(modelClaz);
            mViewModel.getPageData().observe(this, new Observer<PagedList<T>>() {
                @Override
                public void onChanged(PagedList<T> pagedList) {
                    adapter.submitList(pagedList);
                }
            });
            mViewModel.getBoundaryPageData().observe(this, new Observer<Boolean>() {
                @Override
                public void onChanged(Boolean hasData) {
                    // 监听页面是否有数据
                    finishRefresh(hasData);
                }
            });
        }
    }

    /**
     * 在使用paged框架在做下拉刷新时，每一次得到的数据都是一个pagedList，要想将pagedList展现在列表中做出数据的变更，
     * 必须通过pagedList Adapter.submitList
     * @param pagedList 每次下拉刷新返回色pagedList对象
     */
    public void submitList(PagedList<T> pagedList){
        if (pagedList.size()>0){
            adapter.submitList(pagedList);
        }
        finishRefresh(pagedList.size()>0);
    }
    public void finishRefresh(boolean hasData) {
        // 返回适配器当前正在显示的PagedList
        PagedList<T> currentList = adapter.getCurrentList();
        hasData = hasData || currentList != null && currentList.size() > 0;
        // getState()获取当前状态,判断是上拉刷新还是下来刷新
        RefreshState state = mRefreshLayout.getState();
        if (state.isFooter && state.isOpening){
            mRefreshLayout.finishLoadMore();
        }else if (state.isHeader && state.isOpening){
            mRefreshLayout.finishRefresh();
        }
        // 控制空页面的展示和隐藏
        if (hasData){
            mEmptyView.setVisibility(View.GONE);
        }else {
            mEmptyView.setVisibility(View.VISIBLE);
        }
    }

    public abstract PagedListAdapter<T, RecyclerView.ViewHolder> getAdapter();
}
