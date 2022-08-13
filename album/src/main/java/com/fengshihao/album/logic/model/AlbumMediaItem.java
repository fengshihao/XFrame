package com.fengshihao.album.logic.model;


import androidx.annotation.IntDef;
import androidx.annotation.IntRange;
import androidx.annotation.NonNull;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * 页面返回的结果. 是个list.
 */
public class AlbumMediaItem {

  public static final int IMAGE = 0;
  public static final int VIDEO = 1;
  public static final int VIDEO_IMAGE = 2;

  /**
   * IMAGE 只选择图片
   * VIDEO 只选视频
   * VIDEO_IMAGE 都可以选择
   */
  @IntDef({IMAGE, VIDEO, VIDEO_IMAGE})
  @Retention(RetentionPolicy.SOURCE)
  public @interface AlbumType {
  }

  @AlbumType
  public final int mType;

  @NonNull
  public final String mPath;

  @IntRange(from = 0)
  public final int mPosition;

  public final long mId;

  public float mLatitude;
  public float mLongitude;


  public AlbumMediaItem(long id,
                        @IntRange(from = 0) int position,
                        @AlbumType int type,
                        @NonNull String path) {
    mId = id;
    mPosition = position;
    mType = type;
    mPath = path;
  }

  @Override
  public String toString() {
    return "AlbumMediaItem: mId=" + mId + " mPosition=" + mPosition + " mPath=" + mPath;
  }
}