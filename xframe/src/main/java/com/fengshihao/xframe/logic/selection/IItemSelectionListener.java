package com.fengshihao.xframe.logic.selection;

import android.support.annotation.NonNull;

public interface IItemSelectionListener<T> {
  void onSelect(@NonNull T item);

  void onUnSelect(@NonNull T item);

  default void onSelectFull(int maxSelectCount) {}
}
