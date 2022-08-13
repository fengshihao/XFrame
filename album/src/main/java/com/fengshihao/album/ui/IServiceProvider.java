package com.fengshihao.album.ui;

import androidx.annotation.NonNull;

import com.fengshihao.album.api.IAlbumProject;

public interface IServiceProvider {
  @NonNull
  IAlbumProject getAlbumProject();
}
