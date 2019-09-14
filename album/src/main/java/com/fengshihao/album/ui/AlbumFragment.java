package com.fengshihao.album.ui;

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
import com.fengshihao.album.api.IAlbumDataLoader;
import com.fengshihao.album.api.IAlbumDataLoaderListener;
import com.fengshihao.album.logic.AlbumDataLoader;
import com.fengshihao.album.logic.AlbumItem;
import com.fengshihao.xframe.ui.widget.CommonRecyclerView;

import java.util.LinkedList;
import java.util.List;

public class AlbumFragment extends Fragment implements IAlbumDataLoaderListener {
  private static final String TAG = "AlbumFragment";
  @Nullable
  private CommonRecyclerView mAlbumItemListView;

  @NonNull
  IAlbumDataLoader mAlbumDataLoader = new AlbumDataLoader();
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

    mAlbumItemListView.setItemLayoutId(R.layout.fragment_album_item,
        R.layout.fragment_album_item_video);

    List<AlbumItemUIModel> l = new LinkedList<>();
    for (int i = 0; i < 1000; i++) {
      l.add(new AlbumItemUIModel(i % 2 , "no " + i, ""));
    }
    mAlbumItemListView.setModels(l);
    mAlbumDataLoader.loadAlbum(AlbumItem.VIDEO_IMAGE, 0, 1000);

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
  public void onAlbumLoaded(@NonNull List<AlbumItem> result) {
    Log.d(TAG, "onAlbumLoaded() called with: result = [" + result + "]");
  }

  @Override
  public void onAlbumLoadError(Throwable e) {
    Log.d(TAG, "onAlbumLoadError() called with: e = [" + e + "]");
  }
}
