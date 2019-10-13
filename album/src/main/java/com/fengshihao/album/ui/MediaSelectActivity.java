package com.fengshihao.album.ui;

import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.fengshihao.album.R;

public class MediaSelectActivity extends AppCompatActivity {

  private static final String TAG = "MediaSelectActivity";

  @Nullable
  private AlbumFragment mAlbumFragment;

  @Nullable
  private AlbumSelectedFragment mAlbumSelectedFragment;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_media_select);

    mAlbumFragment = (AlbumFragment) getSupportFragmentManager()
        .findFragmentById(R.id.media_list);
    mAlbumSelectedFragment = (AlbumSelectedFragment) getSupportFragmentManager()
        .findFragmentById(R.id.media_selected);
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
