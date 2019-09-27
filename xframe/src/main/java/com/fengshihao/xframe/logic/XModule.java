package com.fengshihao.xframe.logic;

import java.util.Collections;
import java.util.List;

import android.app.Application;
import android.support.annotation.NonNull;

import com.fengshihao.xframe.logic.debug.ModuleConfig;

public interface XModule {

  @NonNull
  String getName();

  default void onApplicationStart(Application app) {}

  @NonNull
  default List<ModuleConfig> getConfigs() {return Collections.emptyList();}

}
