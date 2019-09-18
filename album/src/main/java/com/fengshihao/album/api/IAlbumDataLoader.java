package com.fengshihao.album.api;

import android.support.annotation.NonNull;

import com.fengshihao.xframe.logic.listener.IListeners;

/**
 * 手机相册逻辑接口
 */
public interface IAlbumDataLoader extends IListeners<IAlbumDataLoaderListener> {

  void loadAlbum(@NonNull AlbumLoaderRequest request);
}
 
