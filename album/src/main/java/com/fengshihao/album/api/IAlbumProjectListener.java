package com.fengshihao.album.api;

import android.support.annotation.NonNull;

import com.fengshihao.xframe.logic.ItemSelection;

public interface IAlbumProjectListener extends ItemSelection.Listener<Integer> {
  void onAlbumLoaded(@NonNull AlbumLoaderResult result);
}