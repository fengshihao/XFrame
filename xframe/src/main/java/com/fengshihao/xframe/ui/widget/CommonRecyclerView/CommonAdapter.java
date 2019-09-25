package com.fengshihao.xframe.ui.widget.CommonRecyclerView;

import java.util.Arrays;

import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.fengshihao.xframe.logic.layzlist.IPageListListener;
import com.fengshihao.xframe.logic.layzlist.PageList;

public class CommonAdapter extends RecyclerView.Adapter<CommonViewHolder>
    implements IPageListListener {
  private static final String TAG = "CommonAdapter";

  @NonNull
  private final PageList<ICommonItemModel> mList = new PageList<>();

  @LayoutRes
  private int[] mItemLayoutIds;


  public CommonAdapter() {
    mList.addListener(this);
  }
  
  @NonNull
  @Override
  public CommonViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
    if (mItemLayoutIds == null) {
      throw new IllegalStateException("must call CommonAdapter.setItemLayoutIds first!!");
    }

    if (mItemLayoutIds.length <= viewType) {
      throw new IllegalStateException("must call CommonAdapter.setItemLayoutIds lack of viewType="
          + viewType + " now mItemLayoutIds=" + Arrays.toString(mItemLayoutIds));
    }

    CommonItemView v = (CommonItemView) LayoutInflater
        .from(parent.getContext()).inflate(mItemLayoutIds[viewType], parent, false);
    v.bindViews();
    return new CommonViewHolder(v);
  }

  @Override
  public void onBindViewHolder(@NonNull CommonViewHolder holder, int position) {
    ICommonItemModel item = mList.get(position);
    mList.visitItem(position);
    if (item == null) {
      return;
    }
    holder.updateView(item, position);
  }

  @Override
  public int getItemCount() {
    return mList.size();
  }

  @Override
  public int getItemViewType(int position) {
    ICommonItemModel item = mList.get(position);
    if (item == null) {
      return 0;
    }
    return item.getViewType();
  }

  public void setItemLayoutIds(@LayoutRes int... layoutIds) {
    Log.d(TAG, "setItemLayoutIds() called with: layoutIds = ["
        + Arrays.toString(layoutIds) + "]");
    mItemLayoutIds = layoutIds;
  }

  @Override
  public void onAddNewItems(int from, int to) {
    Log.d(TAG, "onAddNewItems() called with: from = [" + from + "], to = [" + to + "]");
    notifyItemRangeInserted(from, to);
  }

  @Override
  public void onUpdateItems(int from, int to) {
    Log.d(TAG, "onUpdateItems() called with: from = [" + from + "], to = [" + to + "]");
    notifyItemChanged(from, to);
  }

  @NonNull
  public PageList<ICommonItemModel> getPageList() {
    return mList;
  }
}
