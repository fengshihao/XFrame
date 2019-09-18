package com.fengshihao.album.logic;

import android.support.annotation.NonNull;
import android.util.Log;

import com.fengshihao.album.api.AlbumLoaderRequest;
import com.fengshihao.album.api.IAlbumDataLoader;
import com.fengshihao.album.api.IAlbumDataLoaderListener;
import com.fengshihao.xframe.logic.listener.Listeners;

import java.util.List;

import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

import static com.fengshihao.album.logic.AlbumSqlTool.loadImageVideos;
import static com.fengshihao.album.logic.AlbumSqlTool.loadImages;
import static com.fengshihao.album.logic.AlbumSqlTool.loadVideos;

public class AlbumDataLoader extends Listeners<IAlbumDataLoaderListener>
    implements IAlbumDataLoader {

  private static final String TAG = "AlbumDataLoader";

  public void loadAlbum(@NonNull AlbumLoaderRequest request) {
    Log.d(TAG, "loadAlbum() called with: request = [" + request + "]");
    Single<List<AlbumItem>> single;
    if (request.mMediaType == AlbumItem.IMAGE) {
      single = Single.fromCallable(
          () -> loadImages(request.mOffset, request.mNum));
    } else if (request.mMediaType == AlbumItem.VIDEO) {
      single = Single.fromCallable(
          () -> loadVideos(request.mOffset, request.mNum));
    } else {
      single = Single.fromCallable(
          () -> loadImageVideos(request.mOffset, request.mNum));
    }

    Disposable disposable = single.subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(
        (result) -> notifyListeners(l -> l.onAlbumLoaded(request, null, result)),
        (e) -> notifyListeners(l -> l.onAlbumLoaded(request, e, null)));
  }
}
