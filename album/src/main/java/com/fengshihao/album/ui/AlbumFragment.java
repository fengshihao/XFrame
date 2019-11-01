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
import android.widget.Toast;

import com.fengshihao.album.R;
import com.fengshihao.album.logic.model.AlbumLoaderRequest;
import com.fengshihao.album.logic.model.AlbumLoaderResult;
import com.fengshihao.album.api.IAlbumProject;
import com.fengshihao.album.api.IAlbumProjectListener;
import com.fengshihao.album.logic.model.AlbumMediaItem;
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

  private Disposable mPermissionDisposable;

  @NonNull
  private final CommonAdapter<AlbumItemUIModel> mCommonAdapter = new CommonAdapter<>();

  @NonNull
  private final IPageListListener mPageListener = new IPageListListener() {
    @Override
    public void onRequireLoad(int pageNo, int pageSize) {
      int first = pageNo * pageSize;
      getProject().loadAlbum(
          new AlbumLoaderRequest(AlbumMediaItem.VIDEO_IMAGE, first, pageSize));
    }
  };

  private void onGetGranted() {
    Log.d(TAG, "onGetGranted: ");

    int pageSize = mCommonAdapter.getPageList().getPageSize();
    getProject().loadAlbum(
        new AlbumLoaderRequest(AlbumMediaItem.VIDEO_IMAGE, 0, pageSize));
  }

  @Override
  public void onAttach(Context context) {
    super.onAttach(context);
    Log.d(TAG, "onAttach() called with: context = [" + context + "]");
  }


  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    Log.d(TAG, "onCreate: ");

    mCommonAdapter.setEmptyLayoutId(R.layout.fragment_album_item);
    mCommonAdapter.getPageList().addListener(mPageListener);
    mCommonAdapter.setHolderCreator((v, layoutId) -> new AlbumMediaViewHolder(v, getProject()));
    getProject().addListener(this);
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
    askPermission();
  }

  private void askPermission() {
    if (mPermissionDisposable != null) {
      return;
    }
    final RxPermissions rxPermissions = new RxPermissions(this);
    mPermissionDisposable = rxPermissions.request(Manifest.permission.READ_EXTERNAL_STORAGE)
        .subscribe(granted -> {
          if (granted) {
            onGetGranted();
          }
          mPermissionDisposable = null;
        });
  }

  @Override
  public void onActivityCreated(@Nullable Bundle savedInstanceState) {
    super.onActivityCreated(savedInstanceState);
    Log.d(TAG, "onActivityCreated() savedInstanceState = [" + savedInstanceState + "]");
  }


  @Override
  public void onStart() {
    super.onStart();
    Log.d(TAG, "onStart: ");
  }

  @Override
  public void onStop() {
    super.onStop();
    Log.d(TAG, "onStop: ");
  }

  @Override
  public void onDestroyView() {
    super.onDestroyView();
    Log.d(TAG, "onDestroyView() called");
    getProject().removeListener(this);
    mCommonAdapter.getPageList().removeListener(mPageListener);
  }

  @Override
  public void onDestroy() {
    super.onDestroy();
    Log.d(TAG, "onDestroy: ");
  }

  @Override
  public void onDetach() {
    Log.d(TAG, "onDetach() called");
    super.onDetach();
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
    mCommonAdapter.notifyItemChanged(item.mPosition, 1);
  }

  @Override
  public void onUnSelect(@NonNull AlbumMediaItem item) {
    mCommonAdapter.notifyItemChanged(item.mPosition, 1);
  }

  @Override
  public void onSelectFull(int maxSelectCount) {
    Toast.makeText(getContext(), "max select " + maxSelectCount, Toast.LENGTH_LONG).show();
  }

  @NonNull
  private IAlbumProject getProject() {
    IServiceProvider provider = (IServiceProvider) getActivity();
    if (provider == null) {
      throw new RuntimeException("pls attach to a activity implements IServiceProvider");
    }
    return provider.getAlbumProject();
  }
}
