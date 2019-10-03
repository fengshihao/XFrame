package com.fengshihao.xframe.ui.widget.CommonRecyclerView;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

class CommonItemViewHolder<T extends CommonItemModel> extends CommonViewHolder<T> {

  @NonNull
  private final CommonItemView<T> mItemView;
  CommonItemViewHolder(@NonNull CommonItemView<T> itemView) {
    super(itemView);
    mItemView = itemView;
    mItemView.bindViews();
  }

  @Override
  protected void updateView(@Nullable T model, int position) {
    mItemView.updateView(model, position);
  }
}
