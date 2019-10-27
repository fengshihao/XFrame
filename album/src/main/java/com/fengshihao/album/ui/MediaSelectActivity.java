package com.fengshihao.album.ui;

import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.fengshihao.album.R;
import com.fengshihao.album.api.IAlbumProject;
import com.fengshihao.album.logic.AlbumProject;

public class MediaSelectActivity extends AppCompatActivity {

  private static final String TAG = "MediaSelectActivity";

  @NonNull
  private final IAlbumProject mProject = new AlbumProject();

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    Log.d(TAG, "onCreate() called with: savedInstanceState = [" + savedInstanceState + "]");
    AlbumProject.setCurrentProject(mProject);
    setContentView(R.layout.activity_media_select);

    getSupportFragmentManager()
        .beginTransaction()
        .replace(R.id.media_selected, new AlbumSelectedFragment())
        .commit();

  }

  @Override
  protected void onStart() {
    super.onStart();
    Log.d(TAG, "onStart: ");
  }

  @Override
  protected void onStop() {
    super.onStop();
    Log.d(TAG, "onStop: ");
  }

  @Override
  public void onDestroy() {
    super.onDestroy();
    Log.d(TAG, "onDestroy() called");
    mProject.close();
    AlbumProject.setCurrentProject(null);
  }

  @Override
  public void onConfigurationChanged(Configuration newConfig) {
    super.onConfigurationChanged(newConfig);
    Log.d(TAG, "onConfigurationChanged() called with: newConfig = [" + newConfig + "]");
  }
}
