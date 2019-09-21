package com.fengshihao.xframe.logic.listener;

/**
 * 通用维护一堆listener类的接口
 * @param <T>
 */
public interface IListenerManager<T> {
  void addListener(T listener);

  void removeListener(T listener);

  void clearListener();
}
