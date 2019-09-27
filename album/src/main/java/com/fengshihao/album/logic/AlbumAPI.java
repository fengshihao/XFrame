package com.fengshihao.album.logic;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;

import com.fengshihao.album.api.IAlbumAPI;
import com.fengshihao.album.ui.MediaSelectActivity;
import com.fengshihao.xframe.logic.debug.ModuleConfig;

import java.util.Arrays;
import java.util.List;

public class AlbumAPI implements IAlbumAPI {

  private static Application sContext;

  @NonNull
  public static final ModuleConfig MAX_SELECT_NUM = new ModuleConfig("max_select_num", 10);

  @NonNull
  public static final ModuleConfig TEST_BOOL = new ModuleConfig("test_bool", false);

  static Context getContext() {
    return sContext;
  }

  @Override
  public void startActivity(@NonNull Activity fromActivity) {
    fromActivity.startActivity(new Intent(fromActivity, MediaSelectActivity.class));
  }

  @NonNull
  @Override
  public String getName() {
    return "album";
  }

  @Override
  public void onApplicationStart(Application app) {
    sContext = app;
  }


  @Override
  @NonNull
  public List<ModuleConfig> getConfigs() {
    return Arrays.asList(MAX_SELECT_NUM, TEST_BOOL);
  }

}
