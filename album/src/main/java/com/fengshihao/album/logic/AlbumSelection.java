package com.fengshihao.album.logic;

import androidx.annotation.NonNull;
import android.util.Log;

import com.fengshihao.album.api.IAlbumSelectionListener;
import com.fengshihao.album.logic.model.AlbumMediaItem;
import com.fengshihao.xframe.logic.listener.ListenerManager;

import java.util.ArrayList;
import java.util.List;

public class AlbumSelection extends ListenerManager<IAlbumSelectionListener> {

  private static final String TAG = "AlbumSelection";

  @NonNull
  private final List<AlbumMediaItem> mSelectList = new ArrayList<>();

  private int mMaxSelectNum = 1;

  boolean select(@NonNull AlbumMediaItem item) {
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
      for (AlbumMediaItem it: mSelectList) {
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

  private boolean isSingleSelectMode() {
    return mMaxSelectNum == 1;
  }

  boolean isSelected(@NonNull AlbumMediaItem item) {
    return mSelectList.contains(item);
  }

  void unSelect(@NonNull AlbumMediaItem item) {
    if (mSelectList.remove(item)) {
      Log.d(TAG, "unSelect: " + item);
      notifyListeners(l -> l.onUnSelect(item));
    } else {
      Log.w(TAG, "unSelect: cant find " + item);
    }
  }

  public int getIndexOrder(@NonNull AlbumMediaItem item) {
    return mSelectList.indexOf(item);
  }

  private boolean couldSelect(@NonNull AlbumMediaItem item) {
    return true;
  }

  void setMaxSelectNum(int max) {
    Log.d(TAG, "setMaxSelectNum() called with: max = [" + max + "]");
    if (max < 1) {
      Log.e(TAG, "setMaxSelectNum: wrong arg ");
      return;
    }
    mMaxSelectNum = max;
  }

}
