package com.fengshihao.album.api;

import android.support.annotation.NonNull;

import com.fengshihao.xframe.logic.listener.IListeners;

/**
 * 手机相册逻辑接口
 */
public interface IAlbumProject extends IListeners<IAlbumProjectListener> {

  void loadAlbum(@NonNull AlbumLoaderRequest request);
}
 
