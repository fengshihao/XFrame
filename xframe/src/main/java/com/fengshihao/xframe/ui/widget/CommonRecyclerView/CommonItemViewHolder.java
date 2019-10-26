package com.fengshihao.xframe.ui.widget.CommonRecyclerView;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;

class CommonItemViewHolder<T extends CommonItemModel> extends CommonViewHolder<T> {

  @NonNull
  private final CommonItemView<T> mItemView;
  CommonItemViewHolder(@NonNull CommonItemView<T> itemView) {
    super(itemView);
    mItemView = itemView;
    mItemView.bindViews();
  }

  @Override
  protected void bindView(@Nullable View itemView) {

  }

  @Override
  protected void updateView(int position) {

  }

  @Override
  public void update(@Nullable T model, int position) {
    mItemView.updateView(model, position);
  }
}
