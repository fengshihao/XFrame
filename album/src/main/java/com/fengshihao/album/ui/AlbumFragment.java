package com.fengshihao.album.ui;

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
import com.fengshihao.album.api.IAlbumProject;
import com.fengshihao.album.api.IAlbumProjectListener;
import com.fengshihao.album.logic.AlbumMediaItem;
import com.fengshihao.album.logic.AlbumProject;
import com.fengshihao.xframe.logic.layzlist.IPageListListener;
import com.fengshihao.xframe.ui.widget.CommonRecyclerView.CommonAdapter;
import com.tbruyelle.rxpermissions2.RxPermissions;

import java.util.LinkedList;
import java.util.List;

import io.reactivex.disposables.Disposable;

public class AlbumFragment extends Fragment implements IAlbumProjectListener {
  private static final String TAG = "AlbumFragment";
  @Nullable
  private RecyclerView mAlbumItemRecyclerView;

  @NonNull
  private final CommonAdapter<AlbumItemUIModel> mCommonAdapter = new CommonAdapter<>();

  @NonNull
  private IAlbumProject mProject = AlbumProject.getsCurrentProject();

  @NonNull
  private IPageListListener mPageListener = new IPageListListener() {
    @Override
    public void onRequireLoad(int pageNo, int pageSize) {
      int first = pageNo * pageSize;
      mProject.loadAlbum(
          new AlbumLoaderRequest(AlbumMediaItem.VIDEO_IMAGE, first, pageSize));
    }
  };


  public AlbumFragment() {
    mCommonAdapter.setEmptyLayoutId(R.layout.fragment_album_item);
    mCommonAdapter.getPageList().addListener(mPageListener);
    mCommonAdapter.setHolderCreator((v, layoutId) -> new AlbumMediaViewHolder(v));

    mProject.addListener(this);
  }

  private void onGetGranted() {
    Log.d(TAG, "onGetGranted: ");

    int pageSize = mCommonAdapter.getPageList().getPageSize();

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
    mProject.removeListener(this);
    mCommonAdapter.getPageList().removeListener(mPageListener);
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
    mAlbumItemRecyclerView.setAdapter(mCommonAdapter);
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
    if (result.mMediaList.isEmpty()) {
      if (result.mRequest.isFirstPage()) {
        Log.w(TAG, "onAlbumLoaded: no media");
        Toast.makeText(getContext(), getResources().getString(R.string.no_media),
            Toast.LENGTH_LONG).show();
      }
      return;
    }

    final int pageSize = mCommonAdapter.getPageList().getPageSize();
    boolean isFirstCallback = mCommonAdapter.getPageList().size() == 0;
    List<AlbumItemUIModel> l = new LinkedList<>();
    for (AlbumMediaItem item : result.mMediaList) {
      l.add(new AlbumItemUIModel(item.mId, item.mType, "No. " + item.mPosition, item.mPath));
    }
    int pageNo = result.mRequest.mOffset / pageSize;
    mCommonAdapter.getPageList().setItems(pageNo, l);
    if (isFirstCallback && !result.mMediaList.isEmpty()) {
      Log.d(TAG, "onAlbumLoaded: scroll to " + (pageNo * pageSize));
      if (mAlbumItemRecyclerView != null) {
        mAlbumItemRecyclerView.scrollToPosition(pageNo * pageSize);
      }
    }
  }

  @Override
  public void onSelect(@NonNull AlbumMediaItem item) {
    mCommonAdapter.notifyItemChanged(item.mPosition);
  }

  @Override
  public void onUnSelect(@NonNull AlbumMediaItem item) {
    mCommonAdapter.notifyItemChanged(item.mPosition);
  }

  @Override
  public void onSelectFull(int maxSelectCount) {
    Toast.makeText(getContext(), "max select " + maxSelectCount, Toast.LENGTH_LONG).show();
  }
}
