package com.fengshihao.xframe.ui.widget.CommonRecyclerView;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.LinearLayout;

public abstract class CommonItemView<T extends ICommonItemModel> extends LinearLayout {

  @Nullable
  protected T mModel;
  public CommonItemView(Context context) {
    super(context);
  }

  public CommonItemView(Context context, AttributeSet attrs) {
    super(context, attrs);
  }

  public CommonItemView(Context context, AttributeSet attrs, int defStyleAttr) {
    super(context, attrs, defStyleAttr);
  }

  public void setModel(@Nullable T uiModel) {
    mModel = uiModel;
  }

  abstract public void bindViews();

  abstract public void updateView(int position);
}
