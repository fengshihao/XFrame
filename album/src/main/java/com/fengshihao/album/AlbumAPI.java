package com.fengshihao.album;

import android.app.Activity;
import android.app.Application;
import android.content.Intent;
import androidx.annotation.MainThread;
import androidx.annotation.NonNull;

import com.fengshihao.album.api.IAlbumAPI;
import com.fengshihao.album.api.IAlbumProject;
import com.fengshihao.album.logic.AlbumProject;
import com.fengshihao.album.ui.MediaSelectActivity;
import com.fengshihao.xframe.logic.config.ModuleConfig;

import java.util.Arrays;
import java.util.List;

@MainThread
public class AlbumAPI implements IAlbumAPI {

  @Override
  public void onApplicationStart(@NonNull Application app) {

  }

  @Override
  public void startActivity(@NonNull Activity fromActivity) {
    fromActivity.startActivity(new Intent(fromActivity, MediaSelectActivity.class));
  }

  @Override
  public IAlbumProject createProject() {
    return new AlbumProject();
  }

  @NonNull
  @Override
  public String getName() {
    return "album";
  }


  @Override
  @NonNull
  public List<ModuleConfig> getConfigs() {
    return Arrays.asList(Settings.MAX_SELECT_NUM, Settings.TEST_BOOL);
  }


}
