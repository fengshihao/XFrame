package com.fengshihao.xframe.logic;

import android.app.Application;

public interface XModule {
  default void onApplicationStart(Application app) {}
}
