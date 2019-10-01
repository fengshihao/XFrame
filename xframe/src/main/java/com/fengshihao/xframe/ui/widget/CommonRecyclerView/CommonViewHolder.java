package com.fengshihao.xframe.ui.widget.CommonRecyclerView;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.View;

class CommonViewHolder<T extends CommonItemModel> extends RecyclerView.ViewHolder {

  @NonNull
  private final IItemViewOperator<T> mViewOperator;

  CommonViewHolder(@NonNull View itemView, @NonNull IItemViewOperator<T> operator) {
    super(itemView);
    mViewOperator = operator;
    mViewOperator.bindViews(itemView);
  }

  void updateView(@Nullable T model, int position) {
    mViewOperator.updateView(model, position);
  }
}
