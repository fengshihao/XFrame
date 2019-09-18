package com.fengshihao.album.logic;

import android.support.annotation.NonNull;
import android.util.Log;

import java.util.ArrayList;


public final class Util {
  private Util() {
  }

  public static void logFirstAndLast(@NonNull String tag,
                                     @NonNull String function,
                                     @NonNull ArrayList<AlbumItem> list) {
    if (list.isEmpty()) {
      Log.d(tag, function + " empty list");
      return;
    }
    Log.d(tag, function + " list size=" + list.size()
        + " first=" + list.get(0) + " last="
        + list.get(list.size() - 1));
  }
}
