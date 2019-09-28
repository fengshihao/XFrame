package com.fengshihao.album.api;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.fengshihao.album.api.IAlbumAPI;
import com.fengshihao.album.ui.MediaSelectActivity;
import com.fengshihao.xframe.logic.config.ModuleConfig;

import java.util.Arrays;
import java.util.List;

public class AlbumAPI implements IAlbumAPI {

  @Nullable
  private static Application sContext;

  @NonNull
  public static final ModuleConfig MAX_SELECT_NUM = new ModuleConfig("max_select_num", 10);

  @NonNull
  public static final ModuleConfig TEST_BOOL = new ModuleConfig("test_bool", false);

  private AlbumAPI() {}

  @NonNull
  private static AlbumAPI sInstance = new AlbumAPI();

  @NonNull
  public static IAlbumAPI getInstance() {
    return sInstance;
  }

  @Override
  public void onApplicationStart(@NonNull Application app) {
    sContext = app;
  }

  @Nullable
  public static Context getContext() {
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
  @NonNull
  public List<ModuleConfig> getConfigs() {
    return Arrays.asList(MAX_SELECT_NUM, TEST_BOOL);
  }

}
