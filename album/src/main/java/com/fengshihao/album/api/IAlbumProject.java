package com.fengshihao.album.api;

import androidx.annotation.NonNull;

import com.fengshihao.album.logic.model.AlbumLoaderRequest;
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
 
