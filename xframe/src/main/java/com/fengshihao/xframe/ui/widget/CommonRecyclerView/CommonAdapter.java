package com.fengshihao.xframe.ui.widget.CommonRecyclerView;

import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.fengshihao.xframe.logic.layzlist.IPageListListener;
import com.fengshihao.xframe.logic.layzlist.PageList;

import java.util.Arrays;

public class CommonAdapter<T extends ICommonItemModel>
    extends RecyclerView.Adapter<CommonViewHolder<T>>
    implements IPageListListener {
  private static final String TAG = "CommonAdapter";

  @NonNull
  private final PageList<T> mList = new PageList<>();

  @Nullable
  @LayoutRes
  private int[] mItemLayoutIds;

  public CommonAdapter(@LayoutRes int... layoutIds) {
    mList.addListener(this);
    setItemLayoutIds(layoutIds);
  }

  @SuppressWarnings("unchecked")
  @NonNull
  @Override
  public CommonViewHolder<T> onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
    if (mItemLayoutIds == null) {
      throw new IllegalStateException("must call CommonAdapter.setItemLayoutIds first!!");
    }

    if (mItemLayoutIds.length <= viewType) {
      throw new IllegalStateException("must call CommonAdapter.setItemLayoutIds lack of viewType="
          + viewType + " now mItemLayoutIds=" + Arrays.toString(mItemLayoutIds));
    }
    View  v = LayoutInflater
        .from(parent.getContext()).inflate(mItemLayoutIds[viewType], parent, false);
    CommonViewHolder<T> holder = generateViewHolder(v);
    if (holder != null) {
      return holder;
    }
    CommonItemView<T>  cv = (CommonItemView<T>) v;
    cv.bindViews();
    return new CommonViewHolder<>(cv);
  }

  public CommonViewHolder<T> generateViewHolder(@NonNull View v) {
    return null;
  }

  @Override
  public void onBindViewHolder(@NonNull CommonViewHolder<T> holder, int position) {
    T item = mList.get(position);
    mList.visitItem(position);
    holder.updateView(item, position);
  }

  @Override
  public int getItemCount() {
    return mList.size();
  }

  @Override
  public int getItemViewType(int position) {
    T item = mList.get(position);
    if (item == null) {
      return 0;
    }
    return item.getViewType();
  }

  private void setItemLayoutIds(@LayoutRes int... layoutIds) {
    Log.d(TAG, "setItemLayoutIds() called with: layoutIds = ["
        + Arrays.toString(layoutIds) + "]");
    mItemLayoutIds = layoutIds;
  }

  @Override
  public void onAddNewItems(int start, int count) {
    Log.d(TAG, "onAddNewItems() called with: start = [" + start + "], count = [" + count + "]");
    notifyItemRangeInserted(start, count);
  }

  @Override
  public void onUpdateItems(int start, int count) {
    Log.d(TAG, "onUpdateItems() called with: start = [" + start + "], count = [" + count + "]");
    notifyItemRangeChanged(start, count);
  }

  @NonNull
  public PageList<T> getPageList() {
    return mList;
  }
}
