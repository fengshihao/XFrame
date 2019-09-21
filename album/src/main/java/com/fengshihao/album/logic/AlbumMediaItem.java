package com.fengshihao.album.logic;


import android.support.annotation.IntDef;
import android.support.annotation.IntRange;
import android.support.annotation.NonNull;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * 页面返回的结果. 是个list.
 */
public class AlbumMediaItem {

  public static final int IMAGE = 0;
  public static final int VIDEO = 1;
  public static final int VIDEO_IMAGE = 2;
  public String mCountry;
  public String mProvince;
  public String mCity;
  public long mTakeTime;
  public boolean mUnusable;

  /**
   * IMAGE 只选择图片
   * VIDEO 只选视频
   * VIDEO_IMAGE 都可以选择
   */
  @IntDef({IMAGE, VIDEO, VIDEO_IMAGE})
  @Retention(RetentionPolicy.SOURCE)
  public @interface AlbumType {
  }

  public String mName;

  @AlbumType
  public final int mType;

  @NonNull
  public final String mPath;

  @IntRange(from = 0)
  public final int mPosition;

  public final long mId;

  public int mLocationId = -1;

  public float mLatitude;
  public float mLongitude;
  public int mScore;


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
    return "SAMediaItem: mId=" + mId + " mPosition=" + mPosition + " mPath=" + mPath
        + " lati=" + mLatitude + " longi=" + mLongitude;
  }
}