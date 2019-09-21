package com.fengshihao.xframe.ui.widget.CommonRecyclerView;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.widget.FrameLayout;

public abstract class CommonItemView<T extends ICommonItemModel> extends FrameLayout {
  protected int mPosition = RecyclerView.NO_POSITION;

  public CommonItemView(Context context) {
    super(context);
  }

  public CommonItemView(Context context, AttributeSet attrs) {
    super(context, attrs);
  }

  public CommonItemView(Context context, AttributeSet attrs, int defStyleAttr) {
    super(context, attrs, defStyleAttr);
  }

  void setPosition(int pos) {
    mPosition = pos;
  }

  public int getPosition() {
    return mPosition;
  }

  abstract public void bindViews();

  abstract public void updateView(@NonNull T uiModel);
}
