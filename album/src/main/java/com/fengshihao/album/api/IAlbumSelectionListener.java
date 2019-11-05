package com.fengshihao.album.api;

import android.support.annotation.NonNull;

import com.fengshihao.album.logic.model.AlbumMediaItem;

public interface IAlbumSelectionListener {
  void onSelect(@NonNull AlbumMediaItem item);

  void onUnSelect(@NonNull AlbumMediaItem item);

  default void onSelectFull(int maxSelectCount) {}
}
