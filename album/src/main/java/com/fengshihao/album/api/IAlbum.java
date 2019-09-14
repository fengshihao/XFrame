package com.fengshihao.album.api;

import android.app.Activity;
import android.support.annotation.NonNull;

import com.fengshihao.xframe.logic.XModule;

public interface IAlbum extends XModule {


  void startActivity(@NonNull Activity fromActivity);
}
