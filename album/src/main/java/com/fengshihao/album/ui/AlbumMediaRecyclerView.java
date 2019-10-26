package com.fengshihao.album.ui;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.Log;
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

import java.util.LinkedList;
import java.util.List;

public class AlbumMediaRecyclerView extends RecyclerView implements IAlbumProjectListener {
  private static final String TAG = "AlbumMediaRecyclerView";

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

  public AlbumMediaRecyclerView(@NonNull Context context) {
    super(context);
    init();
  }

  public AlbumMediaRecyclerView(@NonNull Context context, @Nullable AttributeSet attrs) {
    super(context, attrs);
    init();
  }

  public AlbumMediaRecyclerView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyle) {
    super(context, attrs, defStyle);
    init();
  }

  protected void init() {
    mCommonAdapter.setEmptyLayoutId(R.layout.fragment_album_item);
    mCommonAdapter.getPageList().addListener(mPageListener);
    mCommonAdapter.setHolderCreator((v, layoutId) ->
        new AlbumMediaViewHolder(v));

    setAdapter(mCommonAdapter);
    mProject.addListener(this);
  }

  public int getPageSize() {
    return mCommonAdapter.getPageList().getPageSize();
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
      scrollToPosition(pageNo * pageSize);
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

  public void onDestroy() {
    mProject.removeListener(this);
    mCommonAdapter.getPageList().removeListener(mPageListener);
  }

}
