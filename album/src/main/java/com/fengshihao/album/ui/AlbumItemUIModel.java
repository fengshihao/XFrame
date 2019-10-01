package com.fengshihao.album.ui;

import android.support.annotation.NonNull;

import com.fengshihao.album.logic.AlbumProject;
import com.fengshihao.xframe.ui.widget.CommonRecyclerView.CommonItemModel;

public class AlbumItemUIModel extends CommonItemModel {

  @NonNull
  final String mInfo;

  @NonNull
  final String mImagePath;

  private final int mViewType;

  final long mItemId;

  public AlbumItemUIModel(long id, int viewType, @NonNull String info, @NonNull String img) {
    mItemId = id;
    mViewType = viewType;
    mInfo = info;
    mImagePath = img;
  }

  @Override
  public int getViewType() {
    return mViewType;
  }


  boolean isSelected() {
    return AlbumProject.getActiveProject().isSelected(mItemId);
  }

  @NonNull
  @Override
  public String toString() {
    return "AlbumItemUIModel mItemId=" + mItemId + " mViewType=" + mViewType;
  }
}
