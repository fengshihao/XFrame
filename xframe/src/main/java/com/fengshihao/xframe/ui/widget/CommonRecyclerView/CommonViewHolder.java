package com.fengshihao.xframe.ui.widget.CommonRecyclerView;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;

class CommonViewHolder<T extends ICommonItemModel> extends RecyclerView.ViewHolder {

  CommonViewHolder(@NonNull CommonItemView<T> itemView) {
    super(itemView);
  }

  @SuppressWarnings("unchecked")
  void updateView(@Nullable T model, int position) {
    CommonItemView<T> view = (CommonItemView<T>) itemView;
    view.setModel(model);
    view.updateView(position);
  }
}
