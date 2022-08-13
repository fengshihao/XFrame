package com.fengshihao.album.api;

import androidx.annotation.NonNull;

import com.fengshihao.album.logic.model.AlbumMediaItem;

public interface IAlbumSelectionListener {
  void onSelect(@NonNull AlbumMediaItem item);

  void onUnSelect(@NonNull AlbumMediaItem item);

  default void onSelectFull(int maxSelectCount) {}
}
