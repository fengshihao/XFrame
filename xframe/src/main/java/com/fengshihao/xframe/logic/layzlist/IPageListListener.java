package com.fengshihao.xframe.logic.layzlist;

public interface IPageListListener {
  default void onChangeSize(int old, int size) {}

  default void lackOf(int position) {}

  default void accessPageChange(int newPage, int old) {}

  default void onAddNewItems(int from, int to) {}

  default void onRequireLoad(int pageNo, int pageSize) {}

  default void onUpdateItems(int start, int end) {}
}
