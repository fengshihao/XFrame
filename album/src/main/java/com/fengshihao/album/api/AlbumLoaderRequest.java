package com.fengshihao.album.api;


import android.support.annotation.NonNull;

import com.fengshihao.album.logic.AlbumItem;

public class AlbumLoaderRequest {
  @AlbumItem.AlbumType
  public final int mMediaType;
  public final int mOffset;
  public final int mNum;

  public AlbumLoaderRequest(int type, int offset, int num) {
    this.mMediaType = type;
    this.mOffset = offset;
    this.mNum = num;
  }

  @NonNull
  @Override
  public String toString() {
    return "AlbumLoaderRequest mMediaType=" + mMediaType + " mOffset=" + mOffset + " mNum=" + mNum;
  }
}
