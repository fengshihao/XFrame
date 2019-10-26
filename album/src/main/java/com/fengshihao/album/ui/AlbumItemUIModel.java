package com.fengshihao.album.ui;

import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;

import com.fengshihao.album.R;
import com.fengshihao.album.logic.AlbumMediaItem;
import com.fengshihao.xframe.ui.widget.CommonRecyclerView.CommonItemModel;

public class AlbumItemUIModel extends CommonItemModel {

  @NonNull
  final String mInfo;

  @NonNull
  final String mImagePath;

  @LayoutRes
  final int mLayoutId;

  AlbumItemUIModel(long id, @AlbumMediaItem.AlbumType int viewType,
                   @NonNull String info, @NonNull String img) {
    super(id);
    mInfo = info;
    if (viewType == AlbumMediaItem.IMAGE) {
      mLayoutId = R.layout.fragment_album_item;
    } else {
      mLayoutId = R.layout.fragment_album_item_video;
    }
    mImagePath = img;
  }

  @Override
  public int getLayoutId() {
    return mLayoutId;
  }

  @NonNull
  @Override
  public String toString() {
    return "AlbumItemUIModel mId=" + mId + " mLayoutId=" + mLayoutId;
  }
}
