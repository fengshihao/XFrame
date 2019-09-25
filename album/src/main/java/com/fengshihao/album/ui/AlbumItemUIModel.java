package com.fengshihao.album.ui;

import android.support.annotation.NonNull;

import com.fengshihao.album.logic.AlbumMediaItem;
import com.fengshihao.album.logic.AlbumProject;
import com.fengshihao.xframe.ui.widget.CommonRecyclerView.ICommonItemModel;

public class AlbumItemUIModel implements ICommonItemModel {

  @NonNull
  final String mInfo;

  @NonNull
  final AlbumMediaItem mData;

  private final int mViewType;

  AlbumItemUIModel(@NonNull String info, @NonNull AlbumMediaItem data) {
    mData = data;
    mViewType = mData.mType;
    mInfo = info;
  }

  @Override
  public int getViewType() {
    return mViewType;
  }


  boolean isSelected() {
    return AlbumProject.getActiveProject().isSelected(mData);
  }
}
