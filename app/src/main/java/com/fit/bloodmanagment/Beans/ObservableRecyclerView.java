package com.fit.bloodmanagment.Beans;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

public class ObservableRecyclerView extends com.github.ksoichiro.android.observablescrollview.ObservableRecyclerView {
    private Context mContext;
    //The empty view which is shown when the data is empty
    //hello1
    private View emptyView;
    public ObservableRecyclerView(Context context) {
        super(context);
        mContext = context;
    }

    public ObservableRecyclerView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
    }

    public ObservableRecyclerView(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        mContext = context;
    }
    @Override
    public void setAdapter(final Adapter adapter) {
        super.setAdapter(adapter);
        //Setting the adapter data change listener
        adapter.registerAdapterDataObserver(new AdapterDataObserver() {
            @Override
            public void onChanged() {
                Log.d("item count:", adapter.getItemCount() + "");
                //If data count is not 0 emptyView visibility is set to gone and recycler view is shown
                if (adapter.getItemCount() != 0) {
                    if (emptyView != null) {
                        emptyView.setVisibility(GONE);
                        setVisibility(VISIBLE);
                    }
                } else {
                    //If data count is 0 emptyView visibility is set to visible and recycler view
                    // visibility is set to gone
                    if (emptyView != null) {
                        setVisibility(GONE);
                        emptyView.setVisibility(VISIBLE);
                    }
                }
            }
        });
    }

    //Call this function to set the empty view
    public void setEmptyView(View emptyView) {
        this.emptyView = emptyView;
    }
}
