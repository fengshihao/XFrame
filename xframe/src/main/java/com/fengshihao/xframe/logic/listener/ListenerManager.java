package com.fengshihao.xframe.logic.listener;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import java.util.LinkedList;
import java.util.List;

/**
 * 类如果有很多listener 可以继承这个类,减少样板代码
 *
 * 例如一个listener的接口是
 * interface TestListener {
 * void foo1(int a, int b)
 * }
 *
 * class Test extends ListenerManager<TestListener> {
 *
 * }
 *
 * 然后就可以使用
 *
 * Test t ;
 * TestListener testListener;
 *
 * t.addListener(testListener);
 * t.removeListener(testListener);
 * 通知所有的listener
 *
 * t.notifyListeners(l -> { l.foo1(10, 20); })
 *
 * @param <T> 一个接口
 */
public class ListenerManager<T> {
  private static final String TAG = "ListenerManager";
  @NonNull
  private final List<T> mListeners = new LinkedList<>();

  @Nullable
  private List<? extends T> mPipeListeners;

  public void addListener(T listener) {
    Log.d(TAG, "addListener() called with: listener = [" + listener + "]");
    if (listener == null) {
      throw new RuntimeException("listener is null");
    }
    if (mListeners.contains(listener)) {
      Log.w(TAG, "addListener: already exist listener=" + listener);
      return;
    }
    mListeners.add(listener);
  }

  public void removeListener(T listener) {
    Log.d(TAG, "removeListener() called with: listener = [" + listener + "]");
    if (listener == null) {
      throw new RuntimeException("listener is null");
    }
    if (!mListeners.contains(listener)) {
      Log.w(TAG, "removeListener: don't contain this listener=" + listener);
      return;
    }
    mListeners.remove(listener);
  }

  public void pipeEventTo(@NonNull ListenerManager<? extends T> l) {
    Log.d(TAG, "pipeEventTo: this=" + this + " to " + l);
    mPipeListeners = l.mListeners;
  }

  public void clearListener() {
    Log.d(TAG, "clearListener() called");
    mListeners.clear();
  }

  public interface Caller<T> {
    void apply(T t);
  }

  public void notifyListeners(Caller<T> caller) {
    for (T listener : mListeners) {
      caller.apply(listener);
    }

    if (mPipeListeners != null) {
      for (T listener : mPipeListeners) {
        caller.apply(listener);
      }
    }
  }
}
