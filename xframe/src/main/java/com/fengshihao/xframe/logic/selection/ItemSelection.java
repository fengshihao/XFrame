package com.fengshihao.xframe.logic.selection;

import android.support.annotation.NonNull;
import android.util.Log;

import com.fengshihao.xframe.logic.listener.ListenerManager;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ItemSelection<T> extends ListenerManager<IItemSelectionListener<T>> {

  private static final String TAG = "ItemSelection";


  @NonNull
  private final Set<T> mSelects = new HashSet<>();

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

    if (isSingleSelectMode() && !mSelects.isEmpty()) {
      for (T it: mSelectList) {
        unSelect(it);
      }
    } else if (mSelects.size() >= mMaxSelectNum) {
      Log.d(TAG, "select: mMaxSelectNum=" + mMaxSelectNum + " select=" + mSelects.size());
      notifyListeners(l -> l.onSelectFull(mMaxSelectNum));
      return false;
    }

    Log.d(TAG, "select: add item " + item);
    mSelects.add(item);
    mSelectList.add(item);
    notifyListeners(l -> l.onSelect(item));
    return true;
  }

  public boolean isSingleSelectMode() {
    return mMaxSelectNum == 1;
  }

  public boolean isSelected(@NonNull T item) {
    return mSelects.contains(item);
  }

  public void unSelect(@NonNull T item) {
    if (mSelects.remove(item)) {
      Log.d(TAG, "unSelect: " + item);
      mSelectList.remove(item);
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
