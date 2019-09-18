package com.fengshihao.album.ui;

import android.Manifest;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.fengshihao.album.R;
import com.fengshihao.album.api.AlbumLoaderRequest;
import com.fengshihao.album.api.IAlbumDataLoaderListener;
import com.fengshihao.album.logic.AlbumDataLoader;
import com.fengshihao.album.logic.AlbumItem;
import com.fengshihao.xframe.ui.widget.CommonRecyclerView;
import com.tbruyelle.rxpermissions2.RxPermissions;

import java.util.LinkedList;
import java.util.List;

import io.reactivex.disposables.Disposable;

public class AlbumFragment extends Fragment implements IAlbumDataLoaderListener {
  private static final String TAG = "AlbumFragment";
  @Nullable
  private CommonRecyclerView mAlbumItemListView;

  @NonNull
  AlbumDataLoader mAlbumDataLoader = new AlbumDataLoader();

  private List<Integer> mSelects = new LinkedList<>();

  public AlbumFragment() {
  }

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    Log.d(TAG, "onCreate: ");
    mAlbumDataLoader.addListener(this);
  }

  @Override
  public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                           Bundle savedInstanceState) {
    Log.d(TAG, "onCreateView: ");
    mAlbumItemListView = (CommonRecyclerView) inflater.inflate(R.layout.fragment_album_item_list,
        container, false);

    mAlbumItemListView.setItemLayoutIds(R.layout.fragment_album_item,
        R.layout.fragment_album_item_video);

    List<AlbumItemUIModel> l = new LinkedList<>();
    for (int i = 0; i < 1000; i++) {
      l.add(new AlbumItemUIModel(i % 2, "no " + i, ""));
    }
    mAlbumItemListView.setModels(l);
    final RxPermissions rxPermissions = new RxPermissions(this);
    Disposable disposable = rxPermissions.request(Manifest.permission.READ_EXTERNAL_STORAGE)
        .subscribe(granted -> {
          if (granted) {
            mAlbumDataLoader.loadAlbum(
                new AlbumLoaderRequest(AlbumItem.VIDEO_IMAGE, 0, 1000));
          }
        });

    mAlbumItemListView.setListener((view , pos) -> {
      if (mSelects.contains(pos)) {
        mSelects.remove(Integer.valueOf(pos));
      } else {
        mSelects.add(pos);
        if (mSelects.size() > 5) {
          mSelects.remove(0);
        }
      }

      mAlbumItemListView.select(mSelects.toArray(new Integer[0]));

    });
    return mAlbumItemListView;
  }

  @Override
  public void onAttach(Context context) {
    super.onAttach(context);
    Log.d(TAG, "onAttach: ");
  }

  @Override
  public void onDetach() {
    super.onDetach();
    Log.d(TAG, "onDetach: ");
  }

  @Override
  public void onAlbumLoaded(@NonNull AlbumLoaderRequest request, Throwable error, @NonNull List<AlbumItem> result) {
    Log.d(TAG, "onAlbumLoaded() called with: result = [" + result.size() + "]");
    List<AlbumItemUIModel> l = new LinkedList<>();
    for (AlbumItem item : result) {
      l.add(new AlbumItemUIModel(0, "no " + l.size(), item.mPath));
    }
    if (mAlbumItemListView != null) {
      mAlbumItemListView.setModels(l);
    }
  }
}
