package com.fengshihao.xframe.ui.widget.CommonRecyclerView;

import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.View;

public abstract class CommonViewHolder<T extends CommonItemModel> extends RecyclerView.ViewHolder {

  public CommonViewHolder(View itemView) {
    super(itemView);
  }

  protected abstract void updateView(@Nullable T model, int position);
}
