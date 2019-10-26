package com.fengshihao.xframe.ui.widget.CommonRecyclerView;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.View;

public abstract class CommonViewHolder<T extends CommonItemModel> extends RecyclerView.ViewHolder {

  @Nullable
  protected T mModel;

  public CommonViewHolder(@NonNull View itemView) {
    super(itemView);
    bindView(itemView);
  }

  public void update(@Nullable T model, int position) {
    mModel = model;
    updateView(position);
  }

  protected abstract void bindView(@NonNull View itemView);

  protected abstract void updateView(int position);
}
