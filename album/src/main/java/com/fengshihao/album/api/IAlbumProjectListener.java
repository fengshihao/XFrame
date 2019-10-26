package com.fengshihao.album.api;

import android.support.annotation.NonNull;

import com.fengshihao.album.logic.AlbumMediaItem;
import com.fengshihao.xframe.logic.selection.IItemSelectionListener;

public interface IAlbumProjectListener extends IItemSelectionListener<AlbumMediaItem> {
  default void onAlbumLoaded(@NonNull AlbumLoaderResult result) {}
}
