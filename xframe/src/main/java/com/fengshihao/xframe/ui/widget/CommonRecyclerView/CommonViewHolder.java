package com.fengshihao.xframe.ui.widget.CommonRecyclerView;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;

class CommonViewHolder extends RecyclerView.ViewHolder {

  CommonViewHolder(CommonItemView itemView) {
    super(itemView);
  }

  void updateView(@NonNull ICommonItemModel model, int position) {
    CommonItemView v = (CommonItemView) itemView;
    v.setPosition(position);
    v.updateView(model);
  }
}
