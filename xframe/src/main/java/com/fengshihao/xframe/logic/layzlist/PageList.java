package com.fengshihao.xframe.logic.layzlist;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import com.fengshihao.xframe.logic.listener.ListenerManager;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class PageList<T> extends ListenerManager<IPageListListener> {
  private static final String TAG = "PageList";

  private static final int DEFAULT_PAGE_SIZE = 100;
  private static final int MIN_PAGE_SIZE = 4;
  private static final int NO_POS = -1;
  private int mPageSize = DEFAULT_PAGE_SIZE;

  private int mCurrentPage = NO_POS;

  private int mLastPage = NO_POS;

  @NonNull
  private final List<T> mList = new ArrayList<>();

  @NonNull
  private final Set<Integer> mLoadingPage = new HashSet<>();

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

  private void updateCurrentPage(int position) {
    if (position < 0) {
      Log.e(TAG, "updateCurrentPage: wrong arg position=" + position);
      return;
    }
    int newPage = position / mPageSize;
    if (newPage != mCurrentPage) {
      int old = mCurrentPage;
      mCurrentPage = newPage;
      if (mCurrentPage >= 1) {
        requireLoadPage(mCurrentPage - 1);
      }
      requireLoadPage(mCurrentPage + 1);
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
    mCurrentPage = NO_POS;
    mLastPage = NO_POS;
  }

  public T remove(int index) {
    if (index < 0 || index >= mList.size()) {
      Log.e(TAG, "remove: wrong arg index=" + index + " list size=" + mList.size());
      return null;
    }
    T item = mList.remove(index);
    notifyListeners(l-> l.onRemoveItem(index));
    if (mList.isEmpty()) {
      clear();
    }
    return item;
  }

  public void addItem(@NonNull T item) {
    addAll(Collections.singletonList(item));
  }

  public void addAll(@NonNull List<? extends T> list) {
    if (list.isEmpty()) {
      Log.w(TAG, "addAll: empty list");
      return;
    }
    int from = mList.size();
    mList.addAll(list);
    int to = mList.size();
    notifyListeners(l -> l.onAddNewItems(from, to));
  }

  public void setItems(int pageNo, @NonNull List<? extends T> models) {
    Log.d(TAG, "setItems() called with: pageNo = [" + pageNo
        + "], models = [" + models.size() + "]");
    if (pageNo < 0) {
      Log.e(TAG, "setItems: wrong args pageNo=" + pageNo);
      return;
    }

    mLoadingPage.remove(pageNo);

    if (models.isEmpty()) {
      Log.w(TAG, "setItems: empty models");
      return;
    }

    if (models.size() < mPageSize && pageNo != mLastPage) {
      Log.w(TAG, "setItems: less than mPageSize " + models.size()
          + " pageNo=" + pageNo
          + " mLastPage=" + mLastPage);
    }
    int startPos = pageNo * mPageSize;
    if (updatePageItems(startPos, models)) {
      return;
    }

    if (mList.size() < startPos) {
      int nowEnd = mList.size();
      int needFillNum = startPos - nowEnd;
      Log.d(TAG, "setItems: need fill empty items " + needFillNum);
      for (int i = nowEnd; i < needFillNum; i++) {
        mList.add(i, null);
      }
      notifyListeners(l -> l.onAddNewItems(nowEnd, needFillNum));
    }

    addAll(models);
  }

  private boolean updatePageItems(int start, @NonNull List<? extends T> models) {
    Log.v(TAG, "updatePageItems() called with: start = ["
        + start + ", models size = [" + models.size() + "]");
    if (start < 0  || models.isEmpty()) {
      Log.e(TAG, "updatePageItems: wrong args");
      return false;
    }

    int end = start + models.size();
    if (end <= mList.size()) {
      for (int i = start, j = 0; i < end; i++, j++) {
        mList.set(i, models.get(j));
      }
      notifyListeners(l -> l.onUpdateItems(start, end - start));
      Log.d(TAG, "updatePageItems: start=" + start
          + " end=" + end + " list size=" + models.size()) ;
      return true;
    }
    return false;
  }

  public void visitItem(int position) {
    if (position < 0) {
      Log.e(TAG, "visitItem: wrong arg position=" + position);
      return;
    }
    updateCurrentPage(position);
  }

  private void requireLoadPage(int page) {
    Log.d(TAG, "requireLoadPage() called with: page = [" + page + "]");
    if (page < 0) {
      Log.e(TAG, "requireLoadPage: wrong arg page=" + page);
      return;
    }

    // 到了最后一页
    if (mLastPage >= 0 && page >= mLastPage) {
      return;
    }


    if (pageIsLoaded(page)) {
      Log.d(TAG, "requireLoadPage: has loaded " + page);
      return;
    }

    if (mLoadingPage.contains(page)) {
      Log.d(TAG, "requireLoadPage: is loading " + page);
      return;
    }

    mLoadingPage.add(page);
    notifyListeners(l-> l.onRequireLoad(page, mPageSize));

  }

  private boolean pageIsLoaded(int page) {
    int pageFirst = page * mPageSize;
    if (pageFirst >= mList.size()) {
      return false;
    } else {
      return mList.get(pageFirst) != null;
    }
  }
}
