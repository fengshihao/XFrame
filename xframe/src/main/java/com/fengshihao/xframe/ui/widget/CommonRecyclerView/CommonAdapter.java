package com.fengshihao.xframe.ui.widget.CommonRecyclerView;

import android.graphics.pdf.PdfDocument;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.fengshihao.xframe.logic.layzlist.IPageListListener;
import com.fengshihao.xframe.logic.layzlist.PageList;

import java.util.Arrays;
import java.util.List;

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
    mList.updateCurrentPage(position);
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

  void setItemLayoutIds(@LayoutRes int... layoutIds) {
    Log.d(TAG, "setItemLayoutIds() called with: layoutIds = ["
        + Arrays.toString(layoutIds) + "]");
    mItemLayoutIds = layoutIds;
  }

  void clear() {
    mList.clear();
  }

  <T extends ICommonItemModel> void addAll(@NonNull List<T> list) {
    mList.addAll(list);
    Log.d(TAG, "addAll: size=" + list.size());
    notifyDataSetChanged();
  }

  public int getPageSize() {
    return mList.getPageSize();
  }

  public void addPageListListener(IPageListListener listener) {
    mList.addListener(listener);
  }

  public void removePageListListener(IPageListListener listener) {
    mList.removeListener(listener);
  }

  public void setModels(int offset, @NonNull List<? extends ICommonItemModel> models) {
    mList.setItems(offset, models);
  }

  @Override
  public void onAddNew(int from, int to) {
    Log.d(TAG, "onAddNew() called with: from = [" + from + "], to = [" + to + "]");
    notifyItemRangeInserted(from, to);
  }

//
//    void select(@NonNull Integer... pos) {
//      Set<Integer> oldSelect = new HashSet<>(mSelects);
//
//      mSelects.clear();
//      for (Integer idx : pos) {
//        if (idx < 0 || idx >= mList.size()) {
//          throw new IllegalArgumentException("wrong idx=" + idx);
//        }
//
//        mSelects.add(idx);
//      }
//
//      for (Integer idx : mSelects) {
//        if (oldSelect.isSelected(idx)) {
//          continue;
//        }
//        Log.d(TAG, "select: new select =" + idx);
//        notifyItemChanged(idx);
//      }
//      oldSelect.removeAll(mSelects);
//      for (Integer idx : oldSelect) {
//        Log.d(TAG, "select: un select =" + idx);
//        notifyItemChanged(idx);
//      }
//    }
}
