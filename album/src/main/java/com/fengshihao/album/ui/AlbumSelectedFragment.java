package com.fengshihao.album.ui;

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
import com.fengshihao.album.api.IAlbumProject;
import com.fengshihao.album.api.IAlbumProjectListener;
import com.fengshihao.album.logic.model.AlbumMediaItem;
import com.fengshihao.xframe.ui.widget.CommonRecyclerView.CommonAdapter;

import java.util.Objects;


/**
 *
 */
public class AlbumSelectedFragment extends Fragment implements IAlbumProjectListener {
  private static final String TAG = "AlbumSelectedFragment";

  @NonNull
  private final CommonAdapter<AlbumSelectItemUIModel> mCommonAdapter = new CommonAdapter<>();

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
  }

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
                           Bundle savedInstanceState) {
    Log.d(TAG, "onCreateView: ");
    return inflater.inflate(R.layout.fragment_album_selected, container, false);
  }

  @Override
  public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
    Log.d(TAG, "onViewCreated: ");
    getProject().addListener(this);
    RecyclerView listView = view.findViewById(R.id.list);
    mCommonAdapter.setHolderCreator((v, layoutId) ->
        new AlbumSelectedViewHolder(v, getProject()));
    Objects.requireNonNull(listView).setAdapter(mCommonAdapter);
  }

  @Override
  public void onActivityCreated(@Nullable Bundle savedInstanceState) {
    super.onActivityCreated(savedInstanceState);
    Log.d(TAG, "onActivityCreated() savedInstanceState = [" + savedInstanceState + "]");
  }

  @Override
  public void onDestroyView() {
    super.onDestroyView();
    getProject().removeListener(this);
  }

  @Override
  public void onSelect(@NonNull AlbumMediaItem item) {
    Log.d(TAG, "onSelect() called with: item = [" + item + "]");
    mCommonAdapter.addItem(new AlbumSelectItemUIModel(item.mId, item.mType, item.mPath));
  }

  @Override
  public void onUnSelect(@NonNull AlbumMediaItem item) {
    Log.d(TAG, "onUnSelect() called with: item = [" + item + "]");
    mCommonAdapter.removeById(item.mId);
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
