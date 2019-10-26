package com.fengshihao.xframe.ui.widget.CommonRecyclerView;

import android.support.annotation.LayoutRes;

public abstract class CommonItemModel {

  public final long mId;

  public CommonItemModel(long id) {
    mId = id;
  }

  @LayoutRes
  public abstract int getLayoutId();
}
