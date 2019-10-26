package com.fengshihao.xframe.ui.widget.CommonRecyclerView;

import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.view.View;

public interface ICommonViewHolderCreator<T extends CommonItemModel> {

  @NonNull
  CommonViewHolder<T> createViewHolder(@NonNull View v, @LayoutRes int layoutId);
}
