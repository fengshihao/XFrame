package com.fengshihao.album.logic;

import android.util.Log;

import com.fengshihao.album.api.IAlbumDataLoader;
import com.fengshihao.album.api.IAlbumDataLoaderListener;
import com.fengshihao.xframe.logic.listener.Listeners;

import java.util.List;

import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

import static com.fengshihao.album.logic.AlbumSqlTool.LoadImages;
import static com.fengshihao.album.logic.AlbumSqlTool.LoadVideos;

public class AlbumDataLoader extends Listeners<IAlbumDataLoaderListener>
    implements IAlbumDataLoader {

  private static final String TAG = "AlbumDataLoader";

  @Override
  public void loadAlbum(@AlbumItem.AlbumType int type, int offset, int pageItemCount) {
    Log.d(TAG, "loadAlbum() called with: type = [" + type + "], offset = ["
        + offset + "], pageItemCount = [" + pageItemCount + "]");
    Single<List<AlbumItem>> single;
    if (type == AlbumItem.IMAGE) {
      single = Single.fromCallable(
          () -> LoadImages(offset, pageItemCount))
          .subscribeOn(Schedulers.io())
          .observeOn(AndroidSchedulers.mainThread());
    } else if (type == AlbumItem.VIDEO) {
      single = Single.fromCallable(
          () -> LoadVideos(offset, pageItemCount))
          .subscribeOn(Schedulers.io())
          .observeOn(AndroidSchedulers.mainThread());
    } else {
      Log.e(TAG, "loadAlbum: wrong type ? ");
      return;
    }

    Disposable disposable = single.subscribe(
        (result) -> notifyListeners(l -> l.onAlbumLoaded(result)),
        (e) -> notifyListeners(l -> l.onAlbumLoadError(e)));
  }
}
