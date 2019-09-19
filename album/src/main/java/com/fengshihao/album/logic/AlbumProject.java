package com.fengshihao.album.logic;


import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import com.fengshihao.album.api.AlbumLoaderRequest;
import com.fengshihao.album.api.IAlbumProjectListener;
import com.fengshihao.xframe.logic.ItemSelection;
import com.fengshihao.xframe.logic.listener.Listeners;

public class AlbumProject extends Listeners<IAlbumProjectListener> {

  private static final String TAG = "AlbumProject";

  private static AlbumProject sActiveProject;
  private static int sIndex = 0;


  private final int mId = sIndex += 1;

  @NonNull
  private final AlbumDataLoader mAlbumDataLoader = new AlbumDataLoader();

  @NonNull
  private final ItemSelection<Integer> mSelection = new ItemSelection<>();

  public AlbumProject() {
    mAlbumDataLoader.pipeEventTo(this);
    mSelection.pipeEventTo(this);
  }

  @NonNull
  public static AlbumProject getActiveProject() {
    if (sActiveProject == null) {
      Log.d(TAG, "getActiveProject: create new project ");
      sActiveProject = new AlbumProject();
    }
    return sActiveProject;
  }

  public static void setActiveProject(@Nullable AlbumProject project) {
    Log.d(TAG, "setActiveProject: " + project);
    sActiveProject = project;
  }


  public void select(int position) {
    Log.d(TAG, "select: position=" + position);
    if (!mSelection.select(position)) {

    }
  }

  @NonNull
  @Override
  public String toString() {
    return "AlbumProject mId=" + mId;
  }

  public void loadAlbum(@NonNull AlbumLoaderRequest albumLoaderRequest) {
    mAlbumDataLoader.loadAlbum(albumLoaderRequest);
  }

  public boolean isSelected(int mPosition) {
    return mSelection.isSelected(mPosition);
  }
}
