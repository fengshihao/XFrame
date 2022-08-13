package com.fengshihao.album.ui;

import androidx.annotation.NonNull;

import com.fengshihao.album.R;
import com.fengshihao.album.logic.model.AlbumMediaItem;
import com.fengshihao.xframe.ui.widget.CommonRecyclerView.CommonItemModel;

public class AlbumSelectItemUIModel extends CommonItemModel {

  @NonNull
  final String mInfo;

  @NonNull
  final String mImagePath;

  AlbumSelectItemUIModel(long id, @AlbumMediaItem.AlbumType int viewType, @NonNull String img) {
    super(id);
    mInfo = viewType == AlbumMediaItem.IMAGE ? "image" : "video";
    mImagePath = img;
  }

  @Override
  public int getLayoutId() {
    return R.layout.fragment_album_selected_item;
  }

  @NonNull
  @Override
  public String toString() {
    return "AlbumSelectItemUIModel mId=" + mId;
  }
}
