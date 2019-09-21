package com.fengshihao.album.ui;

import android.support.annotation.NonNull;

import com.fengshihao.album.logic.AlbumProject;
import com.fengshihao.xframe.ui.widget.CommonRecyclerView.ICommonItemModel;

public class AlbumItemUIModel implements ICommonItemModel {

  @NonNull
  final String mInfo;

  @NonNull
  final String mImagePath;

  final int mViewType;

  final int mPosition;

  public AlbumItemUIModel(int pos, int viewType, @NonNull String info, @NonNull String img) {
    mPosition = pos;
    mViewType = viewType;
    mInfo = info;
    mImagePath = img;
  }

  @Override
  public int getViewType() {
    return mViewType;
  }


  boolean isSelected() {
    return AlbumProject.getActiveProject().isSelected(mPosition);
  }
}
