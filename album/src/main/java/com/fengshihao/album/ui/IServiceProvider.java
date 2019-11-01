package com.fengshihao.album.ui;

import android.support.annotation.NonNull;

import com.fengshihao.album.api.IAlbumProject;

public interface IServiceProvider {
  @NonNull
  IAlbumProject getAlbumProject();
}
