package com.fengshihao.xframe.logic.selection;

import android.support.annotation.NonNull;
import android.util.Log;

import com.fengshihao.xframe.logic.listener.ListenerManager;

import java.util.ArrayList;
import java.util.List;

public class ItemSelection<T> extends ListenerManager<IItemSelectionListener<T>> {

  private static final String TAG = "ItemSelection";

  @NonNull
  private final List<T> mSelectList = new ArrayList<>();

  private int mMaxSelectNum = 1;

  public boolean select(@NonNull T item) {
    Log.d(TAG, "select() called with: item = [" + item + "]");
    if (isSelected(item)) {
      Log.d(TAG, "select: already exist ");
      return true;
    }

    if (!couldSelect(item)) {
      Log.d(TAG, "select: cant select " + item);
      return false;
    }

    if (isSingleSelectMode() && !mSelectList.isEmpty()) {
      for (T it: mSelectList) {
        unSelect(it);
      }
    } else if (mSelectList.size() >= mMaxSelectNum) {
      Log.d(TAG, "select: mMaxSelectNum=" + mMaxSelectNum + " select=" + mSelectList.size());
      notifyListeners(l -> l.onSelectFull(mMaxSelectNum));
      return false;
    }

    Log.d(TAG, "select: add item " + item);
    mSelectList.add(item);
    notifyListeners(l -> l.onSelect(item));
    return true;
  }

  public boolean isSingleSelectMode() {
    return mMaxSelectNum == 1;
  }

  public boolean isSelected(@NonNull T item) {
    return mSelectList.contains(item);
  }

  public void unSelect(@NonNull T item) {
    if (mSelectList.remove(item)) {
      Log.d(TAG, "unSelect: " + item);
      notifyListeners(l -> l.onUnSelect(item));
    } else {
      Log.w(TAG, "unSelect: cant find " + item);
    }
  }

  public int getIndexOrder(@NonNull T item) {
    return mSelectList.indexOf(item);
  }

  protected boolean couldSelect(@NonNull T item) {
    return true;
  }

  public void setMaxSelectNum(int max) {
    Log.d(TAG, "setMaxSelectNum() called with: max = [" + max + "]");
    if (max < 1) {
      Log.e(TAG, "setMaxSelectNum: wrong arg ");
      return;
    }
    mMaxSelectNum = max;
  }

}
