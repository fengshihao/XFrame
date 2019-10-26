package com.fengshihao.album.ui;

import android.Manifest;
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
import com.fengshihao.album.logic.AlbumMediaItem;
import com.fengshihao.album.logic.AlbumProject;
import com.tbruyelle.rxpermissions2.RxPermissions;

import io.reactivex.disposables.Disposable;

public class AlbumFragment extends Fragment {
  private static final String TAG = "AlbumFragment";
  @Nullable
  private AlbumMediaRecyclerView mAlbumItemRecyclerView;

  public AlbumFragment() {
  }

  private void onGetGranted() {
    Log.d(TAG, "onGetGranted: ");
    if (mAlbumItemRecyclerView == null) {
      Log.w(TAG, "onGetGranted: mAlbumItemRecyclerView is null");
      return;
    }

    int pageSize = mAlbumItemRecyclerView.getPageSize();

    AlbumProject.getsCurrentProject().loadAlbum(
        new AlbumLoaderRequest(AlbumMediaItem.VIDEO_IMAGE, 0, pageSize));
  }

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    Log.d(TAG, "onCreate: ");
  }

  @Override
  public void onDestroy() {
    super.onDestroy();
    Log.d(TAG, "onDestroy: ");
  }

  @Override
  public void onDestroyView() {
    super.onDestroyView();
    Log.d(TAG, "onDestroyView() called");
    if (mAlbumItemRecyclerView != null) {
      mAlbumItemRecyclerView.onDestroy();
    }
  }

  @Override
  public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                           Bundle savedInstanceState) {
    Log.d(TAG, "onCreateView: ");
    return inflater.inflate(R.layout.fragment_album_item_list, container, false);
  }

  @Override
  public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
    Log.d(TAG, "onViewCreated: ");
    mAlbumItemRecyclerView = view.findViewById(R.id.list);
  }

  @Override
  public void onStart() {
    super.onStart();
    Log.d(TAG, "onStart: ");

    final RxPermissions rxPermissions = new RxPermissions(this);
    Disposable disposable = rxPermissions.request(Manifest.permission.READ_EXTERNAL_STORAGE)
        .subscribe(granted -> {
          if (granted) {
            onGetGranted();
          }
        });
  }

  @Override
  public void onDetach() {
    super.onDetach();
    Log.d(TAG, "onDetach: ");
  }
}
