package com.fengshihao.xframe.logic.layzlist;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import com.fengshihao.xframe.logic.listener.Listeners;

import java.util.ArrayList;
import java.util.List;

public class PageList<T> extends Listeners<IPageListListener> {
  private static final String TAG = "PageList";

  @NonNull
  private final List<T> mList = new ArrayList<>();

  @Nullable
  public T get(int position) {
    return mList.get(position);
  }

  public int size() {
    return mList.size();
  }

  public void setSize(int size) {
    Log.d(TAG, "setSize: " + size);
    if (size < 0) {
      Log.e(TAG, "setSize: wrong arg ");
      return;
    }
    if (size == mList.size()) {
      Log.w(TAG, "setSize: same size ");
      return;
    }

    clear();
    for (int i = 0; i < size; i++) {
      mList.add(null);
    }
  }

  public void clear() {
    Log.d(TAG, "clear() called");
    mList.clear();
  }

  public void add(T m) {
    mList.add(m);
  }

  public void addAll(List<? extends T> list) {
    mList.addAll(list);
  }
}
