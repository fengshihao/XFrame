package com.fengshihao.xframe.ui.widget.CommonRecyclerView;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;

class CommonViewHolder<T extends ICommonItemModel> extends RecyclerView.ViewHolder {

  @NonNull
  private CommonItemView<T> mHoldView;
  CommonViewHolder(@NonNull CommonItemView<T> itemView) {
    super(itemView);
    mHoldView = itemView;
  }

  void updateView(@NonNull T model, int position) {
    mHoldView.setPosition(position);
    mHoldView.updateView(model);
  }
}
