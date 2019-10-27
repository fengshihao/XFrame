package com.fengshihao.album.api;

import android.support.annotation.NonNull;

import com.fengshihao.xframe.logic.listener.IListenerManager;

/**
 * 手机相册逻辑接口
 */
public interface IAlbumProject extends IListenerManager<IAlbumProjectListener> {

  void loadAlbum(@NonNull AlbumLoaderRequest request);

  void close();

  int getId();

  void toggleSelect(long id);

  void unSelect(long id);

  boolean isSelected(long mItemId);
}
 
