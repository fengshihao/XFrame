package com.fengshihao.album.api;

import com.fengshihao.album.logic.AlbumItem;
import com.fengshihao.xframe.logic.listener.IListeners;

/**
 * 手机相册逻辑接口
 */
public interface IAlbumDataLoader extends IListeners<IAlbumDataLoaderListener> {
  /**
   * 加载相册数据, 最新的在最前边.
   * @param type 要加载数据的类型
   * @param offset   在数据库中的偏移
   * @param pageItemCount  要load的数据个数.
   */
  void loadAlbum(@AlbumItem.AlbumType int type, int offset, int pageItemCount);
}
 
