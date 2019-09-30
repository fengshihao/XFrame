package com.fengshihao.album;

import android.support.annotation.NonNull;

import com.fengshihao.xframe.logic.config.ModuleConfig;

public interface Settings {
  @NonNull
  ModuleConfig MAX_SELECT_NUM = new ModuleConfig("max_select_num", 10);
  @NonNull
  ModuleConfig TEST_BOOL = new ModuleConfig("test_bool", false);
}
