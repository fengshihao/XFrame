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

import java.util.Collections;
import java.util.List;

public class CommonAdapter<T extends CommonItemModel>
    extends RecyclerView.Adapter<CommonViewHolder<T>>
    implements IPageListListener {
  private static final String TAG = "CommonAdapter";

  @LayoutRes
  private int mEmptyLayoutId;

  @Nullable
  private ICommonViewHolderCreator<T> mHolderCreator;

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
    if (mHolderCreator != null) {
      return mHolderCreator.createViewHolder(v, viewType);
    }

    throw new RuntimeException("you should override createItemViewHolder()");
  }

  @Override
  public void onBindViewHolder(@NonNull CommonViewHolder<T> holder, int position) {
    Log.d(TAG, "onBindViewHolder: ");
    T item = mList.get(position);
    mList.visitItem(position);
    holder.update(item, position, Collections.emptyList());
  }

  @Override
  public void onBindViewHolder(@NonNull CommonViewHolder<T> holder, int position, @NonNull List<Object> payloads) {
    Log.d(TAG, "onBindViewHolder: payloads=" + payloads);
    T item = mList.get(position);
    mList.visitItem(position);
    holder.update(item, position, payloads);
  }

  @Override
  public int getItemCount() {
    return mList.size();
  }

  @Override
  public int getItemViewType(int position) {
    T item = mList.get(position);
    if (item == null) {
      if (mEmptyLayoutId == 0) {
        throw new RuntimeException("some position need a empty view for placeholder" +
            " call setEmptyLayoutId first");
      }
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
  public void onRemoveItem(int index) {
    Log.d(TAG, "onRemoveItem() called with: index = [" + index + "]");
    notifyItemRemoved(index);
  }

  @NonNull
  public PageList<T> getPageList() {
    return mList;
  }

  public void addItem(@NonNull T item) {
    mList.addItem(item);
  }

  public void setHolderCreator(@Nullable ICommonViewHolderCreator<T> creator) {
    mHolderCreator = creator;
  }

  public void removeById(long id) {
    for (int i = 0; i < mList.size(); i++) {
      T item = mList.get(i);
      if (item != null && item.mId == id) {
        Log.d(TAG, "removeById: find it and remove it");
        mList.remove(i);
        return;
      }
    }
    Log.w(TAG, "removeById: cant find id " + id);
  }
}
