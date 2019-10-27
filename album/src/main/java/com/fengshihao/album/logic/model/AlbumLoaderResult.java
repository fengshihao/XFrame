package com.fengshihao.album.logic.model;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.util.List;

public class AlbumLoaderResult {

  @NonNull
  public final AlbumLoaderRequest mRequest;

  @NonNull
  public final List<AlbumMediaItem> mMediaList;

  @Nullable
  public final Throwable mError;

  public AlbumLoaderResult(@NonNull AlbumLoaderRequest request, @NonNull List<AlbumMediaItem> result,
                           @Nullable Throwable error) {
    mRequest = request;
    mRequest.setRequestDisposable(null);
    mMediaList = result;
    mError = error;
  }

  @NonNull
  @Override
  public String toString() {
    return "AlbumLoaderResult error=" + mError + " result size="
        + mMediaList.size() + " request=" + mRequest;
  }
}
