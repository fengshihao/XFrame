package com.fengshihao.xframe.ui.widget.CommonRecyclerView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import android.view.View;

import java.util.List;

public abstract class CommonViewHolder<T extends CommonItemModel> extends RecyclerView.ViewHolder {

  @Nullable
  protected T mModel;

  public CommonViewHolder(@NonNull View itemView) {
    super(itemView);
    bindView(itemView);
  }

  public void update(@Nullable T model, int position, @NonNull List<Object> payloads) {
    mModel = model;
    updateView(position, payloads);
  }

  protected abstract void bindView(@NonNull View itemView);

  protected abstract void updateView(int position, List<Object> payloads);
}
