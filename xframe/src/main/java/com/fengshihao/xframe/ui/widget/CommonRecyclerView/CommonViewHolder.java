package com.fengshihao.xframe.ui.widget.CommonRecyclerView;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;

class CommonViewHolder extends RecyclerView.ViewHolder {

  @NonNull
  private CommonItemView mHoldView;
  CommonViewHolder(@NonNull CommonItemView itemView) {
    super(itemView);
    mHoldView = itemView;
  }

  void updateView(@Nullable ICommonItemModel model, int position) {
    mHoldView.setPosition(position);
    mHoldView.updateView(model);
  }
}
