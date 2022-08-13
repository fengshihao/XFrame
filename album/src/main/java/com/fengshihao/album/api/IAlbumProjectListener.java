package com.fengshihao.album.api;

import androidx.annotation.NonNull;

import com.fengshihao.album.logic.model.AlbumLoaderResult;

public interface IAlbumProjectListener extends IAlbumSelectionListener {
  default void onAlbumLoaded(@NonNull AlbumLoaderResult result) {}
}
