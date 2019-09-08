package com.fengshihao.xframe.logic.listener;

import android.support.annotation.NonNull;

/**
 * 通用维护一堆listener类的接口
 * @param <T>
 */
public interface IListeners<T> {
  void addListener(@NonNull T listener);

  void removeListener(@NonNull T listener);

  void clearListener();
}
