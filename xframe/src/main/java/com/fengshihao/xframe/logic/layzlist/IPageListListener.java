package com.fengshihao.xframe.logic.layzlist;

interface IPageListListener {
  default void onChangeSize(int old, int size) {};

  default void lackOf(int position) {};

  default void accessPageChange(int newPage, int old) {};
}
