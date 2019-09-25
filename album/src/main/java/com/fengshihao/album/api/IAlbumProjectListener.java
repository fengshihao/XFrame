package com.fengshihao.album.api;

import android.support.annotation.NonNull;

import com.fengshihao.album.logic.AlbumMediaItem;
import com.fengshihao.xframe.logic.ItemSelection;

public interface IAlbumProjectListener extends ItemSelection.Listener<AlbumMediaItem> {
  void onAlbumLoaded(@NonNull AlbumLoaderResult result);
}
