package com.example.smallfish;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.paging.DataSource;
import androidx.paging.LivePagedListBuilder;
import androidx.paging.PagedList;

/**
 * author : Iwen大大怪
 * create : 2020/10/27 18:36
 */
public abstract class AbsViewModel<T> extends ViewModel {

    protected PagedList.Config config;
    private LiveData<PagedList<T>> pageData;
    private DataSource dataSource;

    private MutableLiveData<Boolean> boundaryPageData = new MutableLiveData<>();

    public AbsViewModel() {
        config = new PagedList.Config.Builder()
                .setPageSize(10)
                .setInitialLoadSizeHint(12)
                // .setMaxSize(100)；
                // .setEnablePlaceholders(false)
                // .setPrefetchDistance()
                .build();
        pageData = new LivePagedListBuilder(factory, config)
                .setInitialLoadKey(0)
                .setBoundaryCallback(callback)
                .build();
    }

    public LiveData<PagedList<T>> getPageData(){
        return pageData;
    }

    public DataSource getDataSource(){
        return dataSource;
    }

    public MutableLiveData<Boolean> getBoundaryPageData(){
        return boundaryPageData;
    }

    PagedList.BoundaryCallback<T> callback = new PagedList.BoundaryCallback<T>() {
        @Override
        public void onZeroItemsLoaded() {
            boundaryPageData.postValue(false);
        }

        @Override
        public void onItemAtFrontLoaded(@NonNull T itemAtFront) {
            boundaryPageData.postValue(true);
        }

        @Override
        public void onItemAtEndLoaded(@NonNull T itemAtEnd) {
            super.onItemAtEndLoaded(itemAtEnd);
        }
    };

    DataSource.Factory factory = new DataSource.Factory() {
        @NonNull
        @Override
        public DataSource create() {
            dataSource = createDataSource();
            return dataSource;
        }
    };

    public abstract DataSource createDataSource() ;
}
