package com.fengshihao.xframe.logic.layzlist;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import com.fengshihao.xframe.logic.listener.ListenerManager;

import java.util.ArrayList;
import java.util.List;

public class PageList<T> extends ListenerManager<IPageListListener> {
  private static final String TAG = "PageList";

  private static final int DEFAULT_PAGE_SIZE = 100;
  private static final int MIN_PAGE_SIZE = 4;

  private int mPageSize = DEFAULT_PAGE_SIZE;

  private int mCurrentPage = -1;

  @NonNull
  private final List<T> mList = new ArrayList<>();


  public void setPageSize(int pageSize) {
    Log.d(TAG, "setPageSize() called with: pageSize = [" + pageSize + "]");
    if (pageSize < MIN_PAGE_SIZE) {
      Log.e(TAG, "setPageSize: too small");
      return;
    }
    mPageSize = pageSize;
  }

  public int getPageSize() {
    return mPageSize;
  }

  @Nullable
  public T get(int position) {
    if (position < 0 || position >= mList.size()) {
      Log.e(TAG, "get: wrong arg position=" + position + " size=" + mList.size());
      return null;
    }
    return mList.get(position);
  }

  public void updateCurrentPage(int position) {
    if (position < 0) {
      Log.e(TAG, "get: wrong arg position=" + position);
      return;
    }
    int newPage = position / mPageSize;
    if (newPage != mCurrentPage) {
      int old = mCurrentPage;
      mCurrentPage = newPage;
      Log.d(TAG, "updateCurrentPage: old=" + old + " newPage=" + newPage);
      notifyListeners(l -> l.accessPageChange(newPage, old));
    }
  }

  public int size() {
    return mList.size();
  }

  public void setSize(int newSize) {
    Log.d(TAG, "setSize: " + newSize);
    if (newSize < 0) {
      Log.e(TAG, "setSize: wrong arg ");
      return;
    }
    int oldSize = mList.size();

    if (newSize == oldSize) {
      Log.w(TAG, "setSize: same size ");
      return;
    }

    clear();
    for (int i = 0; i < newSize; i++) {
      mList.add(null);
    }
    notifyListeners(l -> l.onChangeSize(oldSize, mList.size()));
  }

  public void clear() {
    Log.d(TAG, "clear() called");
    mList.clear();
    mCurrentPage = -1;
  }

  public void addAll(@NonNull List<? extends T> list) {
    if (list.isEmpty()) {
      Log.w(TAG, "addAll: empty list");
      return;
    }
    int from = mList.size();
    mList.addAll(list);
    int to = mList.size();
    notifyListeners(l -> l.onAddNew(from, to));
  }

  public void setItems(int offset, @NonNull List<? extends T> models) {
    Log.d(TAG, "setItems() called with: offset = [" + offset
        + "], models = [" + models.size() + "]");
    if (offset < 0) {
      Log.e(TAG, "setItems: wrong args offset < 0");
      return;
    }

    if (models.isEmpty()) {
      Log.w(TAG, "setItems: empty models");
      return;
    }

    int nowSize = mList.size();
    if (nowSize == offset) {
      addAll(models);
    }
  }
}
