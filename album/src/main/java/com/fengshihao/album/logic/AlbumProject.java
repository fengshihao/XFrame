package com.fengshihao.album.logic;


import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import com.fengshihao.album.api.AlbumLoaderRequest;
import com.fengshihao.album.api.AlbumLoaderResult;
import com.fengshihao.album.api.IAlbumProject;
import com.fengshihao.album.api.IAlbumProjectListener;
import com.fengshihao.xframe.logic.ItemSelection;
import com.fengshihao.xframe.logic.listener.ListenerManager;

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

  private static AlbumProject sActiveProject;
  private static int sIndex = 0;


  private final int mId = sIndex += 1;

  @NonNull
  private final ItemSelection<Integer> mSelection = new ItemSelection<>();

  AlbumProject() {
    mSelection.pipeEventTo(this);
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
            (result) -> notifyListeners(l ->
                l.onAlbumLoaded(new AlbumLoaderResult(request, result, null))),
            (e) -> notifyListeners(l -> l.onAlbumLoaded(
                new AlbumLoaderResult(request, Collections.emptyList(), e))));

    request.setRequestDisposable(disposable);
  }

  @NonNull
  public static AlbumProject getActiveProject() {
    if (sActiveProject == null) {
      Log.d(TAG, "getActiveProject: create new project ");
      sActiveProject = new AlbumProject();
    }
    return sActiveProject;
  }

  public static void setActiveProject(@Nullable AlbumProject project) {
    Log.d(TAG, "setActiveProject: " + project);
    sActiveProject = project;
  }


  public void select(int position) {
    Log.d(TAG, "select: position=" + position);
    if (!mSelection.select(position)) {

    }
  }

  @NonNull
  @Override
  public String toString() {
    return "AlbumProject mId=" + mId;
  }

  public boolean isSelected(int mPosition) {
    return mSelection.isSelected(mPosition);
  }
}
