package com.fengshihao.album.ui;

import android.Manifest;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.fengshihao.album.R;
import com.fengshihao.album.api.AlbumLoaderRequest;
import com.fengshihao.album.api.IAlbumProjectListener;
import com.fengshihao.album.logic.AlbumItem;
import com.fengshihao.album.logic.AlbumProject;
import com.fengshihao.xframe.ui.widget.CommonRecyclerView;
import com.tbruyelle.rxpermissions2.RxPermissions;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import io.reactivex.disposables.Disposable;

public class AlbumFragment extends Fragment implements IAlbumProjectListener {
  private static final String TAG = "AlbumFragment";
  @Nullable
  private CommonRecyclerView mAlbumItemListView;


  public AlbumFragment() {
  }

  @NonNull
  private AlbumProject getProject() {
    return AlbumProject.getActiveProject();
  }

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    Log.d(TAG, "onCreate: ");
    getProject().addListener(this);
  }
  @Override
  public  void onDestroy() {
    super.onDestroy();
    Log.d(TAG, "onDestroy: ");
    getProject().removeListener(this);
  }

  @Override
  public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                           Bundle savedInstanceState) {
    Log.d(TAG, "onCreateView: ");
    mAlbumItemListView = (CommonRecyclerView) inflater.inflate(R.layout.fragment_album_item_list,
        container, false);

    mAlbumItemListView.setItemLayoutIds(R.layout.fragment_album_item,
        R.layout.fragment_album_item_video);

    final RxPermissions rxPermissions = new RxPermissions(this);
    Disposable disposable = rxPermissions.request(Manifest.permission.READ_EXTERNAL_STORAGE)
        .subscribe(granted -> {
          if (granted) {
            getProject().loadAlbum(
                new AlbumLoaderRequest(AlbumItem.VIDEO_IMAGE, 0, 1000));
          }
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
      l.add(new AlbumItemUIModel(item.mPosition, 0, "no " + l.size(), item.mPath));
    }
    if (mAlbumItemListView != null) {
      mAlbumItemListView.setModels(l);
    }
  }

  @Override
  public void onSelect(@NonNull Integer item) {
    if (mAlbumItemListView!= null && mAlbumItemListView.getAdapter() != null) {
      mAlbumItemListView.getAdapter().notifyItemChanged(item);

    }
  }

  @Override
  public void onUnSelect(@NonNull Integer item) {
    if (mAlbumItemListView!= null && mAlbumItemListView.getAdapter() != null) {
      mAlbumItemListView.getAdapter().notifyItemChanged(item);
    }
  }
}
