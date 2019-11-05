package com.fengshihao.album.api;

import android.support.annotation.NonNull;

import com.fengshihao.album.logic.model.AlbumLoaderResult;
import com.fengshihao.album.logic.model.AlbumMediaItem;

public interface IAlbumProjectListener extends IAlbumSelectionListener {
  default void onAlbumLoaded(@NonNull AlbumLoaderResult result) {}
}
