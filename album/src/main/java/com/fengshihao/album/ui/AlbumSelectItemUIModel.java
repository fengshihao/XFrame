package com.fengshihao.album.ui;

import android.support.annotation.NonNull;

import com.fengshihao.album.R;
import com.fengshihao.album.api.IAlbumProject;
import com.fengshihao.album.logic.AlbumMediaItem;
import com.fengshihao.xframe.ui.widget.CommonRecyclerView.CommonItemModel;

public class AlbumSelectItemUIModel extends CommonItemModel {

  @NonNull
  final String mInfo;

  @NonNull
  final String mImagePath;

  @NonNull
  private final IAlbumProject mProject;

  AlbumSelectItemUIModel(@NonNull IAlbumProject project,
                         long id, @AlbumMediaItem.AlbumType int viewType, @NonNull String img) {
    super(id);
    mProject = project;
    mInfo = viewType == AlbumMediaItem.IMAGE ? "image" : "video";
    mImagePath = img;
  }

  @Override
  public int getLayoutId() {
    return R.layout.fragment_album_selected_item;
  }

  void remove() {
    mProject.unSelect(mId);
  }

  @NonNull
  @Override
  public String toString() {
    return "AlbumSelectItemUIModel mId=" + mId;
  }
}
