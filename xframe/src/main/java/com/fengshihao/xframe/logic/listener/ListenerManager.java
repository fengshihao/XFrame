package com.fengshihao.xframe.logic.listener;

import android.os.Looper;
import android.support.annotation.MainThread;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import java.util.LinkedList;
import java.util.List;

import com.fengshihao.xframe.BuildConfig;

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
@MainThread
public class ListenerManager<T> {
  private static final String TAG = "ListenerManager";
  private static final int TOO_LONG = 100; //ms


  @NonNull
  private final List<T> mListeners = new LinkedList<>();

  @Nullable
  private List<? extends T> mPipeListeners;

  @MainThread
  public void addListener(T listener) {
    checkInMainThread();
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

  @MainThread
  public void removeListener(T listener) {
    checkInMainThread();
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

  @MainThread
  public void pipeEventTo(@NonNull ListenerManager<? extends T> l) {
    checkInMainThread();
    Log.d(TAG, "pipeEventTo: this=" + this + " to " + l);
    mPipeListeners = l.mListeners;
  }

  @MainThread
  public void clearListener() {
    checkInMainThread();
    Log.d(TAG, "clearListener() called");
    mListeners.clear();
  }

  public interface Caller<T> {
    void apply(T t);
  }

  @MainThread
  public void notifyListeners(Caller<T> caller) {
    checkInMainThread();
    long allStart = System.currentTimeMillis();
    for (T listener : mListeners) {
      long start = 0;
      if (BuildConfig.DEBUG) {
        start = System.currentTimeMillis();
      }
      caller.apply(listener);

      if (BuildConfig.DEBUG) {
        long end = System.currentTimeMillis();
        if (end - start >= TOO_LONG) {
            throw new RuntimeException("listeners callback cost too much time=" + (end - start)
                + " " + listener);
        }
      }

    }

    if (mPipeListeners != null) {
      for (T listener : mPipeListeners) {
        caller.apply(listener);
      }
    }

    long allEnd = System.currentTimeMillis();
    if (allEnd - allStart >= TOO_LONG) {
      throw new RuntimeException("listeners callback cost too much time=" + (allEnd - allStart));
    }
  }

  private void checkInMainThread() {
    if (BuildConfig.DEBUG && Looper.getMainLooper().getThread() != Thread.currentThread()) {
      throw new RuntimeException("this method must call in main thread");
    }
  }
}
