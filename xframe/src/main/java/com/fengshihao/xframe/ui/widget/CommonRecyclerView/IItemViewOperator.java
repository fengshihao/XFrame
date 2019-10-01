package com.fengshihao.xframe.ui.widget.CommonRecyclerView;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;

public interface IItemViewOperator<T extends CommonItemModel> {

    void bindViews(@NonNull View view);

    void updateView(@Nullable T data, int position);
  }