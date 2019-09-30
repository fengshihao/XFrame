package com.fengshihao.album.api;

import android.app.Activity;
import android.support.annotation.NonNull;

import com.fengshihao.xframe.logic.IXModule;

public interface IAlbumAPI extends IXModule {

  void startActivity(@NonNull Activity fromActivity);
}
