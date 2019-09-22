package com.fengshihao.xframe.ui.widget.CommonRecyclerView;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.Log;

import com.fengshihao.xframe.logic.layzlist.IPageListListener;

import java.util.List;

/**
 * A Common RecyclerView to avoiding define a Adapter and a ViewHolder
 */
public class CommonRecyclerView extends RecyclerView  {

  private static final String TAG = "CommonRecyclerView";

  @NonNull
  private final CommonAdapter mCommonAdapter = new CommonAdapter();

  public CommonRecyclerView(Context context) {
    super(context);
    init();
  }

  public CommonRecyclerView(Context context, AttributeSet attrs) {
    super(context, attrs);
    init();
  }

  public CommonRecyclerView(Context context, AttributeSet attrs, int defStyle) {
    super(context, attrs, defStyle);
    init();
  }

  private void init() {
    setAdapter(mCommonAdapter);
  }

  public void setItemLayoutIds(@LayoutRes int... layoutIds) {
    mCommonAdapter.setItemLayoutIds(layoutIds);
  }

  public <T extends ICommonItemModel> void setModels(@NonNull List<T> list1) {
    mCommonAdapter.clear();
    mCommonAdapter.addAll(list1);
  }

  public int getPageSize() {
    return mCommonAdapter.getPageSize();
  }

  public void addPageListListener(IPageListListener listener) {
    mCommonAdapter.addPageListListener(listener);
  }

  public void removePageListListener(IPageListListener listener) {
    mCommonAdapter.removePageListListener(listener);
  }

  public void setModels(int offset, @NonNull List<? extends ICommonItemModel> models) {
    mCommonAdapter.setModels(offset, models);
  }
}
