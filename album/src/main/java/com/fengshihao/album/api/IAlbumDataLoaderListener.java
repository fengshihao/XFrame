package com.fengshihao.album.api;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.fengshihao.album.logic.AlbumItem;

import java.util.List;

/**
 * 手机相册逻辑的数据回调
 */
public interface IAlbumDataLoaderListener {
  void onAlbumLoaded(@NonNull AlbumLoaderRequest request,
                     Throwable err,
                     @Nullable List<AlbumItem> result);
}