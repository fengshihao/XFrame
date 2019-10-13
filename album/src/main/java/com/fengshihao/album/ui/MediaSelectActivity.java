package com.fengshihao.album.ui;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.fengshihao.album.R;
import com.fengshihao.album.logic.AlbumProject;

import java.util.Objects;

public class MediaSelectActivity extends AppCompatActivity {

  private static final String TAG = "MediaSelectActivity";

  @NonNull
  private final AlbumProject mProject = new AlbumProject();

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_media_select);

    AlbumFragment mAlbumFragment = (AlbumFragment) getSupportFragmentManager()
        .findFragmentById(R.id.media_list);
    Objects.requireNonNull(mAlbumFragment).setProject(mProject);


    AlbumSelectedFragment mAlbumSelectedFragment = (AlbumSelectedFragment) getSupportFragmentManager()
        .findFragmentById(R.id.media_selected);
    Objects.requireNonNull(mAlbumSelectedFragment).setProject(mProject);

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
}
