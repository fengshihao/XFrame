package com.fengshihao.album.ui;

import android.support.annotation.NonNull;

import com.fengshihao.xframe.ui.widget.CommonRecyclerView;

public class AlbumItemUIModel implements CommonRecyclerView.ItemModel {

  @NonNull
  final String mInfo;

  @NonNull
  final String mImagePath;

  final int mViewType;

  public AlbumItemUIModel(int viewType, @NonNull String info, @NonNull String img) {
    mViewType = viewType;
    mInfo = info;
    mImagePath = img;
  }

  @Override
  public int getViewType() {
    return mViewType;
  }
}
