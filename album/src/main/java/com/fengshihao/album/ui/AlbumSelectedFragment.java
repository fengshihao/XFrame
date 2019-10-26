package com.fengshihao.album.ui;

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
import com.fengshihao.album.api.IAlbumProject;
import com.fengshihao.album.api.IAlbumProjectListener;
import com.fengshihao.album.logic.AlbumMediaItem;
import com.fengshihao.xframe.ui.widget.CommonRecyclerView.CommonAdapter;


/**
 */
public class AlbumSelectedFragment extends Fragment implements IAlbumProjectListener {
  private static final String TAG = "AlbumSelectedFragment";
  @Nullable
  private IAlbumProject mProject;


  @NonNull
  private final CommonAdapter<AlbumSelectItemUIModel> mCommonAdapter = new CommonAdapter<>();

  @Nullable
  private RecyclerView mListView;

  public AlbumSelectedFragment() {
    // Required empty public constructor
  }

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
  }

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
                           Bundle savedInstanceState) {
    // Inflate the layout for this fragment
    return inflater.inflate(R.layout.fragment_album_selected, container, false);
  }

  @Override
  public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
    Log.d(TAG, "onViewCreated: ");
    mListView = view.findViewById(R.id.list);
    mListView.setAdapter(mCommonAdapter);
  }

  @Override
  public void onAttach(Context context) {
    super.onAttach(context);
  }

  @Override
  public void onDetach() {
    super.onDetach();
  }

  public void setProject(@NonNull IAlbumProject project) {
    mProject = project;
    mProject.addListener(this);
  }

  @Override
  public void onSelect(@NonNull AlbumMediaItem item) {
    Log.d(TAG, "onSelect() called with: item = [" + item + "]");
    if (mProject == null) {
      Log.e(TAG, "onSelect: mProject is null call setProject first!");
      return;
    }
    mCommonAdapter.addItem(new AlbumSelectItemUIModel(mProject,
        item.mId, item.mType, item.mPath));
  }

  @Override
  public void onUnSelect(@NonNull AlbumMediaItem item) {
    Log.d(TAG, "onUnSelect() called with: item = [" + item + "]");
    if (mProject == null) {
      Log.e(TAG, "onUnSelect: mProject is null call setProject first!");
      return;
    }
   // mCommonAdapter.getPageList().remove()
  }
}
