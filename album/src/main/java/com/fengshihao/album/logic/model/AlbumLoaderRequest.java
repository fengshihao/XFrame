package com.fengshihao.album.logic.model;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.util.Log;

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

  public boolean isFirstPage() {
    return mOffset == 0;
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
