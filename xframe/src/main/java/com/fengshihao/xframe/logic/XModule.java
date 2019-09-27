package com.fengshihao.xframe.logic;

import java.util.Collections;
import java.util.List;

import android.app.Application;
import android.support.annotation.NonNull;

import com.fengshihao.xframe.logic.debug.DebugConfig;

public interface XModule {

  default void onApplicationStart(Application app) {}

  @NonNull
  default List<DebugConfig> getConfigs() {return Collections.emptyList();}

}
