package com.fengshihao.album;

import android.app.Activity;
import android.app.Application;
import android.content.Intent;
import android.support.annotation.NonNull;

import com.fengshihao.album.ui.MediaSelectActivity;
import com.fengshihao.album.api.IAlbum;

public class Album implements IAlbum {

  @Override
  public void startActivity(@NonNull Activity fromActivity) {
    fromActivity.startActivity(new Intent(fromActivity, MediaSelectActivity.class));
  }

  @Override
  public void onApplicationStart(Application app) {

  }
}
