package com.fengshihao.album.api;


import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import com.fengshihao.album.logic.AlbumMediaItem;

import io.reactivex.disposables.Disposable;

public class AlbumLoaderRequest {
  private static final String TAG = "AlbumLoaderRequest";

  @AlbumMediaItem.AlbumType
  public final int mMediaType;
  public final int mOffset;
  public final int mNum;

  @Nullable
  private Disposable mRequestDisposable;

  public AlbumLoaderRequest(int type, int offset, int num) {
    this.mMediaType = type;
    this.mOffset = offset;
    this.mNum = num;
  }

  public void setRequestDisposable(@Nullable Disposable disposable) {
    Log.d(TAG, "setRequestDisposable: ");
    mRequestDisposable = disposable;
  }

  public void cancel() {
    Log.d(TAG, "cancel: " + this);
    if (mRequestDisposable != null && mRequestDisposable.isDisposed()) {
      mRequestDisposable.dispose();
      mRequestDisposable = null;
      Log.d(TAG, "cancel: dispose " + this);
    }
  }

  @NonNull
  @Override
  public String toString() {
    return "AlbumLoaderRequest mMediaType=" + mMediaType + " mOffset=" + mOffset + " mNum=" + mNum;
  }
}
