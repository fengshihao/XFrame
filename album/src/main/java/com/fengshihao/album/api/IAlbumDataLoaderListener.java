package com.fengshihao.album.api;

import android.support.annotation.NonNull;

import com.fengshihao.album.logic.AlbumItem;

import java.util.List;

/**
 * 手机相册逻辑的数据回调
 */
public interface IAlbumDataLoaderListener {
  int LOAD_ERROR_WRONG_ARG = -1;
  int LOAD_ERROR_UNKOWN = -2;

  void onAlbumLoaded(@NonNull List<AlbumItem> result);
  void onAlbumLoadError(Throwable e);
}