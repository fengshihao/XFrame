package com.fengshihao.album.logic;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;

import com.fengshihao.album.api.IAlbumAPI;
import com.fengshihao.album.ui.MediaSelectActivity;

public class AlbumAPI implements IAlbumAPI {

  private static Application sContext;

  static Context getContext() {
    return sContext;
  }
  
  @Override
  public void startActivity(@NonNull Activity fromActivity) {
    fromActivity.startActivity(new Intent(fromActivity, MediaSelectActivity.class));
  }

  @Override
  public void onApplicationStart(Application app) {
    sContext = app;
  }
}
