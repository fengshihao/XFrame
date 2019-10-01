package com.fengshihao.xframe.ui.widget.CommonRecyclerView;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;

public abstract class CommonItemView<T extends CommonItemModel> extends FrameLayout
    implements IItemViewOperator<T> {

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

  @Override
  public void bindViews(@NonNull View view) {
    bindViews();
  }

  @Override
  public void updateView(@Nullable T data, int position) {
    mModel = data;
    updateView(position);
  }

  abstract public void bindViews();

  abstract public void updateView(int position);
}
