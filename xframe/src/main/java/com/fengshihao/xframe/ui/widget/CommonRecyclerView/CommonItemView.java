package com.fengshihao.xframe.ui.widget.CommonRecyclerView;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.RelativeLayout;

public abstract class CommonItemView<T extends CommonItemModel> extends RelativeLayout {

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

  public void updateView(@Nullable T data, int position) {
    mModel = data;
    updateView(position);
  }

  abstract protected void bindViews();

  abstract protected void updateView(int position);
}
