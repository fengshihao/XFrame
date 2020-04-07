package com.fengshihao.xframe.logic.rxtool;

import javax.annotation.Nullable;

public class Task<T> {
  @Nullable
  private T mResult;

  private int mPercent;


  @Nullable
  public T getResult() {
    return mResult;
  }

  public int getPercent() {
    return mPercent;
  }
}
