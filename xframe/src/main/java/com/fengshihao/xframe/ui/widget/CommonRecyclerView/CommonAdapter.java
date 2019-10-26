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

public class CommonAdapter<T extends CommonItemModel>
    extends RecyclerView.Adapter<CommonViewHolder<T>>
    implements IPageListListener {
  private static final String TAG = "CommonAdapter";

  @LayoutRes
  private int mEmptyLayoutId;

  @NonNull
  private final PageList<T> mList = new PageList<>();

  public CommonAdapter() {
    mList.addListener(this);
  }

  public void setEmptyLayoutId(@LayoutRes int emptyLayoutId) {
    mEmptyLayoutId = emptyLayoutId;
  }

  @SuppressWarnings("unchecked")
  @NonNull
  @Override
  public CommonViewHolder<T> onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
    if (viewType == 0) {
      throw new IllegalArgumentException("viewType must be a layout resource");
    }
    Log.d(TAG, "onCreateViewHolder: " + viewType);
    View  v = LayoutInflater
        .from(parent.getContext()).inflate(viewType, parent, false);
    CommonViewHolder<T> holder = createItemViewHolder(v, viewType);
    if (holder != null) {
      return holder;
    }

    if (v instanceof CommonItemView) {
      return new CommonItemViewHolder<>((CommonItemView<T>) v);
    }

    throw new RuntimeException("you should override createItemViewHolder() or using CommonItemView");
  }

  @Nullable
  protected CommonViewHolder<T> createItemViewHolder(@NonNull View v, @LayoutRes int layoutId) {
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
      return mEmptyLayoutId;
    }
    return item.getLayoutId();
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

  @Override
  public void onRemoveItems(int start, int count) {
    Log.d(TAG, "onRemoveItems() called with: start = [" + start + "], count = [" + count + "]");
    notifyItemRangeRemoved(start, count);
  }

  @NonNull
  public PageList<T> getPageList() {
    return mList;
  }

  public void addItem(@NonNull T item) {
    mList.addItem(item);
  }
}
