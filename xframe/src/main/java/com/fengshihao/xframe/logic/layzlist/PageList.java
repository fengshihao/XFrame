package com.fengshihao.xframe.logic.layzlist;

import android.support.annotation.NonNull;

import com.fengshihao.xframe.logic.listener.Listeners;

import java.util.ArrayList;
import java.util.List;

public class PageList<T> extends Listeners<IPageListListener> {

  @NonNull
  private final List<T> mList = new ArrayList<>();

  private int mSize;

  public T get(int position) {
    return mList.get(position);
  }

  public int size() {
    return mList.size();
  }

  public void setSize(int mSize) {
    this.mSize = mSize;
  }

  public void clear() {
    mList.clear();
  }

  public void add(T m) {
    mList.add(m);
  }

  public void addAll(List<T> list) {
    mList.addAll(list);
  }
}
