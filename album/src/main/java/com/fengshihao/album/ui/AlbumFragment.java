package com.fengshihao.album.ui;

import java.util.LinkedList;
import java.util.List;

import android.Manifest;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.fengshihao.album.R;
import com.fengshihao.album.api.AlbumLoaderRequest;
import com.fengshihao.album.api.AlbumLoaderResult;
import com.fengshihao.album.api.IAlbumProjectListener;
import com.fengshihao.album.logic.AlbumMediaItem;
import com.fengshihao.album.logic.AlbumProject;
import com.fengshihao.xframe.logic.layzlist.IPageListListener;
import com.fengshihao.xframe.ui.widget.CommonRecyclerView.CommonAdapter;
import com.tbruyelle.rxpermissions2.RxPermissions;

import io.reactivex.disposables.Disposable;

public class AlbumFragment extends Fragment implements IAlbumProjectListener {
  private static final String TAG = "AlbumFragment";
  @Nullable
  private RecyclerView mAlbumItemListView;

  @NonNull
  private CommonAdapter mCommonAdapter = new CommonAdapter();


  @NonNull
  private IPageListListener mPageListener = new IPageListListener() {
    @Override
    public void onRequireLoad(int pageNo, int pageSize) {
      int first = pageNo * pageSize;
      getProject().loadAlbum(
          new AlbumLoaderRequest(AlbumMediaItem.VIDEO_IMAGE, first, pageSize));
    }
  };

  public AlbumFragment() {
  }

  private void onGetGranted() {
    Log.d(TAG, "onGetGranted: ");
    if (mAlbumItemListView == null) {
      Log.w(TAG, "onGetGranted: mAlbumItemListView is null");
      return;
    }
    mCommonAdapter.getPageList().addListener(mPageListener);
    int pageSize = mCommonAdapter.getPageList().getPageSize();

    getProject().loadAlbum(
        new AlbumLoaderRequest(AlbumMediaItem.VIDEO_IMAGE, pageSize, pageSize));
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
  public void onDestroy() {
    super.onDestroy();
    Log.d(TAG, "onDestroy: ");
    getProject().removeListener(this);
    mCommonAdapter.getPageList().removeListener(mPageListener);
  }

  @Override
  public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                           Bundle savedInstanceState) {
    Log.d(TAG, "onCreateView: ");
    mAlbumItemListView = (RecyclerView) inflater.inflate(R.layout.fragment_album_item_list,
        container, false);


    mCommonAdapter.setItemLayoutIds(R.layout.fragment_album_item,
        R.layout.fragment_album_item_video);

    if (mAlbumItemListView != null) {
      mAlbumItemListView.setAdapter(mCommonAdapter);
    }

    return mAlbumItemListView;
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

  @Override
  public void onAlbumLoaded(@NonNull AlbumLoaderResult result) {
    Log.d(TAG, "onAlbumLoaded() called with: result = [" + result + "]");

    if (result.mError != null) {
      throw new RuntimeException(result.mError);
    }
    if (result.mRequest.isFirstPage() && result.mMediaList.isEmpty()) {
      Log.w(TAG, "onAlbumLoaded: no media");
      Toast.makeText(getContext(), getString(R.string.no_media), Toast.LENGTH_LONG).show();
      return;
    }

    if (mAlbumItemListView == null) {
      Log.e(TAG, "onAlbumLoaded: mAlbumItemListView is null");
      return;
    }

    List<AlbumItemUIModel> l = new LinkedList<>();
    for (AlbumMediaItem item : result.mMediaList) {
      l.add(new AlbumItemUIModel(item.mPosition, item.mType, "no " + item.mPosition, item.mPath));
    }
    int pageNo = result.mRequest.mOffset / mCommonAdapter.getPageList().getPageSize();
    mCommonAdapter.getPageList().setItems(pageNo, l);
    mAlbumItemListView.scrollToPosition(pageNo * mCommonAdapter.getPageList().size());
  }

  @Override
  public void onSelect(@NonNull Integer item) {
    mCommonAdapter.notifyItemChanged(item);
  }

  @Override
  public void onUnSelect(@NonNull Integer item) {
    mCommonAdapter.notifyItemChanged(item);
  }
}
