package com.fengshihao.album.logic;


import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.util.LongSparseArray;

import com.fengshihao.album.Settings;
import com.fengshihao.album.api.AlbumLoaderRequest;
import com.fengshihao.album.api.AlbumLoaderResult;
import com.fengshihao.album.api.IAlbumProject;
import com.fengshihao.album.api.IAlbumProjectListener;
import com.fengshihao.xframe.logic.listener.ListenerManager;
import com.fengshihao.xframe.logic.selection.ItemSelection;

import java.util.Collections;
import java.util.List;

import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

import static com.fengshihao.album.logic.AlbumSqlTool.loadImageVideos;
import static com.fengshihao.album.logic.AlbumSqlTool.loadImages;
import static com.fengshihao.album.logic.AlbumSqlTool.loadVideos;

public class AlbumProject extends ListenerManager<IAlbumProjectListener> implements IAlbumProject {

  private static final String TAG = "AlbumProject";

  private static int sIndex = 0;
  private final int mId = sIndex += 1;

  @Nullable
  private static IAlbumProject sCurrentProject;

  @NonNull
  private final LongSparseArray<AlbumMediaItem> mAllMediaItems =
      new LongSparseArray<>(3000);

  @NonNull
  private final ItemSelection<AlbumMediaItem> mSelection = new ItemSelection<>();

  public AlbumProject() {
    mSelection.pipeEventTo(this);
    mSelection.setMaxSelectNum(Settings.MAX_SELECT_NUM.get());
    Settings.MAX_SELECT_NUM.addListener(v -> {
      Log.d(TAG, "AlbumAPI.MAX_SELECT_NUM onChanged() called with: v = [" + v + "]");
      if (v == null) {
        return;
      }
      mSelection.setMaxSelectNum((Integer) v);
    });

    Settings.TEST_BOOL.addListener(v -> {
      Log.d(TAG, "AlbumAPI.TEST_BOOL change: " + v);
    });
  }

  public static void setCurrentProject(@Nullable IAlbumProject project) {
    sCurrentProject = project;
  }

  @NonNull
  public static IAlbumProject getsCurrentProject() {
    if (sCurrentProject == null) {
      throw new RuntimeException("should call setCurrentProject first");
    }
    return sCurrentProject;
  }

  @Override
  public void loadAlbum(@NonNull AlbumLoaderRequest request) {
    Log.d(TAG, "loadAlbum() called with: request = [" + request + "]");
    Single<List<AlbumMediaItem>> single;
    if (request.mMediaType == AlbumMediaItem.IMAGE) {
      single = Single.fromCallable(
          () -> loadImages(request.mOffset, request.mNum));
    } else if (request.mMediaType == AlbumMediaItem.VIDEO) {
      single = Single.fromCallable(
          () -> loadVideos(request.mOffset, request.mNum));
    } else {
      single = Single.fromCallable(
          () -> loadImageVideos(request.mOffset, request.mNum));
    }

    Disposable disposable = single.subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(
            (result) -> notifyListeners(l -> {
                  for (AlbumMediaItem item : result) {
                    mAllMediaItems.put(item.mId, item);
                  }
                  l.onAlbumLoaded(new AlbumLoaderResult(request, result, null));
                }
            ),
            (e) -> notifyListeners(l -> l.onAlbumLoaded(
                new AlbumLoaderResult(request, Collections.emptyList(), e))));

    request.setRequestDisposable(disposable);
  }

  public void select(long itemId) {
    Log.d(TAG, "select: itemId=" + itemId);
    AlbumMediaItem item = mAllMediaItems.get(itemId);
    if (item == null) {
      Log.w(TAG, "select: select a not exit itemId " + itemId);
      return;
    }
    mSelection.select(item);
  }

  @Override
  public void unSelect(long itemId) {
    Log.d(TAG, "unSelect: itemId=" + itemId);
    AlbumMediaItem item = mAllMediaItems.get(itemId);
    if (item == null) {
      Log.w(TAG, "unSelect: select a not exit itemId " + itemId);
      return;
    }
    mSelection.unSelect(item);
  }


  @Override
  public void toggleSelect(long itemId) {
    Log.d(TAG, "toggleSelect: itemId=" + itemId);
    AlbumMediaItem item = mAllMediaItems.get(itemId);
    if (item == null) {
      Log.w(TAG, "toggleSelect: select a not exit itemId " + itemId);
      return;
    }
    if (mSelection.isSelected(item)) {
      mSelection.unSelect(item);
    } else {
      mSelection.select(item);
    }
  }

  @NonNull
  @Override
  public String toString() {
    return "AlbumProject mId=" + mId;
  }

  public boolean isSelected(long itemId) {
    AlbumMediaItem item = mAllMediaItems.get(itemId);
    if (item == null) {
      Log.w(TAG, "isSelected: a not exit itemId " + itemId);
      return false;
    }
    return mSelection.isSelected(item);
  }

  @Override
  public void close() {
    Log.d(TAG, "close() called");
    clearListener();
  }
}
